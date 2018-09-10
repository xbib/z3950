package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Null;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>Operator</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * Operator ::=
 * [46] EXPLICIT CHOICE {
 *   and [0] IMPLICIT NULL
 *   or [1] IMPLICIT NULL
 *   and-not [2] IMPLICIT NULL
 *   prox [3] IMPLICIT ProximityOperator
 * }
 * </pre>
 */
public final class Operator extends ASN1Any {

    public ASN1Null andOp;
    public ASN1Null orOp;
    public ASN1Null andNotOp;
    public ProximityOperator proxOp;

    public Operator() {
    }

    /**
     * Constructor for a Operator from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public Operator(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // Check tag matches

        if (checkTag) {
            if (ber.getTag() != 46 ||
                    ber.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
                throw new ASN1EncodingException("bad BER: tag=" + ber.getTag() + " expected 46");
            }
        }

        // Unwrap explicit tag

        BERConstructed tagwrapper;
        try {
            tagwrapper = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("bad BER tag form");
        }
        if (tagwrapper.numberComponents() != 1) {
            throw new ASN1EncodingException("bad BER tag form");
        }
        ber = tagwrapper.elementAt(0);

        // Null out all choices

        andOp = null;
        orOp = null;
        andNotOp = null;
        proxOp = null;

        // Try choice and
        if (ber.getTag() == 0 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            andOp = new ASN1Null(ber, false);
            return;
        }

        // Try choice or
        if (ber.getTag() == 1 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            orOp = new ASN1Null(ber, false);
            return;
        }

        // Try choice and-not
        if (ber.getTag() == 2 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            andNotOp = new ASN1Null(ber, false);
            return;
        }

        // Try choice prox
        if (ber.getTag() == 3 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            proxOp = new ProximityOperator(ber, false);
            return;
        }
        throw new ASN1Exception("bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of Operator.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 46);
    }

    /**
     * Returns a BER encoding of Operator, implicitly tagged.
     *
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        BEREncoding chosen = null;

        // Encoding choice: c_and
        if (andOp != null) {
            chosen = andOp.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 0);
        }

        // Encoding choice: c_or
        if (orOp != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = orOp.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
        }

        // Encoding choice: c_and_not
        if (andNotOp != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = andNotOp.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 2);
        }

        // Encoding choice: c_prox
        if (proxOp != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = proxOp.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 3);
        }

        // Check for error of having none of the choices set
        if (chosen == null) {
            throw new ASN1Exception("CHOICE not set");
        }

        // Return chosen element wrapped in its explicit tag

        BEREncoding exp_tag_data[] = new BEREncoding[1];
        exp_tag_data[0] = chosen;
        return new BERConstructed(tagType, tag, exp_tag_data);
    }

    /**
     * Returns a new String object containing a text representing
     * of the Operator.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");

        boolean found = false;

        if (andOp != null) {
            found = true;
            str.append("and ");
            str.append(andOp);
        }

        if (orOp != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: or> ");
            }
            found = true;
            str.append("or ");
            str.append(orOp);
        }

        if (andNotOp != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: and-not> ");
            }
            found = true;
            str.append("and-not ");
            str.append(andNotOp);
        }

        if (proxOp != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: prox> ");
            }
            str.append("prox ");
            str.append(proxOp);
        }

        str.append("}");

        return str.toString();
    }
}
