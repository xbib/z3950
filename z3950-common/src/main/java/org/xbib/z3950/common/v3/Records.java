package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>Records</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * Records ::=
 * CHOICE {
 *   responseRecords [28] IMPLICIT SEQUENCE OF NamePlusRecord
 *   nonSurrogateDiagnostic [130] IMPLICIT DefaultDiagFormat
 *   multipleNonSurDiagnostics [205] IMPLICIT SEQUENCE OF DiagRec
 * }
 * </pre>
 */
public final class Records extends ASN1Any {

    public NamePlusRecord c_responseRecords[];
    public DefaultDiagFormat c_nonSurrogateDiagnostic;
    public DiagRec c_multipleNonSurDiagnostics[];

    /**
     * Constructor for a Records from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public Records(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        c_responseRecords = null;
        c_nonSurrogateDiagnostic = null;
        c_multipleNonSurDiagnostics = null;

        // Try choice responseRecords
        if (ber.getTag() == 28 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            BEREncoding ber_data;
            ber_data = ber;
            BERConstructed berConstructed;
            try {
                berConstructed = (BERConstructed) ber_data;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException
                        ("Records: bad BER form\n");
            }

            int numParts = berConstructed.numberComponents();
            int p;

            c_responseRecords = new NamePlusRecord[numParts];

            for (p = 0; p < numParts; p++) {
                c_responseRecords[p] = new NamePlusRecord(berConstructed.elementAt(p), true);
            }
            return;
        }

        // Try choice nonSurrogateDiagnostic
        if (ber.getTag() == 130 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_nonSurrogateDiagnostic = new DefaultDiagFormat(ber, false);
            return;
        }

        // Try choice multipleNonSurDiagnostics
        if (ber.getTag() == 205 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            BEREncoding ber_data;
            ber_data = ber;
            BERConstructed berConstructed;
            try {
                berConstructed = (BERConstructed) ber_data;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException
                        ("Records: bad BER form\n");
            }

            int numParts = berConstructed.numberComponents();
            int p;

            c_multipleNonSurDiagnostics = new DiagRec[numParts];

            for (p = 0; p < numParts; p++) {
                c_multipleNonSurDiagnostics[p] = new DiagRec(berConstructed.elementAt(p), true);
            }
            return;
        }
        throw new ASN1Exception("Records: bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of Records.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        BEREncoding chosen = null;

        BEREncoding f2[];
        int p;
        // Encoding choice: c_responseRecords
        if (c_responseRecords != null) {
            f2 = new BEREncoding[c_responseRecords.length];

            for (p = 0; p < c_responseRecords.length; p++) {
                f2[p] = c_responseRecords[p].berEncode();
            }

            chosen = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 28, f2);
        }

        // Encoding choice: c_nonSurrogateDiagnostic
        if (c_nonSurrogateDiagnostic != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_nonSurrogateDiagnostic.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 130);
        }

        // Encoding choice: c_multipleNonSurDiagnostics
        if (c_multipleNonSurDiagnostics != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            f2 = new BEREncoding[c_multipleNonSurDiagnostics.length];

            for (p = 0; p < c_multipleNonSurDiagnostics.length; p++) {
                f2[p] = c_multipleNonSurDiagnostics[p].berEncode();
            }

            chosen = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 205, f2);
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

        throw new ASN1EncodingException("Records: cannot implicitly tag");
    }

    /**
     * Returns a new String object containing a text representing
     * of the Records.
     */
    @Override
    public String toString() {
        int p;
        StringBuilder str = new StringBuilder("{");
        boolean found = false;
        if (c_responseRecords != null) {
            found = true;
            str.append("responseRecords ");
            str.append("{");
            for (p = 0; p < c_responseRecords.length; p++) {
                str.append(c_responseRecords[p]);
            }
            str.append("}");
        }

        if (c_nonSurrogateDiagnostic != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: nonSurrogateDiagnostic> ");
            }
            found = true;
            str.append("nonSurrogateDiagnostic ");
            str.append(c_nonSurrogateDiagnostic);
        }

        if (c_multipleNonSurDiagnostics != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: multipleNonSurDiagnostics> ");
            }
            str.append("multipleNonSurDiagnostics ");
            str.append("{");
            for (p = 0; p < c_multipleNonSurDiagnostics.length; p++) {
                str.append(c_multipleNonSurDiagnostics[p]);
            }
            str.append("}");
        }
        str.append("}");
        return str.toString();
    }
}
