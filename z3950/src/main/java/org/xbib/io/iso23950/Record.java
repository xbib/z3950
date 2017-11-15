package org.xbib.io.iso23950;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * A record for Z39.50 presentations.
 */
public class Record {

    private final int number;

    private final byte[] content;

    private final ByteArrayInputStream stream;

    public Record(int number, byte[] content) {
        this.number = number;
        this.content = content;
        this.stream = new ByteArrayInputStream(content);
    }

    public int getNumber() {
        return number;
    }

    public InputStream asStream() {
        return stream;
    }

    public String toString(Charset charset) {
        return new String(content, charset);
    }
}
