package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>Range</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * Range ::=
 * SEQUENCE {
 *   startingPosition [1] IMPLICIT INTEGER
 *   numberOfRecords [2] IMPLICIT INTEGER
 * }
 * </pre>
 */
public final class Range extends ASN1Any {

    public ASN1Integer s_startingPosition;
    public ASN1Integer s_numberOfRecords;

    /**
     * Constructor for a Range from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public Range(BEREncoding ber, boolean checkTag)
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
    @Override
    public void berDecode(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        // Range should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException
                    ("Range: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;

        // Decoding: startingPosition [1] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("Range: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() != 1 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("Range: bad tag in s_startingPosition\n");
        }

        s_startingPosition = new ASN1Integer(p, false);
        part++;

        // Decoding: numberOfRecords [2] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("Range: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() != 2 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("Range: bad tag in s_numberOfRecords\n");
        }

        s_numberOfRecords = new ASN1Integer(p, false);
        part++;

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception("Range: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the Range.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of Range, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        // Calculate the number of fields in the encoding

        int numFields = 2; // number of mandatories

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;

        // Encoding s_startingPosition: INTEGER

        fields[x++] = s_startingPosition.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);

        // Encoding s_numberOfRecords: INTEGER

        fields[x] = s_numberOfRecords.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 2);

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the Range.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;

        str.append("startingPosition ");
        str.append(s_startingPosition);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("numberOfRecords ");
        str.append(s_numberOfRecords);

        str.append("}");
        return str.toString();
    }
}
