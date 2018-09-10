package org.xbib.asn1;

/**
 * Representation for an ASN.1 VideotexString.
 * The <code>VideotexString</code> type denotes an arbitary string
 * of Videotex characters.
 * This type is a string type.
 */
public class ASN1VideotexString extends ASN1OctetString {
    /**
     * This constant is the ASN.1 UNIVERSAL tag value for VideotexString.
     */
    public static final int VIDEOTEX_STRING_TAG = 0x15;

    /**
     * Constructor for an ASN.1 VideotexString object. It sets the tag to the
     * default value of UNIVERSAL 15 (0x21).
     * @param value value
     */
    public ASN1VideotexString(String value) {
        super(value);
    }

    /**
     * Constructor for a VideotexString object from a primitive BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception If the BER encoding is incorrect.
     */
    public ASN1VideotexString(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        super(ber, false);
        if (checkTag && (ber.getTag() != VIDEOTEX_STRING_TAG || ber.getTagType() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException("bad BER: tag=" + ber.getTag() + " expected " + VIDEOTEX_STRING_TAG);
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
        return berEncode(BEREncoding.UNIVERSAL_TAG, VIDEOTEX_STRING_TAG);
    }
}
