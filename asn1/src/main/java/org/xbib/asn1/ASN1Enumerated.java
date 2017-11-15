package org.xbib.asn1;

/**
 * Representation of an ASN.1 ENUMERATED.
 * The <code>ENUMERATED</code> type denotes an integer from a selected set.
 * ASN.1 ENUMERATED values can be positive, negative, or zero; and can
 * have any magnitude.
 * The current implementation limits the values of ENUMERATED to 32-bit
 * two's complement values.
 */
public final class ASN1Enumerated extends ASN1Any {
    /**
     * This constant is the ASN.1 UNIVERSAL tag value for ENUMERATED.
     */
    public static final int ENUMERATED_TAG = 0x0A;

    /**
     * The value of the ENUMERATED is stored in this variable.
     * This is private for good information hiding, so that we are able
     * to change its representation (e.g. to a long) at a later date
     * without affecting the interface.
     */
    private int value;

    /**
     * Constructor for an ASN.1 ENUMERATED object. The tag is
     * set to the default tag of UNIVERSAL 2, and the value to the
     * given number.
     *
     * @param number the value of the ENUMERATED.
     */
    public ASN1Enumerated(int number) {
        value = number;
    }

    /**
     * Constructor for an ASN.1 ENUMERATED object from a BER encoding.
     * @param ber  the BER encoding
     * @param checkTag the check tag
     * @throws ASN1Exception if the BER encoding is incorrect.
     */
    public ASN1Enumerated(BEREncoding ber, boolean checkTag)
            throws ASN1Exception {
        super(ber, checkTag);
    }

    /**
     * Method for initializing the object from a BER encoding.
     *
     * @throws ASN1EncodingException if the BER encoding is incorrect.
     */
    @Override
    public void berDecode(BEREncoding berEncoding, boolean checkTag)
            throws ASN1EncodingException {
        if (checkTag && (berEncoding.tagGet() != ENUMERATED_TAG ||
                    berEncoding.tagTypeGet() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException("bad BER: tag=" + berEncoding.tagGet() +
                            " expected " + ENUMERATED_TAG + "\n");
        }
        if (!(berEncoding instanceof BERPrimitive)) {
            throw new ASN1EncodingException("bad form, constructed");
        }
        BERPrimitive ber = (BERPrimitive) berEncoding;
        int[] encoding = ber.peek();
        if (encoding.length < 1) {
            throw new ASN1EncodingException("invalid encoding, length = " + encoding.length);
        }
        value = (byte) encoding[0]; // to ensure sign extension
        for (int x = 1; x < encoding.length; x++) {
            value <<= 8;
            value |= (encoding[x] & 0xff);
        }
    }

    /**
     * Returns a BER encoding of the ENUMERATED.
     *
     * @return The BER encoding of the ENUMERATED
     * @throws ASN1Exception when the ENUMERATED is invalid
     *                       and cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ENUMERATED_TAG);
    }

    /**
     * Returns a BER encoding of the ENUMERATED. Explicitly tagged with
     * the supplied tag.
     *
     * @return The BER encoding of the ENUMERATED
     * @throws ASN1Exception when the ENUMERATED is invalid
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
     * @param newVal the value to set the ENUMERATED to.
     * @return the object.
     */
    public ASN1Enumerated set(int newVal) {
        value = newVal;
        return this;
    }

    /**
     * Method to get the integer's value.
     *
     * @return the ENUMERATED's current value.
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
