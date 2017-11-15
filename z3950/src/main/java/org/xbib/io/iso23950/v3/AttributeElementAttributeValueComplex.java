package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>AttributeElement_attributeValue_complex</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * AttributeElement_attributeValue_complex ::=
 * SEQUENCE {
 *   list [1] IMPLICIT SEQUENCE OF StringOrNumeric
 *   semanticAction [2] IMPLICIT SEQUENCE OF INTEGER OPTIONAL
 * }
 * </pre>
 */
public final class AttributeElementAttributeValueComplex extends ASN1Any {

    public StringOrNumeric[] sList;
    public ASN1Integer[] sSemanticAction; // optional

    /**
     * Constructor for a AttributeElement_attributeValue_complex from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public AttributeElementAttributeValueComplex(BEREncoding ber, boolean checkTag)
            throws ASN1Exception {
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
    public void berDecode(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("AttributeElement_attributeValue_complex: bad BER form\n");
        }
        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        if (numParts <= part) {
            throw new ASN1Exception("AttributeElement_attributeValue_complex: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() != 1 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("AttributeElement_attributeValue_complex: bad tag in s_list\n");
        }
        try {
            BERConstructed cons = (BERConstructed) p;
            int parts = cons.numberComponents();
            sList = new StringOrNumeric[parts];
            int n;
            for (n = 0; n < parts; n++) {
                sList[n] = new StringOrNumeric(cons.elementAt(n), true);
            }
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("Bad BER");
        }
        part++;
        sSemanticAction = null;
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() == 2 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                BERConstructed cons = (BERConstructed) p;
                int parts = cons.numberComponents();
                sSemanticAction = new ASN1Integer[parts];
                int n;
                for (n = 0; n < parts; n++) {
                    sSemanticAction[n] = new ASN1Integer(cons.elementAt(n), true);
                }
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("Bad BER");
            }
            part++;
        }
        if (part < numParts) {
            throw new ASN1Exception("AttributeElement_attributeValue_complex: bad BER: extra data " +
                    part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the AttributeElement_attributeValue_complex.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of AttributeElement_attributeValue_complex, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numFields = 1;
        if (sSemanticAction != null) {
            numFields++;
        }
        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding f2[];
        int p;
        f2 = new BEREncoding[sList.length];
        for (p = 0; p < sList.length; p++) {
            f2[p] = sList[p].berEncode();
        }
        fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 1, f2);
        if (sSemanticAction != null) {
            f2 = new BEREncoding[sSemanticAction.length];
            for (p = 0; p < sSemanticAction.length; p++) {
                f2[p] = sSemanticAction[p].berEncode();
            }
            fields[x] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 2, f2);
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the AttributeElement_attributeValue_complex.
     */
    public String toString() {
        int p;
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;
        str.append("list ");
        str.append("{");
        for (p = 0; p < sList.length; p++) {
            if (p != 0) {
                str.append(", ");
            }
            str.append(sList[p]);
        }
        str.append("}");
        outputted++;
        if (sSemanticAction != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("semanticAction ");
            str.append("{");
            for (p = 0; p < sSemanticAction.length; p++) {
                if (p != 0) {
                    str.append(", ");
                }
                str.append(sSemanticAction[p]);
            }
            str.append("}");
        }
        str.append("}");
        return str.toString();
    }
}
