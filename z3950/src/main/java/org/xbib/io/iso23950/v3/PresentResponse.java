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

    public ReferenceId s_referenceId; // optional
    public ASN1Integer s_numberOfRecordsReturned;
    public ASN1Integer s_nextResultSetPosition;
    public PresentStatus s_presentStatus;
    public Records s_records; // optional
    public OtherInformation s_otherInfo; // optional
    /**
     * Constructor for a PresentResponse from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public PresentResponse(BEREncoding ber, boolean checkTag)
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
        // PresentResponse should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException
                    ("PresentResponse: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;

        // Decoding: referenceId ReferenceId OPTIONAL

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("PresentResponse: incomplete");
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
            throw new ASN1Exception("PresentResponse: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() != 24 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException
                    ("PresentResponse: bad tag in s_numberOfRecordsReturned\n");
        }

        s_numberOfRecordsReturned = new ASN1Integer(p, false);
        part++;

        // Decoding: nextResultSetPosition [25] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("PresentResponse: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() != 25 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException
                    ("PresentResponse: bad tag in s_nextResultSetPosition\n");
        }

        s_nextResultSetPosition = new ASN1Integer(p, false);
        part++;

        // Decoding: presentStatus PresentStatus

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("PresentResponse: incomplete");
        }
        p = berConstructed.elementAt(part);

        s_presentStatus = new PresentStatus(p, true);
        part++;

        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER

        s_records = null;
        s_otherInfo = null;

        // Decoding: records Records OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        try {
            s_records = new Records(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_records = null; // no, not present
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
            throw new ASN1Exception("PresentResponse: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the PresentResponse.
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
     * Returns a BER encoding of PresentResponse, implicitly tagged.
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
        if (s_records != null) {
            numFields++;
        }
        if (s_otherInfo != null) {
            numFields++;
        }

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;

        // Encoding s_referenceId: ReferenceId OPTIONAL

        if (s_referenceId != null) {
            fields[x++] = s_referenceId.berEncode();
        }

        // Encoding s_numberOfRecordsReturned: INTEGER

        fields[x++] = s_numberOfRecordsReturned.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 24);

        // Encoding s_nextResultSetPosition: INTEGER

        fields[x++] = s_nextResultSetPosition.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 25);

        // Encoding s_presentStatus: PresentStatus

        fields[x++] = s_presentStatus.berEncode();

        // Encoding s_records: Records OPTIONAL

        if (s_records != null) {
            fields[x++] = s_records.berEncode();
        }

        // Encoding s_otherInfo: OtherInformation OPTIONAL

        if (s_otherInfo != null) {
            fields[x] = s_otherInfo.berEncode();
        }

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the PresentResponse.
     */

    public String
    toString() {
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
        str.append("nextResultSetPosition ");
        str.append(s_nextResultSetPosition);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("presentStatus ");
        str.append(s_presentStatus);
        outputted++;

        if (s_records != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("records ");
            str.append(s_records);
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
