package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1Boolean;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>ResourceControlRequest</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * ResourceControlRequest ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   suspendedFlag [39] IMPLICIT BOOLEAN OPTIONAL
 *   resourceReport [40] EXPLICIT ResourceReport OPTIONAL
 *   partialResultsAvailable [41] IMPLICIT INTEGER OPTIONAL
 *   responseRequired [42] IMPLICIT BOOLEAN
 *   triggeredRequestFlag [43] IMPLICIT BOOLEAN OPTIONAL
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */
public final class ResourceControlRequest extends ASN1Any {

    // Enumerated constants for partialResultsAvailable
    public static final int E_subset = 1;
    public static final int E_interim = 2;
    public static final int E_none = 3;
    public ReferenceId s_referenceId; // optional
    public ASN1Boolean s_suspendedFlag; // optional
    public ResourceReport s_resourceReport; // optional
    public ASN1Integer s_partialResultsAvailable; // optional
    public ASN1Boolean s_responseRequired;
    public ASN1Boolean s_triggeredRequestFlag; // optional
    public OtherInformation s_otherInfo; // optional

    /**
     * Default constructor for a ResourceControlRequest.
     */
    public ResourceControlRequest() {
    }

    /**
     * Constructor for a ResourceControlRequest from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public ResourceControlRequest(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // ResourceControlRequest should be encoded by a constructed BER

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
        BERConstructed tagged;

        // Decoding: referenceId ReferenceId OPTIONAL

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        try {
            s_referenceId = new ReferenceId(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_referenceId = null; // no, not present
        }

        // Decoding: suspendedFlag [39] IMPLICIT BOOLEAN OPTIONAL

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 39 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_suspendedFlag = new ASN1Boolean(p, false);
            part++;
        }

        // Decoding: resourceReport [40] EXPLICIT ResourceReport OPTIONAL

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 40 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagged = (BERConstructed) p;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("bad BER encoding: s_resourceReport tag bad");
            }
            if (tagged.numberComponents() != 1) {
                throw new ASN1EncodingException("bad BER encoding: s_resourceReport tag bad");
            }
            s_resourceReport = new ResourceReport(tagged.elementAt(0), true);
            part++;
        }

        // Decoding: partialResultsAvailable [41] IMPLICIT INTEGER OPTIONAL

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 41 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_partialResultsAvailable = new ASN1Integer(p, false);
            part++;
        }

        // Decoding: responseRequired [42] IMPLICIT BOOLEAN

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() != 42 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_responseRequired");
        }

        s_responseRequired = new ASN1Boolean(p, false);
        part++;

        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER

        s_triggeredRequestFlag = null;
        s_otherInfo = null;

        // Decoding: triggeredRequestFlag [43] IMPLICIT BOOLEAN OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 43 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_triggeredRequestFlag = new ASN1Boolean(p, false);
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
     * Returns a BER encoding of the ResourceControlRequest.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of ResourceControlRequest, implicitly tagged.
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
        if (s_suspendedFlag != null) {
            numFields++;
        }
        if (s_resourceReport != null) {
            numFields++;
        }
        if (s_partialResultsAvailable != null) {
            numFields++;
        }
        if (s_triggeredRequestFlag != null) {
            numFields++;
        }
        if (s_otherInfo != null) {
            numFields++;
        }

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding enc[];

        // Encoding s_referenceId: ReferenceId OPTIONAL

        if (s_referenceId != null) {
            fields[x++] = s_referenceId.berEncode();
        }

        // Encoding s_suspendedFlag: BOOLEAN OPTIONAL

        if (s_suspendedFlag != null) {
            fields[x++] = s_suspendedFlag.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 39);
        }

        // Encoding s_resourceReport: ResourceReport OPTIONAL

        if (s_resourceReport != null) {
            enc = new BEREncoding[1];
            enc[0] = s_resourceReport.berEncode();
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 40, enc);
        }

        // Encoding s_partialResultsAvailable: INTEGER OPTIONAL

        if (s_partialResultsAvailable != null) {
            fields[x++] = s_partialResultsAvailable.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 41);
        }

        // Encoding s_responseRequired: BOOLEAN

        fields[x++] = s_responseRequired.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 42);

        // Encoding s_triggeredRequestFlag: BOOLEAN OPTIONAL

        if (s_triggeredRequestFlag != null) {
            fields[x++] = s_triggeredRequestFlag.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 43);
        }

        // Encoding s_otherInfo: OtherInformation OPTIONAL

        if (s_otherInfo != null) {
            fields[x] = s_otherInfo.berEncode();
        }

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the ResourceControlRequest.
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

        if (s_suspendedFlag != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("suspendedFlag ");
            str.append(s_suspendedFlag);
            outputted++;
        }

        if (s_resourceReport != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("resourceReport ");
            str.append(s_resourceReport);
            outputted++;
        }

        if (s_partialResultsAvailable != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("partialResultsAvailable ");
            str.append(s_partialResultsAvailable);
            outputted++;
        }

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("responseRequired ");
        str.append(s_responseRequired);
        outputted++;

        if (s_triggeredRequestFlag != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("triggeredRequestFlag ");
            str.append(s_triggeredRequestFlag);
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
