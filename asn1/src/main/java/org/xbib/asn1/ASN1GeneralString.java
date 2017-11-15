package org.xbib.asn1;

/**
 * ASN.1 GeneralString.
 * The <code>GeneralString</code> type denotes an arbitary string
 * of General characters.
 * This type is a string type.
 */
public class ASN1GeneralString extends ASN1OctetString {
    /**
     * This constant is the UNIVERSAL tag value for GeneralString.
     */
    public static final int GENERAL_STRING_TAG = 0x1B;

    /**
     * Constructor for a GeneralString object. It sets the tag to the
     * default value of UNIVERSAL 27 (0x1B).
     *
     * @param value The string value.
     */
    public ASN1GeneralString(String value) {
        super(value);
    }

    /**
     * Constructor for a GeneralString object from a primitive BER encoding.
     * @param ber ber
     * @param checkTag check tag
     * @throws ASN1Exception if the BER encoding is incorrect.
     */
    public ASN1GeneralString(BEREncoding ber, boolean checkTag)
            throws ASN1Exception {
        super(ber, false);
        if (checkTag && (ber.tagGet() != GENERAL_STRING_TAG || ber.tagTypeGet() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException("bad BER: tag=" + ber.tagGet() +
                            " expected " + GENERAL_STRING_TAG + "\n");
        }
    }

    /**
     * Encode with no explicit tag.
     *
     * @throws ASN1Exception if the BER encoding cannot be made.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, GENERAL_STRING_TAG);
    }
}
