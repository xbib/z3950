package org.xbib.io.iso23950;

import java.io.IOException;

/**
 *
 */
@FunctionalInterface
public interface SearchListener {

    void onResponse(int status, int total, int returned, long elapsedMillis) throws IOException;
}
