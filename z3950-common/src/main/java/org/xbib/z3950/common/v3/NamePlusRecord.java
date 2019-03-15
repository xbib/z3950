package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>NamePlusRecord</code> from <code>Z39-50-APDU-1995</code>
 * <pre>
 * NamePlusRecord ::=
 * SEQUENCE {
 *   name [0] IMPLICIT DatabaseName OPTIONAL
 *   record [1] EXPLICIT NamePlusRecord_record
 * }
 * </pre>
 */

public final class NamePlusRecord extends ASN1Any {

    public DatabaseName name; // optional
    public NamePlusRecordRecord record;

    /**
     * Default constructor for a NamePlusRecord.
     */
    public NamePlusRecord() {
    }

    /**
     * Constructor for a NamePlusRecord from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public NamePlusRecord(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // NamePlusRecord should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("bad BER form");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        BERConstructed tagged;

        // Decoding: name [0] IMPLICIT DatabaseName OPTIONAL

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 0 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            name = new DatabaseName(p, false);
            part++;
        }

        // Decoding: record [1] EXPLICIT NamePlusRecord_record

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() != 1 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in record");
        }

        try {
            tagged = (BERConstructed) p;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("bad BER encoding: record tag bad");
        }
        if (tagged.numberComponents() != 1) {
            throw new ASN1EncodingException("bad BER encoding: record tag bad");
        }

        record = new NamePlusRecordRecord(tagged.elementAt(0), true);
        part++;

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception("bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the NamePlusRecord.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of NamePlusRecord, implicitly tagged.
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
        if (name != null) {
            numFields++;
        }

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding enc[];

        // Encoding s_name: DatabaseName OPTIONAL

        if (name != null) {
            fields[x++] = name.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 0);
        }

        // Encoding s_record: NamePlusRecord_record

        enc = new BEREncoding[1];
        enc[0] = record.berEncode();
        fields[x] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 1, enc);
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the NamePlusRecord.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;

        if (name != null) {
            str.append("name ");
            str.append(name);
            outputted++;
        }

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("record ");
        str.append(record);

        str.append("}");

        return str.toString();
    }
}
