package org.xbib.asn1;

/**
 * Representation for an ASN.1 NumericString.
 * The <code>NumericString</code> type denotes an arbitary string
 * of Numeric characters (digits and space).
 * This type is a string type.
 */
public class ASN1NumericString extends ASN1OctetString {
    /**
     * This constant is the ASN.1 UNIVERSAL tag value for NumericString.
     */
    public static final int NUMERIC_STRING_TAG = 0x12;

    /**
     * Constructor for an ASN.1 NumericString object. It sets the tag to the
     * default value of UNIVERSAL 18 (0x12).
     * @param value value
     */
    public ASN1NumericString(String value) {
        super(value);
    }

    /**
     * Constructor for a NumericString object from a primitive BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception If the BER encoding is incorrect.
     */
    public ASN1NumericString(BEREncoding ber, boolean checkTag)
            throws ASN1Exception {
        super(ber, false);
        if (checkTag && (ber.getTag() != NUMERIC_STRING_TAG || ber.getTagType() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException("bad BER: tag=" + ber.getTag() +
                            " expected " + NUMERIC_STRING_TAG + "\n");
        }
    }

    /**
     * Encodes the numeric string.
     *
     * @throws ASN1Exception if the BER encoding cannot be formed.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, NUMERIC_STRING_TAG);
    }
}
