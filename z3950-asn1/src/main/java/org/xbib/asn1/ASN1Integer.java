package org.xbib.asn1;

/**
 * Representation of an ASN.1 INTEGER.
 * The <code>INTEGER</code> type denotes an arbitary integer.
 * ASN.1 INTEGER values can be positive, negative, or zero; and can
 * have any magnitude.
 * The current implementation limits the values of INTEGERs to 32-bit
 * two's complement values.
 */
public final class ASN1Integer extends ASN1Any {
    /**
     * This constant is the ASN.1 UNIVERSAL tag value for INTEGER.
     */
    public static final int INTEGER_TAG = 0x02;

    /**
     * The value of the INTEGER is stored in this variable.
     * This is private for good information hiding, so that we are able
     * to change its representation (e.g. to a long) at a later date
     * without affecting the interface.
     */
    private int value;

    /**
     * Constructor for an ASN.1 INTEGER object. The tag is
     * set to the default tag of UNIVERSAL 2, and the value to the
     * given number.
     *
     * @param number the value of the INTEGER.
     */
    public ASN1Integer(int number) {
        value = number;
    }

    /**
     * Constructor for an ASN.1 INTEGER object from a BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception If the BER encoding is incorrect.
     */
    public ASN1Integer(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        super(ber, checkTag);
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
        if (checkTag && (berEncoding.getTag() != INTEGER_TAG ||
                    berEncoding.getTagType() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException("bad BER: tag=" + berEncoding.getTag() +
                            " expected " + INTEGER_TAG + "\n");
        }
        if (!(berEncoding instanceof BERPrimitive)) {
            throw new ASN1EncodingException("bad form, constructed");
        }
        BERPrimitive ber = (BERPrimitive) berEncoding;
        int[] encoding = ber.getContentOctets();
        if (encoding.length < 1) {
            throw new ASN1EncodingException("invalid encoding, length = " + encoding.length);
        }
        value = (byte) encoding[0];
        for (int x = 1; x < encoding.length; x++) {
            value <<= 8;
            value |= (encoding[x] & 0xff);
        }
    }

    /**
     * Returns a BER encoding of the INTEGER.
     *
     * @return The BER encoding of the INTEGER
     * @throws ASN1Exception when the INTEGER is invalid
     *                       and cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, INTEGER_TAG);
    }

    /**
     * Returns a BER encoding of the INTEGER. Explicitly tagged with
     * the supplied tag.
     *
     * @return The BER encoding of the INTEGER
     * @throws ASN1Exception when the INTEGER is invalid
     *                       and cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int length = 0;
        int shifted = value;
        if (value < 0) {
            shifted = ~value;
        }
        boolean needPad;
        do {
            needPad = (shifted & 0x80) == 0x80;
            shifted >>= 8;
            length++;
        } while (shifted != 0);
        if (needPad) {
            length++;
        }
        int[] encoding = new int[length];
        int index = 0;
        while (0 < length) {
            encoding[index++] = (value >> (8 * (length - 1))) & 0xff;
            length--;
        }
        return new BERPrimitive(tagType, tag, encoding);
    }

    /**
     * Method to set the integer's value.
     *
     * @param newVal the value to set the INTEGER to.
     * @return the object.
     */
    public ASN1Integer set(int newVal) {
        value = newVal;
        return this;
    }

    /**
     * Method to get the integer's value.
     *
     * @return the INTEGER's current value.
     */

    public int get() {
        return value;
    }

    /**
     * Returns a new String object representing this ASN.1 object's value.
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
