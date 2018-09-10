package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>ListStatuses1</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * ListStatuses1 ::=
 * SEQUENCE {
 *   id ResultSetId
 *   status DeleteSetStatus
 * }
 * </pre>
 */
public final class ListStatuses1 extends ASN1Any {

    public ResultSetId s_id;
    public DeleteSetStatus s_status;


    /**
     * Constructor for a ListStatuses1 from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public ListStatuses1(BEREncoding ber, boolean checkTag)
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
        // ListStatuses1 should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException
                    ("ListStatuses1: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;

        // Decoding: id ResultSetId

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("ListStatuses1: incomplete");
        }
        p = berConstructed.elementAt(part);

        s_id = new ResultSetId(p, true);
        part++;

        // Decoding: status DeleteSetStatus

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("ListStatuses1: incomplete");
        }
        p = berConstructed.elementAt(part);

        s_status = new DeleteSetStatus(p, true);
        part++;

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception("ListStatuses1: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the ListStatuses1.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of ListStatuses1, implicitly tagged.
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

        // Encoding s_id: ResultSetId

        fields[x++] = s_id.berEncode();

        // Encoding s_status: DeleteSetStatus

        fields[x] = s_status.berEncode();

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the ListStatuses1.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;

        str.append("id ");
        str.append(s_id);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("status ");
        str.append(s_status);
        str.append("}");
        return str.toString();
    }

}
