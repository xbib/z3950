package org.xbib.z3950.api;

import java.io.IOException;

@FunctionalInterface
public interface SearchListener {

    void onResponse(int status, int total, int returned, long elapsedMillis) throws IOException;
}
