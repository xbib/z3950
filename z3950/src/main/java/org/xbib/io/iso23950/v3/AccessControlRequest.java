package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>AccessControlRequest</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * AccessControlRequest ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   securityChallenge AccessControlRequest_securityChallenge
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */
public final class AccessControlRequest extends ASN1Any {

    public ReferenceId sReferenceId; // optional

    public AccessControlRequestSecurityChallenge sSecurityChallenge;

    public OtherInformation sOtherInfo; // optional

    /**
     * Constructor for a AccessControlRequest from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public AccessControlRequest(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
            throw new ASN1EncodingException("AccessControlRequest: bad BER form\n");
        }
        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        if (numParts <= part) {
            throw new ASN1Exception("AccessControlRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        try {
            sReferenceId = new ReferenceId(p, true);
            part++;
        } catch (ASN1Exception e) {
            sReferenceId = null; // no, not present
        }
        if (numParts <= part) {
            throw new ASN1Exception("AccessControlRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        sSecurityChallenge = new AccessControlRequestSecurityChallenge(p, true);
        part++;
        sOtherInfo = null;
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
            throw new ASN1Exception("AccessControlRequest: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the AccessControlRequest.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of AccessControlRequest, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numFields = 1;
        if (sReferenceId != null) {
            numFields++;
        }
        if (sOtherInfo != null) {
            numFields++;
        }
        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        if (sReferenceId != null) {
            fields[x++] = sReferenceId.berEncode();
        }
        fields[x++] = sSecurityChallenge.berEncode();
        if (sOtherInfo != null) {
            fields[x] = sOtherInfo.berEncode();
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the AccessControlRequest.
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
        str.append("securityChallenge ");
        str.append(sSecurityChallenge);
        outputted++;
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
