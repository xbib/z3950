package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>TermInfo</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * TermInfo ::=
 * SEQUENCE {
 *   term Term
 *   displayTerm [0] IMPLICIT InternationalString OPTIONAL
 *   suggestedAttributes AttributeList OPTIONAL
 *   alternativeTerm [4] IMPLICIT SEQUENCE OF AttributesPlusTerm OPTIONAL
 *   globalOccurrences [2] IMPLICIT INTEGER OPTIONAL
 *   byAttributes [3] IMPLICIT OccurrenceByAttributes OPTIONAL
 *   otherTermInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */

public final class TermInfo extends ASN1Any {

    public Term s_term;
    public InternationalString s_displayTerm; // optional
    public AttributeList s_suggestedAttributes; // optional
    public AttributesPlusTerm s_alternativeTerm[]; // optional
    public ASN1Integer s_globalOccurrences; // optional
    public OccurrenceByAttributes s_byAttributes; // optional
    public OtherInformation s_otherTermInfo; // optional

    /**
     * Default constructor for a TermInfo.
     */
    public TermInfo() {
    }

    /**
     * Constructor for a TermInfo from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public TermInfo(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // TermInfo should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("TermInfo: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;

        // Decoding: term Term

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("TermInfo: incomplete");
        }
        p = berConstructed.elementAt(part);

        s_term = new Term(p, true);
        part++;

        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER

        s_displayTerm = null;
        s_suggestedAttributes = null;
        s_alternativeTerm = null;
        s_globalOccurrences = null;
        s_byAttributes = null;
        s_otherTermInfo = null;

        // Decoding: displayTerm [0] IMPLICIT InternationalString OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 0 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_displayTerm = new InternationalString(p, false);
            part++;
        }

        // Decoding: suggestedAttributes AttributeList OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        try {
            s_suggestedAttributes = new AttributeList(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_suggestedAttributes = null; // no, not present
        }

        // Decoding: alternativeTerm [4] IMPLICIT SEQUENCE OF AttributesPlusTerm OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 4 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                BERConstructed cons = (BERConstructed) p;
                int parts = cons.numberComponents();
                s_alternativeTerm = new AttributesPlusTerm[parts];
                int n;
                for (n = 0; n < parts; n++) {
                    s_alternativeTerm[n] = new AttributesPlusTerm(cons.elementAt(n), true);
                }
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("Bad BER");
            }
            part++;
        }

        // Decoding: globalOccurrences [2] IMPLICIT INTEGER OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 2 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_globalOccurrences = new ASN1Integer(p, false);
            part++;
        }

        // Decoding: byAttributes [3] IMPLICIT OccurrenceByAttributes OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 3 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_byAttributes = new OccurrenceByAttributes(p, false);
            part++;
        }

        // Decoding: otherTermInfo OtherInformation OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        try {
            s_otherTermInfo = new OtherInformation(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_otherTermInfo = null; // no, not present
        }

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception("TermInfo: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the TermInfo.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of TermInfo, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        // Calculate the number of fields in the encoding

        int numFields = 1; // number of mandatories
        if (s_displayTerm != null) {
            numFields++;
        }
        if (s_suggestedAttributes != null) {
            numFields++;
        }
        if (s_alternativeTerm != null) {
            numFields++;
        }
        if (s_globalOccurrences != null) {
            numFields++;
        }
        if (s_byAttributes != null) {
            numFields++;
        }
        if (s_otherTermInfo != null) {
            numFields++;
        }

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding f2[];
        int p;

        // Encoding s_term: Term

        fields[x++] = s_term.berEncode();

        // Encoding s_displayTerm: InternationalString OPTIONAL

        if (s_displayTerm != null) {
            fields[x++] = s_displayTerm.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 0);
        }

        // Encoding s_suggestedAttributes: AttributeList OPTIONAL

        if (s_suggestedAttributes != null) {
            fields[x++] = s_suggestedAttributes.berEncode();
        }

        // Encoding s_alternativeTerm: SEQUENCE OF OPTIONAL

        if (s_alternativeTerm != null) {
            f2 = new BEREncoding[s_alternativeTerm.length];

            for (p = 0; p < s_alternativeTerm.length; p++) {
                f2[p] = s_alternativeTerm[p].berEncode();
            }

            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 4, f2);
        }

        // Encoding s_globalOccurrences: INTEGER OPTIONAL

        if (s_globalOccurrences != null) {
            fields[x++] = s_globalOccurrences.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 2);
        }

        // Encoding s_byAttributes: OccurrenceByAttributes OPTIONAL

        if (s_byAttributes != null) {
            fields[x++] = s_byAttributes.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 3);
        }

        // Encoding s_otherTermInfo: OtherInformation OPTIONAL

        if (s_otherTermInfo != null) {
            fields[x] = s_otherTermInfo.berEncode();
        }

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the TermInfo.
     */
    @Override
    public String toString() {
        int p;
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;

        str.append("term ");
        str.append(s_term);
        outputted++;

        if (s_displayTerm != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("displayTerm ");
            str.append(s_displayTerm);
            outputted++;
        }

        if (s_suggestedAttributes != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("suggestedAttributes ");
            str.append(s_suggestedAttributes);
            outputted++;
        }

        if (s_alternativeTerm != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("alternativeTerm ");
            str.append("{");
            for (p = 0; p < s_alternativeTerm.length; p++) {
                if (p != 0) {
                    str.append(", ");
                }
                str.append(s_alternativeTerm[p]);
            }
            str.append("}");
            outputted++;
        }

        if (s_globalOccurrences != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("globalOccurrences ");
            str.append(s_globalOccurrences);
            outputted++;
        }

        if (s_byAttributes != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("byAttributes ");
            str.append(s_byAttributes);
            outputted++;
        }

        if (s_otherTermInfo != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("otherTermInfo ");
            str.append(s_otherTermInfo);
        }

        str.append("}");

        return str.toString();
    }
}
