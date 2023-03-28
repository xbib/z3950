package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>SortResponse</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * SortResponse ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   sortStatus [3] IMPLICIT INTEGER
 *   resultSetStatus [4] IMPLICIT INTEGER OPTIONAL
 *   diagnostics [5] IMPLICIT SEQUENCE OF DiagRec OPTIONAL
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */
public final class SortResponse extends ASN1Any {

    public static final int E_success = 0;

    public static final int E_partial_1 = 1;

    public static final int E_failure = 2;

    public static final int E_empty = 1;

    public static final int E_interim = 2;

    public static final int E_unchanged = 3;

    public static final int E_none = 4;

    public ReferenceId s_referenceId; // optional

    public ASN1Integer s_sortStatus;

    public ASN1Integer s_resultSetStatus; // optional

    public DiagRec[] s_diagnostics; // optional

    public OtherInformation s_otherInfo; // optional

    public SortResponse() {
    }

    /**
     * Constructor for a SortResponse from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public SortResponse(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // SortResponse should be encoded by a constructed BER

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
            throw new ASN1Exception(" incomplete");
        }
        p = berConstructed.elementAt(part);
        try {
            s_referenceId = new ReferenceId(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_referenceId = null; // no, not present
        }
        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() != 3 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in sortStatus");
        }

        s_sortStatus = new ASN1Integer(p, false);
        part++;

        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER

        s_resultSetStatus = null;
        s_diagnostics = null;
        s_otherInfo = null;

        // Decoding: resultSetStatus [4] IMPLICIT INTEGER OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 4 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_resultSetStatus = new ASN1Integer(p, false);
            part++;
        }

        // Decoding: diagnostics [5] IMPLICIT SEQUENCE OF DiagRec OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 5 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                BERConstructed cons = (BERConstructed) p;
                int parts = cons.numberComponents();
                s_diagnostics = new DiagRec[parts];
                int n;
                for (n = 0; n < parts; n++) {
                    s_diagnostics[n] = new DiagRec(cons.elementAt(n), true);
                }
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("bad BER");
            }
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
            throw new ASN1Exception("SortResponse: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the SortResponse.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of SortResponse, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numFields = 1; // number of mandatories
        if (s_referenceId != null) {
            numFields++;
        }
        if (s_resultSetStatus != null) {
            numFields++;
        }
        if (s_diagnostics != null) {
            numFields++;
        }
        if (s_otherInfo != null) {
            numFields++;
        }
        BEREncoding[] fields = new BEREncoding[numFields];
        int x = 0;
        BEREncoding[] f2;
        int p;
        if (s_referenceId != null) {
            fields[x++] = s_referenceId.berEncode();
        }
        fields[x++] = s_sortStatus.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 3);
        if (s_resultSetStatus != null) {
            fields[x++] = s_resultSetStatus.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 4);
        }
        if (s_diagnostics != null) {
            f2 = new BEREncoding[s_diagnostics.length];
            for (p = 0; p < s_diagnostics.length; p++) {
                f2[p] = s_diagnostics[p].berEncode();
            }
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 5, f2);
        }
        if (s_otherInfo != null) {
            fields[x] = s_otherInfo.berEncode();
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the SortResponse.
     */
    @Override
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
        str.append("sortStatus ");
        str.append(s_sortStatus);
        outputted++;
        if (s_resultSetStatus != null) {
            str.append(", ");
            str.append("resultSetStatus ");
            str.append(s_resultSetStatus);
            outputted++;
        }
        if (s_diagnostics != null) {
            str.append(", ");
            str.append("diagnostics ");
            str.append("{");
            for (p = 0; p < s_diagnostics.length; p++) {
                if (p != 0) {
                    str.append(", ");
                }
                str.append(s_diagnostics[p]);
            }
            str.append("}");
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
