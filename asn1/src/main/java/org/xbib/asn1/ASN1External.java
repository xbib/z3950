package org.xbib.asn1;

/**
 * ASN1 EXTERNAL
 * The <code>EXTERNAL</code> type represents an external object.
 * According to clause 34.4 of the ASN.1 standard,
 * the EXTERNAL type can be defined as:
 * <pre>
 * EXTERNAL := [UNIVERSAL 8] IMPLICIT SEQUENCE {
 *               direct_reference OBJECT IDENTIFIER OPTIONAL,
 *               indirect_reference INTEGER OPTIONAL,
 *               data_value_descriptor ObjectDescriptor OPTIONAL,
 *               encoding CHOICE {
 *                 single_ASN1_type [0] ANY,
 *                 octet_Aligned    [1] IMPLICIT OCTET STRING,
 *                 arbitrary        [2] IMPLICIT BIT STRING
 *               }
 *             }
 * </pre>
 * This construct has been represented by a class with six
 * variables:
 * sDirectReference,
 * sIndirectReference,
 * sDataValueDescriptor,
 * cSingleASN1Type,
 * cOctetAligned,
 * cArbitrary.
 * The first three should be set to point to the appropriate object
 * if present, or null if not.
 * One of the last three variables should be set to non-null (the choice)
 * and the rest to null.
 */
public final class ASN1External extends ASN1Any {
    /**
     * This constant is the ASN.1 UNIVERSAL tag value for an EXTERNAL.
     */
    public static final int EXTERNAL_TAG = 0x08;

    /*
     * The values are stored in these variables.
     */
    private ASN1ObjectIdentifier sDirectReference;
    private ASN1Integer sIndirectReference;
    private ASN1ObjectDescriptor sDataValueDescriptor;
    private ASN1Any cSingleASN1Type;
    private ASN1OctetString cOctetAligned;
    private ASN1BitString cArbitrary;

    /**
     * Constructor for an ASN.1 EXTERNAL object. It sets the tag to the
     * default value of UNIVERSAL 8.
     */
    public ASN1External() {
        //
    }

    /**
     * Constructor for an ASN.1 EXTERNAL object from a BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception If the BER encoding is incorrect.
     */
    public ASN1External(BEREncoding ber, boolean checkTag)
            throws ASN1Exception {
        super(ber, checkTag);
    }

    /**
     * Method for initializing the object from a BER encoding.
     *
     * @param berEncoding The BER encoding to use.
     * @param checkTag    If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception If the BER encoding is incorrect.
     */
    @Override
    public void berDecode(BEREncoding berEncoding, boolean checkTag)
            throws ASN1Exception {
        if (checkTag && (berEncoding.getTag() != EXTERNAL_TAG || berEncoding.getTagType() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException("bad BER: tag=" + berEncoding.getTag() +
                            " expected " + EXTERNAL_TAG + "\n");
        }
        if (berEncoding instanceof BERPrimitive) {
            throw new ASN1EncodingException("incorrect form, primitive encoding");
        }
        BERConstructed ber = (BERConstructed) berEncoding;
        sDirectReference = null;
        sIndirectReference = null;
        sDataValueDescriptor = null;
        cSingleASN1Type = null;
        cOctetAligned = null;
        cArbitrary = null;
        int numParts = ber.numberComponents();
        if (numParts < 1) {
            throw new ASN1EncodingException("incomplete");
        }
        int part = 0;
        BEREncoding p = ber.elementAt(part);
        if (p.getTag() == ASN1ObjectIdentifier.OBJECT_IDENTIFIER_TAG &&
                p.getTagType() == BEREncoding.UNIVERSAL_TAG) {
            sDirectReference = new ASN1ObjectIdentifier(p, true);
            if (numParts <= ++part) {
                throw new ASN1EncodingException("incomplete");
            }
            p = ber.elementAt(part);
        }
        if (p.getTag() == ASN1Integer.INTEGER_TAG &&
                p.getTagType() == BEREncoding.UNIVERSAL_TAG) {
            sIndirectReference = new ASN1Integer(p, true);
            if (numParts <= ++part) {
                throw new ASN1EncodingException("incomplete");
            }
            p = ber.elementAt(part);
        }
        if (p.getTag() == ASN1ObjectDescriptor.OBJECT_DESCRIPTOR_TAG &&
                p.getTagType() == BEREncoding.UNIVERSAL_TAG) {
            sDataValueDescriptor = new ASN1ObjectDescriptor(p, true);
            if (numParts <= ++part) {
                throw new ASN1EncodingException("incomplete");
            }
            p = ber.elementAt(part);
        }
        switch (p.getTag()) {
            case 0:
                if (!(p instanceof BERConstructed)) {
                    throw new ASN1EncodingException("singleASN1type: bad form, primitive");
                }
                if (((BERConstructed) p).numberComponents() != 1) {
                    throw new ASN1EncodingException("singleASN1type: bad form, no explicit tag");
                }
                cSingleASN1Type = ASN1Decoder.toASN1(((BERConstructed) p).elementAt(0));
                break;
            case 1:
                // octetAligned [1] IMPLICIT OCTET STRING
                if (p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
                    throw new ASN1EncodingException("encoding: bad tag type " + p);
                }
                cOctetAligned = new ASN1OctetString(p, false);
                break;
            case 2:
                // arbitrary [2] IMPLICIT BIT STRING
                if (p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
                    throw new ASN1EncodingException("encoding: bad tag type " + p);
                }
                cArbitrary = new ASN1BitString(p, false);
                break;
            default:
                throw new ASN1EncodingException("encoding: tag = " + p.getTag());
        }
        if (part != (numParts - 1)) {
            throw new ASN1Exception("extra element(s)");
        }
    }

    public ASN1OctetString getcOctetAligned() {
        return cOctetAligned;
    }

    public ASN1Any getSingleASN1Type() {
        return cSingleASN1Type;
    }

    /**
     * Returns a BER encoding of the EXTERNAL.
     *
     * @return The BER encoding of the EXTERNAL
     * @throws ASN1Exception when the EXTERNAL is invalid
     *                       and cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, EXTERNAL_TAG);
    }

    /**
     * Returns a BER encoding of the EXTERNAL.
     *
     * @return The BER encoding of the EXTERNAL
     * @throws ASN1Exception when the EXTERNAL is invalid
     *                       and cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numParts = 0;
        if (cSingleASN1Type != null) {
            numParts++;
        }
        if (cOctetAligned != null) {
            numParts++;
        }
        if (cArbitrary != null) {
            numParts++;
        }
        if (numParts < 1) {
            throw new ASN1Exception("no encoding has been set");
        }
        if (1 < numParts) {
            throw new ASN1Exception("more than one encoding set");
        }
        if (sDirectReference != null) {
            numParts++;
        }
        if (sIndirectReference != null) {
            numParts++;
        }
        if (sDataValueDescriptor != null) {
            numParts++;
        }
        BEREncoding[] parts = new BEREncoding[numParts];
        int part = 0;
        if (sDirectReference != null) {
            parts[part++] = sDirectReference.berEncode();
        }
        if (sIndirectReference != null) {
            parts[part++] = sIndirectReference.berEncode();
        }
        if (sDataValueDescriptor != null) {
            parts[part++] = sDataValueDescriptor.berEncode();
        }
        if (cSingleASN1Type != null) {
            BEREncoding[] contents = new BEREncoding[1];
            contents[0] = cSingleASN1Type.berEncode();
            parts[part] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 0,
                    contents);
        } else if (cOctetAligned != null) {
            parts[part] = cOctetAligned.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG,
                    1);
        } else if (cArbitrary != null) {
            parts[part] = cArbitrary.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG,
                    2);
        }
        return new BERConstructed(tagType, tag, parts);
    }

    /**
     * Returns a new String object representing this ASN.1 object's value.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        boolean hasElement = false;
        if (sDirectReference != null) {
            str.append("directReference ");
            str.append(sDirectReference);
            hasElement = true;
        }
        if (sIndirectReference != null) {
            if (hasElement) {
                str.append(", ");
            }
            str.append("indirectReference ");
            str.append(sIndirectReference);
            hasElement = true;
        }
        if (sDataValueDescriptor != null) {
            if (hasElement) {
                str.append(", ");
            }
            str.append("dataValueDescriptor ");
            str.append(sDataValueDescriptor);
            hasElement = true;
        }
        if (hasElement) {
            str.append(", ");
        }
        str.append("encoding {");
        if (cSingleASN1Type != null) {
            str.append("singleASN1type ");
            str.append(cSingleASN1Type);
        }
        if (cOctetAligned != null) {
            str.append("octetAligned ");
            str.append(cOctetAligned);
        }
        if (cArbitrary != null) {
            str.append("arbitrary ");
            str.append(cArbitrary);
        }
        str.append("}}");
        return str.toString();
    }
}
