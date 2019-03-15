package org.xbib.z3950.api;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public interface Client extends Closeable {

    int searchCQL(String query, int offset, int length,
                         SearchListener searchListener,
                         RecordListener recordListener) throws IOException;

    int searchPQF(String query, int offset, int length,
                  SearchListener searchListener,
                  RecordListener recordListener) throws IOException;

    void scanPQF(String query, int nTerms, int step, int position,
                 ScanListener scanListener) throws IOException;

    String getHost();

    int getPort();

    String getUser();

    String getPass();

    long getTimeout();

    String getPreferredRecordSyntax();

    String getResultSetName();

    String getElementSetName();

    String getEncoding();

    String getFormat();

    String getType();

    List<String> getDatabases();

}
