package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>RPNStructure</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * RPNStructure ::=
 * CHOICE {
 *   op [0] EXPLICIT Operand
 *   rpnRpnOp [1] IMPLICIT RPNStructure_rpnRpnOp
 * }
 * </pre>
 */
public final class RPNStructure extends ASN1Any {

    public Operand c_op;
    public RPNStructureRpnRpnOp c_rpnRpnOp;

    /**
     * Default constructor for a RPNStructure.
     */
    public RPNStructure() {
    }

    /**
     * Constructor for a RPNStructure from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public RPNStructure(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
    public void berDecode(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        BERConstructed tagwrapper;
        c_op = null;
        c_rpnRpnOp = null;
        if (ber.tagGet() == 0 &&
                ber.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagwrapper = (BERConstructed) ber;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException
                        ("RPNStructure: bad BER form\n");
            }
            if (tagwrapper.numberComponents() != 1) {
                throw new ASN1EncodingException
                        ("RPNStructure: bad BER form\n");
            }
            c_op = new Operand(tagwrapper.elementAt(0), true);
            return;
        }
        if (ber.tagGet() == 1 &&
                ber.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_rpnRpnOp = new RPNStructureRpnRpnOp(ber, false);
            return;
        }
        throw new ASN1Exception("RPNStructure: bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of RPNStructure.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    public BEREncoding berEncode() throws ASN1Exception {
        BEREncoding chosen = null;

        BEREncoding enc[];

        // Encoding choice: c_op
        if (c_op != null) {
            enc = new BEREncoding[1];
            enc[0] = c_op.berEncode();
            chosen = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 0, enc);
        }

        // Encoding choice: c_rpnRpnOp
        if (c_rpnRpnOp != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_rpnRpnOp.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
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

        throw new ASN1EncodingException("RPNStructure: cannot implicitly tag");
    }

    /**
     * Returns a new String object containing a text representing
     * of the RPNStructure.
     */
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        boolean found = false;
        if (c_op != null) {
            found = true;
            str.append("op ");
            str.append(c_op);
        }
        if (c_rpnRpnOp != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: rpnRpnOp> ");
            }
            str.append("rpnRpnOp ");
            str.append(c_rpnRpnOp);
        }
        str.append("}");
        return str.toString();
    }

}
