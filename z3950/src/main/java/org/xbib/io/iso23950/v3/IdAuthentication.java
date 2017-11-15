package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1External;
import org.xbib.asn1.ASN1Null;
import org.xbib.asn1.ASN1VisibleString;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>IdAuthentication</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * IdAuthentication ::=
 * CHOICE {
 *   open VisibleString
 *   idPass IdAuthentication_idPass
 *   anonymous NULL
 *   other EXTERNAL
 * }
 * </pre>
 */

public final class IdAuthentication extends ASN1Any {

    public ASN1VisibleString c_open;
    public IdAuthenticationIdPass c_idPass;
    public ASN1Null c_anonymous;
    public ASN1External c_other;

    /**
     * Default constructor for a IdAuthentication.
     */

    public IdAuthentication() {
    }

    /**
     * Constructor for a IdAuthentication from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public IdAuthentication(BEREncoding ber, boolean checkTag)
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

    public void
    berDecode(BEREncoding ber, boolean checkTag)
            throws ASN1Exception {
        // Null out all choices

        c_open = null;
        c_idPass = null;
        c_anonymous = null;
        c_other = null;

        // Try choice open
        try {
            c_open = new ASN1VisibleString(ber, checkTag);
            return;
        } catch (ASN1Exception e) {
            // failed to decode, continue on
        }

        // Try choice idPass
        try {
            c_idPass = new IdAuthenticationIdPass(ber, checkTag);
            return;
        } catch (ASN1Exception e) {
            // failed to decode, continue on
        }

        // Try choice anonymous
        try {
            c_anonymous = new ASN1Null(ber, checkTag);
            return;
        } catch (ASN1Exception e) {
            // failed to decode, continue on
        }

        // Try choice other
        try {
            c_other = new ASN1External(ber, checkTag);
            return;
        } catch (ASN1Exception e) {
            // failed to decode, continue on
        }

        throw new ASN1Exception("IdAuthentication: bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of IdAuthentication.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */

    public BEREncoding
    berEncode()
            throws ASN1Exception {
        BEREncoding chosen = null;

        // Encoding choice: c_open
        if (c_open != null) {
            chosen = c_open.berEncode();
        }

        // Encoding choice: c_idPass
        if (c_idPass != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_idPass.berEncode();
        }

        // Encoding choice: c_anonymous
        if (c_anonymous != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_anonymous.berEncode();
        }

        // Encoding choice: c_other
        if (c_other != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_other.berEncode();
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

    public BEREncoding
    berEncode(int tagType, int tag)
            throws ASN1Exception {
        // This method must not be called!

        // Method is not available because this is a basic CHOICE
        // which does not have an explicit tag on it. So it is not
        // permitted to allow something else to apply an implicit
        // tag on it, otherwise the tag identifying which CHOICE
        // it is will be overwritten and lost.

        throw new ASN1EncodingException("IdAuthentication: cannot implicitly tag");
    }

    /**
     * Returns a new String object containing a text representing
     * of the IdAuthentication.
     */

    public String
    toString() {
        StringBuilder str = new StringBuilder("{");

        boolean found = false;

        if (c_open != null) {
            found = true;
            str.append("open ");
            str.append(c_open);
        }

        if (c_idPass != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: idPass> ");
            }
            found = true;
            str.append("idPass ");
            str.append(c_idPass);
        }

        if (c_anonymous != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: anonymous> ");
            }
            found = true;
            str.append("anonymous ");
            str.append(c_anonymous);
        }

        if (c_other != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: other> ");
            }
            str.append("other ");
            str.append(c_other);
        }

        str.append("}");

        return str.toString();
    }

}