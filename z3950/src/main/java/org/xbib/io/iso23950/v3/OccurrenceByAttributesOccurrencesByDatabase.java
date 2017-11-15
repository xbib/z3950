package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>OccurrenceByAttributes_occurrences_byDatabase</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * OccurrenceByAttributes_occurrences_byDatabase ::=
 * SEQUENCE {
 *   db DatabaseName
 *   num [1] IMPLICIT INTEGER OPTIONAL
 *   otherDbInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */
public final class OccurrenceByAttributesOccurrencesByDatabase extends ASN1Any {

    public DatabaseName s_db;
    public ASN1Integer s_num; // optional
    public OtherInformation s_otherDbInfo; // optional

    /**
     * Constructor for a OccurrenceByAttributes_occurrences_byDatabase from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public OccurrenceByAttributesOccurrencesByDatabase(BEREncoding ber, boolean checkTag)
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
        // OccurrenceByAttributes_occurrences_byDatabase should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException
                    ("OccurrenceByAttributes_occurrences_byDatabase: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;

        // Decoding: db DatabaseName

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("OccurrenceByAttributes_occurrences_byDatabase: incomplete");
        }
        p = berConstructed.elementAt(part);

        s_db = new DatabaseName(p, true);
        part++;

        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER

        s_num = null;
        s_otherDbInfo = null;

        // Decoding: num [1] IMPLICIT INTEGER OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 1 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_num = new ASN1Integer(p, false);
            part++;
        }

        // Decoding: otherDbInfo OtherInformation OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        try {
            s_otherDbInfo = new OtherInformation(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_otherDbInfo = null; // no, not present
        }

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception("OccurrenceByAttributes_occurrences_byDatabase: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the OccurrenceByAttributes_occurrences_byDatabase.
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
     * Returns a BER encoding of OccurrenceByAttributes_occurrences_byDatabase, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */

    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        // Calculate the number of fields in the encoding

        int numFields = 1; // number of mandatories
        if (s_num != null) {
            numFields++;
        }
        if (s_otherDbInfo != null) {
            numFields++;
        }

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;

        // Encoding s_db: DatabaseName

        fields[x++] = s_db.berEncode();

        // Encoding s_num: INTEGER OPTIONAL

        if (s_num != null) {
            fields[x++] = s_num.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
        }

        // Encoding s_otherDbInfo: OtherInformation OPTIONAL

        if (s_otherDbInfo != null) {
            fields[x] = s_otherDbInfo.berEncode();
        }

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the OccurrenceByAttributes_occurrences_byDatabase.
     */

    public String
    toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;

        str.append("db ");
        str.append(s_db);
        outputted++;

        if (s_num != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("num ");
            str.append(s_num);
            outputted++;
        }

        if (s_otherDbInfo != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("otherDbInfo ");
            str.append(s_otherDbInfo);
        }

        str.append("}");

        return str.toString();
    }

}