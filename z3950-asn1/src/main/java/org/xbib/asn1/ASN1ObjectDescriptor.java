package org.xbib.asn1;

/**
 * Representation for an ASN.1 OBJECT DESCRIPTOR.
 * The ASN.1 OBJECT DESCRIPTOR consists of a human-readable text
 * which serves to describe an information object.
 * According to clause 35.3 of the standard,
 * ObjectDescriptor ::= [UNIVERSAL 7] IMPLICIT GraphicString
 */
public final class ASN1ObjectDescriptor extends ASN1GraphicString {
    /**
     * This constant is the UNIVERSAL tag value for ObjectDescriptor.
     */
    public static final int OBJECT_DESCRIPTOR_TAG = 0x07;

    /**
     * Constructor for an ObjectDescriptor object. It sets the tag to the
     * default value of UNIVERSAL 7, and the descriptor to the given value.
     * @param descriptor descriptor
     */
    public ASN1ObjectDescriptor(String descriptor) {
        super(descriptor);
    }

    /**
     * Constructor for a ObjectDescriptor object from a primitive BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception if the BER encoding is incorrect.
     */
    public ASN1ObjectDescriptor(BEREncoding ber, boolean checkTag)
            throws ASN1Exception {
        super(ber, false);
        if (checkTag && (ber.getTag() != OBJECT_DESCRIPTOR_TAG || ber.getTagType() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException
                    ("ASN.1 ObjectDescriptor: bad BER: tag=" + ber.getTag() +
                            " expected " + OBJECT_DESCRIPTOR_TAG + "\n");
        }
    }

    /**
     * Returns a BER encoding with no implicit tag.
     *
     * @return The BER encoding
     * @throws ASN1Exception when the object is invalid
     *                       and cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, OBJECT_DESCRIPTOR_TAG);
    }
}
