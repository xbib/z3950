package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1External;
import org.xbib.asn1.ASN1ObjectIdentifier;
import org.xbib.asn1.ASN1OctetString;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>OtherInformation_information</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * OtherInformation_information ::=
 * CHOICE {
 *   characterInfo [2] IMPLICIT InternationalString
 *   binaryInfo [3] IMPLICIT OCTET STRING
 *   externallyDefinedInfo [4] IMPLICIT EXTERNAL
 *   oid [5] IMPLICIT OBJECT IDENTIFIER
 * }
 * </pre>
 */
public final class OtherInformationInformation extends ASN1Any {

    public InternationalString c_characterInfo;
    public ASN1OctetString c_binaryInfo;
    public ASN1External c_externallyDefinedInfo;
    public ASN1ObjectIdentifier c_oid;

    /**
     * Constructor for a OtherInformation_information from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public OtherInformationInformation(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // Null out all choices

        c_characterInfo = null;
        c_binaryInfo = null;
        c_externallyDefinedInfo = null;
        c_oid = null;

        // Try choice characterInfo
        if (ber.getTag() == 2 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_characterInfo = new InternationalString(ber, false);
            return;
        }

        // Try choice binaryInfo
        if (ber.getTag() == 3 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_binaryInfo = new ASN1OctetString(ber, false);
            return;
        }

        // Try choice externallyDefinedInfo
        if (ber.getTag() == 4 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_externallyDefinedInfo = new ASN1External(ber, false);
            return;
        }

        // Try choice oid
        if (ber.getTag() == 5 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_oid = new ASN1ObjectIdentifier(ber, false);
            return;
        }

        throw new ASN1Exception("bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of OtherInformation_information.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        BEREncoding chosen = null;

        // Encoding choice: c_characterInfo
        if (c_characterInfo != null) {
            chosen = c_characterInfo.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 2);
        }

        // Encoding choice: c_binaryInfo
        if (c_binaryInfo != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_binaryInfo.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 3);
        }

        // Encoding choice: c_externallyDefinedInfo
        if (c_externallyDefinedInfo != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_externallyDefinedInfo.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 4);
        }

        // Encoding choice: c_oid
        if (c_oid != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_oid.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 5);
        }

        // Check for error of having none of the choices set
        if (chosen == null) {
            throw new ASN1Exception("CHOICE not set");
        }
        return chosen;
    }

    /**
     * Generating a BER encoding of the object
     * and implicitly tagging it.
     * This method is for internal use only. You should use
     * the berEncode method that does not take a parameter.
     * This function should never be used, because this
     * production is a CHOICE.
     * It must never have an implicit tag.
     * An exception will be thrown if it is called.
     *
     * @param tagType the type of the tag.
     * @param tag      the tag.
     * @throws ASN1Exception if it cannot be BER encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        // This method must not be called!

        // Method is not available because this is a basic CHOICE
        // which does not have an explicit tag on it. So it is not
        // permitted to allow something else to apply an implicit
        // tag on it, otherwise the tag identifying which CHOICE
        // it is will be overwritten and lost.

        throw new ASN1EncodingException("cannot implicitly tag");
    }

    /**
     * Returns a new String object containing a text representing
     * of the OtherInformation_information.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        boolean found = false;
        if (c_characterInfo != null) {
            found = true;
            str.append("characterInfo ");
            str.append(c_characterInfo);
        }

        if (c_binaryInfo != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: binaryInfo> ");
            }
            found = true;
            str.append("binaryInfo ");
            str.append(c_binaryInfo);
        }

        if (c_externallyDefinedInfo != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: externallyDefinedInfo> ");
            }
            found = true;
            str.append("externallyDefinedInfo ");
            str.append(c_externallyDefinedInfo);
        }

        if (c_oid != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: oid> ");
            }
            found = true;
            str.append("oid ");
            str.append(c_oid);
        }
        str.append("}");
        return str.toString();
    }
}
