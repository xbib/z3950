package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>SortElement</code> from <code>Z39-50-APDU-1995</code>
 * <pre>
 * SortElement ::=
 * CHOICE {
 *   generic [1] EXPLICIT SortKey
 *   datbaseSpecific [2] IMPLICIT SEQUENCE OF SortElement_datbaseSpecific
 * }
 * </pre>
 */
public final class SortElement extends ASN1Any {

    public SortKey c_generic;
    public SortElementDatabaseSpecific c_datbaseSpecific[];


    /**
     * Constructor for a SortElement from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public SortElement(BEREncoding ber, boolean checkTag)
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
        BERConstructed tagwrapper;

        // Null out all choices

        c_generic = null;
        c_datbaseSpecific = null;

        // Try choice generic
        if (ber.tagGet() == 1 &&
                ber.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagwrapper = (BERConstructed) ber;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("SortElement: bad BER form\n");
            }
            if (tagwrapper.numberComponents() != 1) {
                throw new ASN1EncodingException("SortElement: bad BER form\n");
            }
            c_generic = new SortKey(tagwrapper.elementAt(0), true);
            return;
        }

        // Try choice datbaseSpecific
        if (ber.tagGet() == 2 &&
                ber.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            BEREncoding ber_data;
            ber_data = ber;
            BERConstructed berConstructed;
            try {
                berConstructed = (BERConstructed) ber_data;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("SortElement: bad BER form\n");
            }

            int numParts = berConstructed.numberComponents();
            int p;

            c_datbaseSpecific = new SortElementDatabaseSpecific[numParts];

            for (p = 0; p < numParts; p++) {
                c_datbaseSpecific[p] = new SortElementDatabaseSpecific(berConstructed.elementAt(p), true);
            }
            return;
        }

        throw new ASN1Exception("SortElement: bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of SortElement.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */

    public BEREncoding
    berEncode()
            throws ASN1Exception {
        BEREncoding chosen = null;

        BEREncoding f2[];
        int p;
        BEREncoding enc[];

        // Encoding choice: c_generic
        if (c_generic != null) {
            enc = new BEREncoding[1];
            enc[0] = c_generic.berEncode();
            chosen = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 1, enc);
        }

        // Encoding choice: c_datbaseSpecific
        if (c_datbaseSpecific != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            f2 = new BEREncoding[c_datbaseSpecific.length];

            for (p = 0; p < c_datbaseSpecific.length; p++) {
                f2[p] = c_datbaseSpecific[p].berEncode();
            }

            chosen = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 2, f2);
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

        throw new ASN1EncodingException("SortElement: cannot implicitly tag");
    }

    /**
     * Returns a new String object containing a text representing
     * of the SortElement.
     */
    public String toString() {
        int p;
        StringBuilder str = new StringBuilder("{");
        boolean found = false;
        if (c_generic != null) {
            found = true;
            str.append("generic ");
            str.append(c_generic);
        }

        if (c_datbaseSpecific != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: datbaseSpecific> ");
            }
            found = true;
            str.append("datbaseSpecific ");
            str.append("{");
            for (p = 0; p < c_datbaseSpecific.length; p++) {
                str.append(c_datbaseSpecific[p]);
            }
            str.append("}");
        }

        str.append("}");

        return str.toString();
    }

}
