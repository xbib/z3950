package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>ProximityOperator_proximityUnitCode</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * ProximityOperator_proximityUnitCode ::=
 * CHOICE {
 *   known [1] IMPLICIT KnownProximityUnit
 *   private [2] IMPLICIT INTEGER
 * }
 * </pre>
 */
public final class ProximityOperatorProximityUnitCode extends ASN1Any {

    public KnownProximityUnit c_known;
    public ASN1Integer c_private;

    /**
     * Constructor for a ProximityOperator_proximityUnitCode from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public ProximityOperatorProximityUnitCode(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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

        c_known = null;
        c_private = null;

        // Try choice known
        if (ber.getTag() == 1 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_known = new KnownProximityUnit(ber, false);
            return;
        }

        // Try choice private
        if (ber.getTag() == 2 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_private = new ASN1Integer(ber, false);
            return;
        }

        throw new ASN1Exception("ProximityOperator_proximityUnitCode: bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of ProximityOperator_proximityUnitCode.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        BEREncoding chosen = null;

        // Encoding choice: c_known
        if (c_known != null) {
            chosen = c_known.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
        }

        // Encoding choice: c_private
        if (c_private != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_private.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 2);
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

        throw new ASN1EncodingException("ProximityOperator_proximityUnitCode: cannot implicitly tag");
    }

    /**
     * Returns a new String object containing a text representing
     * of the ProximityOperator_proximityUnitCode.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");

        boolean found = false;

        if (c_known != null) {
            found = true;
            str.append("known ");
            str.append(c_known);
        }

        if (c_private != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: private> ");
            }
            found = true;
            str.append("private ");
            str.append(c_private);
        }

        str.append("}");

        return str.toString();
    }
}
