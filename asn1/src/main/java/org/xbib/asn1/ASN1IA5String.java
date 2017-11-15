package org.xbib.asn1;

/**
 * Representation for ASN.1 IA5String.
 * The <code>IA5String</code> type denotes an arbitary string of IA5
 * characters. IA5 stands for International Alphabet 5, which is
 * the same as ASCII. The character set includes non-printing control
 * characters. An IA5String can be of any length, including zero.
 */
public final class ASN1IA5String extends ASN1OctetString {
    /**
     * This constant is the ASN.1 UNIVERSAL tag value for IA5String.
     */
    public static final int IA5_STRING_TAG = 0x16;

    /**
     * Constructor for a IA5String object. It sets the tag to the
     * default value of UNIVERSAL 22 (0x16).
     * @param value value
     */
    public ASN1IA5String(String value) {
        super(value);
    }

    /**
     * Constructor for a IA5String object from a primitive BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception If the BER encoding is incorrect.
     */
    public ASN1IA5String(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        super(ber, false);
        if (checkTag && (ber.tagGet() != IA5_STRING_TAG || ber.tagTypeGet() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException("bad BER: tag=" + ber.tagGet() +
                            " expected " + IA5_STRING_TAG + "\n");
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
        return berEncode(BEREncoding.UNIVERSAL_TAG, IA5_STRING_TAG);
    }
}
