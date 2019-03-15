package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>DeleteResultSetRequest</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * DeleteResultSetRequest ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   deleteFunction [32] IMPLICIT INTEGER
 *   resultSetList SEQUENCE OF ResultSetId OPTIONAL
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */

public final class DeleteResultSetRequest extends ASN1Any {

    // Enumerated constants for deleteFunction
    public static final int E_LIST = 0;
    public static final int E_ALL = 1;
    public ReferenceId referenceId; // optional
    public ASN1Integer sDeleteFunction;
    public ResultSetId[] sResultSetList; // optional
    public OtherInformation sOtherInfo; // optional

    /**
     * Constructor for a DeleteResultSetRequest from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public DeleteResultSetRequest(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        try {
            referenceId = new ReferenceId(p, true);
            part++;
        } catch (ASN1Exception e) {
            referenceId = null;
        }
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 32 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_deleteFunction");
        }
        sDeleteFunction = new ASN1Integer(p, false);
        part++;
        sResultSetList = null;
        sOtherInfo = null;
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        try {
            try {
                BERConstructed cons = (BERConstructed) p;
                int parts = cons.numberComponents();
                sResultSetList = new ResultSetId[parts];
                int n;
                for (n = 0; n < parts; n++) {
                    sResultSetList[n] = new ResultSetId(cons.elementAt(n), true);
                }
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("bad BER");
            }
            part++;
        } catch (ASN1Exception e) {
            sResultSetList = null;
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
     * Returns a BER encoding of the DeleteResultSetRequest.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of DeleteResultSetRequest, implicitly tagged.
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
        if (sResultSetList != null) {
            numFields++;
        }
        if (sOtherInfo != null) {
            numFields++;
        }
        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding f2[];
        int p;
        if (referenceId != null) {
            fields[x++] = referenceId.berEncode();
        }
        fields[x++] = sDeleteFunction.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 32);
        if (sResultSetList != null) {
            f2 = new BEREncoding[sResultSetList.length];
            for (p = 0; p < sResultSetList.length; p++) {
                f2[p] = sResultSetList[p].berEncode();
            }
            fields[x++] = new BERConstructed(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG, f2);
        }
        if (sOtherInfo != null) {
            fields[x] = sOtherInfo.berEncode();
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the DeleteResultSetRequest.
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
        str.append("deleteFunction ");
        str.append(sDeleteFunction);
        outputted++;
        if (sResultSetList != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("resultSetList ");
            str.append("{");
            for (p = 0; p < sResultSetList.length; p++) {
                if (p != 0) {
                    str.append(", ");
                }
                str.append(sResultSetList[p]);
            }
            str.append("}");
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
