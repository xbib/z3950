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

    public ASN1Null c_and;
    public ASN1Null c_or;
    public ASN1Null c_and_not;
    public ProximityOperator c_prox;


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

    public Operator(BEREncoding ber, boolean checkTag)
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
        // Check tag matches

        if (checkTag) {
            if (ber.tagGet() != 46 ||
                    ber.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
                throw new ASN1EncodingException("Operator: bad BER: tag=" + ber.tagGet() + " expected 46\n");
            }
        }

        // Unwrap explicit tag

        BERConstructed tagwrapper;
        try {
            tagwrapper = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException
                    ("Operator: bad BER tag form\n");
        }
        if (tagwrapper.numberComponents() != 1) {
            throw new ASN1EncodingException
                    ("Operator: bad BER tag form\n");
        }
        ber = tagwrapper.elementAt(0);

        // Null out all choices

        c_and = null;
        c_or = null;
        c_and_not = null;
        c_prox = null;

        // Try choice and
        if (ber.tagGet() == 0 &&
                ber.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_and = new ASN1Null(ber, false);
            return;
        }

        // Try choice or
        if (ber.tagGet() == 1 &&
                ber.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_or = new ASN1Null(ber, false);
            return;
        }

        // Try choice and-not
        if (ber.tagGet() == 2 &&
                ber.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_and_not = new ASN1Null(ber, false);
            return;
        }

        // Try choice prox
        if (ber.tagGet() == 3 &&
                ber.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_prox = new ProximityOperator(ber, false);
            return;
        }

        throw new ASN1Exception("Operator: bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of Operator.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */

    public BEREncoding
    berEncode()
            throws ASN1Exception {
        return berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 46);
    }

    /**
     * Returns a BER encoding of Operator, implicitly tagged.
     *
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */

    public BEREncoding
    berEncode(int tagType, int tag)
            throws ASN1Exception {
        BEREncoding chosen = null;

        // Encoding choice: c_and
        if (c_and != null) {
            chosen = c_and.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 0);
        }

        // Encoding choice: c_or
        if (c_or != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_or.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
        }

        // Encoding choice: c_and_not
        if (c_and_not != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_and_not.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 2);
        }

        // Encoding choice: c_prox
        if (c_prox != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_prox.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 3);
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

    public String
    toString() {
        StringBuilder str = new StringBuilder("{");

        boolean found = false;

        if (c_and != null) {
            found = true;
            str.append("and ");
            str.append(c_and);
        }

        if (c_or != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: or> ");
            }
            found = true;
            str.append("or ");
            str.append(c_or);
        }

        if (c_and_not != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: and-not> ");
            }
            found = true;
            str.append("and-not ");
            str.append(c_and_not);
        }

        if (c_prox != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: prox> ");
            }
            found = true;
            str.append("prox ");
            str.append(c_prox);
        }

        str.append("}");

        return str.toString();
    }

}
