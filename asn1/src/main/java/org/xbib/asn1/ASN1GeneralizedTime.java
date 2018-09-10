package org.xbib.asn1;

/**
 * ASN.1 GeneralizedTime.
 * The <code>GeneralizedTime</code> type denotes a string corrsponding
 * to an ISO 8601 date string.
 * This type is a string type.
 */
public final class ASN1GeneralizedTime extends ASN1VisibleString {
    /**
     * This constant is the UNIVERSAL tag value for GeneralizedTime.
     */
    public static final int GENERALIZED_TIME_TAG = 0x18;

    /**
     * Constructor for an GeneralizedTime object. It sets the tag to the
     * default value of UNIVERSAL 24 (0x18).
     * @param value value
     */
    public ASN1GeneralizedTime(String value) {
        super(value);
    }

    /**
     * Constructor for a GeneralizedTime object from a BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception if the BER encoding is incorrect.
     */
    public ASN1GeneralizedTime(BEREncoding ber, boolean checkTag)
            throws ASN1Exception {
        super(ber, false);
        if (checkTag && (ber.getTag() != GENERALIZED_TIME_TAG || ber.getTagType() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException("bad BER: tag=" + ber.getTag() +
                            " expected " + GENERALIZED_TIME_TAG + "\n");
        }
    }

    /**
     * Returns a BER encoding with no implicit tag.
     *
     * @return The BER encoding
     * @throws ASN1Exception when the object is invalid
     *                       and cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, GENERALIZED_TIME_TAG);
    }
}
