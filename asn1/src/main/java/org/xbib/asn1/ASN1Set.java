package org.xbib.asn1;

/**
 * Representation of an ASN.1 SET.
 * The <code>SET</code> type denotes an ordered collection
 * of one or more types. The SET OF type denotes an ordered
 * collection of zero or more occurances of a given type.
 * This class is available for the generic handling of ASN.1
 * definitions. However, specialised ASN.1 productions will usually
 * use their own encoding for SETs directly.
 * For DER encoding, DEFAULTs are not included and all the elements
 * are sorted according to tag number.
 */
public final class ASN1Set extends ASN1Any {
    /**
     * This constant value is the ASN.1 UNIVERSAL tag value indicating
     * a SET or a SET OF type.
     */
    public static final int SET_TAG = 0x11;

    /**
     * The values of the SET are stored in this array.
     */

    private ASN1Any[] elements;

    /**
     * Constructor for an ASN.1 SET object. The tag is set to the
     * default of UNIVERSAL 17 (0x11) and the elements to the given array.
     *
     * @param elementArray Elements in the set.
     */
    public ASN1Set(ASN1Any[] elementArray) {
        elements = elementArray;
    }

    /**
     * Constructor for a SET object from a BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception If the BER encoding is incorrect.
     */
    public ASN1Set(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        super(ber, checkTag);
    }

    /**
     * Method for initializing the object from a BER encoding.
     *
     * @param berEncoding The BER encoding to use.
     * @param checkTag    If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception If the BER encoding is incorrect.
     */
    @Override
    public void berDecode(BEREncoding berEncoding, boolean checkTag) throws ASN1Exception {
        if (checkTag && (berEncoding.getTag() != SET_TAG ||
                berEncoding.getTagType() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException("bad BER: tag=" + berEncoding.getTag() + " expected " + SET_TAG);
        }
        if (berEncoding instanceof BERPrimitive) {
            throw new ASN1EncodingException("bad form, primitive");
        }
        BERConstructed ber = (BERConstructed) berEncoding;
        int len = ber.numberComponents();
        elements = new ASN1Any[len];
        for (int x = 0; x < len; x++) {
            elements[x] = ASN1Decoder.toASN1(ber.elementAt(x));
        }
    }

    /**
     * Returns a BER encoding of the SET.
     *
     * @return The BER encoding of the SET
     * @throws ASN1Exception when the SET is invalid and cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, SET_TAG);
    }

    /**
     * Returns a BER encoding of the SET.
     *
     * @param tagType The type of the implcit tag
     * @param tag     The implicit tag number
     * @return The BER encoding of the SET
     * @throws ASN1Exception when the SET is invalid and cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int len = elements.length;
        BEREncoding[] encodings = new BEREncoding[len];
        for (int index = 0; index < len; index++) {
            encodings[index] = elements[index].berEncode();
        }
        return new BERConstructed(tagType, tag, encodings);
    }

    /**
     * Method to set the SET's elements.
     *
     * @param elementArray an array of ASN.1 objects.
     */

    public void set(ASN1Any[] elementArray) {
        elements = elementArray;
    }

    /**
     * Method to get the elements of the SET.
     *
     * @return an array containing the SET's elements.
     */

    public ASN1Any[] get() {
        return elements;
    }

    /**
     * Returns a new String object representing this ASN.1 object's value.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        for (int index = 0; index < elements.length; index++) {
            if (index != 0) {
                str.append(", ");
            }
            str.append(elements[index].toString());
        }
        str.append('}');
        return new String(str);
    }
}
