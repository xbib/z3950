package org.xbib.asn1.io;

import org.xbib.asn1.BEREncoding;

import java.io.IOException;

public interface BERWriter extends AutoCloseable {

    void write(BEREncoding ber) throws IOException;

    void close() throws IOException;
}
