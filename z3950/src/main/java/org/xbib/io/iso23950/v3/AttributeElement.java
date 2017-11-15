package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>AttributeElement</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * AttributeElement ::=
 * SEQUENCE {
 *   attributeSet [1] IMPLICIT AttributeSetId OPTIONAL
 *   attributeType [120] IMPLICIT INTEGER
 *   attributeValue AttributeElement_attributeValue
 * }
 * </pre>
 */
public final class AttributeElement extends ASN1Any {

    public AttributeSetId attributeSetId; // optional

    public ASN1Integer sAttributeType;

    public AttributeElementAttributeValue attributeValue;

    /**
     * Default constructor for a AttributeElement.
     */

    public AttributeElement() {
    }

    /**
     * Constructor for a AttributeElement from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public AttributeElement(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
            throw new ASN1EncodingException("AttributeElement: bad BER form\n");
        }
        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        if (numParts <= part) {
            throw new ASN1Exception("AttributeElement: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() == 1 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            attributeSetId = new AttributeSetId(p, false);
            part++;
        }
        if (numParts <= part) {
            throw new ASN1Exception("AttributeElement: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() != 120 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("AttributeElement: bad tag in s_attributeType\n");
        }
        sAttributeType = new ASN1Integer(p, false);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("AttributeElement: incomplete");
        }
        p = berConstructed.elementAt(part);
        attributeValue = new AttributeElementAttributeValue(p, true);
        part++;
        if (part < numParts) {
            throw new ASN1Exception("AttributeElement: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the AttributeElement.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of AttributeElement, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     * @see org.xbib.asn1.BEREncoding#UNIVERSAL_TAG
     * @see org.xbib.asn1.BEREncoding#APPLICATION_TAG
     * @see org.xbib.asn1.BEREncoding#CONTEXT_SPECIFIC_TAG
     * @see org.xbib.asn1.BEREncoding#PRIVATE_TAG
     */
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numFields = 2;
        if (attributeSetId != null) {
            numFields++;
        }
        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        if (attributeSetId != null) {
            fields[x++] = attributeSetId.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
        }
        fields[x++] = sAttributeType.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 120);
        fields[x] = attributeValue.berEncode();
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the AttributeElement.
     */
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;
        if (attributeSetId != null) {
            str.append("attributeSet ");
            str.append(attributeSetId);
            outputted++;
        }
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("attributeType ");
        str.append(sAttributeType);
        outputted++;
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("attributeValue ");
        str.append(attributeValue);
        str.append("}");
        return str.toString();
    }
}
