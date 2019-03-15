package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1Boolean;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>SearchResponse</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * SearchResponse ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   resultCount [23] IMPLICIT INTEGER
 *   numberOfRecordsReturned [24] IMPLICIT INTEGER
 *   nextResultSetPosition [25] IMPLICIT INTEGER
 *   searchStatus [22] IMPLICIT BOOLEAN
 *   resultSetStatus [26] IMPLICIT INTEGER OPTIONAL
 *   presentStatus PresentStatus OPTIONAL
 *   records Records OPTIONAL
 *   additionalSearchInfo [203] IMPLICIT OtherInformation OPTIONAL
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */
public final class SearchResponse extends ASN1Any {

    // Enumerated constants for resultSetStatus
    public static final int E_subset = 1;
    public static final int E_interim = 2;
    public static final int E_none = 3;
    public ReferenceId referenceId; // optional
    public ASN1Integer resultCount;
    public ASN1Integer numberOfRecordsReturned;
    public ASN1Integer nextResultSetPosition;
    public ASN1Boolean s_searchStatus;
    public ASN1Integer s_resultSetStatus; // optional
    public PresentStatus s_presentStatus; // optional
    public Records s_records; // optional
    public OtherInformation s_additionalSearchInfo; // optional
    public OtherInformation s_otherInfo; // optional

    /**
     * Constructor for a SearchResponse from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public SearchResponse(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        try {
            referenceId = new ReferenceId(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            referenceId = null; // no, not present
        }
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 23 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_resultCount");
        }
        resultCount = new ASN1Integer(p, false);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 24 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_numberOfRecordsReturned");
        }
        numberOfRecordsReturned = new ASN1Integer(p, false);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 25 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_nextResultSetPosition");
        }
        nextResultSetPosition = new ASN1Integer(p, false);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 22 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_searchStatus");
        }
        s_searchStatus = new ASN1Boolean(p, false);
        part++;
        s_resultSetStatus = null;
        s_presentStatus = null;
        s_records = null;
        s_additionalSearchInfo = null;
        s_otherInfo = null;
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 26 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_resultSetStatus = new ASN1Integer(p, false);
            part++;
        }

        // Decoding: presentStatus PresentStatus OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        try {
            s_presentStatus = new PresentStatus(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_presentStatus = null; // no, not present
        }

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

        // Decoding: additionalSearchInfo [203] IMPLICIT OtherInformation OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 203 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_additionalSearchInfo = new OtherInformation(p, false);
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
            throw new ASN1Exception("bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the SearchResponse.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of SearchResponse, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        // Calculate the number of fields in the encoding
        int numFields = 4; // number of mandatories
        if (referenceId != null) {
            numFields++;
        }
        if (s_resultSetStatus != null) {
            numFields++;
        }
        if (s_presentStatus != null) {
            numFields++;
        }
        if (s_records != null) {
            numFields++;
        }
        if (s_additionalSearchInfo != null) {
            numFields++;
        }
        if (s_otherInfo != null) {
            numFields++;
        }

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;

        // Encoding s_referenceId: ReferenceId OPTIONAL

        if (referenceId != null) {
            fields[x++] = referenceId.berEncode();
        }

        // Encoding s_resultCount: INTEGER

        fields[x++] = resultCount.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 23);

        // Encoding s_numberOfRecordsReturned: INTEGER

        fields[x++] = numberOfRecordsReturned.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 24);

        // Encoding s_nextResultSetPosition: INTEGER

        fields[x++] = nextResultSetPosition.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 25);

        // Encoding s_searchStatus: BOOLEAN

        fields[x++] = s_searchStatus.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 22);

        // Encoding s_resultSetStatus: INTEGER OPTIONAL

        if (s_resultSetStatus != null) {
            fields[x++] = s_resultSetStatus.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 26);
        }

        // Encoding s_presentStatus: PresentStatus OPTIONAL

        if (s_presentStatus != null) {
            fields[x++] = s_presentStatus.berEncode();
        }

        // Encoding s_records: Records OPTIONAL

        if (s_records != null) {
            fields[x++] = s_records.berEncode();
        }

        // Encoding s_additionalSearchInfo: OtherInformation OPTIONAL

        if (s_additionalSearchInfo != null) {
            fields[x++] = s_additionalSearchInfo.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 203);
        }

        // Encoding s_otherInfo: OtherInformation OPTIONAL

        if (s_otherInfo != null) {
            fields[x] = s_otherInfo.berEncode();
        }

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the SearchResponse.
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
        str.append("resultCount ");
        str.append(resultCount);
        outputted++;

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
        str.append("searchStatus ");
        str.append(s_searchStatus);
        outputted++;

        if (s_resultSetStatus != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("resultSetStatus ");
            str.append(s_resultSetStatus);
            outputted++;
        }

        if (s_presentStatus != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("presentStatus ");
            str.append(s_presentStatus);
            outputted++;
        }

        if (s_records != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("records ");
            str.append(s_records);
            outputted++;
        }

        if (s_additionalSearchInfo != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("additionalSearchInfo ");
            str.append(s_additionalSearchInfo);
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
