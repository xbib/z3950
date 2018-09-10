package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1External;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>Specification_elementSpec</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * Specification_elementSpec ::=
 * CHOICE {
 *   elementSetName [1] IMPLICIT InternationalString
 *   externalEspec [2] IMPLICIT EXTERNAL
 * }
 * </pre>
 */
public final class SpecificationElementSpec extends ASN1Any {

    public InternationalString c_elementSetName;
    public ASN1External c_externalEspec;

    /**
     * Constructor for a Specification_elementSpec from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public SpecificationElementSpec(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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

        c_elementSetName = null;
        c_externalEspec = null;

        // Try choice elementSetName
        if (ber.getTag() == 1 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_elementSetName = new InternationalString(ber, false);
            return;
        }

        // Try choice externalEspec
        if (ber.getTag() == 2 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_externalEspec = new ASN1External(ber, false);
            return;
        }

        throw new ASN1Exception("bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of Specification_elementSpec.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        BEREncoding chosen = null;

        // Encoding choice: c_elementSetName
        if (c_elementSetName != null) {
            chosen = c_elementSetName.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
        }

        // Encoding choice: c_externalEspec
        if (c_externalEspec != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_externalEspec.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 2);
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
     * of the Specification_elementSpec.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        boolean found = false;
        if (c_elementSetName != null) {
            found = true;
            str.append("elementSetName ");
            str.append(c_elementSetName);
        }
        if (c_externalEspec != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: externalEspec> ");
            }
            str.append("externalEspec ");
            str.append(c_externalEspec);
        }
        str.append("}");
        return str.toString();
    }
}
