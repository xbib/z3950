package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>ListEntries</code> from <code>Z39-50-APDU-1995</code>
 * <pre>
 * ListEntries ::=
 * SEQUENCE {
 *   entries [1] IMPLICIT SEQUENCE OF Entry OPTIONAL
 *   nonsurrogateDiagnostics [2] IMPLICIT SEQUENCE OF DiagRec OPTIONAL
 * }
 * </pre>
 */
public final class ListEntries extends ASN1Any {

    public Entry s_entries[]; // optional
    public DiagRec s_nonsurrogateDiagnostics[]; // optional


    /**
     * Constructor for a ListEntries from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public ListEntries(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // ListEntries should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("ListEntries: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;

        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER

        s_entries = null;
        s_nonsurrogateDiagnostics = null;

        // Decoding: entries [1] IMPLICIT SEQUENCE OF Entry OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 1 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                BERConstructed cons = (BERConstructed) p;
                int parts = cons.numberComponents();
                s_entries = new Entry[parts];
                int n;
                for (n = 0; n < parts; n++) {
                    s_entries[n] = new Entry(cons.elementAt(n), true);
                }
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("Bad BER");
            }
            part++;
        }

        // Decoding: nonsurrogateDiagnostics [2] IMPLICIT SEQUENCE OF DiagRec OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 2 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                BERConstructed cons = (BERConstructed) p;
                int parts = cons.numberComponents();
                s_nonsurrogateDiagnostics = new DiagRec[parts];
                int n;
                for (n = 0; n < parts; n++) {
                    s_nonsurrogateDiagnostics[n] = new DiagRec(cons.elementAt(n), true);
                }
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("Bad BER");
            }
            part++;
        }

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception("ListEntries: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the ListEntries.
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
     * Returns a BER encoding of ListEntries, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        // Calculate the number of fields in the encoding

        int numFields = 0; // number of mandatories
        if (s_entries != null) {
            numFields++;
        }
        if (s_nonsurrogateDiagnostics != null) {
            numFields++;
        }

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding f2[];
        int p;

        // Encoding s_entries: SEQUENCE OF OPTIONAL

        if (s_entries != null) {
            f2 = new BEREncoding[s_entries.length];

            for (p = 0; p < s_entries.length; p++) {
                f2[p] = s_entries[p].berEncode();
            }

            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 1, f2);
        }

        // Encoding s_nonsurrogateDiagnostics: SEQUENCE OF OPTIONAL

        if (s_nonsurrogateDiagnostics != null) {
            f2 = new BEREncoding[s_nonsurrogateDiagnostics.length];

            for (p = 0; p < s_nonsurrogateDiagnostics.length; p++) {
                f2[p] = s_nonsurrogateDiagnostics[p].berEncode();
            }

            fields[x] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 2, f2);
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the ListEntries.
     */
    public String toString() {
        int p;
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;

        if (s_entries != null) {
            str.append("entries ");
            str.append("{");
            for (p = 0; p < s_entries.length; p++) {
                if (p != 0) {
                    str.append(", ");
                }
                str.append(s_entries[p]);
            }
            str.append("}");
            outputted++;
        }

        if (s_nonsurrogateDiagnostics != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("nonsurrogateDiagnostics ");
            str.append("{");
            for (p = 0; p < s_nonsurrogateDiagnostics.length; p++) {
                if (p != 0) {
                    str.append(", ");
                }
                str.append(s_nonsurrogateDiagnostics[p]);
            }
            str.append("}");
        }
        str.append("}");
        return str.toString();
    }

}
