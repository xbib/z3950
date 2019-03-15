package org.xbib.asn1;

/**
 * Representation for ASN.1 PrintableString.
 * The <code>PrintableString</code> type denotes an arbitary string
 * of printable characters from the following character set:
 * <table>
 *     <caption>Printable Strings</caption>
 * <tr><td>Capital letters<td>A, B, ... , Z
 * <tr><td>Small letters<td>a, b, ..., z
 * <tr><td>Digits<td>0, 1, ..., 9
 * <tr><td>Space<td>
 * <tr><td>Apostrophe<td>'
 * <tr><td>Left partnthesis<td>(
 * <tr><td>Right partnthesis<td>)
 * <tr><td>Plus<td>+
 * <tr><td>comma<td>,
 * <tr><td>hyphen<td>-
 * <tr><td>Full stop<td>.
 * <tr><td>Solidus<td>/
 * <tr><td>Colon<td>:
 * <tr><td>Equal sign<td>=
 * <tr><td>Question mark<td>?
 * </table>
 * This type is a string type.
 */
public final class ASN1PrintableString extends ASN1OctetString {
    /**
     * This constant is the ASN.1 UNIVERSAL tag value for PrintableString.
     */
    public static final int PRINTABLE_STRING_TAG = 0x13;

    /**
     * Constructor for a PrintableString object. It sets the tag to the
     * default value of UNIVERSAL 19 (0x13).
     * @param text text
     */
    public ASN1PrintableString(String text) {
        super(text);
    }

    /**
     * Constructor for a PrintableString object from a primitive BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception If the BER encoding is incorrect.
     */
    public ASN1PrintableString(BEREncoding ber, boolean checkTag)
            throws ASN1Exception {
        super(ber, false);
        if (checkTag && (ber.getTag() != PRINTABLE_STRING_TAG || ber.getTagType() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException("ASN.1 PrintableString: bad BER: tag=" + ber.getTag() +
                            " expected " + PRINTABLE_STRING_TAG + "\n");
        }
    }
}
