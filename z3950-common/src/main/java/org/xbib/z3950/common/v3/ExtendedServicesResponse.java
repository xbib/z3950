package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1External;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>ExtendedServicesResponse</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * ExtendedServicesResponse ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   operationStatus [3] IMPLICIT INTEGER
 *   diagnostics [4] IMPLICIT SEQUENCE OF DiagRec OPTIONAL
 *   taskPackage [5] IMPLICIT EXTERNAL OPTIONAL
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */
public final class ExtendedServicesResponse extends ASN1Any {

    public static final int E_DONE = 1;
    public static final int E_ACCEPTED = 2;
    public static final int E_FAILURE = 3;
    public ReferenceId sReferenceId; // optional
    public ASN1Integer sOperationStatus;
    public DiagRec[] sDiagnostics; // optional
    public ASN1External sTaskPackage; // optional
    public OtherInformation sOtherInfo; // optional

    /**
     * Constructor for a ExtendedServicesResponse from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public ExtendedServicesResponse(BEREncoding ber, boolean checkTag)
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
            throw new ASN1EncodingException("bad BER form");
        }
        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        try {
            sReferenceId = new ReferenceId(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            sReferenceId = null; // no, not present
        }
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 3 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in operationStatus");
        }
        sOperationStatus = new ASN1Integer(p, false);
        part++;
        sDiagnostics = null;
        sTaskPackage = null;
        sOtherInfo = null;
        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 4 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                BERConstructed cons = (BERConstructed) p;
                int parts = cons.numberComponents();
                sDiagnostics = new DiagRec[parts];
                int n;
                for (n = 0; n < parts; n++) {
                    sDiagnostics[n] = new DiagRec(cons.elementAt(n), true);
                }
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("Bad BER");
            }
            part++;
        }
        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 5 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            sTaskPackage = new ASN1External(p, false);
            part++;
        }
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        try {
            sOtherInfo = new OtherInformation(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            sOtherInfo = null; // no, not present
        }
        if (part < numParts) {
            throw new ASN1Exception("bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the ExtendedServicesResponse.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of ExtendedServicesResponse, implicitly tagged.
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
        if (sDiagnostics != null) {
            numFields++;
        }
        if (sTaskPackage != null) {
            numFields++;
        }
        if (sOtherInfo != null) {
            numFields++;
        }
        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding f2[];
        int p;
        if (sReferenceId != null) {
            fields[x++] = sReferenceId.berEncode();
        }
        fields[x++] = sOperationStatus.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 3);
        if (sDiagnostics != null) {
            f2 = new BEREncoding[sDiagnostics.length];
            for (p = 0; p < sDiagnostics.length; p++) {
                f2[p] = sDiagnostics[p].berEncode();
            }
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 4, f2);
        }
        if (sTaskPackage != null) {
            fields[x++] = sTaskPackage.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 5);
        }
        if (sOtherInfo != null) {
            fields[x] = sOtherInfo.berEncode();
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the ExtendedServicesResponse.
     */
    @Override
    public String toString() {
        int p;
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
        str.append("operationStatus ");
        str.append(sOperationStatus);
        outputted++;
        if (sDiagnostics != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("diagnostics ");
            str.append("{");
            for (p = 0; p < sDiagnostics.length; p++) {
                if (p != 0) {
                    str.append(", ");
                }
                str.append(sDiagnostics[p]);
            }
            str.append("}");
            outputted++;
        }
        if (sTaskPackage != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("taskPackage ");
            str.append(sTaskPackage);
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
