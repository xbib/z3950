package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>AccessControlResponse</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * AccessControlResponse ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   securityChallengeResponse AccessControlResponse_securityChallengeResponse OPTIONAL
 *   diagnostic [223] EXPLICIT DiagRec OPTIONAL
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */
public final class AccessControlResponse extends ASN1Any {

    public ReferenceId referenceId; // optional

    public AccessControlResponseSecurityChallengeResponse securityChallengeResponse; // optional

    public DiagRec diagRec; // optional

    public OtherInformation otherInformation; // optional

    /**
     * Constructor for a AccessControlResponse from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public AccessControlResponse(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
            throw new ASN1EncodingException("AccessControlResponse: bad BER form\n");
        }
        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        BERConstructed tagged;
        referenceId = null;
        securityChallengeResponse = null;
        diagRec = null;
        otherInformation = null;
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        try {
            referenceId = new ReferenceId(p, true);
            part++;
        } catch (ASN1Exception e) {
            referenceId = null;
        }
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        try {
            securityChallengeResponse = new AccessControlResponseSecurityChallengeResponse(p, true);
            part++;
        } catch (ASN1Exception e) {
            securityChallengeResponse = null;
        }
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 223 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagged = (BERConstructed) p;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("AccessControlResponse: bad BER encoding: s_diagnostic tag bad\n");
            }
            if (tagged.numberComponents() != 1) {
                throw new ASN1EncodingException("AccessControlResponse: bad BER encoding: s_diagnostic tag bad\n");
            }
            diagRec = new DiagRec(tagged.elementAt(0), true);
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
            throw new ASN1Exception("AccessControlResponse: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the AccessControlResponse.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of AccessControlResponse, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numFields = 0; // number of mandatories
        if (referenceId != null) {
            numFields++;
        }
        if (securityChallengeResponse != null) {
            numFields++;
        }
        if (diagRec != null) {
            numFields++;
        }
        if (otherInformation != null) {
            numFields++;
        }
        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding enc[];
        if (referenceId != null) {
            fields[x++] = referenceId.berEncode();
        }
        if (securityChallengeResponse != null) {
            fields[x++] = securityChallengeResponse.berEncode();
        }
        if (diagRec != null) {
            enc = new BEREncoding[1];
            enc[0] = diagRec.berEncode();
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 223, enc);
        }
        if (otherInformation != null) {
            fields[x] = otherInformation.berEncode();
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the AccessControlResponse.
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
        if (securityChallengeResponse != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("securityChallengeResponse ");
            str.append(securityChallengeResponse);
            outputted++;
        }
        if (diagRec != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("diagnostic ");
            str.append(diagRec);
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
