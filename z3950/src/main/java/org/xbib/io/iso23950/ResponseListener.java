package org.xbib.io.iso23950;

import java.io.IOException;

/**
 *
 */
@FunctionalInterface
public interface ResponseListener {

    void onResponse(int status, int total, int returned, long elapsedMillis) throws IOException;
}
