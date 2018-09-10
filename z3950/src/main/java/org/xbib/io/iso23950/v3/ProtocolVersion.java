package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1BitString;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>ProtocolVersion</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * ProtocolVersion ::=
 * [3] IMPLICIT BIT STRING
 * </pre>
 */
public final class ProtocolVersion extends ASN1Any {

    public ASN1BitString value;

    /**
     * Default constructor for a ProtocolVersion.
     */
    public ProtocolVersion() {
    }

    /**
     * Constructor for a ProtocolVersion from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public ProtocolVersion(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
            if (ber.getTag() != 3 ||
                    ber.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
                throw new ASN1EncodingException("ProtocolVersion: bad BER: tag=" + ber.getTag() + " expected 3\n");
            }
        }

        value = new ASN1BitString(ber, false);
    }

    /**
     * Returns a BER encoding of the ProtocolVersion.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 3);
    }

    /**
     * Returns a BER encoding of ProtocolVersion, implicitly tagged.
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
     * of the ProtocolVersion.
     */
    @Override
    public String toString() {
        return value.toString();
    }
}
