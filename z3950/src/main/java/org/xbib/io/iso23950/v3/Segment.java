package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>Segment</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * Segment ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   numberOfRecordsReturned [24] IMPLICIT INTEGER
 *   segmentRecords [0] IMPLICIT SEQUENCE OF NamePlusRecord
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */
public final class Segment extends ASN1Any {

    public ReferenceId s_referenceId; // optional
    public ASN1Integer s_numberOfRecordsReturned;
    public NamePlusRecord s_segmentRecords[];
    public OtherInformation s_otherInfo; // optional

    /**
     * Constructor for a Segment from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public Segment(BEREncoding ber, boolean checkTag)
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

    public void
    berDecode(BEREncoding ber, boolean checkTag)
            throws ASN1Exception {
        // Segment should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException
                    ("Segment: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;

        // Decoding: referenceId ReferenceId OPTIONAL

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("Segment: incomplete");
        }
        p = berConstructed.elementAt(part);

        try {
            s_referenceId = new ReferenceId(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_referenceId = null; // no, not present
        }

        // Decoding: numberOfRecordsReturned [24] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("Segment: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() != 24 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException
                    ("Segment: bad tag in s_numberOfRecordsReturned\n");
        }

        s_numberOfRecordsReturned = new ASN1Integer(p, false);
        part++;

        // Decoding: segmentRecords [0] IMPLICIT SEQUENCE OF NamePlusRecord

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("Segment: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() != 0 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException
                    ("Segment: bad tag in s_segmentRecords\n");
        }

        try {
            BERConstructed cons = (BERConstructed) p;
            int parts = cons.numberComponents();
            s_segmentRecords = new NamePlusRecord[parts];
            int n;
            for (n = 0; n < parts; n++) {
                s_segmentRecords[n] = new NamePlusRecord(cons.elementAt(n), true);
            }
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("Bad BER");
        }
        part++;

        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER

        s_otherInfo = null;

        // Decoding: otherInfo OtherInformation OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        try {
            s_otherInfo = new OtherInformation(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_otherInfo = null; // no, not present
        }

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception("Segment: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the Segment.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of Segment, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        // Calculate the number of fields in the encoding

        int numFields = 2; // number of mandatories
        if (s_referenceId != null) {
            numFields++;
        }
        if (s_otherInfo != null) {
            numFields++;
        }

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding f2[];
        int p;

        // Encoding s_referenceId: ReferenceId OPTIONAL

        if (s_referenceId != null) {
            fields[x++] = s_referenceId.berEncode();
        }

        // Encoding s_numberOfRecordsReturned: INTEGER

        fields[x++] = s_numberOfRecordsReturned.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 24);

        // Encoding s_segmentRecords: SEQUENCE OF

        f2 = new BEREncoding[s_segmentRecords.length];

        for (p = 0; p < s_segmentRecords.length; p++) {
            f2[p] = s_segmentRecords[p].berEncode();
        }

        fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 0, f2);

        // Encoding s_otherInfo: OtherInformation OPTIONAL

        if (s_otherInfo != null) {
            fields[x] = s_otherInfo.berEncode();
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the Segment.
     */
    public String toString() {
        int p;
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;

        if (s_referenceId != null) {
            str.append("referenceId ");
            str.append(s_referenceId);
            outputted++;
        }

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("numberOfRecordsReturned ");
        str.append(s_numberOfRecordsReturned);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("segmentRecords ");
        str.append("{");
        for (p = 0; p < s_segmentRecords.length; p++) {
            if (p != 0) {
                str.append(", ");
            }
            str.append(s_segmentRecords[p]);
        }
        str.append("}");
        outputted++;

        if (s_otherInfo != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("otherInfo ");
            str.append(s_otherInfo);
        }
        str.append("}");
        return str.toString();
    }

}
