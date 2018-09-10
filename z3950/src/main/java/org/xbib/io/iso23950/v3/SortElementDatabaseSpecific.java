package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>SortElement_datbaseSpecific</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * SortElement_datbaseSpecific ::=
 * SEQUENCE {
 *   databaseName DatabaseName
 *   dbSort SortKey
 * }
 * </pre>
 */
public final class SortElementDatabaseSpecific extends ASN1Any {

    public DatabaseName s_databaseName;
    public SortKey s_dbSort;

    /**
     * Constructor for a SortElement_datbaseSpecific from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public SortElementDatabaseSpecific(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // SortElement_datbaseSpecific should be encoded by a constructed BER

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

        // Decoding: databaseName DatabaseName

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        s_databaseName = new DatabaseName(p, true);
        part++;

        // Decoding: dbSort SortKey

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        s_dbSort = new SortKey(p, true);
        part++;

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception("bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the SortElement_datbaseSpecific.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of SortElement_datbaseSpecific, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        // Calculate the number of fields in the encoding

        int numFields = 2; // number of mandatories

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;

        // Encoding s_databaseName: DatabaseName

        fields[x++] = s_databaseName.berEncode();

        // Encoding s_dbSort: SortKey

        fields[x] = s_dbSort.berEncode();
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the SortElement_datbaseSpecific.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;

        str.append("databaseName ");
        str.append(s_databaseName);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("dbSort ");
        str.append(s_dbSort);

        str.append("}");

        return str.toString();
    }

}
