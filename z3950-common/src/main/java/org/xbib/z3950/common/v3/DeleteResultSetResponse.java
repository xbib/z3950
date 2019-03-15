package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>DeleteResultSetResponse</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * DeleteResultSetResponse ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   deleteOperationStatus [0] IMPLICIT DeleteSetStatus
 *   deleteListStatuses [1] IMPLICIT ListStatuses OPTIONAL
 *   numberNotDeleted [34] IMPLICIT INTEGER OPTIONAL
 *   bulkStatuses [35] IMPLICIT ListStatuses OPTIONAL
 *   deleteMessage [36] IMPLICIT InternationalString OPTIONAL
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */

public final class DeleteResultSetResponse extends ASN1Any {

    public ReferenceId sReferenceId; // optional
    public DeleteSetStatus sDeleteOperationStatus;
    public ListStatuses sDeleteListStatuses; // optional
    public ASN1Integer sNumberNotDeleted; // optional
    public ListStatuses sBulkStatuses; // optional
    public InternationalString sDeleteMessage; // optional
    public OtherInformation sOtherInfo; // optional

    /**
     * Constructor for a DeleteResultSetResponse from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public DeleteResultSetResponse(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
            throw new ASN1EncodingException("DeleteResultSetResponse: bad BER form\n");
        }
        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        if (numParts <= part) {
            throw new ASN1Exception("DeleteResultSetResponse: incomplete");
        }
        p = berConstructed.elementAt(part);
        try {
            sReferenceId = new ReferenceId(p, true);
            part++;
        } catch (ASN1Exception e) {
            sReferenceId = null;
        }
        if (numParts <= part) {
            throw new ASN1Exception("DeleteResultSetResponse: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() != 0 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("DeleteResultSetResponse: bad tag in s_deleteOperationStatus\n");
        }
        sDeleteOperationStatus = new DeleteSetStatus(p, false);
        part++;
        sDeleteListStatuses = null;
        sNumberNotDeleted = null;
        sBulkStatuses = null;
        sDeleteMessage = null;
        sOtherInfo = null;
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 1 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            sDeleteListStatuses = new ListStatuses(p, false);
            part++;
        }
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 34 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            sNumberNotDeleted = new ASN1Integer(p, false);
            part++;
        }
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 35 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            sBulkStatuses = new ListStatuses(p, false);
            part++;
        }
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 36 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            sDeleteMessage = new InternationalString(p, false);
            part++;
        }
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        try {
            sOtherInfo = new OtherInformation(p, true);
            part++;
        } catch (ASN1Exception e) {
            sOtherInfo = null; // no, not present
        }
        if (part < numParts) {
            throw new ASN1Exception("DeleteResultSetResponse: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the DeleteResultSetResponse.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of DeleteResultSetResponse, implicitly tagged.
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
        if (sDeleteListStatuses != null) {
            numFields++;
        }
        if (sNumberNotDeleted != null) {
            numFields++;
        }
        if (sBulkStatuses != null) {
            numFields++;
        }
        if (sDeleteMessage != null) {
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
        fields[x++] = sDeleteOperationStatus.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 0);
        if (sDeleteListStatuses != null) {
            fields[x++] = sDeleteListStatuses.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
        }
        if (sNumberNotDeleted != null) {
            fields[x++] = sNumberNotDeleted.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 34);
        }
        if (sBulkStatuses != null) {
            fields[x++] = sBulkStatuses.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 35);
        }
        if (sDeleteMessage != null) {
            fields[x++] = sDeleteMessage.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 36);
        }
        if (sOtherInfo != null) {
            fields[x] = sOtherInfo.berEncode();
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the DeleteResultSetResponse.
     */
    @Override
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
        str.append("deleteOperationStatus ");
        str.append(sDeleteOperationStatus);
        outputted++;
        if (sDeleteListStatuses != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("deleteListStatuses ");
            str.append(sDeleteListStatuses);
            outputted++;
        }
        if (sNumberNotDeleted != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("numberNotDeleted ");
            str.append(sNumberNotDeleted);
            outputted++;
        }
        if (sBulkStatuses != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("bulkStatuses ");
            str.append(sBulkStatuses);
            outputted++;
        }
        if (sDeleteMessage != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("deleteMessage ");
            str.append(sDeleteMessage);
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
