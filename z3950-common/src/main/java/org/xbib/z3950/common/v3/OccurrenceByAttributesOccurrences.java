package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>OccurrenceByAttributes_occurrences</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * OccurrenceByAttributes_occurrences ::=
 * CHOICE {
 *   global [2] EXPLICIT INTEGER
 *   byDatabase [3] IMPLICIT SEQUENCE OF OccurrenceByAttributes_occurrences_byDatabase
 * }
 * </pre>
 */
public final class OccurrenceByAttributesOccurrences extends ASN1Any {

    public ASN1Integer c_global;
    public OccurrenceByAttributesOccurrencesByDatabase c_byDatabase[];


    /**
     * Constructor for a OccurrenceByAttributes_occurrences from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public OccurrenceByAttributesOccurrences(BEREncoding ber, boolean checkTag)
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
    @Override
    public void berDecode(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        BERConstructed tagwrapper;
        c_global = null;
        c_byDatabase = null;
        if (ber.getTag() == 2 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagwrapper = (BERConstructed) ber;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("bad BER form");
            }
            if (tagwrapper.numberComponents() != 1) {
                throw new ASN1EncodingException("bad BER form");
            }
            c_global = new ASN1Integer(tagwrapper.elementAt(0), true);
            return;
        }

        // Try choice byDatabase
        if (ber.getTag() == 3 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            BEREncoding ber_data;
            ber_data = ber;
            BERConstructed berConstructed;
            try {
                berConstructed = (BERConstructed) ber_data;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("bad BER form");
            }

            int numParts = berConstructed.numberComponents();
            int p;

            c_byDatabase = new OccurrenceByAttributesOccurrencesByDatabase[numParts];

            for (p = 0; p < numParts; p++) {
                c_byDatabase[p] = new OccurrenceByAttributesOccurrencesByDatabase(berConstructed.elementAt(p), true);
            }
            return;
        }

        throw new ASN1Exception("bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of OccurrenceByAttributes_occurrences.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        BEREncoding chosen = null;

        BEREncoding f2[];
        int p;
        BEREncoding enc[];

        // Encoding choice: c_global
        if (c_global != null) {
            enc = new BEREncoding[1];
            enc[0] = c_global.berEncode();
            chosen = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 2, enc);
        }

        // Encoding choice: c_byDatabase
        if (c_byDatabase != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            f2 = new BEREncoding[c_byDatabase.length];

            for (p = 0; p < c_byDatabase.length; p++) {
                f2[p] = c_byDatabase[p].berEncode();
            }

            chosen = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 3, f2);
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
     * of the OccurrenceByAttributes_occurrences.
     */
    @Override
    public String toString() {
        int p;
        StringBuilder str = new StringBuilder("{");

        boolean found = false;

        if (c_global != null) {
            found = true;
            str.append("global ");
            str.append(c_global);
        }

        if (c_byDatabase != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: byDatabase> ");
            }
            str.append("byDatabase ");
            str.append("{");
            for (p = 0; p < c_byDatabase.length; p++) {
                str.append(c_byDatabase[p]);
            }
            str.append("}");
        }

        str.append("}");

        return str.toString();
    }
}
