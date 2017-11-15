package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>OtherInformation1</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * OtherInformation1 ::=
 * SEQUENCE {
 *   category [1] IMPLICIT InfoCategory OPTIONAL
 *   information OtherInformation_information
 * }
 * </pre>
 */
public final class OtherInformation1 extends ASN1Any {

    public InfoCategory s_category; // optional
    public OtherInformationInformation s_information;

    /**
     * Constructor for a OtherInformation1 from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public OtherInformation1(BEREncoding ber, boolean checkTag)
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
        // OtherInformation1 should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("OtherInformation1: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;

        // Decoding: category [1] IMPLICIT InfoCategory OPTIONAL

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("OtherInformation1: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 1 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_category = new InfoCategory(p, false);
            part++;
        }

        // Decoding: information OtherInformation_information

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("OtherInformation1: incomplete");
        }
        p = berConstructed.elementAt(part);

        s_information = new OtherInformationInformation(p, true);
        part++;

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception("OtherInformation1: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the OtherInformation1.
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
     * Returns a BER encoding of OtherInformation1, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */

    public BEREncoding
    berEncode(int tagType, int tag)
            throws ASN1Exception {
        // Calculate the number of fields in the encoding

        int numFields = 1; // number of mandatories
        if (s_category != null) {
            numFields++;
        }

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;

        // Encoding s_category: InfoCategory OPTIONAL

        if (s_category != null) {
            fields[x++] = s_category.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
        }

        // Encoding s_information: OtherInformation_information

        fields[x] = s_information.berEncode();

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the OtherInformation1.
     */

    public String
    toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;

        if (s_category != null) {
            str.append("category ");
            str.append(s_category);
            outputted++;
        }

        if (0 < outputted) {
            str.append(", ");
        }


        str.append("information ");
        str.append(s_information);

        str.append("}");

        return str.toString();
    }

}