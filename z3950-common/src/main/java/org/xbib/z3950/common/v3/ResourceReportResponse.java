package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>ResourceReportResponse</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * ResourceReportResponse ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   resourceReportStatus [50] IMPLICIT INTEGER
 *   resourceReport [51] EXPLICIT ResourceReport OPTIONAL
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */
public final class ResourceReportResponse extends ASN1Any {

    // Enumerated constants for resourceReportStatus
    public static final int E_success = 0;
    public static final int E_partial = 1;
    public static final int E_failure_1 = 2;
    public static final int E_failure_2 = 3;
    public static final int E_failure_3 = 4;
    public static final int E_failure_4 = 5;
    public static final int E_failure_5 = 6;
    public static final int E_failure_6 = 7;
    public ReferenceId s_referenceId; // optional
    public ASN1Integer s_resourceReportStatus;
    public ResourceReport s_resourceReport; // optional
    public OtherInformation s_otherInfo; // optional

    /**
     * Default constructor for a ResourceReportResponse.
     */
    public ResourceReportResponse() {
    }

    /**
     * Constructor for a ResourceReportResponse from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public ResourceReportResponse(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // ResourceReportResponse should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("ResourceReportResponse: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        BERConstructed tagged;

        // Decoding: referenceId ReferenceId OPTIONAL

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("ResourceReportResponse: incomplete");
        }
        p = berConstructed.elementAt(part);

        try {
            s_referenceId = new ReferenceId(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_referenceId = null; // no, not present
        }

        // Decoding: resourceReportStatus [50] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("ResourceReportResponse: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() != 50 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("ResourceReportResponse: bad tag in s_resourceReportStatus\n");
        }

        s_resourceReportStatus = new ASN1Integer(p, false);
        part++;

        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER

        s_resourceReport = null;
        s_otherInfo = null;

        // Decoding: resourceReport [51] EXPLICIT ResourceReport OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 51 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagged = (BERConstructed) p;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("ResourceReportResponse: bad BER encoding: s_resourceReport tag bad\n");
            }
            if (tagged.numberComponents() != 1) {
                throw new ASN1EncodingException("ResourceReportResponse: bad BER encoding: s_resourceReport tag bad\n");
            }

            s_resourceReport = new ResourceReport(tagged.elementAt(0), true);
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
            throw new ASN1Exception("ResourceReportResponse: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the ResourceReportResponse.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of ResourceReportResponse, implicitly tagged.
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
        if (s_referenceId != null) {
            numFields++;
        }
        if (s_resourceReport != null) {
            numFields++;
        }
        if (s_otherInfo != null) {
            numFields++;
        }

        // Encode it

        BEREncoding[] fields = new BEREncoding[numFields];
        int x = 0;
        BEREncoding[] enc;

        // Encoding s_referenceId: ReferenceId OPTIONAL

        if (s_referenceId != null) {
            fields[x++] = s_referenceId.berEncode();
        }

        // Encoding s_resourceReportStatus: INTEGER

        fields[x++] = s_resourceReportStatus.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 50);

        // Encoding s_resourceReport: ResourceReport OPTIONAL

        if (s_resourceReport != null) {
            enc = new BEREncoding[1];
            enc[0] = s_resourceReport.berEncode();
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 51, enc);
        }

        // Encoding s_otherInfo: OtherInformation OPTIONAL

        if (s_otherInfo != null) {
            fields[x] = s_otherInfo.berEncode();
        }

        return new BERConstructed(tagType, tag, fields);
    }

    @Override
    public String toString() {
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
        str.append("resourceReportStatus ");
        str.append(s_resourceReportStatus);
        outputted++;

        if (s_resourceReport != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("resourceReport ");
            str.append(s_resourceReport);
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
