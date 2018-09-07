package org.xbib.asn1.io;

import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;
import org.xbib.asn1.BERPrimitive;

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamBERWriter implements BERWriter {

    private final OutputStream outputStream;

    private final boolean autoflush;

    public OutputStreamBERWriter(OutputStream outputStream) {
        this(outputStream, true);
    }

    public OutputStreamBERWriter(OutputStream outputStream, boolean autoflush) {
        this.outputStream = outputStream;
        this.autoflush = autoflush;
    }

    /**
     * Outputs the BER object to an OutputStream. This method should work
     * with any OutputStream, whether it is from a socket, file, etc.
     * Note: the output is not flushed, so you <strong>must</strong>  explicitly
     * flush the output stream after calling this method to ensure that
     * the data has been written out.
     *
     * @throws IOException On output I/O error
     */
    @Override
    public void write(BEREncoding ber) throws IOException {
        if (ber instanceof BERPrimitive) {
            writeBERPrimitive((BERPrimitive) ber);
        } else if (ber instanceof BERConstructed) {
            writeBERConstructed((BERConstructed) ber);
        }
        if (autoflush) {
            outputStream.flush();
        }
    }

    @Override
    public void close() throws IOException {
        outputStream.close();
    }

    /**
     * This method outputs the encoded octets for this object
     * to the output stream.
     * Note: the output is not flushed, so you <strong>must</strong>  explicitly
     * flush the output stream after calling this method to ensure that
     * the data has been written out.
     */
    private void writeBERConstructed(BERConstructed ber) throws IOException {
        outputHead(ber);
        for (BEREncoding contentElement : ber.getContentElements()) {
            write(contentElement);
        }
    }

    /**
     * This method outputs the encoded octets to the destination OutputStream.
     * Note: the output is not flushed, so you <strong>must</strong>  explicitly
     * flush the output stream after calling this method to ensure that
     * the data has been written out.
     */
    private void writeBERPrimitive(BERPrimitive ber) throws IOException {
        outputHead(ber);
        outputBytes(ber.getContentOctets());
    }

    /*
     * This is a protected method used to output the encoded identifier
     * and length octets. It is used by the superclasses
     * to implement the "output" method.
     */
    private void outputHead(BEREncoding ber) throws IOException {
        outputBytes(ber.getIdentifierEncoding());
        outputBytes(ber.getLengthEncoding());
    }

    /*
     * This is a protected routine used for outputting an array of
     * integers, interpreted as bytes, to an OutputStream. It is used
     * by the superclasses to implement the "output" method.
     */
    private void outputBytes(int[] data) throws IOException {
        for (int aData : data) {
            outputStream.write(aData);
        }
    }
}
