package org.xbib.io.iso23950;

import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.io.InputStreamBERReader;
import org.xbib.asn1.io.OutputStreamBERWriter;
import org.xbib.cql.CQLParser;
import org.xbib.io.iso23950.cql.CQLRPNGenerator;
import org.xbib.io.iso23950.operations.InitOperation;
import org.xbib.io.iso23950.operations.PresentOperation;
import org.xbib.io.iso23950.operations.SearchOperation;
import org.xbib.io.iso23950.pqf.PQFParser;
import org.xbib.io.iso23950.pqf.PQFRPNGenerator;
import org.xbib.io.iso23950.v3.Close;
import org.xbib.io.iso23950.v3.CloseReason;
import org.xbib.io.iso23950.v3.PDU;
import org.xbib.io.iso23950.v3.RPNQuery;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default Z client.
 */
public class ZClient implements AutoCloseable {

    private static final Logger logger = Logger.getLogger(ZClient.class.getName());

    private final String host;

    private final int port;

    private final String user;

    private final String pass;

    private final long timeout;

    private final String preferredRecordSyntax;

    private final String resultSetName;

    private final String elementSetName;

    private final String encoding;

    private final String format;

    private final String type;

    private final List<String> databases;

    private final Integer preferredMessageSize;

    private final InitListener initListener;

    private final Lock lock;

    private Socket socket;

    private InputStreamBERReader berReader;

    private OutputStreamBERWriter berWriter;

    private ZClient(String host, int port, String user, String pass, long timeout,
                   String preferredRecordSyntax,
                   String resultSetName,
                   String elementSetName,
                   String encoding,
                   String format,
                   String type,
                   List<String> databases,
                   Integer preferredMessageSize,
                   InitListener initListener) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.timeout = timeout;
        this.preferredRecordSyntax = preferredRecordSyntax;
        this.resultSetName = resultSetName;
        this.elementSetName = elementSetName;
        this.encoding = encoding;
        this.format = format;
        this.type = type;
        this.databases = databases;
        this.preferredMessageSize = preferredMessageSize;
        this.initListener = initListener;
        this.lock = new ReentrantLock();
    }

    @Override
    public void close() {
        if (isConnected()) {
            try {
                lock.lock();
                try {
                    sendClose(0);
                } catch (IOException e) {
                    logger.log(Level.WARNING, "while attempting to send close for close connection: " + e.getMessage(), e);
                }
                try {
                    berReader.close();
                } catch (IOException e) {
                    logger.log(Level.WARNING, "error attempting to close src: " + e.getMessage(), e);
                }
                try {
                    berWriter.close();
                } catch (IOException e) {
                    logger.log(Level.WARNING, "error attempting to close dest: " + e.getMessage(), e);
                }
                try {
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    logger.log(Level.WARNING, "error attempting to close socket: " + e.getMessage(), e);
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public int executeCQL(String query, int offset, int length,
                           ResponseListener responseListener,
                           RecordListener recordListener) throws IOException {
        if (query == null) {
            throw new IllegalArgumentException("no query");
        }
        ensureConnected();
        try {
            lock.lock();
            SearchOperation searchOperation = new SearchOperation(berReader, berWriter, resultSetName, databases, host);
            boolean success = searchOperation.execute(createRPNQueryFromCQL(query));
            if (!success) {
                logger.log(Level.WARNING, MessageFormat.format("search was not a success [{0}]", query));
            } else {
                if (responseListener == null) {
                    responseListener = (status, total, returned, elapsedMillis) -> {
                        logger.log(Level.INFO, MessageFormat.format("[{0}ms] [{1}] [{2}] [{3}]",
                                elapsedMillis, total, returned, query));
                    };
                }
                if (searchOperation.getCount() > 0) {
                    PresentOperation present = new PresentOperation(berReader, berWriter,
                            resultSetName, elementSetName, preferredRecordSyntax);
                    if (offset < 1) {
                        // Z39.50 present bails out when offset = 0
                        offset = 1;
                    }
                    if (length > searchOperation.getCount()) {
                        // avoid condition 13 "Present request out-of-range"
                        length = searchOperation.getCount();
                    }
                    present.execute(offset, length, searchOperation.getCount(), responseListener, recordListener);
                }
            }
            return searchOperation.getCount();
        } finally {
            lock.unlock();
        }
    }

    public int executePQF(String query, int offset, int length,
                           ResponseListener responseListener,
                           RecordListener recordListener) throws IOException {
        if (query == null) {
            throw new IllegalArgumentException("no query");
        }
        ensureConnected();
        try {
            lock.lock();
            SearchOperation search = new SearchOperation(berReader, berWriter, resultSetName, databases, host);
            search.execute(createRPNQueryFromPQF(query));
            if (!search.isSuccess()) {
                logger.log(Level.WARNING, MessageFormat.format("search was not a success [{0}]", query));
            } else {
                if (responseListener == null) {
                    responseListener = (status, total, returned, elapsedMillis) -> {
                        logger.log(Level.INFO, MessageFormat.format("[{0}ms] [{1}] [{2}] [{3}]",
                                elapsedMillis, total, returned, query));
                    };
                }
                if (search.getCount() > 0) {
                    logger.log(Level.INFO, "search returned " + search.getCount());
                    PresentOperation present = new PresentOperation(berReader, berWriter,
                            resultSetName, elementSetName, preferredRecordSyntax);
                    if (offset < 1) {
                        // Z39.50 bails out when offset = 0
                        offset = 1;
                    }
                    if (length > search.getCount()) {
                        // avoid condition 13 "Present request out-of-range"
                        length = search.getCount();
                    }
                    present.execute(offset, length, search.getCount(), responseListener, recordListener);
                }
            }
            return search.getCount();
        } finally {
            lock.unlock();
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public long getTimeout() {
        return timeout;
    }

    public String getPreferredRecordSyntax() {
        return preferredRecordSyntax;
    }

    public String getResultSetName() {
        return resultSetName;
    }

    public String getElementSetName() {
        return elementSetName;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getFormat() {
        return format;
    }

    public String getType() {
        return type;
    }

    public List<String> getDatabases() {
        return databases;
    }

    private RPNQuery createRPNQueryFromCQL(String query) {
        CQLRPNGenerator generator = new CQLRPNGenerator();
        CQLParser parser = new CQLParser(query);
        parser.parse();
        parser.getCQLQuery().accept(generator);
        return generator.getQueryResult();
    }


    private RPNQuery createRPNQueryFromPQF(String query) {
        PQFRPNGenerator generator = new PQFRPNGenerator();
        PQFParser parser = new PQFParser(new StringReader(query));
        parser.parse();
        parser.getResult().accept(generator);
        return generator.getResult();
    }

    /**
     * Send a close request to the server.
     *
     * @param reason reason Reason codes are:
     * 0=finished 1=shutdown 2=system problem 3=cost limits
     * 4=resources 5=security violation 6=protocol error 7=lack of activity
     * 8=peer abort 9=unspecified
     * @throws IOException if close fails
     */
    private void sendClose(int reason) throws IOException {
        if (!isConnected()) {
            return;
        }
        try {
            lock.lock();
            PDU pdu = new PDU();
            pdu.c_close = new Close();
            pdu.c_close.sCloseReason = new CloseReason();
            pdu.c_close.sCloseReason.value = new ASN1Integer(reason);
            pdu.c_close.sReferenceId = null;
            try {
                berWriter.write(pdu.berEncode());
            } catch (ASN1Exception ex) {
                throw new IOException(ex);
            }
            // do not wait, it may hang
            //waitClosePDU();
        } finally {
            lock.unlock();
        }
    }


    private void connect() throws IOException {
        try {
            lock.lock();
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), (int) timeout);
            socket.setSoTimeout((int) timeout * 1000);
            this.socket = socket;
            InputStream src = new BufferedInputStream(socket.getInputStream());
            OutputStream dest = new BufferedOutputStream(socket.getOutputStream());
            this.berReader = new InputStreamBERReader(src);
            this.berWriter = new OutputStreamBERWriter(dest);
            InitOperation initOperation = new InitOperation(berReader, berWriter, user, pass);
            if (initOperation.execute(preferredMessageSize, initListener)) {
                throw new IOException("could not initiate connection");
            }
        } finally {
            lock.unlock();
        }
    }

    private boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    private void ensureConnected() throws IOException {
        if (!isConnected()) {
            connect();
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     *
     */
    public static class Builder {

        private static final ResourceBundle recordSyntaxBundle =
                ResourceBundle.getBundle("org.xbib.io.iso23950.recordsyntax");

        private String host;

        private int port;

        private String user;

        private String pass;

        private long timeout;

        private String preferredRecordSyntax = "1.2.840.10003.5.10"; // marc21

        private String resultSetName = "default";

        private String elementSetName = "F";

        private String encoding = "ANSEL";

        private String format = "MARC21";

        private String type = "Bibliographic";

        private List<String> databases = Collections.singletonList("");

        private Integer preferredMessageSize = 1024 * 1024;

        private InitListener initListener;

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder setUser(String user) {
            this.user = user;
            return this;
        }

        public Builder setPass(String pass) {
            this.pass = pass;
            return this;
        }

        public Builder setTimeout(long timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder setPreferredRecordSyntax(String preferredRecordSyntax) {
            this.preferredRecordSyntax = preferredRecordSyntax;
            if (recordSyntaxBundle.containsKey(preferredRecordSyntax)) {
                this.preferredRecordSyntax = recordSyntaxBundle.getString(preferredRecordSyntax);
            }
            return this;
        }

        public Builder setResultSetName(String resultSetName) {
            this.resultSetName = resultSetName;
            return this;
        }

        public Builder setElementSetName(String elementSetName) {
            this.elementSetName = elementSetName;
            return this;
        }

        public Builder setEncoding(String encoding) {
            this.encoding = encoding;
            return this;
        }

        public Builder setFormat(String format) {
            this.format = format;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setDatabases(List<String> databases) {
            this.databases = databases;
            return this;
        }

        public Builder setPreferredMessageSize(int preferredMessageSize) {
            this.preferredMessageSize = preferredMessageSize;
            return this;
        }

        public Builder setInitListener(InitListener initListener) {
            this.initListener = initListener;
            return this;
        }

        public ZClient build() {
            return new ZClient(host, port, user, pass, timeout,
                    preferredRecordSyntax,
                    resultSetName,
                    elementSetName,
                    encoding,
                    format,
                    type,
                    databases,
                    preferredMessageSize,
                    initListener);
        }
    }
}
