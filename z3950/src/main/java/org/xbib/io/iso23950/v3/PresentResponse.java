package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>PresentResponse</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * PresentResponse ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   numberOfRecordsReturned [24] IMPLICIT INTEGER
 *   nextResultSetPosition [25] IMPLICIT INTEGER
 *   presentStatus PresentStatus
 *   records Records OPTIONAL
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */
public final class PresentResponse extends ASN1Any {

    public ReferenceId referenceId; // optional
    public ASN1Integer numberOfRecordsReturned;
    public ASN1Integer nextResultSetPosition;
    public PresentStatus presentStatus;
    public Records records; // optional
    public OtherInformation otherInfo; // optional

    /**
     * Constructor for a PresentResponse from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public PresentResponse(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // PresentResponse should be encoded by a constructed BER
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
        // Decoding: numberOfRecordsReturned [24] IMPLICIT INTEGER
        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("PresentResponse: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 24 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_numberOfRecordsReturned");
        }
        numberOfRecordsReturned = new ASN1Integer(p, false);
        part++;
        // Decoding: nextResultSetPosition [25] IMPLICIT INTEGER
        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 25 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_nextResultSetPosition\n");
        }
        nextResultSetPosition = new ASN1Integer(p, false);
        part++;
        // Decoding: presentStatus PresentStatus
        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        presentStatus = new PresentStatus(p, true);
        part++;
        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER
        records = null;
        otherInfo = null;
        // Decoding: records Records OPTIONAL
        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);
        try {
            records = new Records(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            records = null; // no, not present
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
     * Returns a BER encoding of the PresentResponse.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of PresentResponse, implicitly tagged.
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
        if (records != null) {
            numFields++;
        }
        if (otherInfo != null) {
            numFields++;
        }
        // Encode it
        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        // Encoding s_referenceId: ReferenceId OPTIONAL
        if (referenceId != null) {
            fields[x++] = referenceId.berEncode();
        }
        // Encoding s_numberOfRecordsReturned: INTEGER
        fields[x++] = numberOfRecordsReturned.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 24);
        // Encoding s_nextResultSetPosition: INTEGER
        fields[x++] = nextResultSetPosition.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 25);
        // Encoding s_presentStatus: PresentStatus
        fields[x++] = presentStatus.berEncode();
        // Encoding s_records: Records OPTIONAL
        if (records != null) {
            fields[x++] = records.berEncode();
        }
        // Encoding s_otherInfo: OtherInformation OPTIONAL
        if (otherInfo != null) {
            fields[x] = otherInfo.berEncode();
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the PresentResponse.
     */
    @Override
    public String toString() {
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
        str.append("numberOfRecordsReturned ");
        str.append(numberOfRecordsReturned);
        outputted++;
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("nextResultSetPosition ");
        str.append(nextResultSetPosition);
        outputted++;
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("presentStatus ");
        str.append(presentStatus);
        outputted++;
        if (records != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("records ");
            str.append(records);
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
