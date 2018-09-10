package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>OtherInformation</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * OtherInformation ::=
 * [201] IMPLICIT SEQUENCE OF OtherInformation1
 * </pre>
 */
public final class OtherInformation extends ASN1Any {

    public OtherInformation1[] value;

    /**
     * Default constructor for a OtherInformation.
     */

    public OtherInformation() {
    }

    /**
     * Constructor for a OtherInformation from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public OtherInformation(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // Check tag matches

        if (checkTag) {
            if (ber.getTag() != 201 ||
                    ber.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
                throw new ASN1EncodingException("bad BER: tag=" + ber.getTag() + " expected 201");
            }
        }

        // OtherInformation should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("bad BER form");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        value = new OtherInformation1[numParts];
        int p;
        for (p = 0; p < numParts; p++) {
            value[p] = new OtherInformation1(berConstructed.elementAt(p), true);
        }
    }

    /**
     * Returns a BER encoding of the OtherInformation.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 201);
    }

    /**
     * Returns a BER encoding of OtherInformation, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        BEREncoding fields[] = new BERConstructed[value.length];
        int p;

        for (p = 0; p < value.length; p++) {
            fields[p] = value[p].berEncode();
        }

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the OtherInformation.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        int p;

        for (p = 0; p < value.length; p++) {
            str.append(value[p]);
        }

        str.append("}");

        return str.toString();
    }
}
