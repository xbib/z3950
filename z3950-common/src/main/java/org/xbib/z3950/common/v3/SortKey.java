package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>SortKey</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * SortKey ::=
 * CHOICE {
 *   sortfield [0] IMPLICIT InternationalString
 *   elementSpec [1] IMPLICIT Specification
 *   sortAttributes [2] IMPLICIT SortKey_sortAttributes
 * }
 * </pre>
 */
public final class SortKey extends ASN1Any {

    public InternationalString c_sortfield;
    public Specification c_elementSpec;
    public SortKeySortAttributes c_sortAttributes;

    /**
     * Constructor for a SortKey from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public SortKey(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        c_sortfield = null;
        c_elementSpec = null;
        c_sortAttributes = null;
        if (ber.getTag() == 0 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_sortfield = new InternationalString(ber, false);
            return;
        }
        if (ber.getTag() == 1 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_elementSpec = new Specification(ber, false);
            return;
        }
        if (ber.getTag() == 2 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_sortAttributes = new SortKeySortAttributes(ber, false);
            return;
        }
        throw new ASN1Exception("bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of SortKey.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        BEREncoding chosen = null;

        // Encoding choice: c_sortfield
        if (c_sortfield != null) {
            chosen = c_sortfield.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 0);
        }

        // Encoding choice: c_elementSpec
        if (c_elementSpec != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_elementSpec.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
        }

        // Encoding choice: c_sortAttributes
        if (c_sortAttributes != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_sortAttributes.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 2);
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

        throw new ASN1EncodingException("SortKey: cannot implicitly tag");
    }

    /**
     * Returns a new String object containing a text representing
     * of the SortKey.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        boolean found = false;
        if (c_sortfield != null) {
            found = true;
            str.append("sortfield ");
            str.append(c_sortfield);
        }

        if (c_elementSpec != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: elementSpec> ");
            }
            found = true;
            str.append("elementSpec ");
            str.append(c_elementSpec);
        }

        if (c_sortAttributes != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: sortAttributes> ");
            }
            found = true;
            str.append("sortAttributes ");
            str.append(c_sortAttributes);
        }

        str.append("}");

        return str.toString();
    }
}
