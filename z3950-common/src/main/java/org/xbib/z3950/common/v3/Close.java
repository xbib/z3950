package org.xbib.z3950.common.v3;

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

    public ReferenceId referenceId; // optional

    public CloseReason closeReason;

    public InternationalString diagnosticInformation; // optional

    public ResourceReportId resourceReportFormat; // optional

    public ResourceReport resourceReport; // optional

    public OtherInformation otherInformation; // optional

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
    @Override
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
            referenceId = new ReferenceId(p, true);
            part++;
        } catch (ASN1Exception e) {
            referenceId = null;
        }
        if (numParts <= part) {
            throw new ASN1Exception("Close: incomplete");
        }
        p = berConstructed.elementAt(part);
        closeReason = new CloseReason(p, true);
        part++;
        diagnosticInformation = null;
        resourceReportFormat = null;
        resourceReport = null;
        otherInformation = null;
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 3 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            diagnosticInformation = new InternationalString(p, false);
            part++;
        }
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 4 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            resourceReportFormat = new ResourceReportId(p, false);
            part++;
        }
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 5 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagged = (BERConstructed) p;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("Close: bad BER encoding: s_resourceReport tag bad");
            }
            if (tagged.numberComponents() != 1) {
                throw new ASN1EncodingException("Close: bad BER encoding: s_resourceReport tag bad");
            }
            resourceReport = new ResourceReport(tagged.elementAt(0), true);
            part++;
        }
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        try {
            otherInformation = new OtherInformation(p, true);
            part++;
        } catch (ASN1Exception e) {
            otherInformation = null;
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
    @Override
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
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numFields = 1;
        if (referenceId != null) {
            numFields++;
        }
        if (diagnosticInformation != null) {
            numFields++;
        }
        if (resourceReportFormat != null) {
            numFields++;
        }
        if (resourceReport != null) {
            numFields++;
        }
        if (otherInformation != null) {
            numFields++;
        }
        BEREncoding[] fields = new BEREncoding[numFields];
        int x = 0;
        BEREncoding[] enc;
        if (referenceId != null) {
            fields[x++] = referenceId.berEncode();
        }
        fields[x++] = closeReason.berEncode();
        if (diagnosticInformation != null) {
            fields[x++] = diagnosticInformation.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 3);
        }
        if (resourceReportFormat != null) {
            fields[x++] = resourceReportFormat.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 4);
        }
        if (resourceReport != null) {
            enc = new BEREncoding[1];
            enc[0] = resourceReport.berEncode();
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 5, enc);
        }
        if (otherInformation != null) {
            fields[x] = otherInformation.berEncode();
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the Close.
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
        str.append("closeReason ");
        str.append(closeReason);
        outputted++;
        if (diagnosticInformation != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("diagnosticInformation ");
            str.append(diagnosticInformation);
            outputted++;
        }
        if (resourceReportFormat != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("resourceReportFormat ");
            str.append(resourceReportFormat);
            outputted++;
        }
        if (resourceReport != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("resourceReport ");
            str.append(resourceReport);
            outputted++;
        }
        if (otherInformation != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("otherInfo ");
            str.append(otherInformation);
        }
        str.append("}");
        return str.toString();
    }
}
