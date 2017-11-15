package org.xbib.asn1;

/**
 * ASN1 ANY
 * The ANY type denotes an arbitary value of an arbitary type.
 * This class also serves as the base class for all ASN.1 classes.
 * The ASN.1 syntax is defined in
 * <strong>Information Technology - Open Systems Interconnection -
 * Specification of Abstract Syntax Notation One (ASN.1)</strong>
 * AS 3625-1991
 * ISO/IEC 8824:1990
 * The current implementation assumes values are limited to 32-bit
 * signed integers for tags, lengths, etc.
 */
public class ASN1Any {

    /* Hack to support creation of ASN1 ANY types from a BER and have
    it behave normally.  This is not used by any other ASN.1 subclasses.
    It is a waste of space in that respect. */

    private BEREncoding asn1anyBer;

    public ASN1Any() {
        //
    }

    /**
     * Constructor for an ASN.1 ANY object from a BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Does nothing for ASN1Any.
     * @throws ASN1Exception if the BER encoding is incorrect.
     */
    public ASN1Any(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        berDecode(ber, checkTag);
    }

    /**
     * Method for initializing the object from a BER encoding.
     * All classes derived from this one must implement a version of this.
     * This method will be overridden by derived types.
     *
     * @param berEncoding The BER encoding to use.
     * @param checkTag    If true, it checks the tag. Does nothing for ASN1Any.
     * @throws ASN1Exception If the BER encoding is incorrect.
     *                       Never occurs for ASN1Any.
     */
    public void berDecode(BEREncoding berEncoding, boolean checkTag)
            throws ASN1Exception {
        asn1anyBer = berEncoding;
    }

    /**
     * Constructs a BER encoding for this ASN.1 object.
     * This method is usually overridden by a subclass method.
     *
     * @return BER encoding
     * @throws ASN1Exception If the object cannot be BER encoded.
     */
    public BEREncoding berEncode() throws ASN1Exception {
        if (asn1anyBer == null) {
            throw new ASN1EncodingException("uninitialised");
        }
        return asn1anyBer;
    }

    /**
     * Returns a BER encoding of ASN1Any, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag     The implicit tag number.
     * @return The BER encoding of the object.
     * @throws ASN1Exception when invalid or cannot be encoded.
     */
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        if (asn1anyBer == null) {
            throw new ASN1EncodingException("uninitialised");
        }
        throw new ASN1EncodingException("cannot implicitly tag");
    }

    /**
     * Returns a new String object representing this ASN.1 object's value.
     *
     * @return A text string representation.
     */
    @Override
    public String toString() {
        if (asn1anyBer == null) {
            return "<empty ASN.1 ANY>";
        }
        return asn1anyBer.toString();
    }
}
