package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>SortKeySpec</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * SortKeySpec ::=
 * SEQUENCE {
 *   sortElement SortElement
 *   sortRelation [1] IMPLICIT INTEGER
 *   caseSensitivity [2] IMPLICIT INTEGER
 *   missingValueAction [3] EXPLICIT SortKeySpec_missingValueAction OPTIONAL
 * }
 * </pre>
 */
public final class SortKeySpec extends ASN1Any {

    public static final int E_ascending = 0;

    public static final int E_descending = 1;

    public static final int E_ascendingByFrequency = 3;

    public static final int E_descendingByfrequency = 4;

    public static final int E_caseSensitive = 0;

    public static final int E_caseInsensitive = 1;

    public SortElement s_sortElement;

    public ASN1Integer s_sortRelation;

    public ASN1Integer s_caseSensitivity;

    public SortKeySpecMissingValueAction s_missingValueAction; // optional

    public SortKeySpec() {
    }

    /**
     * Constructor for a SortKeySpec from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public SortKeySpec(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // SortKeySpec should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        BERConstructed tagged;

        // Decoding: sortElement SortElement

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        s_sortElement = new SortElement(p, true);
        part++;

        // Decoding: sortRelation [1] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() != 1 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_sortRelation");
        }

        s_sortRelation = new ASN1Integer(p, false);
        part++;

        // Decoding: caseSensitivity [2] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() != 2 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_caseSensitivity");
        }

        s_caseSensitivity = new ASN1Integer(p, false);
        part++;

        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER

        s_missingValueAction = null;

        // Decoding: missingValueAction [3] EXPLICIT SortKeySpec_missingValueAction OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 3 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagged = (BERConstructed) p;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("bad BER encoding: s_missingValueAction tag bad");
            }
            if (tagged.numberComponents() != 1) {
                throw new ASN1EncodingException("bad BER encoding: s_missingValueAction tag bad");
            }

            s_missingValueAction = new SortKeySpecMissingValueAction(tagged.elementAt(0), true);
            part++;
        }

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception("bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the SortKeySpec.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of SortKeySpec, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numFields = 3; // number of mandatories
        if (s_missingValueAction != null) {
            numFields++;
        }
        BEREncoding[] fields = new BEREncoding[numFields];
        int x = 0;
        BEREncoding[] enc;
        fields[x++] = s_sortElement.berEncode();
        fields[x++] = s_sortRelation.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
        fields[x++] = s_caseSensitivity.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 2);
        if (s_missingValueAction != null) {
            enc = new BEREncoding[1];
            enc[0] = s_missingValueAction.berEncode();
            fields[x] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 3, enc);
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the SortKeySpec.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        str.append("sortElement ");
        str.append(s_sortElement);
        str.append(", ");
        str.append("sortRelation ");
        str.append(s_sortRelation);
        str.append(", ");
        str.append("caseSensitivity ");
        str.append(s_caseSensitivity);
        if (s_missingValueAction != null) {
            str.append(", ");
            str.append("missingValueAction ");
            str.append(s_missingValueAction);
        }
        str.append("}");
        return str.toString();
    }
}
