package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>ResourceReportRequest</code> from <code>Z39-50-APDU-1995</code>
 * <pre>
 * ResourceReportRequest ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   opId [210] IMPLICIT ReferenceId OPTIONAL
 *   prefResourceReportFormat [49] IMPLICIT ResourceReportId OPTIONAL
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */
public final class ResourceReportRequest extends ASN1Any {

    public ReferenceId s_referenceId; // optional
    public ReferenceId s_opId; // optional
    public ResourceReportId s_prefResourceReportFormat; // optional
    public OtherInformation s_otherInfo; // optional

    /**
     * Constructor for a ResourceReportRequest from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public ResourceReportRequest(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // ResourceReportRequest should be encoded by a constructed BER

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

        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER

        s_referenceId = null;
        s_opId = null;
        s_prefResourceReportFormat = null;
        s_otherInfo = null;

        // Decoding: referenceId ReferenceId OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        try {
            s_referenceId = new ReferenceId(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_referenceId = null; // no, not present
        }

        // Decoding: opId [210] IMPLICIT ReferenceId OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 210 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_opId = new ReferenceId(p, false);
            part++;
        }

        // Decoding: prefResourceReportFormat [49] IMPLICIT ResourceReportId OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 49 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_prefResourceReportFormat = new ResourceReportId(p, false);
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
     * Returns a BER encoding of the ResourceReportRequest.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of ResourceReportRequest, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        // Calculate the number of fields in the encoding

        int numFields = 0; // number of mandatories
        if (s_referenceId != null) {
            numFields++;
        }
        if (s_opId != null) {
            numFields++;
        }
        if (s_prefResourceReportFormat != null) {
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

        // Encoding s_opId: ReferenceId OPTIONAL

        if (s_opId != null) {
            fields[x++] = s_opId.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 210);
        }

        // Encoding s_prefResourceReportFormat: ResourceReportId OPTIONAL

        if (s_prefResourceReportFormat != null) {
            fields[x++] = s_prefResourceReportFormat.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 49);
        }

        // Encoding s_otherInfo: OtherInformation OPTIONAL

        if (s_otherInfo != null) {
            fields[x] = s_otherInfo.berEncode();
        }

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the ResourceReportRequest.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;

        if (s_referenceId != null) {
            str.append("referenceId ");
            str.append(s_referenceId);
            outputted++;
        }

        if (s_opId != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("opId ");
            str.append(s_opId);
            outputted++;
        }

        if (s_prefResourceReportFormat != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("prefResourceReportFormat ");
            str.append(s_prefResourceReportFormat);
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
