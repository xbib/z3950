package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>OccurrenceByAttributes1</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * OccurrenceByAttributes1 ::=
 * SEQUENCE {
 *   attributes [1] EXPLICIT AttributeList
 *   occurrences OccurrenceByAttributes_occurrences OPTIONAL
 *   otherOccurInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */

public final class OccurrenceByAttributes1 extends ASN1Any {

    public AttributeList s_attributes;
    public OccurrenceByAttributesOccurrences s_occurrences; // optional
    public OtherInformation s_otherOccurInfo; // optional

    /**
     * Constructor for a OccurrenceByAttributes1 from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public OccurrenceByAttributes1(BEREncoding ber, boolean checkTag)
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
        // OccurrenceByAttributes1 should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException
                    ("OccurrenceByAttributes1: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        BERConstructed tagged;

        // Decoding: attributes [1] EXPLICIT AttributeList

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("OccurrenceByAttributes1: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() != 1 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException
                    ("OccurrenceByAttributes1: bad tag in s_attributes\n");
        }

        try {
            tagged = (BERConstructed) p;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException
                    ("OccurrenceByAttributes1: bad BER encoding: s_attributes tag bad\n");
        }
        if (tagged.numberComponents() != 1) {
            throw new ASN1EncodingException
                    ("OccurrenceByAttributes1: bad BER encoding: s_attributes tag bad\n");
        }

        s_attributes = new AttributeList(tagged.elementAt(0), true);
        part++;

        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER

        s_occurrences = null;
        s_otherOccurInfo = null;

        // Decoding: occurrences OccurrenceByAttributes_occurrences OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        try {
            s_occurrences = new OccurrenceByAttributesOccurrences(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_occurrences = null; // no, not present
        }

        // Decoding: otherOccurInfo OtherInformation OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        try {
            s_otherOccurInfo = new OtherInformation(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_otherOccurInfo = null; // no, not present
        }

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception("OccurrenceByAttributes1: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the OccurrenceByAttributes1.
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
     * Returns a BER encoding of OccurrenceByAttributes1, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */

    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        // Calculate the number of fields in the encoding

        int numFields = 1; // number of mandatories
        if (s_occurrences != null) {
            numFields++;
        }
        if (s_otherOccurInfo != null) {
            numFields++;
        }

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding enc[];

        // Encoding s_attributes: AttributeList

        enc = new BEREncoding[1];
        enc[0] = s_attributes.berEncode();
        fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 1, enc);

        // Encoding s_occurrences: OccurrenceByAttributes_occurrences OPTIONAL

        if (s_occurrences != null) {
            fields[x++] = s_occurrences.berEncode();
        }

        // Encoding s_otherOccurInfo: OtherInformation OPTIONAL

        if (s_otherOccurInfo != null) {
            fields[x] = s_otherOccurInfo.berEncode();
        }

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the OccurrenceByAttributes1.
     */

    public String
    toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;

        str.append("attributes ");
        str.append(s_attributes);
        outputted++;

        if (s_occurrences != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("occurrences ");
            str.append(s_occurrences);
            outputted++;
        }

        if (s_otherOccurInfo != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("otherOccurInfo ");
            str.append(s_otherOccurInfo);
        }

        str.append("}");

        return str.toString();
    }

}
