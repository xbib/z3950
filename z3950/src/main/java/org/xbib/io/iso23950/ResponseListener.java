package org.xbib.io.iso23950;

import java.io.IOException;

/**
 *
 */
@FunctionalInterface
public interface ResponseListener {

    void onResponse(int status, int recordCount, long elapsedMillis) throws IOException;
}
