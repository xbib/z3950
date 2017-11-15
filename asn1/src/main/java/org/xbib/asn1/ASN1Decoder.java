package org.xbib.asn1;

/**
 * ASN1Decoder.
 * This is an ASN1 decoder which can handle all the standard ASN.1 types
 * (those with UNIVERSAL tag types).
 * It is used for decoding generic BER encodings into ASN.1 objects.
 */
public class ASN1Decoder {

    private ASN1Decoder() {
        // private constructor
    }

    public static ASN1Any toASN1(BEREncoding ber) throws ASN1Exception {
        if (ber.tagTypeGet() == BEREncoding.UNIVERSAL_TAG) {
            switch (ber.tagGet()) {
                case ASN1Boolean.TAG:
                    return new ASN1Boolean(ber, true);
                case ASN1Integer.INTEGER_TAG:
                    return new ASN1Integer(ber, true);
                case ASN1BitString.BIT_STRING_TAG:
                    return new ASN1BitString(ber, true);
                case ASN1OctetString.OCTET_STRING_TAG:
                    return new ASN1OctetString(ber, true);
                case ASN1Null.NULL_TAG:
                    return new ASN1Null(ber, true);
                case ASN1ObjectIdentifier.OBJECT_IDENTIFIER_TAG:
                    return new ASN1ObjectIdentifier(ber, true);
                case ASN1ObjectDescriptor.OBJECT_DESCRIPTOR_TAG:
                    return new ASN1ObjectDescriptor(ber, true);
                case ASN1External.EXTERNAL_TAG:
                    return new ASN1External(ber, true);
                case ASN1Enumerated.ENUMERATED_TAG:
                    return new ASN1Enumerated(ber, true);
                case ASN1Sequence.SEQUENCE_TAG:
                    return new ASN1Sequence(ber, true);
                case ASN1Set.SET_TAG:
                    return new ASN1Set(ber, true);
                case ASN1NumericString.NUMERIC_STRING_TAG:
                    return new ASN1NumericString(ber, true);
                case ASN1PrintableString.PRINTABLE_STRING_TAG:
                    return new ASN1PrintableString(ber, true);
                case ASN1T61String.T61_STRING_TAG:
                    return new ASN1T61String(ber, true);
                case ASN1VideotexString.VIDEOTEX_STRING_TAG:
                    return new ASN1VideotexString(ber, true);
                case ASN1IA5String.IA5_STRING_TAG:
                    return new ASN1IA5String(ber, true);
                case ASN1UTCTime.UTC_TIME_TAG:
                    return new ASN1UTCTime(ber, true);
                case ASN1GeneralizedTime.GENERALIZED_TIME_TAG:
                    return new ASN1GeneralizedTime(ber, true);
                case ASN1GraphicString.GRAPHIC_STRING_TAG:
                    return new ASN1GraphicString(ber, true);
                case ASN1VisibleString.TAG:
                    return new ASN1VisibleString(ber, true);
                case ASN1GeneralString.GENERAL_STRING_TAG:
                    return new ASN1GeneralString(ber, true);
                default:
                    break;
            }
        }
        return new ASN1Any(ber, true);
    }
}
