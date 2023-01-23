package org.xbib.asn1;

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

    /**
     * The tag type of this BER encoded object. This value must be
     * the same as that encoded in the identiferEncoding.
     * This is an internal member. You should not use this.
     */
    protected int tagType;
    /**
     * The tag number of this BER encoded object. This value must be
     * the same as that encoded in the identiferEncoding.
     * This is an internal member. You should not use this.
     */
    protected int tag;
    /**
     * The total length of this BER object (the identifier octets, plus
     * length octets, plus content octects). This variable must be
     * set up before this object is used (using the init method).
     * This is an internal member. You should not use this.
     */
    protected int totalLength;
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

    public BEREncoding() {
    }

    public int[] getIdentifierEncoding() {
        return identifierEncoding;
    }

    public int[] getLengthEncoding() {
        return lengthEncoding;
    }

    /**
     * Method to examine the tag type of the BER encoded ASN.1 object.
     * @return integer
     */
    public int getTagType() {
        return tagType;
    }

    /**
     * Method to examine the tag number of the BER encoded ASN.1 object.
     * @return integer
     */
    public int getTag() {
        return tag;
    }

    /**
     * Returns the total number of bytes the encoding occupies.
     * @return integer
     */
    public int getTotalLength() {
        return totalLength;
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
        totalLength = identifierEncoding.length + lengthEncoding.length + length;
    }

    /*
     * Internal protected method fills in the data array (starting from index
     * position offset) with the encoding for the identifier and length.
     * This is used by the superclasses to implement the "encodingGet"
     * method.
     */
    protected int getHead(int offset, byte[] data) {
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
    protected abstract int getEncoding(int offset, byte[] data);

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
        this.tagType = tagType & 0xC0;
        b = this.tagType;
        if (isConstructed) {
            b |= 0x20;
        }
        this.tag = tag;
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
