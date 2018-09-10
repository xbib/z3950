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

    public ReferenceId referenceId; // optional
    public ResultSetId resultSetId;
    public ASN1Integer resultSetStartPoint;
    public ASN1Integer numberOfRecordsRequested;
    public Range[] additionalRanges; // optional
    public PresentRequestRecordComposition recordComposition; // optional
    public ASN1ObjectIdentifier preferredRecordSyntax; // optional
    public ASN1Integer maxSegmentCount; // optional
    public ASN1Integer maxRecordSize; // optional
    public ASN1Integer maxSegmentSize; // optional
    public OtherInformation otherInfo; // optional

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
    public PresentRequest(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // PresentRequest should be encoded by a constructed BER
        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("bad BER form");
        }
        // Prepare to decode the components
        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        // Decoding: referenceId ReferenceId OPTIONAL
        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        try {
            referenceId = new ReferenceId(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            referenceId = null; // no, not present
        }
        // Decoding: resultSetId ResultSetId
        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        resultSetId = new ResultSetId(p, true);
        part++;
        // Decoding: resultSetStartPoint [30] IMPLICIT INTEGER
        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 30 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in resultSetStartPoint\n");
        }
        resultSetStartPoint = new ASN1Integer(p, false);
        part++;
        // Decoding: numberOfRecordsRequested [29] IMPLICIT INTEGER
        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 29 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("PresentRequest: bad tag in numberOfRecordsRequested");
        }
        numberOfRecordsRequested = new ASN1Integer(p, false);
        part++;
        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER
        additionalRanges = null;
        recordComposition = null;
        preferredRecordSyntax = null;
        maxSegmentCount = null;
        maxRecordSize = null;
        maxSegmentSize = null;
        otherInfo = null;
        // Decoding: additionalRanges [212] IMPLICIT SEQUENCE OF Range OPTIONAL
        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 212 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                BERConstructed cons = (BERConstructed) p;
                int parts = cons.numberComponents();
                additionalRanges = new Range[parts];
                int n;
                for (n = 0; n < parts; n++) {
                    additionalRanges[n] = new Range(cons.elementAt(n), true);
                }
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("bad BER");
            }
            part++;
        }
        // Decoding: recordComposition PresentRequest_recordComposition OPTIONAL
        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);
        try {
            recordComposition = new PresentRequestRecordComposition(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            recordComposition = null; // no, not present
        }
        // Decoding: preferredRecordSyntax [104] IMPLICIT OBJECT IDENTIFIER OPTIONAL
        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 104 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            preferredRecordSyntax = new ASN1ObjectIdentifier(p, false);
            part++;
        }
        // Decoding: maxSegmentCount [204] IMPLICIT INTEGER OPTIONAL
        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 204 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            maxSegmentCount = new ASN1Integer(p, false);
            part++;
        }
        // Decoding: maxRecordSize [206] IMPLICIT INTEGER OPTIONAL
        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 206 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            maxRecordSize = new ASN1Integer(p, false);
            part++;
        }
        // Decoding: maxSegmentSize [207] IMPLICIT INTEGER OPTIONAL
        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 207 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            maxSegmentSize = new ASN1Integer(p, false);
            part++;
        }
        // Decoding: otherInfo OtherInformation OPTIONAL
        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);
        try {
            otherInfo = new OtherInformation(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            otherInfo = null; // no, not present
        }
        // Should not be any more parts
        if (part < numParts) {
            throw new ASN1Exception("bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the PresentRequest.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
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
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        // Calculate the number of fields in the encoding
        int numFields = 3; // number of mandatories
        if (referenceId != null) {
            numFields++;
        }
        if (additionalRanges != null) {
            numFields++;
        }
        if (recordComposition != null) {
            numFields++;
        }
        if (preferredRecordSyntax != null) {
            numFields++;
        }
        if (maxSegmentCount != null) {
            numFields++;
        }
        if (maxRecordSize != null) {
            numFields++;
        }
        if (maxSegmentSize != null) {
            numFields++;
        }
        if (otherInfo != null) {
            numFields++;
        }
        // Encode it
        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding f2[];
        int p;
        // Encoding s_referenceId: ReferenceId OPTIONAL
        if (referenceId != null) {
            fields[x++] = referenceId.berEncode();
        }
        // Encoding s_resultSetId: ResultSetId
        fields[x++] = resultSetId.berEncode();
        // Encoding s_resultSetStartPoint: INTEGER
        fields[x++] = resultSetStartPoint.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 30);
        // Encoding s_numberOfRecordsRequested: INTEGER
        fields[x++] = numberOfRecordsRequested.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 29);
        // Encoding s_additionalRanges: SEQUENCE OF OPTIONAL
        if (additionalRanges != null) {
            f2 = new BEREncoding[additionalRanges.length];
            for (p = 0; p < additionalRanges.length; p++) {
                f2[p] = additionalRanges[p].berEncode();
            }
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 212, f2);
        }
        // Encoding s_recordComposition: PresentRequest_recordComposition OPTIONAL
        if (recordComposition != null) {
            fields[x++] = recordComposition.berEncode();
        }
        // Encoding s_preferredRecordSyntax: OBJECT IDENTIFIER OPTIONAL
        if (preferredRecordSyntax != null) {
            fields[x++] = preferredRecordSyntax.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 104);
        }
        // Encoding s_maxSegmentCount: INTEGER OPTIONAL
        if (maxSegmentCount != null) {
            fields[x++] = maxSegmentCount.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 204);
        }
        // Encoding s_maxRecordSize: INTEGER OPTIONAL
        if (maxRecordSize != null) {
            fields[x++] = maxRecordSize.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 206);
        }
        // Encoding s_maxSegmentSize: INTEGER OPTIONAL
        if (maxSegmentSize != null) {
            fields[x++] = maxSegmentSize.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 207);
        }
        // Encoding s_otherInfo: OtherInformation OPTIONAL
        if (otherInfo != null) {
            fields[x] = otherInfo.berEncode();
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the PresentRequest.
     */
    @Override
    public String toString() {
        int p;
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;
        if (referenceId != null) {
            str.append("referenceId ");
            str.append(referenceId);
            outputted++;
        }
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("resultSetId ");
        str.append(resultSetId);
        outputted++;
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("resultSetStartPoint ");
        str.append(resultSetStartPoint);
        outputted++;
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("numberOfRecordsRequested ");
        str.append(numberOfRecordsRequested);
        outputted++;
        if (additionalRanges != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("additionalRanges ");
            str.append("{");
            for (p = 0; p < additionalRanges.length; p++) {
                if (p != 0) {
                    str.append(", ");
                }
                str.append(additionalRanges[p]);
            }
            str.append("}");
            outputted++;
        }
        if (recordComposition != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("recordComposition ");
            str.append(recordComposition);
            outputted++;
        }
        if (preferredRecordSyntax != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("preferredRecordSyntax ");
            str.append(preferredRecordSyntax);
            outputted++;
        }
        if (maxSegmentCount != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("maxSegmentCount ");
            str.append(maxSegmentCount);
            outputted++;
        }
        if (maxRecordSize != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("maxRecordSize ");
            str.append(maxRecordSize);
            outputted++;
        }
        if (maxSegmentSize != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("maxSegmentSize ");
            str.append(maxSegmentSize);
            outputted++;
        }
        if (otherInfo != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("otherInfo ");
            str.append(otherInfo);
        }
        str.append("}");
        return str.toString();
    }
}
