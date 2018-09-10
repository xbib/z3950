package org.xbib.asn1;

/**
 * This class represents a primitive ASN.1 object encoded
 * according to the Basic Encoding Rules.
 * <em>Information technology -
 * Open Systems Interconnection -
 * Specification of basic encoding rules for Abstract Syntax Notation
 * One (ASN.1)</em>
 * AS 3626-1991
 * ISO/IEC 8825:1990
 *
 * @see org.xbib.asn1.BEREncoding
 */
public class BERPrimitive extends BEREncoding {

    private static final char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * The octets of the encoding are stored in this array.
     * They are internally stored as int[] for efficiency over byte[].
     */

    private int[] contentsOctets;

    /**
     * Constructor.
     * Note that the contents is int[] because this is the internal
     * representation, which can only be used by the ASN.1 standard object
     * classes. It is not intended that higher level classes create
     * BERPrimitives directly.
     *
     * @param asn1Class ASN.1 class
     * @param tag tag
     * @param contents contents
     *
     * @throws ASN1Exception if BER primitive fails
     *
     * @see org.xbib.asn1.BEREncoding#UNIVERSAL_TAG
     * @see org.xbib.asn1.BEREncoding#APPLICATION_TAG
     * @see org.xbib.asn1.BEREncoding#CONTEXT_SPECIFIC_TAG
     * @see org.xbib.asn1.BEREncoding#PRIVATE_TAG
     *
     *
     */
    public BERPrimitive(int asn1Class, int tag, int[] contents) throws ASN1Exception {
        init(asn1Class, false, tag, contents.length);
        contentsOctets = contents;
    }

    /**
     * This method allows the content octets to be examined.
     * Once again, only the ASN.1 standard objects should be using this.
     * @return content octets
     */
    public int[] getContentOctets() {
        return contentsOctets;
    }

    /**
     * Returns a new String object representing this BER encoded
     * ASN.1 object's value.
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");
        switch (tagType) {
            case BEREncoding.UNIVERSAL_TAG:
                str.append("UNIVERSAL ");
                break;
            case BEREncoding.APPLICATION_TAG:
                str.append("APPLICATION ");
                break;
            case BEREncoding.CONTEXT_SPECIFIC_TAG:
                str.append("CONTEXT SPECIFIC ");
                break;
            case BEREncoding.PRIVATE_TAG:
                str.append("PRIVATE ");
                break;
            default:
                break;
        }
        str.append(String.valueOf(tag)).append("] '");
        for (int octet : contentsOctets) {
            str.append(hex[(octet >> 4) & 0x0f]);
            str.append(hex[octet & 0x0f]);
        }
        str.append("'H");
        return str.toString();
    }

    /**
     * This protected method is used to implement the "get_encoding" method.
     *
     * @return integer
     */
    @Override
    protected int getEncoding(int offset, byte[] data) {
        int i = getHead(offset, data);
        for (int contentsOctet : contentsOctets) {
            data[i++] = (byte) contentsOctet;
        }
        return i;
    }
}
