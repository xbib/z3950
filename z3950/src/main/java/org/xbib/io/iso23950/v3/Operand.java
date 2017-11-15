package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>Operand</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * Operand ::=
 * CHOICE {
 *   attrTerm AttributesPlusTerm
 *   resultSet ResultSetId
 *   resultAttr ResultSetPlusAttributes
 * }
 * </pre>
 */
public final class Operand extends ASN1Any {

    public AttributesPlusTerm c_attrTerm;
    public ResultSetId c_resultSet;
    public ResultSetPlusAttributes c_resultAttr;


    public Operand() {
    }


    /**
     * Constructor for a Operand from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public Operand(BEREncoding ber, boolean checkTag)
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

        c_attrTerm = null;
        c_resultSet = null;
        c_resultAttr = null;

        // Try choice attrTerm
        try {
            c_attrTerm = new AttributesPlusTerm(ber, checkTag);
            return;
        } catch (ASN1Exception e) {
            // failed to decode, continue on
        }

        // Try choice resultSet
        try {
            c_resultSet = new ResultSetId(ber, checkTag);
            return;
        } catch (ASN1Exception e) {
            // failed to decode, continue on
        }

        // Try choice resultAttr
        try {
            c_resultAttr = new ResultSetPlusAttributes(ber, checkTag);
            return;
        } catch (ASN1Exception e) {
            // failed to decode, continue on
        }

        throw new ASN1Exception("Operand: bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of Operand.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */

    public BEREncoding
    berEncode()
            throws ASN1Exception {
        BEREncoding chosen = null;

        // Encoding choice: c_attrTerm
        if (c_attrTerm != null) {
            chosen = c_attrTerm.berEncode();
        }

        // Encoding choice: c_resultSet
        if (c_resultSet != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_resultSet.berEncode();
        }

        // Encoding choice: c_resultAttr
        if (c_resultAttr != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_resultAttr.berEncode();
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
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        // This method must not be called!

        // Method is not available because this is a basic CHOICE
        // which does not have an explicit tag on it. So it is not
        // permitted to allow something else to apply an implicit
        // tag on it, otherwise the tag identifying which CHOICE
        // it is will be overwritten and lost.

        throw new ASN1EncodingException("Operand: cannot implicitly tag");
    }

    /**
     * Returns a new String object containing a text representing
     * of the Operand.
     */

    public String
    toString() {
        StringBuilder str = new StringBuilder("{");

        boolean found = false;

        if (c_attrTerm != null) {
            found = true;
            str.append("attrTerm ");
            str.append(c_attrTerm);
        }

        if (c_resultSet != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: resultSet> ");
            }
            found = true;
            str.append("resultSet ");
            str.append(c_resultSet);
        }

        if (c_resultAttr != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: resultAttr> ");
            }
            str.append("resultAttr ");
            str.append(c_resultAttr);
        }

        str.append("}");

        return str.toString();
    }

}
