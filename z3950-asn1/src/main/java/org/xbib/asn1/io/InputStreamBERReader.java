package org.xbib.asn1.io;

import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;
import org.xbib.asn1.BERPrimitive;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class InputStreamBERReader implements BERReader {

    private static final String ERROR = "Unexpected end in BER encoding";

    private static final int MAX_BER_SIZE = 10 * 1024 * 1024;

    private final InputStream inputStream;

    public InputStreamBERReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * The public wrapping for doInput() method.
     *
     * @return returns the next complete BEREncoding object read
     * in from the input stream. Returns null if the
     * end has been reached.
     * @throws ASN1Exception If data does not represent a BER encoding
     * @throws IOException   On input I/O error
     */
    @Override
    public BEREncoding read() throws IOException {
        int[] numBytesRead = new int[1];
        numBytesRead[0] = 0;
        return doInput(numBytesRead);
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }

    /**
     * Constructs a complete BER encoding object from octets read in from
     * an InputStream.
     * This routine handles all forms of encoding, including the
     * indefite-length method. The length is always known with this
     * class. With indefinite-length encodings,
     * the end-of-contents octets are not included in the returned
     * object (i.e. the returned the raw BER is converted to an object
     * which is in the definite-length form).
     *
     * @param numBytesRead a counter for all read bytes.
     * @return the next complete BEREncoding object read
     * in from the input stream. Returns null if the
     * end has been reached.
     * @throws IOException   If data does not represent a BER encoding or input I/O error
     */
    private BEREncoding doInput(int[] numBytesRead) throws IOException {
        int octet = inputStream.read();
        if (octet < 0) {
            return null;
        }
        numBytesRead[0]++;
        int tagType = octet & 0xC0;
        boolean isCons = false;
        if ((octet & 0x20) != 0) {
            isCons = true;
        }
        int tag = octet & 0x1F;
        if (tag == 0x1F) {
            tag = 0;
            do {
                octet = inputStream.read();
                if (octet < 0) {
                    throw new ASN1EncodingException(ERROR);
                }
                numBytesRead[0]++;
                tag <<= 7;
                tag |= (octet & 0x7F);
            } while ((octet & 0x80) != 0);
        }
        int length;
        octet = inputStream.read();
        if (octet < 0) {
            throw new ASN1EncodingException(ERROR);
        }
        numBytesRead[0]++;
        if ((octet & 0x80) != 0) {
            if ((octet & 0x7f) == 0) {
                length = -1;
                if (!isCons) {
                    throw new ASN1EncodingException("BER encoding corrupted primitive");
                }
            } else {
                if (4 < (octet & 0x7f)) {
                    throw new ASN1EncodingException("BER encoding too long");
                }
                length = 0;
                for (int numBytes = octet & 0x7f; 0 < numBytes; numBytes--) {
                    octet = inputStream.read();
                    if (octet < 0) {
                        throw new ASN1EncodingException(ERROR);
                    }
                    numBytesRead[0]++;
                    length <<= 8;
                    length |= (octet & 0xff);
                }
                if (length < 0 || MAX_BER_SIZE < length) {
                    throw new ASN1EncodingException("BER encoding too long");
                }
            }
        } else {
            length = octet & 0x7F;
        }
        if (!isCons) {
            int[] contents = new int[length];
            for (int x = 0; x < length; x++) {
                octet = inputStream.read();
                if (octet < 0) {
                    throw new ASN1EncodingException(ERROR);
                }
                numBytesRead[0]++;
                contents[x] = octet;
            }
            return new BERPrimitive(tagType, tag, contents);
        } else {
            List<BEREncoding> chunks = new ArrayList<>();
            int totalRead = 0;
            if (0 <= length) {
                while (totalRead < length) {
                    int currentRead = numBytesRead[0];
                    BEREncoding chunk = doInput( numBytesRead);
                    if (chunk == null) {
                        throw new ASN1EncodingException(ERROR);
                    }
                    chunks.add(chunk);
                    totalRead += numBytesRead[0] - currentRead;
                }
            } else {
                while (true) {
                    BEREncoding chunk = doInput(numBytesRead);
                    if (chunk == null) {
                        throw new ASN1EncodingException(ERROR);
                    }
                    if (chunk.getTag() == 0 && chunk.getTagType() == BEREncoding.UNIVERSAL_TAG &&
                            chunk.getTotalLength() == 2) {
                        break;
                    } else {
                        chunks.add(chunk);
                    }
                }
            }
            int numElements = chunks.size();
            BEREncoding[] parts = new BEREncoding[numElements];
            for (int x = 0; x < numElements; x++) {
                parts[x] = chunks.get(x);
            }
            return new BERConstructed(tagType, tag, parts);
        }
    }
}
