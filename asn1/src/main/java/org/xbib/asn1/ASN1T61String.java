package org.xbib.asn1;

/**
 * Representation for ASN.1 T61String.
 * The <code>T61String</code> type denotes an arbitary string
 * of T.61 characters, or TeletextString. T.61 is an eight-bit extension
 * to the ASCII character set. Special escape sequences specify the
 * interpretation of subsequent character values (e.g. as
 * Japanese rather than Latin). A T61String value can be of
 * any length, including zero. This type is a string type.
 */
public final class ASN1T61String extends ASN1OctetString {
    /**
     * This constant is the UNIVERSAL tag value for T61String.
     */
    public static final int T61_STRING_TAG = 0x14;

    /**
     * Constructor for an T61String object. It sets the tag to the
     * default value of UNIVERSAL 20 (0x14).
     *
     * @param value The string value.
     */
    public ASN1T61String(String value) {
        super(value);
    }

    /**
     * Constructor for a T61String object from a primitive BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception If the BER encoding is incorrect.
     */
    public ASN1T61String(BEREncoding ber, boolean checkTag)
            throws ASN1Exception {
        super(ber, false);
        if (checkTag && (ber.getTag() != T61_STRING_TAG || ber.getTagType() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException("ASN.1 T61String: bad BER: tag=" + ber.getTag() + " expected " + T61_STRING_TAG);
        }
    }
}
