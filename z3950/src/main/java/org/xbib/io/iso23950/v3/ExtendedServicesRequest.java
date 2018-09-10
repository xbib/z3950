package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1External;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1ObjectIdentifier;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>ExtendedServicesRequest</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * ExtendedServicesRequest ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   function [3] IMPLICIT INTEGER
 *   packageType [4] IMPLICIT OBJECT IDENTIFIER
 *   packageName [5] IMPLICIT InternationalString OPTIONAL
 *   userId [6] IMPLICIT InternationalString OPTIONAL
 *   retentionTime [7] IMPLICIT IntUnit OPTIONAL
 *   permissions [8] IMPLICIT Permissions OPTIONAL
 *   description [9] IMPLICIT InternationalString OPTIONAL
 *   taskSpecificParameters [10] IMPLICIT EXTERNAL OPTIONAL
 *   waitAction [11] IMPLICIT INTEGER
 *   elements ElementSetName OPTIONAL
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */

public final class ExtendedServicesRequest extends ASN1Any {

    public static final int E_CREATE = 1;
    public static final int E_DELETE = 2;
    public static final int E_MODIFY = 3;
    public static final int E_WAIT = 1;
    public static final int E_WAIT_IF_POSSIBLE = 2;
    public static final int E_DONT_WAIT = 3;
    public static final int E_DONT_RETURN_PACKAGE = 4;
    public ReferenceId sReferenceId; // optional
    public ASN1Integer sFunction;
    public ASN1ObjectIdentifier sPackageType;
    public InternationalString sPackageName; // optional
    public InternationalString sUserId; // optional
    public IntUnit sRetentionTime; // optional
    public Permissions sPermissions; // optional
    public InternationalString sDescription; // optional
    public ASN1External sTaskSpecificParameters; // optional
    public ASN1Integer sWaitAction;
    public ElementSetName sElements; // optional
    public OtherInformation sOtherInfo; // optional

    /**
     * Constructor for a ExtendedServicesRequest from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public ExtendedServicesRequest(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
            sReferenceId = new ReferenceId(p, true);
            part++;
        } catch (ASN1Exception e) {
            sReferenceId = null; // no, not present
        }
        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 3 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_function");
        }
        sFunction = new ASN1Integer(p, false);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 4 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_packageType");
        }
        sPackageType = new ASN1ObjectIdentifier(p, false);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 5 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            sPackageName = new InternationalString(p, false);
            part++;
        }
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 6 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            sUserId = new InternationalString(p, false);
            part++;
        }
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 7 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            sRetentionTime = new IntUnit(p, false);
            part++;
        }
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 8 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            sPermissions = new Permissions(p, false);
            part++;
        }
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 9 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            sDescription = new InternationalString(p, false);
            part++;
        }
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 10 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            sTaskSpecificParameters = new ASN1External(p, false);
            part++;
        }
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 11 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in waitAction");
        }
        sWaitAction = new ASN1Integer(p, false);
        part++;
        sElements = null;
        sOtherInfo = null;
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        try {
            sElements = new ElementSetName(p, true);
            part++;
        } catch (ASN1Exception e) {
            sElements = null;
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
            throw new ASN1Exception("bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the ExtendedServicesRequest.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of ExtendedServicesRequest, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numFields = 3;
        if (sReferenceId != null) {
            numFields++;
        }
        if (sPackageName != null) {
            numFields++;
        }
        if (sUserId != null) {
            numFields++;
        }
        if (sRetentionTime != null) {
            numFields++;
        }
        if (sPermissions != null) {
            numFields++;
        }
        if (sDescription != null) {
            numFields++;
        }
        if (sTaskSpecificParameters != null) {
            numFields++;
        }
        if (sElements != null) {
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
        fields[x++] = sFunction.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 3);
        fields[x++] = sPackageType.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 4);
        if (sPackageName != null) {
            fields[x++] = sPackageName.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 5);
        }
        if (sUserId != null) {
            fields[x++] = sUserId.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 6);
        }
        if (sRetentionTime != null) {
            fields[x++] = sRetentionTime.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 7);
        }
        if (sPermissions != null) {
            fields[x++] = sPermissions.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 8);
        }
        if (sDescription != null) {
            fields[x++] = sDescription.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 9);
        }
        if (sTaskSpecificParameters != null) {
            fields[x++] = sTaskSpecificParameters.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 10);
        }
        fields[x++] = sWaitAction.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 11);
        if (sElements != null) {
            fields[x++] = sElements.berEncode();
        }
        if (sOtherInfo != null) {
            fields[x] = sOtherInfo.berEncode();
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the ExtendedServicesRequest.
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
        str.append("function ");
        str.append(sFunction);
        outputted++;
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("packageType ");
        str.append(sPackageType);
        outputted++;
        if (sPackageName != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("packageName ");
            str.append(sPackageName);
            outputted++;
        }
        if (sUserId != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("userId ");
            str.append(sUserId);
            outputted++;
        }
        if (sRetentionTime != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("retentionTime ");
            str.append(sRetentionTime);
            outputted++;
        }
        if (sPermissions != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("permissions ");
            str.append(sPermissions);
            outputted++;
        }
        if (sDescription != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("description ");
            str.append(sDescription);
            outputted++;
        }
        if (sTaskSpecificParameters != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("taskSpecificParameters ");
            str.append(sTaskSpecificParameters);
            outputted++;
        }
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("waitAction ");
        str.append(sWaitAction);
        outputted++;
        if (sElements != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("elements ");
            str.append(sElements);
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
