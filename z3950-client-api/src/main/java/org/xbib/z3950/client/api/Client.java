package org.xbib.z3950.client.api;

import org.xbib.z3950.api.RecordListener;
import org.xbib.z3950.api.ScanListener;
import org.xbib.z3950.api.SearchListener;
import org.xbib.z3950.api.TimeoutListener;
import org.xbib.z3950.common.operations.SortOperation;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public interface Client extends Closeable {

    int searchCQL(String query,
                  int offset,
                  int length,
                  List<SortOperation.SortParameter> parameters,
                  SearchListener searchListener,
                  RecordListener recordListener,
                  TimeoutListener timeoutListener) throws IOException;

    int searchPQF(String query,
                  int offset,
                  int length,
                  List<SortOperation.SortParameter> parameters,
                  SearchListener searchListener,
                  RecordListener recordListener,
                  TimeoutListener timeoutListener) throws IOException;

    void scanPQF(String query, int nTerms, int step, int position,
                 ScanListener scanListener,
                 TimeoutListener timeoutListener) throws IOException;

    void sort(String referenceId,
              List<SortOperation.SortParameter> parameters,
              TimeoutListener timeoutListener) throws IOException;

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
