package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>ScanRequest</code> from <code>Z39-50-APDU-1995</code>
 * <pre>
 * ScanRequest ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   databaseNames [3] IMPLICIT SEQUENCE OF DatabaseName
 *   attributeSet AttributeSetId OPTIONAL
 *   termListAndStartPoint AttributesPlusTerm
 *   stepSize [5] IMPLICIT INTEGER OPTIONAL
 *   numberOfTermsRequested [6] IMPLICIT INTEGER
 *   preferredPositionInResponse [7] IMPLICIT INTEGER OPTIONAL
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */
public final class ScanRequest extends ASN1Any {
    public ReferenceId s_referenceId; // optional
    public DatabaseName s_databaseNames[];
    public AttributeSetId s_attributeSet; // optional
    public AttributesPlusTerm s_termListAndStartPoint;
    public ASN1Integer s_stepSize; // optional
    public ASN1Integer s_numberOfTermsRequested;
    public ASN1Integer s_preferredPositionInResponse; // optional
    public OtherInformation s_otherInfo; // optional
    /**
     * Constructor for a ScanRequest from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public ScanRequest(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
            throw new ASN1EncodingException("ScanRequest: bad BER form\n");
        }
        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        if (numParts <= part) {
            throw new ASN1Exception("ScanRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        try {
            s_referenceId = new ReferenceId(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_referenceId = null; // no, not present
        }
        if (numParts <= part) {
            throw new ASN1Exception("ScanRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() != 3 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("ScanRequest: bad tag in s_databaseNames\n");
        }
        try {
            BERConstructed cons = (BERConstructed) p;
            int parts = cons.numberComponents();
            s_databaseNames = new DatabaseName[parts];
            int n;
            for (n = 0; n < parts; n++) {
                s_databaseNames[n] = new DatabaseName(cons.elementAt(n), true);
            }
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("Bad BER");
        }
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("ScanRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        try {
            s_attributeSet = new AttributeSetId(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_attributeSet = null; // no, not present
        }
        if (numParts <= part) {
            throw new ASN1Exception("ScanRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        s_termListAndStartPoint = new AttributesPlusTerm(p, true);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("ScanRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() == 5 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_stepSize = new ASN1Integer(p, false);
            part++;
        }
        if (numParts <= part) {
            throw new ASN1Exception("ScanRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() != 6 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException
                    ("ScanRequest: bad tag in s_numberOfTermsRequested\n");
        }
        s_numberOfTermsRequested = new ASN1Integer(p, false);
        part++;
        s_preferredPositionInResponse = null;
        s_otherInfo = null;
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() == 7 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_preferredPositionInResponse = new ASN1Integer(p, false);
            part++;
        }
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
        if (part < numParts) {
            throw new ASN1Exception("ScanRequest: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the ScanRequest.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of ScanRequest, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numFields = 3;
        if (s_referenceId != null) {
            numFields++;
        }
        if (s_attributeSet != null) {
            numFields++;
        }
        if (s_stepSize != null) {
            numFields++;
        }
        if (s_preferredPositionInResponse != null) {
            numFields++;
        }
        if (s_otherInfo != null) {
            numFields++;
        }
        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding f2[];
        int p;
        if (s_referenceId != null) {
            fields[x++] = s_referenceId.berEncode();
        }
        f2 = new BEREncoding[s_databaseNames.length];
        for (p = 0; p < s_databaseNames.length; p++) {
            f2[p] = s_databaseNames[p].berEncode();
        }
        fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 3, f2);
        if (s_attributeSet != null) {
            fields[x++] = s_attributeSet.berEncode();
        }
        fields[x++] = s_termListAndStartPoint.berEncode();
        if (s_stepSize != null) {
            fields[x++] = s_stepSize.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 5);
        }
        fields[x++] = s_numberOfTermsRequested.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 6);
        if (s_preferredPositionInResponse != null) {
            fields[x++] = s_preferredPositionInResponse.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 7);
        }
        if (s_otherInfo != null) {
            fields[x] = s_otherInfo.berEncode();
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the ScanRequest.
     */
    public String toString() {
        int p;
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
        str.append("databaseNames ");
        str.append("{");
        for (p = 0; p < s_databaseNames.length; p++) {
            if (p != 0) {
                str.append(", ");
            }
            str.append(s_databaseNames[p]);
        }
        str.append("}");
        outputted++;
        if (s_attributeSet != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("attributeSet ");
            str.append(s_attributeSet);
            outputted++;
        }
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("termListAndStartPoint ");
        str.append(s_termListAndStartPoint);
        outputted++;
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
        str.append("numberOfTermsRequested ");
        str.append(s_numberOfTermsRequested);
        outputted++;
        if (s_preferredPositionInResponse != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("preferredPositionInResponse ");
            str.append(s_preferredPositionInResponse);
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
