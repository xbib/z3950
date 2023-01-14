package org.xbib.z3950.common;

import java.nio.charset.StandardCharsets;
import org.xbib.z3950.api.Record;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * A record for Z39.50 presentations.
 */
public class DefaultRecord implements Record {

    private final int number;

    private final byte[] content;

    private final ByteArrayInputStream stream;

    public DefaultRecord(int number, byte[] content) {
        this.number = number;
        this.content = content;
        this.stream = new ByteArrayInputStream(content);
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public InputStream asStream() {
        return stream;
    }

    @Override
    public String toString(Charset charset) {
        return new String(content, charset);
    }

    @Override
    public String toString() {
        return toString(StandardCharsets.UTF_8);
    }
}
