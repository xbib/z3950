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
    public ReferenceId referenceId; // optional
    public DatabaseName[] databaseNames;
    public AttributeSetId attributeSet; // optional
    public AttributesPlusTerm termListAndStartPoint;
    public ASN1Integer stepSize; // optional
    public ASN1Integer numberOfTermsRequested;
    public ASN1Integer preferredPositionInResponse; // optional
    public OtherInformation otherInfo; // optional

    public ScanRequest() {
    }

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
    @Override
    public void berDecode(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("bad BER form");
        }
        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        if (numParts <= part) {
            throw new ASN1Exception("ScanRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        try {
            referenceId = new ReferenceId(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            referenceId = null; // no, not present
        }
        if (numParts <= part) {
            throw new ASN1Exception("ScanRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 3 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_databaseNames");
        }
        try {
            BERConstructed cons = (BERConstructed) p;
            int parts = cons.numberComponents();
            databaseNames = new DatabaseName[parts];
            int n;
            for (n = 0; n < parts; n++) {
                databaseNames[n] = new DatabaseName(cons.elementAt(n), true);
            }
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("bad BER");
        }
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        try {
            attributeSet = new AttributeSetId(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            attributeSet = null; // no, not present
        }
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        termListAndStartPoint = new AttributesPlusTerm(p, true);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 5 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            stepSize = new ASN1Integer(p, false);
            part++;
        }
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 6 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_numberOfTermsRequested");
        }
        numberOfTermsRequested = new ASN1Integer(p, false);
        part++;
        preferredPositionInResponse = null;
        otherInfo = null;
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 7 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            preferredPositionInResponse = new ASN1Integer(p, false);
            part++;
        }
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
        if (part < numParts) {
            throw new ASN1Exception("bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the ScanRequest.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
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
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numFields = 3;
        if (referenceId != null) {
            numFields++;
        }
        if (attributeSet != null) {
            numFields++;
        }
        if (stepSize != null) {
            numFields++;
        }
        if (preferredPositionInResponse != null) {
            numFields++;
        }
        if (otherInfo != null) {
            numFields++;
        }
        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding f2[];
        int p;
        if (referenceId != null) {
            fields[x++] = referenceId.berEncode();
        }
        f2 = new BEREncoding[databaseNames.length];
        for (p = 0; p < databaseNames.length; p++) {
            f2[p] = databaseNames[p].berEncode();
        }
        fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 3, f2);
        if (attributeSet != null) {
            fields[x++] = attributeSet.berEncode();
        }
        fields[x++] = termListAndStartPoint.berEncode();
        if (stepSize != null) {
            fields[x++] = stepSize.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 5);
        }
        fields[x++] = numberOfTermsRequested.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 6);
        if (preferredPositionInResponse != null) {
            fields[x++] = preferredPositionInResponse.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 7);
        }
        if (otherInfo != null) {
            fields[x] = otherInfo.berEncode();
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the ScanRequest.
     */
    @Override
    public String toString() {
        int p;
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
        str.append("databaseNames ");
        str.append("{");
        for (p = 0; p < databaseNames.length; p++) {
            if (p != 0) {
                str.append(", ");
            }
            str.append(databaseNames[p]);
        }
        str.append("}");
        outputted++;
        if (attributeSet != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("attributeSet ");
            str.append(attributeSet);
            outputted++;
        }
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("termListAndStartPoint ");
        str.append(termListAndStartPoint);
        outputted++;
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
        str.append("numberOfTermsRequested ");
        str.append(numberOfTermsRequested);
        outputted++;
        if (preferredPositionInResponse != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("preferredPositionInResponse ");
            str.append(preferredPositionInResponse);
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
