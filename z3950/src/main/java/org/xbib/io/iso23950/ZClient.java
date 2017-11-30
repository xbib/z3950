package org.xbib.io.iso23950;

import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.BEREncoding;
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
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
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

    private final Socket socket;

    private final BufferedInputStream src;

    private final BufferedOutputStream dest;

    public ZClient(String host, int port, String user, String pass, long timeout,
                   String preferredRecordSyntax,
                   String resultSetName,
                   String elementSetName,
                   String encoding,
                   String format,
                   String type,
                   List<String> databases,
                   Integer preferredMessageSize,
                   InitListener initListener) throws IOException {
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
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), (int) timeout);
        socket.setSoTimeout((int) timeout * 1000);
        this.socket = socket;
        this.src = new BufferedInputStream(socket.getInputStream());
        this.dest = new BufferedOutputStream(socket.getOutputStream());
        // always send init operation after socket init
        InitOperation init = new InitOperation();
        if (init.execute(this, preferredMessageSize, initListener)) {
            throw new IOException("could not initiatie connection");
        }
    }

    public static ZClient newZClient(String name) throws IOException {
        return newZClient(getProperties(name));
    }

    public static Properties getProperties(String name) throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream =
                     ZClient.class.getResourceAsStream("/org/xbib/io/iso23950/service/" + name + ".properties")) {
            properties.load(inputStream);
        }
        return properties;
    }

    public static ZClient newZClient(Properties properties) throws IOException {
        Builder builder = builder();
        if (properties.containsKey("host")) {
            builder.setHost(properties.getProperty("host"));
        }
        if (properties.containsKey("port")) {
            builder.setPort(Integer.parseInt(properties.getProperty("port")));
        }
        if (properties.containsKey("user")) {
            builder.setUser(properties.getProperty("user"));
        }
        if (properties.containsKey("pass")) {
            builder.setPass(properties.getProperty("pass"));
        }
        if (properties.containsKey("database")) {
            builder.setDatabases(Collections.singletonList(properties.getProperty("database")));
        }
        if (properties.containsKey("elementsetname")) {
            builder.setElementSetName(properties.getProperty("elementsetname"));
        }
        if (properties.containsKey("preferredrecordsyntax")) {
            builder.setPreferredRecordSyntax(properties.getProperty("preferredrecordsyntax"));
        }
        if (properties.containsKey("resultsetname")) {
            builder.setResultSetName(properties.getProperty("resultsetname"));
        }
        if (properties.containsKey("encoding")) {
            builder.setEncoding(properties.getProperty("encoding"));
        }
        if (properties.containsKey("format")) {
            builder.setFormat(properties.getProperty("format"));
        }
        if (properties.containsKey("type")) {
            builder.setType(properties.getProperty("type"));
        }
        return builder.build();
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    @Override
    public void close() throws IOException {
        if (isConnected()) {
            try {
                sendClose(0);
            } catch (IOException e) {
                logger.log(Level.WARNING, "while attempting to close connection: {}", e.getMessage());
            }
            try {
                if (src != null) {
                    src.close();
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "error attempting to close connection: {}", e.getMessage());
            }
            try {
                if (dest != null) {
                    dest.close();
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "error attempting to close connection: {}", e.getMessage());
            }
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "error attempting to close connection: {}", e.getMessage());
            }
        }
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
    public void sendClose(int reason) throws IOException {
        PDU pdu = new PDU();
        pdu.c_close = new Close();
        pdu.c_close.sCloseReason = new CloseReason();
        pdu.c_close.sCloseReason.value = new ASN1Integer(reason);
        pdu.c_close.sReferenceId = null;
        writePDU(pdu);
        // do not wait, it may hang
        //waitClosePDU();
    }

    public void writePDU(PDU pdu) throws IOException {
        if (dest == null) {
            throw new IOException("no output stream");
        }
        try {
            pdu.berEncode().output(dest);
            dest.flush();
        } catch (ASN1Exception ex) {
            throw new IOException(ex);
        }
    }

    public PDU readPDU() throws IOException {
        if (src == null) {
            throw new IOException("no input");
        }
        try {
            BEREncoding ber = BEREncoding.input(src);
            if (ber == null) {
                throw new IOException("read PDU error");
            }
            return new PDU(ber, true);
        } catch (ASN1Exception ex) {
            throw new IOException(ex);
        } catch (NullPointerException ex) {
            throw new IOException("connection read PDU error", ex);
        }
    }

    public int executeCQL(String query, int offset, int length,
                           ResponseListener responseListener,
                           RecordListener recordListener) throws IOException {
        if (query == null) {
            throw new IllegalArgumentException("no query");
        }
        SearchOperation search = new SearchOperation();
        boolean success = search.execute(this, createRPNQueryFromCQL(query));
        if (!success) {
            logger.log(Level.WARNING, MessageFormat.format("search was not a success [{0}]", query));
        } else {
            if (responseListener == null) {
                responseListener = (status, total, returned, elapsedMillis) -> {
                    logger.log(Level.INFO, MessageFormat.format("[{0}ms] [{1}] [{2}] [{3}]",
                            elapsedMillis, total, returned, query));
                };
            }
            if (search.getCount() > 0) {
                PresentOperation present = new PresentOperation();
                if (offset < 1) {
                    // Z39.50 present bails out when offset = 0
                    offset = 1;
                }
                if (length > search.getCount()) {
                    // avoid condition 13 "Present request out-of-range"
                    length = search.getCount();
                }
                present.execute(this, offset, length, search.getCount(), responseListener, recordListener);
            }
        }
        return search.getCount();
    }

    public int executePQF(String query, int offset, int length,
                           ResponseListener responseListener,
                           RecordListener recordListener) throws IOException {
        if (query == null) {
            throw new IllegalArgumentException("no query");
        }
        SearchOperation search = new SearchOperation();
        search.execute(this, createRPNQueryFromPQF(query));
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
                PresentOperation present = new PresentOperation();
                if (offset < 1) {
                    // Z39.50 bails out when offset = 0
                    offset = 1;
                }
                if (length > search.getCount()) {
                    // avoid condition 13 "Present request out-of-range"
                    length = search.getCount();
                }
                present.execute(this, offset, length, search.getCount(), responseListener, recordListener);
            }
        }
        return search.getCount();
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

    public RPNQuery createRPNQueryFromCQL(String query) throws IOException {
        CQLRPNGenerator generator = new CQLRPNGenerator();
        CQLParser parser = new CQLParser(query);
        parser.parse();
        parser.getCQLQuery().accept(generator);
        return generator.getQueryResult();
    }


    public RPNQuery createRPNQueryFromPQF(String query) throws IOException {
        PQFRPNGenerator generator = new PQFRPNGenerator();
        PQFParser parser = new PQFParser(new StringReader(query));
        parser.parse();
        parser.getResult().accept(generator);
        return generator.getResult();
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
            try {
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
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }
}
