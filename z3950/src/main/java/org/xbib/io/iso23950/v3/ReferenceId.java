package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1OctetString;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>ReferenceId</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * ReferenceId ::=
 * [2] IMPLICIT OCTET STRING
 * </pre>
 */
public final class ReferenceId extends ASN1Any {

    public ASN1OctetString value;

    /**
     * Default constructor for a ReferenceId.
     */

    public ReferenceId() {
    }

    /**
     * Constructor for a ReferenceId from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public ReferenceId(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
            if (ber.getTag() != 2 ||
                    ber.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
                throw new ASN1EncodingException
                        ("ReferenceId: bad BER: tag=" + ber.getTag() + " expected 2\n");
            }
        }

        value = new ASN1OctetString(ber, false);
    }

    /**
     * Returns a BER encoding of the ReferenceId.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 2);
    }

    /**
     * Returns a BER encoding of ReferenceId, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        return value.berEncode(tagType, tag);
    }

    /**
     * Returns a new String object containing a text representing
     * of the ReferenceId.
     */
    @Override
    public String toString() {
        return value.toString();
    }
}
