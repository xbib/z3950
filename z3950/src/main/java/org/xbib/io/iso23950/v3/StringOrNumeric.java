package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>StringOrNumeric</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * StringOrNumeric ::=
 * CHOICE {
 *   string [1] IMPLICIT InternationalString
 *   numeric [2] IMPLICIT INTEGER
 * }
 * </pre>
 */
public final class StringOrNumeric extends ASN1Any {
    public InternationalString c_string;
    public ASN1Integer c_numeric;


    /**
     * Constructor for a StringOrNumeric from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public StringOrNumeric(BEREncoding ber, boolean checkTag)
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
    public void berDecode(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        // Null out all choices

        c_string = null;
        c_numeric = null;

        // Try choice string
        if (ber.tagGet() == 1 &&
                ber.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_string = new InternationalString(ber, false);
            return;
        }

        // Try choice numeric
        if (ber.tagGet() == 2 &&
                ber.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_numeric = new ASN1Integer(ber, false);
            return;
        }

        throw new ASN1Exception("StringOrNumeric: bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of StringOrNumeric.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    public BEREncoding berEncode() throws ASN1Exception {
        BEREncoding chosen = null;

        // Encoding choice: c_string
        if (c_string != null) {
            chosen = c_string.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
        }

        // Encoding choice: c_numeric
        if (c_numeric != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_numeric.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 2);
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

        throw new ASN1EncodingException("StringOrNumeric: cannot implicitly tag");
    }

    /**
     * Returns a new String object containing a text representing
     * of the StringOrNumeric.
     */
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        boolean found = false;
        if (c_string != null) {
            found = true;
            str.append("string ");
            str.append(c_string);
        }
        if (c_numeric != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: numeric> ");
            }
            found = true;
            str.append("numeric ");
            str.append(c_numeric);
        }

        str.append("}");

        return str.toString();
    }
}
