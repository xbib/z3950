package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1ObjectIdentifier;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>InfoCategory</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * InfoCategory ::=
 * SEQUENCE {
 *   categoryTypeId [1] IMPLICIT OBJECT IDENTIFIER OPTIONAL
 *   categoryValue [2] IMPLICIT INTEGER
 * }
 * </pre>
 */
public final class InfoCategory extends ASN1Any {

    public ASN1ObjectIdentifier s_categoryTypeId; // optional
    public ASN1Integer s_categoryValue;

    /**
     * Constructor for a InfoCategory from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public InfoCategory(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // InfoCategory should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("InfoCategory: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;

        // Decoding: categoryTypeId [1] IMPLICIT OBJECT IDENTIFIER OPTIONAL

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("InfoCategory: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 1 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_categoryTypeId = new ASN1ObjectIdentifier(p, false);
            part++;
        }

        // Decoding: categoryValue [2] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("InfoCategory: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() != 2 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("InfoCategory: bad tag in s_categoryValue\n");
        }

        s_categoryValue = new ASN1Integer(p, false);
        part++;

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception("InfoCategory: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the InfoCategory.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of InfoCategory, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        // Calculate the number of fields in the encoding

        int numFields = 1; // number of mandatories
        if (s_categoryTypeId != null) {
            numFields++;
        }

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;

        // Encoding s_categoryTypeId: OBJECT IDENTIFIER OPTIONAL

        if (s_categoryTypeId != null) {
            fields[x++] = s_categoryTypeId.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
        }

        // Encoding s_categoryValue: INTEGER

        fields[x] = s_categoryValue.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 2);

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the InfoCategory.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;

        if (s_categoryTypeId != null) {
            str.append("categoryTypeId ");
            str.append(s_categoryTypeId);
            outputted++;
        }

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("categoryValue ");
        str.append(s_categoryValue);

        str.append("}");

        return str.toString();
    }

}