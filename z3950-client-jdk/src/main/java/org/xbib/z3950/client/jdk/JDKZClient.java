package org.xbib.z3950.client.jdk;

import java.nio.charset.StandardCharsets;
import org.xbib.asn1.io.InputStreamBERReader;
import org.xbib.asn1.io.OutputStreamBERWriter;
import org.xbib.z3950.api.TimeoutListener;
import org.xbib.z3950.common.operations.CloseOperation;
import org.xbib.z3950.common.operations.InitOperation;
import org.xbib.z3950.common.operations.PresentOperation;
import org.xbib.z3950.common.operations.ScanOperation;
import org.xbib.z3950.common.operations.SearchOperation;
import org.xbib.z3950.client.api.Client;
import org.xbib.z3950.api.InitListener;
import org.xbib.z3950.api.RecordListener;
import org.xbib.z3950.api.ScanListener;
import org.xbib.z3950.api.SearchListener;
import org.xbib.z3950.common.operations.SortOperation;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
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
public class JDKZClient implements Client, Closeable {

    private static final Logger logger = Logger.getLogger(JDKZClient.class.getName());

    private final Builder builder;

    private final Lock lock;

    private Socket socket;

    private InputStreamBERReader berReader;

    private OutputStreamBERWriter berWriter;

    private JDKZClient(Builder builder) {
        this.builder = builder;
        this.lock = new ReentrantLock();
    }

    @Override
    public int searchCQL(String query,
                         int offset,
                         int length,
                         List<SortOperation.SortParameter> sortParameters,
                         SearchListener searchListener,
                         RecordListener recordListener,
                         TimeoutListener timeoutListener) throws IOException {
        if (query == null) {
            throw new IllegalArgumentException("no query");
        }
        ensureConnected();
        try {
            lock.lock();
            SearchOperation searchOperation = new SearchOperation(berReader, berWriter,
                    builder.resultSetName, builder.databases, builder.host);
            boolean success = searchOperation.executeCQL(query);
            if (!success) {
                logger.log(Level.WARNING, MessageFormat.format("search was not a success [{0}]", query));
            } else {
                if (searchListener == null) {
                    searchListener = (status, total, returned, elapsedMillis) -> {
                        logger.log(Level.INFO, MessageFormat.format("[{0}ms] [{1}] [{2}] [{3}]",
                                elapsedMillis, total, returned, query));
                    };
                }
                if (searchOperation.getCount() > 0) {
                    logger.log(Level.FINE, "search returned " + searchOperation.getCount());
                    String resultSetName = builder.resultSetName;
                    if (sortParameters != null && !sortParameters.isEmpty()) {
                        SortOperation sort = new SortOperation(berReader, berWriter);
                        boolean sortSuccess = sort.execute("sort-ref", resultSetName, resultSetName + "-sort",
                                sortParameters);
                        logger.log(Level.FINE, "sort returned " + sortSuccess);
                        if (sortSuccess) {
                            resultSetName = resultSetName + "-sort";
                        }
                    }
                    PresentOperation present = new PresentOperation(berReader, berWriter,
                            resultSetName, builder.elementSetName, builder.preferredRecordSyntax);
                    if (offset < 1) {
                        // Z39.50 present bails out when offset = 0
                        offset = 1;
                    }
                    present.execute(offset, length, searchOperation.getCount(), searchListener, recordListener);
                }
            }
            return searchOperation.getCount();
        } catch (SocketTimeoutException e) {
            if (timeoutListener != null) {
                timeoutListener.onTimeout();
            }
            return 0;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int searchPQF(String query,
                         int offset,
                         int length,
                         List<SortOperation.SortParameter> sortParameters,
                         SearchListener searchListener,
                         RecordListener recordListener,
                         TimeoutListener timeoutListener) throws IOException {
        if (query == null) {
            throw new IllegalArgumentException("no query");
        }
        ensureConnected();
        try {
            lock.lock();
            SearchOperation searchOperation = new SearchOperation(berReader, berWriter,
                    builder.resultSetName, builder.databases, builder.host);
            searchOperation.executePQF(query, StandardCharsets.UTF_8);
            if (!searchOperation.isSuccess()) {
                logger.log(Level.WARNING, MessageFormat.format("search was not a success [{0}]", query));
            } else {
                if (searchListener == null) {
                    searchListener = (status, total, returned, elapsedMillis) -> {
                        logger.log(Level.INFO, MessageFormat.format("[{0}ms] [{1}] [{2}] [{3}]",
                                elapsedMillis, total, returned, query));
                    };
                }
                if (searchOperation.getCount() > 0) {
                    logger.log(Level.FINE, "search returned " + searchOperation.getCount());
                    String resultSetName = builder.resultSetName;
                    if (sortParameters != null && !sortParameters.isEmpty()) {
                        SortOperation sort = new SortOperation(berReader, berWriter);
                        boolean sortSuccess = sort.execute("sort-ref", resultSetName, resultSetName + "-sort",
                                sortParameters);
                        logger.log(Level.FINE, "sort returned " + sortSuccess);
                        if (sortSuccess) {
                            resultSetName = resultSetName + "-sort";
                        }
                    }
                    PresentOperation present = new PresentOperation(berReader, berWriter,
                            resultSetName, builder.elementSetName, builder.preferredRecordSyntax);
                    if (offset < 1) {
                        // Z39.50 bails out when offset = 0
                        offset = 1;
                    }
                    if (length > searchOperation.getCount()) {
                        // avoid condition 13 "Present request out-of-range"
                        length = searchOperation.getCount();
                    }
                    present.execute(offset, length, searchOperation.getCount(), searchListener, recordListener);
                }
            }
            return searchOperation.getCount();
        } catch (SocketTimeoutException e) {
            if (timeoutListener != null) {
                timeoutListener.onTimeout();
            }
            return 0;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void scanPQF(String query,
                        int nTerms,
                        int step,
                        int position,
                        ScanListener scanListener,
                        TimeoutListener timeoutListener) throws IOException {
        ensureConnected();
        try {
            lock.lock();
            ScanOperation scanOperation = new ScanOperation(berReader, berWriter, builder.databases);
            scanOperation.executePQF(nTerms, step, position, query, scanListener);
        } catch (SocketTimeoutException e) {
            if (timeoutListener != null) {
                timeoutListener.onTimeout();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void sort(String referenceId,
                     List<SortOperation.SortParameter> parameters,
                     TimeoutListener timeoutListener) throws IOException {
        ensureConnected();
        try {
            lock.lock();
            SortOperation sortOperation = new SortOperation(berReader, berWriter);
            sortOperation.execute(referenceId, getResultSetName(), getResultSetName() + "-sort", parameters);
        } catch (SocketTimeoutException e) {
            if (timeoutListener != null) {
                timeoutListener.onTimeout();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String getHost() {
        return builder.host;
    }

    @Override
    public int getPort() {
        return builder.port;
    }

    @Override
    public String getUser() {
        return builder.user;
    }

    @Override
    public String getPass() {
        return builder.pass;
    }

    @Override
    public long getTimeout() {
        return builder.timeout;
    }

    @Override
    public String getPreferredRecordSyntax() {
        return builder.preferredRecordSyntax;
    }

    @Override
    public String getResultSetName() {
        return builder.resultSetName;
    }

    @Override
    public String getElementSetName() {
        return builder.elementSetName;
    }

    @Override
    public String getEncoding() {
        return builder.encoding;
    }

    @Override
    public String getFormat() {
        return builder.format;
    }

    @Override
    public String getType() {
        return builder.type;
    }

    @Override
    public List<String> getDatabases() {
        return builder.databases;
    }

    public void connect() throws IOException {
        try {
            lock.lock();
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(builder.host, builder.port), (int) builder.timeout); // in milliseconds
            socket.setSoTimeout((int) builder.timeout); // timeout in milliseconds
            this.socket = socket;
            InputStream src = new BufferedInputStream(socket.getInputStream());
            OutputStream dest = new BufferedOutputStream(socket.getOutputStream());
            this.berReader = new InputStreamBERReader(src);
            this.berWriter = new OutputStreamBERWriter(dest);
            InitOperation initOperation = new InitOperation(berReader, berWriter, builder.user, builder.pass);
            if (initOperation.execute(builder.preferredMessageSize,
                    builder.implementationName, builder.implementationVersion, builder.initListener)) {
                throw new IOException("could not initiate connection");
            }
            logger.log(Level.INFO, initOperation.getTargetInfo());
        } finally {
            lock.unlock();
        }
    }

    public void disconnect() {
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

    @Override
    public void close() {
        if (isConnected()) {
            disconnect();
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    private void ensureConnected() throws IOException {
        if (!isConnected()) {
           connect();
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
    private void sendClose(int reason) throws IOException {
        if (!isConnected()) {
            return;
        }
        try {
            lock.lock();
            CloseOperation closeOperation = new CloseOperation(berReader, berWriter);
            closeOperation.execute(reason);
        } finally {
            lock.unlock();
        }
    }

    /**
     *
     */
    public static class Builder {

        private static final ResourceBundle recordSyntaxBundle =
                ResourceBundle.getBundle("org.xbib.z3950.common.recordsyntax");

        private String host;

        private int port;

        private String user;

        private String pass;

        private long timeout;

        private String preferredRecordSyntax;

        private String resultSetName;

        private String elementSetName;

        private String encoding;

        private String format;

        private String type;

        private List<String> databases;

        private Integer preferredMessageSize;

        private String implementationName;

        private String implementationVersion;

        private InitListener initListener;

        private Builder() {
            this.timeout = 5000;
            this.preferredRecordSyntax = "1.2.840.10003.5.10"; // marc21
            this.resultSetName = "default";
            this.encoding = "ANSEL";
            this.format = "MARC21";
            this.type = "Bibliographic";
            this.databases = Collections.singletonList("");
            this.preferredMessageSize = 10 * 1024 * 1024;
            this.implementationName = "Java Z Client";
            this.implementationVersion = "1.00";
        }

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

        public Builder setImplementationName(String implementationName) {
            this.implementationName = implementationName;
            return this;
        }

        public Builder setImplementationVersion(String implementationVersion) {
            this.implementationVersion = implementationVersion;
            return this;
        }

        public Builder setInitListener(InitListener initListener) {
            this.initListener = initListener;
            return this;
        }

        public JDKZClient build() {
            return new JDKZClient(this);
        }
    }
}
