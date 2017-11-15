package org.xbib.asn1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a BER (Basic Encoding Rules) encoded ASN.1 object.
 * This is an abstract base class from which there are two specific
 * representations are used: primitive and constructed. This superclass
 * is tightly coupled with its subclasses BERPrimitive and BERConstructed.
 * The BER encoding is described in
 * <em>Information technology -
 * Open Systems Interconnection -
 * Specification of basic encoding rules for Abstract Syntax Notation
 * One (ASN.1)</em>
 * AS 3626-1991
 * ISO/IEC 8825:1990
 *
 * @see org.xbib.asn1.BERPrimitive
 * @see org.xbib.asn1.BERConstructed
 */
public abstract class BEREncoding {

    private static final String ERROR = "Unexpected end in BER encoding";

    /**
     * Constant for indicating UNIVERSAL tag type. The value matches
     * the BER bit encoding. Universal tags are for types defined in
     * the ASN.1 standard.
     */
    public static final int UNIVERSAL_TAG = 0x00;

    /**
     * Constant for indicating APPLICATION tag type. The value matches
     * the BER bit encoding. APPLICATION tags are globally unique to an
     * application.
     */
    public static final int APPLICATION_TAG = 0x40;

    /**
     * Constant for indicating CONTEXT SPECIFIC tag type. The value matches
     * the BER bit encoding. CONTEXT SPECIFIC tags are used in applications,
     * but do not have to be globally unique.
     */
    public static final int CONTEXT_SPECIFIC_TAG = 0x80;

    /**
     * Constant for indicating PRIVATE tag type. The value matches
     * the BER bit encoding.
     */
    public static final int PRIVATE_TAG = 0xC0;

    private static final int MAX_BER_SIZE = 65536 * 4;
    /**
     * The tag type of this BER encoded object. This value must be
     * the same as that encoded in the identiferEncoding.
     * This is an internal member. You should not use this.
     */
    protected int iTagType;
    /**
     * The tag number of this BER encoded object. This value must be
     * the same as that encoded in the identiferEncoding.
     * This is an internal member. You should not use this.
     */
    protected int iTag;
    /**
     * The total length of this BER object (the identifier octets, plus
     * length octets, plus content octects). This variable must be
     * set up before this object is used (using the init method).
     * This is an internal member. You should not use this.
     */
    protected int iTotalLength;
    /**
     * Storage for the identifier octets. This variable is set up by
     * calling the make_identifer method.
     * The octets are internally stored as int[] for efficiency over byte[].
     */
    private int[] identifierEncoding;
    /**
     * Storage for the length encoding octets. This will be set up by
     * calling the makeLength method.
     * The octets are internally stored as int[] for efficiency over byte[].
     */
    private int[] lengthEncoding;

    /**
     * The public wrapping for doInput() method.
     *
     * @param inputStream the InputStream to read the raw BER from.
     * @return Returns the next complete BEREncoding object read
     * in from the input stream. Returns null if the
     * end has been reached.
     * @throws ASN1Exception If data does not represent a BER encoding
     * @throws IOException   On input I/O error
     */
    public static BEREncoding input(InputStream inputStream) throws IOException {
        int[] numBytesRead = new int[1];
        numBytesRead[0] = 0;
        return doInput(inputStream, numBytesRead);
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
     * @param inputStream          the InputStream to read the raw BER from.
     * @return the next complete BEREncoding object read
     * in from the input stream. Returns null if the
     * end has been reached.
     * @throws IOException   If data does not represent a BER encoding or input I/O error
     */
    protected static BEREncoding doInput(InputStream inputStream, int[] numBytesRead) throws IOException {
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
                    BEREncoding chunk = BEREncoding.doInput(inputStream, numBytesRead);
                    if (chunk == null) {
                        throw new ASN1EncodingException(ERROR);
                    }
                    chunks.add(chunk);
                    totalRead += numBytesRead[0] - currentRead;
                }
            } else {
                while (true) {
                    BEREncoding chunk = BEREncoding.doInput(inputStream, numBytesRead);
                    if (chunk == null) {
                        throw new ASN1EncodingException(ERROR);
                    }
                    if (chunk.iTag == 0 && chunk.iTagType == BEREncoding.UNIVERSAL_TAG && chunk.iTotalLength == 2) {
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

    /**
     * Outputs the BER object to an OutputStream. This method should work
     * with any OutputStream, whether it is from a socket, file, etc.
     * Note: the output is not flushed, so you <strong>must</strong>  explicitly
     * flush the output stream after calling this method to ensure that
     * the data has been written out.
     *
     * @param dest - the OutputStream to write the encoding to.
     * @throws IOException On output I/O error
     */
    public abstract void output(OutputStream dest) throws IOException;

    /**
     * Returns the BER encoded object as an array of bytes. This routine
     * may be of use if you want to use the encoding rather than sending
     * it off. If you want to just output it, it is more efficient to
     * use the output method.
     * @return byte array
     */
    public byte[] encodingGet() {
        byte[] result = new byte[iTotalLength];
        iEncodingGet(0, result);
        return result;
    }

    /**
     * Method to examine the tag type of the BER encoded ASN.1 object.
     * @return integer
     */
    public int tagTypeGet() {
        return iTagType;
    }

    /**
     * Method to examine the tag number of the BER encoded ASN.1 object.
     * @return integer
     */
    public int tagGet() {
        return iTag;
    }

    /**
     * Returns the total number of bytes the encoding occupies.
     * @return integer
     */
    public int totalLength() {
        return iTotalLength;
    }

    /**
     * This is the initialization method used by the subclasses.
     * The length must be the total length of the encoding of the
     * contents (i.e. not including the identifier or length encodings).
     *
     * @param tagType       The tag type.
     * @param isConstructed True if constructed, or false if primitive.
     * @param tag           The tag number.
     * @param length length
     * @throws ASN1Exception if tag or tag type is invalid
     */
    protected void init(int tagType, boolean isConstructed, int tag, int length)
            throws ASN1Exception {
        makeIdentifier(tagType, isConstructed, tag);
        makeLength(length);
        iTotalLength = identifierEncoding.length + lengthEncoding.length + length;
    }

    /*
     * This is a protected routine used for outputting an array of
     * integers, interpreted as bytes, to an OutputStream. It is used
     * by the superclasses to implement the "output" method.
     */
    protected void outputBytes(int[] data, OutputStream dest) throws IOException {
        for (int aData : data) {
            dest.write(aData);
        }
    }

    /*
     * This is a protected method used to output the encoded identifier
     * and length octets to an OutputStream. It is used by the superclasses
     * to implement the "output" method.
     */
    protected void outputHead(OutputStream dest) throws IOException {
        outputBytes(identifierEncoding, dest);
        outputBytes(lengthEncoding, dest);
    }

    /*
     * Internal protected method fills in the data array (starting from index
     * position offset) with the encoding for the identifier and length.
     * This is used by the superclasses to implement the "encodingGet"
     * method.
     */
    protected int iGetHead(int offset, byte[] data) {
        for (int anIdentifierEncoding : identifierEncoding) {
            data[offset++] = (byte) anIdentifierEncoding;
        }
        for (int aLengthEncoding : lengthEncoding) {
            data[offset++] = (byte) aLengthEncoding;
        }
        return offset;
    }

    /*
     * This is an abstract method used for implementing the "getEncoding"
     * method. This method places the bytes of the encoding into the data
     * array (as bytes), starting at offset into the array. The
     * offset of the last element used plus one is returned.
     */
    protected abstract int iEncodingGet(int offset, byte[] data);

    /**
     * This private method encodes the identifier octets. When a BER
     * object is created, this method should be used to set up the encoding
     * of the identifier, called via the "init" method.
     * This method sets the internal variables "iTagType" and "iTag"
     * so this object can be queried for the tag type and tag value without
     * needing to decode them from the encoding octets.
     *
     * @param tagType       is the tag type of the object, which
     *                      must be one of the special value defined in ASN1_Any
     * @param isConstructed is a boolean flag: true indicating the
     *                      contents is constructed, or false indicating it is primitive.
     * @param tag           is the value of the tag, which must be non-negative.
     * @throws ASN1Exception when the tagType is improper, or
     *                       the tag value is negative.
     */
    private void makeIdentifier(int tagType, boolean isConstructed, int tag)
            throws ASN1Exception {
        int b;
        if ((tagType & ~0x00C0) != 0) {
            throw new ASN1Exception("Invalid ASN.1 tag type");
        }
        if (tag < 0) {
            throw new ASN1Exception("ASN.1 tag value is negative");
        }
        iTagType = tagType & 0xC0;
        b = iTagType;
        if (isConstructed) {
            b |= 0x20;
        }
        iTag = tag;
        if (tag <= 30) {
            b |= (tag & 0x1F);
            identifierEncoding = new int[1];
            identifierEncoding[0] = b;
        } else {
            b |= 0x1F;
            int numberBytes = 1;
            int tmpTag = tag;
            do {
                numberBytes++;
                tmpTag >>= 7;
            } while (tmpTag != 0);
            identifierEncoding = new int[numberBytes];
            identifierEncoding[0] = b;
            int index = 0;
            for (int digit = numberBytes - 2; 0 <= digit; digit--) {
                identifierEncoding[++index] = (tag >> (digit * 7)) & 0x7f;
                if (digit != 0) {
                    identifierEncoding[index] |= 0x80;
                }
            }
        }
    }

    /**
     * This private method encodes the length octets. When a BER object
     * is created, this method should be used to set up the encoding
     * of the identifier. It should be used by calling the "init" method.
     *
     * @param length is the length value to be encoded. A negative
     *               value indicates an "indefinite length".
     */
    private void makeLength(int length) {
        if (length < 0) {
            lengthEncoding = new int[1];
            lengthEncoding[0] = 0x80;
        } else if (length < 128) {
            lengthEncoding = new int[1];
            lengthEncoding[0] = length;
        } else {
            int count = 0;
            int shifted = length;
            while (shifted != 0) {
                count++;
                shifted >>= 8;
            }
            lengthEncoding = new int[count + 1];
            lengthEncoding[0] = count | 0x80;
            int index = 0;
            while (0 < count) {
                count--;
                int digit = (length >> (count * 8)) & 0xff;
                lengthEncoding[++index] = digit;
            }
        }
    }
}
