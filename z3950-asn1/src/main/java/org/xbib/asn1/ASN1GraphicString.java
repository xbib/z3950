package org.xbib.asn1;

/**
 * ASN.1 GraphicString.
 * The <code>GraphicString</code> type denotes an arbitary string
 * of Graphic characters.
 * This type is a string type.
 */
public class ASN1GraphicString extends ASN1OctetString {
    /**
     * This constant is the UNIVERSAL tag value for GraphicString.
     */
    public static final int GRAPHIC_STRING_TAG = 0x19;

    /**
     * Constructor for a GraphicString object. It sets the tag to the
     * default value of UNIVERSAL 25 (0x19).
     *
     * @param value The string value
     */

    public ASN1GraphicString(String value) {
        super(value);
    }

    /**
     * Constructor for a GraphicString object from a primitive BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception if the BER encoding is incorrect.
     */
    public ASN1GraphicString(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        super(ber, false);
        if (checkTag && (ber.getTag() != GRAPHIC_STRING_TAG || ber.getTagType() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException("bad BER: tag=" + ber.getTag() +
                            " expected " + GRAPHIC_STRING_TAG + "\n");
        }
    }
}
