package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>ScanResponse</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * ScanResponse ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   stepSize [3] IMPLICIT INTEGER OPTIONAL
 *   scanStatus [4] IMPLICIT INTEGER
 *   numberOfEntriesReturned [5] IMPLICIT INTEGER
 *   positionOfTerm [6] IMPLICIT INTEGER OPTIONAL
 *   entries [7] IMPLICIT ListEntries OPTIONAL
 *   attributeSet [8] IMPLICIT AttributeSetId OPTIONAL
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */
public final class ScanResponse extends ASN1Any {

    // Enumerated constants for scanStatus
    public static final int E_success = 0;
    public static final int E_partial_1 = 1;
    public static final int E_partial_2 = 2;
    public static final int E_partial_3 = 3;
    public static final int E_partial_4 = 4;
    public static final int E_partial_5 = 5;
    public static final int E_failure = 6;
    public ReferenceId referenceId; // optional
    public ASN1Integer stepSize; // optional
    public ASN1Integer scanStatus;
    public ASN1Integer numberOfEntriesReturned;
    public ASN1Integer positionOfTerm; // optional
    public ListEntries entries; // optional
    public AttributeSetId attributeSet; // optional
    public OtherInformation otherInfo; // optional

    /**
     * Constructor for a ScanResponse from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public ScanResponse(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // ScanResponse should be encoded by a constructed BER

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

        // Decoding: stepSize [3] IMPLICIT INTEGER OPTIONAL

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 3 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            stepSize = new ASN1Integer(p, false);
            part++;
        }

        // Decoding: scanStatus [4] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() != 4 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in scanStatus");
        }

        scanStatus = new ASN1Integer(p, false);
        part++;

        // Decoding: numberOfEntriesReturned [5] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() != 5 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in numberOfEntriesReturned");
        }

        numberOfEntriesReturned = new ASN1Integer(p, false);
        part++;

        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER

        positionOfTerm = null;
        entries = null;
        attributeSet = null;
        otherInfo = null;

        // Decoding: positionOfTerm [6] IMPLICIT INTEGER OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 6 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            positionOfTerm = new ASN1Integer(p, false);
            part++;
        }

        // Decoding: entries [7] IMPLICIT ListEntries OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 7 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            entries = new ListEntries(p, false);
            part++;
        }

        // Decoding: attributeSet [8] IMPLICIT AttributeSetId OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 8 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            attributeSet = new AttributeSetId(p, false);
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
     * Returns a BER encoding of the ScanResponse.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of ScanResponse, implicitly tagged.
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
        if (referenceId != null) {
            numFields++;
        }
        if (stepSize != null) {
            numFields++;
        }
        if (positionOfTerm != null) {
            numFields++;
        }
        if (entries != null) {
            numFields++;
        }
        if (attributeSet != null) {
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

        // Encoding s_stepSize: INTEGER OPTIONAL

        if (stepSize != null) {
            fields[x++] = stepSize.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 3);
        }

        // Encoding s_scanStatus: INTEGER

        fields[x++] = scanStatus.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 4);

        // Encoding s_numberOfEntriesReturned: INTEGER

        fields[x++] = numberOfEntriesReturned.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 5);

        // Encoding s_positionOfTerm: INTEGER OPTIONAL

        if (positionOfTerm != null) {
            fields[x++] = positionOfTerm.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 6);
        }

        // Encoding s_entries: ListEntries OPTIONAL

        if (entries != null) {
            fields[x++] = entries.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 7);
        }

        // Encoding s_attributeSet: AttributeSetId OPTIONAL

        if (attributeSet != null) {
            fields[x++] = attributeSet.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 8);
        }

        // Encoding s_otherInfo: OtherInformation OPTIONAL

        if (otherInfo != null) {
            fields[x] = otherInfo.berEncode();
        }

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the ScanResponse.
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

        if (stepSize != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("stepSize ");
            str.append(stepSize);
            outputted++;
        }

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("scanStatus ");
        str.append(scanStatus);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("numberOfEntriesReturned ");
        str.append(numberOfEntriesReturned);
        outputted++;

        if (positionOfTerm != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("positionOfTerm ");
            str.append(positionOfTerm);
            outputted++;
        }

        if (entries != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("entries ");
            str.append(entries);
            outputted++;
        }

        if (attributeSet != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("attributeSet ");
            str.append(attributeSet);
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
