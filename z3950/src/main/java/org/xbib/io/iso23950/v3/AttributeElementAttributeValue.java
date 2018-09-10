package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>AttributeElement_attributeValue</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * AttributeElement_attributeValue ::=
 * CHOICE {
 *   numeric [121] IMPLICIT INTEGER
 *   complex [224] IMPLICIT AttributeElement_attributeValue_complex
 * }
 * </pre>
 */
public final class AttributeElementAttributeValue extends ASN1Any {

    public ASN1Integer numeric;

    public AttributeElementAttributeValueComplex complex;

    /**
     * Default constructor for a AttributeElement_attributeValue.
     */
    public AttributeElementAttributeValue() {
    }

    /**
     * Constructor for a AttributeElement_attributeValue from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public AttributeElementAttributeValue(BEREncoding ber, boolean checkTag)
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
        numeric = null;
        complex = null;
        if (ber.getTag() == 121 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            numeric = new ASN1Integer(ber, false);
            return;
        }
        if (ber.getTag() == 224 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            complex = new AttributeElementAttributeValueComplex(ber, false);
            return;
        }
        throw new ASN1Exception("AttributeElement_attributeValue: bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of AttributeElement_attributeValue.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    public BEREncoding berEncode() throws ASN1Exception {
        BEREncoding chosen = null;
        if (numeric != null) {
            chosen = numeric.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 121);
        }
        if (complex != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = complex.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 224);
        }
        if (chosen == null) {
            throw new ASN1Exception("CHOICE not set");
        }
        return chosen;
    }

    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        throw new ASN1EncodingException("AttributeElement_attributeValue: cannot implicitly tag");
    }

    /**
     * Returns a new String object containing a text representing
     * of the AttributeElement_attributeValue.
     */
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        boolean found = false;
        if (numeric != null) {
            found = true;
            str.append("numeric ");
            str.append(numeric);
        }
        if (complex != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: complex> ");
            }
            str.append("complex ");
            str.append(complex);
        }
        str.append("}");
        return str.toString();
    }
}
