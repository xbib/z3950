package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1External;
import org.xbib.asn1.ASN1OctetString;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>FragmentSyntax</code> from <code>Z39-50-APDU-1995</code>
 * <pre>
 * FragmentSyntax ::=
 * CHOICE {
 *   externallyTagged EXTERNAL
 *   notExternallyTagged OCTET STRING
 * }
 * </pre>
 */
public final class FragmentSyntax extends ASN1Any {

    public ASN1External c_externallyTagged;
    public ASN1OctetString c_notExternallyTagged;

    /**
     * Constructor for a FragmentSyntax from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public FragmentSyntax(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        c_externallyTagged = null;
        c_notExternallyTagged = null;
        try {
            c_externallyTagged = new ASN1External(ber, checkTag);
            return;
        } catch (ASN1Exception e) {
            // failed to decode, continue on
        }
        try {
            c_notExternallyTagged = new ASN1OctetString(ber, checkTag);
            return;
        } catch (ASN1Exception e) {
            // failed to decode, continue on
        }
        throw new ASN1Exception("FragmentSyntax: bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of FragmentSyntax.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        BEREncoding chosen = null;
        if (c_externallyTagged != null) {
            chosen = c_externallyTagged.berEncode();
        }
        if (c_notExternallyTagged != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_notExternallyTagged.berEncode();
        }
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
     * of the FragmentSyntax.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        boolean found = false;
        if (c_externallyTagged != null) {
            found = true;
            str.append("externallyTagged ");
            str.append(c_externallyTagged);
        }
        if (c_notExternallyTagged != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: notExternallyTagged> ");
            }
            str.append("notExternallyTagged ");
            str.append(c_notExternallyTagged);
        }
        str.append("}");
        return str.toString();
    }
}
