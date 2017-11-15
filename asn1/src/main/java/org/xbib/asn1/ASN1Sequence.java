package org.xbib.asn1;

/**
 * Representation of an ASN.1 SEQUENCE.
 * The <code>SEQUENCE</code> type denotes an ordered collection
 * of one or more types. The SEQUENCE OF type denotes an ordered
 * collection of zero or more occurances of a given type.
 * This class is available for the generic handling of ASN.1
 * definitions. However, specialised ASN.1 productions will usually
 * use their own encoding for SEQUENCES directly.
 */
public final class ASN1Sequence extends ASN1Any {
    /**
     * This constant tag value is the ASN.1 UNIVERSAL tag value for
     * a SEQUENCE or a SEQUENCE OF type.
     */
    public static final int SEQUENCE_TAG = 0x10;

    /**
     * The values of the SEQUENCE are stored in this array.
     */

    private ASN1Any[] elements;

    /**
     * Default constructor for an ASN.1 SEQUENCE object. The tag is set
     * to the default value.
     *
     * @param elementArray the ASN.1 objects that make up the sequence.
     */

    public ASN1Sequence(ASN1Any[] elementArray) {
        elements = elementArray;
    }

    /**
     * Constructor for an ASN.1 SEQUENCE object from a BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception If the BER encoding is incorrect.
     */
    public ASN1Sequence(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
    public void berDecode(BEREncoding berEncoding, boolean checkTag)
            throws ASN1Exception {
        if (checkTag && (berEncoding.tagGet() != SEQUENCE_TAG || berEncoding.tagTypeGet() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException
                    ("ASN.1 SEQUENCE: bad BER: tag=" + berEncoding.tagGet() +
                            " expected " + SEQUENCE_TAG + "\n");
        }
        if (berEncoding instanceof BERPrimitive) {
            throw new ASN1EncodingException("ASN.1 SEQUENCE: bad form, primitive");
        }
        BERConstructed ber = (BERConstructed) berEncoding;
        int len = ber.numberComponents();
        elements = new ASN1Any[len];
        for (int x = 0; x < len; x++) {
            elements[x] = ASN1Decoder.toASN1(ber.elementAt(x));
        }
    }

    /**
     * Returns a BER encoding with no implicit tag.
     *
     * @return The BER encoding
     * @throws ASN1Exception when the object is invalid and cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of the SEQUENCE implcitly tagged.
     *
     * @param tagType The type of the implcit tag
     * @param tag     The implicit tag number
     * @return The BER encoding of the SEQUENCE
     * @throws ASN1Exception when the SEQUENCE is invalid
     *                       and cannot be encoded.
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
     * Method to set the SEQUENCE's elements.
     *
     * @param elementArray an array of ASN.1 object
     * @return ASN.1 sequence
     */
    public ASN1Sequence set(ASN1Any[] elementArray) {
        elements = elementArray;
        return this;
    }

    /**
     * Method to get the elements of the SEQUENCE.
     *
     * @return an array containing the SEQUENCE's elements.
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
