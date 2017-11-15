package org.xbib.io.iso23950.v3;

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
    public ReferenceId s_referenceId; // optional
    public ASN1Integer s_stepSize; // optional
    public ASN1Integer s_scanStatus;
    public ASN1Integer s_numberOfEntriesReturned;
    public ASN1Integer s_positionOfTerm; // optional
    public ListEntries s_entries; // optional
    public AttributeSetId s_attributeSet; // optional
    public OtherInformation s_otherInfo; // optional
    /**
     * Constructor for a ScanResponse from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public ScanResponse(BEREncoding ber, boolean checkTag)
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
    public void berDecode(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        // ScanResponse should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("ScanResponse: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;

        // Decoding: referenceId ReferenceId OPTIONAL

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("ScanResponse: incomplete");
        }
        p = berConstructed.elementAt(part);

        try {
            s_referenceId = new ReferenceId(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_referenceId = null; // no, not present
        }

        // Decoding: stepSize [3] IMPLICIT INTEGER OPTIONAL

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("ScanResponse: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 3 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_stepSize = new ASN1Integer(p, false);
            part++;
        }

        // Decoding: scanStatus [4] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("ScanResponse: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() != 4 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("ScanResponse: bad tag in s_scanStatus\n");
        }

        s_scanStatus = new ASN1Integer(p, false);
        part++;

        // Decoding: numberOfEntriesReturned [5] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("ScanResponse: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() != 5 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("ScanResponse: bad tag in s_numberOfEntriesReturned\n");
        }

        s_numberOfEntriesReturned = new ASN1Integer(p, false);
        part++;

        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER

        s_positionOfTerm = null;
        s_entries = null;
        s_attributeSet = null;
        s_otherInfo = null;

        // Decoding: positionOfTerm [6] IMPLICIT INTEGER OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 6 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_positionOfTerm = new ASN1Integer(p, false);
            part++;
        }

        // Decoding: entries [7] IMPLICIT ListEntries OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 7 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_entries = new ListEntries(p, false);
            part++;
        }

        // Decoding: attributeSet [8] IMPLICIT AttributeSetId OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 8 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_attributeSet = new AttributeSetId(p, false);
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
            throw new ASN1Exception("ScanResponse: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the ScanResponse.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
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
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        // Calculate the number of fields in the encoding

        int numFields = 2; // number of mandatories
        if (s_referenceId != null) {
            numFields++;
        }
        if (s_stepSize != null) {
            numFields++;
        }
        if (s_positionOfTerm != null) {
            numFields++;
        }
        if (s_entries != null) {
            numFields++;
        }
        if (s_attributeSet != null) {
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

        // Encoding s_stepSize: INTEGER OPTIONAL

        if (s_stepSize != null) {
            fields[x++] = s_stepSize.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 3);
        }

        // Encoding s_scanStatus: INTEGER

        fields[x++] = s_scanStatus.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 4);

        // Encoding s_numberOfEntriesReturned: INTEGER

        fields[x++] = s_numberOfEntriesReturned.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 5);

        // Encoding s_positionOfTerm: INTEGER OPTIONAL

        if (s_positionOfTerm != null) {
            fields[x++] = s_positionOfTerm.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 6);
        }

        // Encoding s_entries: ListEntries OPTIONAL

        if (s_entries != null) {
            fields[x++] = s_entries.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 7);
        }

        // Encoding s_attributeSet: AttributeSetId OPTIONAL

        if (s_attributeSet != null) {
            fields[x++] = s_attributeSet.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 8);
        }

        // Encoding s_otherInfo: OtherInformation OPTIONAL

        if (s_otherInfo != null) {
            fields[x] = s_otherInfo.berEncode();
        }

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the ScanResponse.
     */

    public String toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;

        if (s_referenceId != null) {
            str.append("referenceId ");
            str.append(s_referenceId);
            outputted++;
        }

        if (s_stepSize != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("stepSize ");
            str.append(s_stepSize);
            outputted++;
        }

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("scanStatus ");
        str.append(s_scanStatus);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("numberOfEntriesReturned ");
        str.append(s_numberOfEntriesReturned);
        outputted++;

        if (s_positionOfTerm != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("positionOfTerm ");
            str.append(s_positionOfTerm);
            outputted++;
        }

        if (s_entries != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("entries ");
            str.append(s_entries);
            outputted++;
        }

        if (s_attributeSet != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("attributeSet ");
            str.append(s_attributeSet);
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
