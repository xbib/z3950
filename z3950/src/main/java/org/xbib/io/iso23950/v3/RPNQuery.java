package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>RPNQuery</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * RPNQuery ::=
 * SEQUENCE {
 *   attributeSet AttributeSetId
 *   rpn RPNStructure
 * }
 * </pre>
 */
public final class RPNQuery extends ASN1Any {

    public AttributeSetId attributeSet;
    public RPNStructure rpn;

    /**
     * Default constructor for a RPNQuery.
     */
    public RPNQuery() {
    }

    /**
     * Constructor for a RPNQuery from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public RPNQuery(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        super(ber, checkTag);
    }

    /**
     * Initializing object from a BER encoding.
     * This method is for internal use only. You should use
     * the constructor that takes a BEREncoding.
     *
     * @param ber       the BER to decode.
     * @param checkTag if the tag should be checked.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    @Override
    public void berDecode(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("bad BER form");
        }
        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        attributeSet = new AttributeSetId(p, true);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        rpn = new RPNStructure(p, true);
        part++;
        if (part < numParts) {
            throw new ASN1Exception("bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the RPNQuery.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of RPNQuery, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numFields = 2;
        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        fields[x++] = attributeSet.berEncode();
        fields[x] = rpn.berEncode();
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the RPNQuery.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;
        str.append("attributeSet ");
        str.append(attributeSet);
        outputted++;
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("rpn ");
        str.append(rpn);
        str.append("}");
        return str.toString();
    }
}
