package org.xbib.asn1;

/**
 * ASN.1 VisibleString.
 * The <code>VisibleString</code> type denotes an arbitary string
 * of Visible characters. It is also known as ISO646String, or
 * InternationalString.
 * This type is a string type.
 */
public class ASN1VisibleString extends ASN1OctetString {
    /**
     * This constant is the ASN.1 UNIVERSAL tag value for VisibleString.
     */
    public static final int TAG = 0x1a;

    /**
     * Constructor for a VisibleString object. It sets the tag to the
     * default value of UNIVERSAL 26 (0x1a).
     * @param value value
     */
    public ASN1VisibleString(String value) {
        super(value);
    }

    /**
     * Constructor for a VisibleString object from a primitive BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception If the BER encoding is incorrect.
     */

    public ASN1VisibleString(BEREncoding ber, boolean checkTag)
            throws ASN1Exception {
        super(ber, false);
        if (checkTag && (ber.getTag() != TAG || ber.getTagType() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException("bad BER: tag=" + ber.getTag() + " expected " + TAG);
        }
    }

    /**
     * Encode with no explicit tag.
     *
     * @return The BER encoding
     * @throws ASN1Exception If the BER encoding is incorrect.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, TAG);
    }
}
