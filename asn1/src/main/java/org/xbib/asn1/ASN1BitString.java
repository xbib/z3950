package org.xbib.asn1;

/**
 * Representation of an ASN.1 <code>BIT STRING</code>.
 * The BIT STRING type denotes an arbitary string of bits (ones and zeros).
 * A BIT STRING value can have any length, including zero. The type is a
 * string type.
 */
public final class ASN1BitString extends ASN1Any {
    /**
     * This constant is the ASN.1 UNIVERSAL tag value for BIT STRING.
     */
    public static final int BIT_STRING_TAG = 0x03;

    /**
     * The values of the BIT STRING are stored in this array of boolean
     * values.
     */
    private boolean[] bits;

    /**
     * Constructor for an ASN.1 BIT STRING object. It sets the tag
     * to the default value of UNIVERSAL 3, and the bits to the
     * given bit values.
     *
     * @param bitValues - array of booleans representing the bit string.
     */
    public ASN1BitString(boolean[] bitValues) {
        bits = bitValues;
    }

    /**
     * Constructor for an ASN.1 BIT STRING object from a BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception If the BER encoding is incorrect.
     */
    public ASN1BitString(BEREncoding ber, boolean checkTag)
            throws ASN1Exception {
        super(ber, checkTag); // superclass will call berDecode
    }

    /**
     * Method for initializing the object from a BER encoding.
     *
     * @param berEncoding The BER encoding to use.
     * @param checkTag    If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1EncodingException If the BER encoding is incorrect.
     */
    @Override
    public void berDecode(BEREncoding berEncoding, boolean checkTag)
            throws ASN1EncodingException {
        if (checkTag && (berEncoding.getTag() != BIT_STRING_TAG || berEncoding.getTagType() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException("ASN.1 BIT STRING: bad BER: tag=" + berEncoding.getTag() +
                            " expected " + BIT_STRING_TAG + "\n");
        }
        if (berEncoding instanceof BERPrimitive) {
            BERPrimitive ber = (BERPrimitive) berEncoding;
            int[] encoding = ber.getContentOctets();
            if (encoding.length < 1) {
                throw new ASN1EncodingException("ASN1 BIT STRING: invalid encoding, length = " + encoding.length);
            }
            int unusedBits = encoding[0] & 0x07;
            int numBits = (encoding.length - 1) * 8 - unusedBits;
            bits = new boolean[numBits];
            for (int bit = 0; bit < numBits; bit++) {
                int octet = encoding[bit / 8 + 1];
                octet <<= (bit % 8);
                bits[bit] = (octet & 0x80) != 0;
            }
        } else {
            throw new ASN1EncodingException("ASN.1 BIT STRING: decoding constructed NOT IMPLEMENTED YET");
        }
    }

    /**
     * Returns a BER encoding of the BIT STRING.
     * Bit strings can have a primitive encoding and a constructed
     * encoding. This method performs the primitive encoding (which
     * is the one specified for DER encoding).
     *
     * @return The BER encoding of the BIT STRING
     * @throws ASN1Exception when the BIT STRING is invalid
     *                       and cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, BIT_STRING_TAG);
    }

    /**
     * Returns a BER encoding of the BIT STRING.
     * Bit strings can have a primitive encoding and a constructed
     * encoding. This method performs the primitive encoding (which
     * is the one specified for DER encoding).
     *
     * @return The BER encoding of the BIT STRING
     * @throws ASN1Exception when the BIT STRING is invalid
     *                       and cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numOctets = (bits.length + 7) / 8;
        int[] encoding = new int[numOctets + 1];
        encoding[0] = (numOctets * 8) - bits.length;
        for (int count = 1; count <= numOctets; count++) {
            encoding[count] = 0x00;
            int bitBaseIndex = (count - 1) * 8;
            for (int bitIndex = 0; bitIndex < 8; bitIndex++) {
                int n = bitBaseIndex + bitIndex;
                encoding[count] <<= 1;
                if (n < bits.length && bits[n]) {
                    encoding[count] |= 0x01;
                }
            }
        }
        return new BERPrimitive(tagType, tag, encoding);
    }

    /**
     * Method to set the bit string's value.
     *
     * @param newBits the value to set the BIT STRING to.
     * @return the object.
     */
    public ASN1BitString set(boolean[] newBits) {
        bits = newBits;
        return this;
    }

    /**
     * Method to get the bit string's value.
     *
     * @return the BIT STRING's current value.
     */
    public boolean[] get() {
        return bits;
    }

    /**
     * Returns a new String object representing this ASN.1 object's value.
     *
     * @return A text string representation of the BitString.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append('\'');
        for (boolean bit : bits) {
            str.append(bit ? '1' : '0');
        }
        str.append("'B");
        return str.toString();
    }
}
