package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1External;
import org.xbib.asn1.ASN1OctetString;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>AccessControlRequest_securityChallenge</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * AccessControlRequest_securityChallenge ::=
 * CHOICE {
 *   simpleForm [37] IMPLICIT OCTET STRING
 *   externallyDefined [0] EXPLICIT EXTERNAL
 * }
 * </pre>
 */
public final class AccessControlRequestSecurityChallenge extends ASN1Any {

    public ASN1OctetString cSimpleForm;

    public ASN1External cExternallyDefined;

    /**
     * Constructor for a AccessControlRequest_securityChallenge from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public AccessControlRequestSecurityChallenge(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        BERConstructed tagwrapper;
        cSimpleForm = null;
        cExternallyDefined = null;
        if (ber.getTag() == 37 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            cSimpleForm = new ASN1OctetString(ber, false);
            return;
        }
        if (ber.getTag() == 0 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagwrapper = (BERConstructed) ber;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("AccessControlRequest_securityChallenge: bad BER form\n");
            }
            if (tagwrapper.numberComponents() != 1) {
                throw new ASN1EncodingException("AccessControlRequest_securityChallenge: bad BER form\n");
            }
            cExternallyDefined = new ASN1External(tagwrapper.elementAt(0), true);
            return;
        }
        throw new ASN1Exception("AccessControlRequest_securityChallenge: bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of AccessControlRequest_securityChallenge.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        BEREncoding chosen = null;
        BEREncoding[] enc;
        if (cSimpleForm != null) {
            chosen = cSimpleForm.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 37);
        }
        if (cExternallyDefined != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            enc = new BEREncoding[1];
            enc[0] = cExternallyDefined.berEncode();
            chosen = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 0, enc);
        }
        if (chosen == null) {
            throw new ASN1Exception("CHOICE not set");
        }
        return chosen;
    }

    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        throw new ASN1EncodingException("AccessControlRequest_securityChallenge: cannot implicitly tag");
    }

    /**
     * Returns a new String object containing a text representing
     * of the AccessControlRequest_securityChallenge.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        boolean found = false;
        if (cSimpleForm != null) {
            found = true;
            str.append("simpleForm ");
            str.append(cSimpleForm);
        }
        if (cExternallyDefined != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: externallyDefined> ");
            }
            str.append("externallyDefined ");
            str.append(cExternallyDefined);
        }
        str.append("}");
        return str.toString();
    }
}
