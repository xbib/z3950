package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>Permissions1</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * Permissions1 ::=
 * SEQUENCE {
 *   userId [1] IMPLICIT InternationalString
 *   allowableFunctions [2] IMPLICIT SEQUENCE OF INTEGER
 * }
 * </pre>
 */
public final class Permissions1 extends ASN1Any {

    public InternationalString s_userId;
    public ASN1Integer s_allowableFunctions[];


    /**
     * Constructor for a Permissions1 from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public Permissions1(BEREncoding ber, boolean checkTag)
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
        // Permissions1 should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException
                    ("Permissions1: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;

        // Decoding: userId [1] IMPLICIT InternationalString

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("Permissions1: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() != 1 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException
                    ("Permissions1: bad tag in s_userId\n");
        }

        s_userId = new InternationalString(p, false);
        part++;

        // Decoding: allowableFunctions [2] IMPLICIT SEQUENCE OF INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("Permissions1: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() != 2 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException
                    ("Permissions1: bad tag in s_allowableFunctions\n");
        }

        try {
            BERConstructed cons = (BERConstructed) p;
            int parts = cons.numberComponents();
            s_allowableFunctions = new ASN1Integer[parts];
            int n;
            for (n = 0; n < parts; n++) {
                s_allowableFunctions[n] = new ASN1Integer(cons.elementAt(n), true);
            }
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("Bad BER");
        }
        part++;

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception("Permissions1: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the Permissions1.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */

    public BEREncoding
    berEncode()
            throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of Permissions1, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        // Calculate the number of fields in the encoding

        int numFields = 2; // number of mandatories

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding f2[];
        int p;

        // Encoding s_userId: InternationalString

        fields[x++] = s_userId.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);

        // Encoding s_allowableFunctions: SEQUENCE OF

        f2 = new BEREncoding[s_allowableFunctions.length];

        for (p = 0; p < s_allowableFunctions.length; p++) {
            f2[p] = s_allowableFunctions[p].berEncode();
        }

        fields[x] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 2, f2);

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the Permissions1.
     */

    public String
    toString() {
        int p;
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;

        str.append("userId ");
        str.append(s_userId);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("allowableFunctions ");
        str.append("{");
        for (p = 0; p < s_allowableFunctions.length; p++) {
            if (p != 0) {
                str.append(", ");
            }
            str.append(s_allowableFunctions[p]);
        }
        str.append("}");
        str.append("}");
        return str.toString();
    }

}
