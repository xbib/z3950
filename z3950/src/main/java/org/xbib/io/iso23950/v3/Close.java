package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>Close</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * Close ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   closeReason CloseReason
 *   diagnosticInformation [3] IMPLICIT InternationalString OPTIONAL
 *   resourceReportFormat [4] IMPLICIT ResourceReportId OPTIONAL
 *   resourceReport [5] EXPLICIT ResourceReport OPTIONAL
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */

public final class Close extends ASN1Any {

    public ReferenceId sReferenceId; // optional

    public CloseReason sCloseReason;

    public InternationalString sDiagnosticInformation; // optional

    public ResourceReportId sResourceReportFormat; // optional

    public ResourceReport sResourceReport; // optional

    public OtherInformation sOtherInfo; // optional

    /**
     * Default constructor for a Close.
     */
    public Close() {
    }

    /**
     * Constructor for a Close from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public Close(BEREncoding ber, boolean checkTag)
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
        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("Close: bad BER form\n");
        }
        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        BERConstructed tagged;
        if (numParts <= part) {
            throw new ASN1Exception("Close: incomplete");
        }
        p = berConstructed.elementAt(part);
        try {
            sReferenceId = new ReferenceId(p, true);
            part++;
        } catch (ASN1Exception e) {
            sReferenceId = null;
        }
        if (numParts <= part) {
            throw new ASN1Exception("Close: incomplete");
        }
        p = berConstructed.elementAt(part);
        sCloseReason = new CloseReason(p, true);
        part++;
        sDiagnosticInformation = null;
        sResourceReportFormat = null;
        sResourceReport = null;
        sOtherInfo = null;
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() == 3 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            sDiagnosticInformation = new InternationalString(p, false);
            part++;
        }
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() == 4 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            sResourceReportFormat = new ResourceReportId(p, false);
            part++;
        }
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() == 5 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagged = (BERConstructed) p;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("Close: bad BER encoding: s_resourceReport tag bad");
            }
            if (tagged.numberComponents() != 1) {
                throw new ASN1EncodingException("Close: bad BER encoding: s_resourceReport tag bad");
            }
            sResourceReport = new ResourceReport(tagged.elementAt(0), true);
            part++;
        }
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        try {
            sOtherInfo = new OtherInformation(p, true);
            part++;
        } catch (ASN1Exception e) {
            sOtherInfo = null;
        }
        if (part < numParts) {
            throw new ASN1Exception("Close: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the Close.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of Close, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numFields = 1;
        if (sReferenceId != null) {
            numFields++;
        }
        if (sDiagnosticInformation != null) {
            numFields++;
        }
        if (sResourceReportFormat != null) {
            numFields++;
        }
        if (sResourceReport != null) {
            numFields++;
        }
        if (sOtherInfo != null) {
            numFields++;
        }
        BEREncoding[] fields = new BEREncoding[numFields];
        int x = 0;
        BEREncoding[] enc;
        if (sReferenceId != null) {
            fields[x++] = sReferenceId.berEncode();
        }
        fields[x++] = sCloseReason.berEncode();
        if (sDiagnosticInformation != null) {
            fields[x++] = sDiagnosticInformation.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 3);
        }
        if (sResourceReportFormat != null) {
            fields[x++] = sResourceReportFormat.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 4);
        }
        if (sResourceReport != null) {
            enc = new BEREncoding[1];
            enc[0] = sResourceReport.berEncode();
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 5, enc);
        }
        if (sOtherInfo != null) {
            fields[x] = sOtherInfo.berEncode();
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the Close.
     */
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;
        if (sReferenceId != null) {
            str.append("referenceId ");
            str.append(sReferenceId);
            outputted++;
        }
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("closeReason ");
        str.append(sCloseReason);
        outputted++;
        if (sDiagnosticInformation != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("diagnosticInformation ");
            str.append(sDiagnosticInformation);
            outputted++;
        }
        if (sResourceReportFormat != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("resourceReportFormat ");
            str.append(sResourceReportFormat);
            outputted++;
        }
        if (sResourceReport != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("resourceReport ");
            str.append(sResourceReport);
            outputted++;
        }
        if (sOtherInfo != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("otherInfo ");
            str.append(sOtherInfo);
        }
        str.append("}");
        return str.toString();
    }
}
