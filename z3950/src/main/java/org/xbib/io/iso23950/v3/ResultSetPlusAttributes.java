package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>ResultSetPlusAttributes</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * ResultSetPlusAttributes ::=
 * [214] IMPLICIT SEQUENCE {
 *   resultSet ResultSetId
 *   attributes AttributeList
 * }
 * </pre>
 */
public final class ResultSetPlusAttributes extends ASN1Any {

    public ResultSetId s_resultSet;
    public AttributeList s_attributes;

    /**
     * Constructor for a ResultSetPlusAttributes from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public ResultSetPlusAttributes(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        if (checkTag) {
            if (ber.getTag() != 214 ||
                    ber.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
                throw new ASN1EncodingException("bad BER: tag=" + ber.getTag() + " expected 214");
            }
        }
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
        s_resultSet = new ResultSetId(p, true);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        s_attributes = new AttributeList(p, true);
        part++;
        if (part < numParts) {
            throw new ASN1Exception("bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the ResultSetPlusAttributes.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 214);
    }

    /**
     * Returns a BER encoding of ResultSetPlusAttributes, implicitly tagged.
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
        fields[x++] = s_resultSet.berEncode();
        fields[x] = s_attributes.berEncode();
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the ResultSetPlusAttributes.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;

        str.append("resultSet ");
        str.append(s_resultSet);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("attributes ");
        str.append(s_attributes);
        str.append("}");
        return str.toString();
    }

}
