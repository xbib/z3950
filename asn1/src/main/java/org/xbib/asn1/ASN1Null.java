package org.xbib.asn1;

/**
 * Representation of an ASN.1 NULL.
 * This class represents a null value. A NULL is used
 * when only the tag is of interest, and not any value.
 */
public final class ASN1Null extends ASN1Any {
    /**
     * This constant is the ASN.1 UNIVERSAL tag value for NULL.
     */
    public static final int NULL_TAG = 0x05;

    /**
     * Default constructor for an ASN.1 NULL object. The tag is set
     * to the default tag of UNIVERSAL 5. A NULL has no value.
     */
    public ASN1Null() {
        //
    }

    /**
     * Constructor for an ASN.1 NULL object from a primitive BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception If the BER encoding is incorrect.
     */
    public ASN1Null(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        if (checkTag && (berEncoding.tagGet() != NULL_TAG ||
                berEncoding.tagTypeGet() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException("bad BER: tag=" + berEncoding.tagGet() +
                            " expected " + NULL_TAG + "\n");
        }
        if (!(berEncoding instanceof BERPrimitive)) {
            throw new ASN1EncodingException("bad form, constructed");
        }
    }

    /**
     * Returns a BER encoding of the NULL.
     *
     * @return The BER encoding of the NULL
     * @throws ASN1Exception when the NULL is invalid
     *                       and cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, NULL_TAG);
    }

    /**
     * Returns a BER encoding of the NULL.
     *
     * @return The BER encoding of the NULL
     * @throws ASN1Exception when the NULL is invalid
     *                       and cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int[] encoding = new int[0];
        return new BERPrimitive(tagType, tag, encoding);
    }

    /**
     * Returns a new String object representing this ASN.1 object's value.
     */
    @Override
    public String toString() {
        return "null";
    }
}
