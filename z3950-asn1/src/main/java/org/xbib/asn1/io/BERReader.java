package org.xbib.asn1.io;

import org.xbib.asn1.BEREncoding;

import java.io.IOException;

public interface BERReader extends AutoCloseable {

    BEREncoding read() throws IOException;

    void close() throws IOException;
}
