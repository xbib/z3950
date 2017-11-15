package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1ObjectIdentifier;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>PresentRequest</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * PresentRequest ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   resultSetId ResultSetId
 *   resultSetStartPoint [30] IMPLICIT INTEGER
 *   numberOfRecordsRequested [29] IMPLICIT INTEGER
 *   additionalRanges [212] IMPLICIT SEQUENCE OF Range OPTIONAL
 *   recordComposition PresentRequest_recordComposition OPTIONAL
 *   preferredRecordSyntax [104] IMPLICIT OBJECT IDENTIFIER OPTIONAL
 *   maxSegmentCount [204] IMPLICIT INTEGER OPTIONAL
 *   maxRecordSize [206] IMPLICIT INTEGER OPTIONAL
 *   maxSegmentSize [207] IMPLICIT INTEGER OPTIONAL
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */

public final class PresentRequest extends ASN1Any {

    public ReferenceId s_referenceId; // optional
    public ResultSetId s_resultSetId;
    public ASN1Integer s_resultSetStartPoint;
    public ASN1Integer s_numberOfRecordsRequested;
    public Range s_additionalRanges[]; // optional
    public PresentRequestRecordComposition s_recordComposition; // optional
    public ASN1ObjectIdentifier s_preferredRecordSyntax; // optional
    public ASN1Integer s_maxSegmentCount; // optional
    public ASN1Integer s_maxRecordSize; // optional
    public ASN1Integer s_maxSegmentSize; // optional
    public OtherInformation s_otherInfo; // optional
    /**
     * Default constructor for a PresentRequest.
     */

    public PresentRequest() {
    }
    /**
     * Constructor for a PresentRequest from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public PresentRequest(BEREncoding ber, boolean checkTag)
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
        // PresentRequest should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("PresentRequest: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;

        // Decoding: referenceId ReferenceId OPTIONAL

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("PresentRequest: incomplete");
        }
        p = berConstructed.elementAt(part);

        try {
            s_referenceId = new ReferenceId(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_referenceId = null; // no, not present
        }

        // Decoding: resultSetId ResultSetId

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("PresentRequest: incomplete");
        }
        p = berConstructed.elementAt(part);

        s_resultSetId = new ResultSetId(p, true);
        part++;

        // Decoding: resultSetStartPoint [30] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("PresentRequest: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() != 30 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("PresentRequest: bad tag in s_resultSetStartPoint\n");
        }

        s_resultSetStartPoint = new ASN1Integer(p, false);
        part++;

        // Decoding: numberOfRecordsRequested [29] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("PresentRequest: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() != 29 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("PresentRequest: bad tag in s_numberOfRecordsRequested\n");
        }

        s_numberOfRecordsRequested = new ASN1Integer(p, false);
        part++;

        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER

        s_additionalRanges = null;
        s_recordComposition = null;
        s_preferredRecordSyntax = null;
        s_maxSegmentCount = null;
        s_maxRecordSize = null;
        s_maxSegmentSize = null;
        s_otherInfo = null;

        // Decoding: additionalRanges [212] IMPLICIT SEQUENCE OF Range OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 212 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                BERConstructed cons = (BERConstructed) p;
                int parts = cons.numberComponents();
                s_additionalRanges = new Range[parts];
                int n;
                for (n = 0; n < parts; n++) {
                    s_additionalRanges[n] = new Range(cons.elementAt(n), true);
                }
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("Bad BER");
            }
            part++;
        }

        // Decoding: recordComposition PresentRequest_recordComposition OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        try {
            s_recordComposition = new PresentRequestRecordComposition(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_recordComposition = null; // no, not present
        }

        // Decoding: preferredRecordSyntax [104] IMPLICIT OBJECT IDENTIFIER OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 104 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_preferredRecordSyntax = new ASN1ObjectIdentifier(p, false);
            part++;
        }

        // Decoding: maxSegmentCount [204] IMPLICIT INTEGER OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 204 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_maxSegmentCount = new ASN1Integer(p, false);
            part++;
        }

        // Decoding: maxRecordSize [206] IMPLICIT INTEGER OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 206 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_maxRecordSize = new ASN1Integer(p, false);
            part++;
        }

        // Decoding: maxSegmentSize [207] IMPLICIT INTEGER OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 207 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_maxSegmentSize = new ASN1Integer(p, false);
            part++;
        }

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
            throw new ASN1Exception("PresentRequest: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the PresentRequest.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */

    public BEREncoding
    berEncode()
            throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of PresentRequest, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */

    public BEREncoding
    berEncode(int tagType, int tag)
            throws ASN1Exception {
        // Calculate the number of fields in the encoding

        int numFields = 3; // number of mandatories
        if (s_referenceId != null) {
            numFields++;
        }
        if (s_additionalRanges != null) {
            numFields++;
        }
        if (s_recordComposition != null) {
            numFields++;
        }
        if (s_preferredRecordSyntax != null) {
            numFields++;
        }
        if (s_maxSegmentCount != null) {
            numFields++;
        }
        if (s_maxRecordSize != null) {
            numFields++;
        }
        if (s_maxSegmentSize != null) {
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

        // Encoding s_resultSetId: ResultSetId

        fields[x++] = s_resultSetId.berEncode();

        // Encoding s_resultSetStartPoint: INTEGER

        fields[x++] = s_resultSetStartPoint.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 30);

        // Encoding s_numberOfRecordsRequested: INTEGER

        fields[x++] = s_numberOfRecordsRequested.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 29);

        // Encoding s_additionalRanges: SEQUENCE OF OPTIONAL

        if (s_additionalRanges != null) {
            f2 = new BEREncoding[s_additionalRanges.length];

            for (p = 0; p < s_additionalRanges.length; p++) {
                f2[p] = s_additionalRanges[p].berEncode();
            }

            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 212, f2);
        }

        // Encoding s_recordComposition: PresentRequest_recordComposition OPTIONAL

        if (s_recordComposition != null) {
            fields[x++] = s_recordComposition.berEncode();
        }

        // Encoding s_preferredRecordSyntax: OBJECT IDENTIFIER OPTIONAL

        if (s_preferredRecordSyntax != null) {
            fields[x++] = s_preferredRecordSyntax.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 104);
        }

        // Encoding s_maxSegmentCount: INTEGER OPTIONAL

        if (s_maxSegmentCount != null) {
            fields[x++] = s_maxSegmentCount.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 204);
        }

        // Encoding s_maxRecordSize: INTEGER OPTIONAL

        if (s_maxRecordSize != null) {
            fields[x++] = s_maxRecordSize.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 206);
        }

        // Encoding s_maxSegmentSize: INTEGER OPTIONAL

        if (s_maxSegmentSize != null) {
            fields[x++] = s_maxSegmentSize.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 207);
        }

        // Encoding s_otherInfo: OtherInformation OPTIONAL

        if (s_otherInfo != null) {
            fields[x] = s_otherInfo.berEncode();
        }

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the PresentRequest.
     */

    public String
    toString() {
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
        str.append("resultSetId ");
        str.append(s_resultSetId);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("resultSetStartPoint ");
        str.append(s_resultSetStartPoint);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("numberOfRecordsRequested ");
        str.append(s_numberOfRecordsRequested);
        outputted++;

        if (s_additionalRanges != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("additionalRanges ");
            str.append("{");
            for (p = 0; p < s_additionalRanges.length; p++) {
                if (p != 0) {
                    str.append(", ");
                }
                str.append(s_additionalRanges[p]);
            }
            str.append("}");
            outputted++;
        }

        if (s_recordComposition != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("recordComposition ");
            str.append(s_recordComposition);
            outputted++;
        }

        if (s_preferredRecordSyntax != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("preferredRecordSyntax ");
            str.append(s_preferredRecordSyntax);
            outputted++;
        }

        if (s_maxSegmentCount != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("maxSegmentCount ");
            str.append(s_maxSegmentCount);
            outputted++;
        }

        if (s_maxRecordSize != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("maxRecordSize ");
            str.append(s_maxRecordSize);
            outputted++;
        }

        if (s_maxSegmentSize != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("maxSegmentSize ");
            str.append(s_maxSegmentSize);
            outputted++;
        }

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
