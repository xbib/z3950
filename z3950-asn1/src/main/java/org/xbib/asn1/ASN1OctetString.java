package org.xbib.asn1;

import java.nio.charset.StandardCharsets;

/**
 * Representation of an ASN.1 OCTET STRING.
 * This class is used to store an ASN.1 OCTET STRING which is an
 * arbitary string of octets (eight-bit values). An OCTET STRING
 * can have any length including zero. The type is a string type.
 */
public class ASN1OctetString extends ASN1Any {

    /**
     * This constant is the ASN.1 UNIVERSAL tag value for OCTET STRING.
     */
    public static final int OCTET_STRING_TAG = 0x04;

    private static final char[] oct = {'0', '1', '2', '3', '4', '5', '6', '7'};

    private static final char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * The values of the OCTET STRING are stored in this string. Only
     * the lower bytes are valid.
     */

    private byte[] octets;

    /**
     * Constructor for an OCTET STRING object. The tag is set to the
     * default of UNIVERSAL 4, and its value to the given bytes.
     * @param data data
     */
    public ASN1OctetString(byte[] data) {
        octets = new byte[data.length];
        System.arraycopy(data, 0, octets, 0, data.length);
    }

    /**
     * Constructor for an OCTET STRING object. The tag is set to the
     * default of UNIVERSAL 4, and its value to the lower bytes of the
     * characters of the given string.
     * @param str string
     */
    public ASN1OctetString(String str) {
        octets = str.getBytes(StandardCharsets.ISO_8859_1);
    }

    /**
     * Constructor for a OCTET STRING object from a primitive BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception If the BER encoding is incorrect.
     */
    public ASN1OctetString(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        super(ber, checkTag);
    }

    /**
     * Method for initializing the object from a BER encoding.
     *
     * @param berEncoding The BER encoding to use.
     * @param checkTag    If true, it checks the tag. Does nothing for ASN1Any.
     * @throws ASN1EncodingException If the BER cannot be decoded.
     */
    @Override
    public void berDecode(BEREncoding berEncoding, boolean checkTag)
            throws ASN1EncodingException {
        if (checkTag && (berEncoding.getTag() != OCTET_STRING_TAG ||
                    berEncoding.getTagType() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException("bad BER: tag=" + berEncoding.getTag() +
                            " expected " + OCTET_STRING_TAG + "\n");
        }
        if (berEncoding instanceof BERPrimitive) {
            BERPrimitive ber = (BERPrimitive) berEncoding;
            int[] encoding = ber.getContentOctets();
            StringBuilder buf = new StringBuilder(encoding.length);
            for (int anEncoding : encoding) {
                buf.append((char) (anEncoding & 0x00ff));
            }
            octets = buf.toString().getBytes(StandardCharsets.ISO_8859_1);
        } else {
            throw new ASN1EncodingException("decode from constructed NOT IMPLEMENTED YET");
        }
    }

    /**
     * Makes a BER encoding of the OCTET STRING.
     * OCTET STRINGs can have a primitive encoding and a constructed
     * encoding. This implemented performs the primitive encoding (which
     * is the DER form).
     *
     * @return The BER encoding of the OCTET STRING
     * @throws ASN1Exception when the OCTET STRING is invalid
     *                       and cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, OCTET_STRING_TAG);
    }

    /**
     * Makes a BER encoding of the OCTET STRING.
     * OCTET STRINGs can have a primitive encoding and a constructed
     * encoding. This implemented performs the primitive encoding (which
     * is the DER form).
     *
     * @return The BER encoding of the OCTET STRING
     * @throws ASN1Exception when the OCTET STRING is invalid
     *                       and cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int size = octets.length;
        int[] encoding = new int[size];
        for (int index = 0; index < size; index++) {
            encoding[index] = octets[index] & 0x00ff;
        }
        return new BERPrimitive(tagType, tag, encoding);
    }

    /**
     * Method to set the OCTET STRING's value.
     *
     * @param octetArray the value to set the OCTET STRING to.
     * @return the object.
     */
    public ASN1OctetString set(byte[] octetArray) {
        octets = new byte[octetArray.length];
        System.arraycopy(octetArray, 0, octets, 0, octetArray.length);
        return this;
    }

    /**
     * Method to set the OCTET STRING's value.
     *
     * @param str the value to set the OCTET STRING to.
     * @return the object.
     */
    public ASN1OctetString set(String str) {
        octets = str.getBytes(StandardCharsets.ISO_8859_1);
        return this;
    }

    /**
     * Method to get the OCTET STRING's value as a String.
     *
     * @return the OCTET STRING's current value.
     */
    public String get() {
        return new String(octets, StandardCharsets.ISO_8859_1);
    }

    /**
     * Method to get the OCTET STRING's value as an array of bytes.
     *
     * @return the OCTET STRING's current value.
     */
    public byte[] getBytes() {
        return octets;
    }

    /**
     * Returns a new String object representing this ASN.1 object's value.
     */
    @Override
    public String toString() {
        int size = octets.length;
        StringBuilder buf = new StringBuilder(32 + (size * 4));
        int printable = 0;
        int binary = 0;
        for (byte octet1 : octets) {
            char octet = (char) octet1;
            if ((' ' <= octet && octet <= '~') || octet == '\n') {
                printable++;
            } else {
                binary++;
            }
        }
        if (binary <= printable) {
            buf.append('"');
            for (byte octet1 : octets) {
                char octet = (char) octet1;
                if (' ' <= octet && octet <= '~') {
                    if (octet == '\\' || octet == '"' || octet == '\'') {
                        buf.append('\\');
                    }
                    buf.append(octet);
                } else if (octet == '\n') {
                    buf.append("\\n");
                } else if (octet == '\t') {
                    buf.append("\\t");
                } else if (octet == '\r') {
                    buf.append("\\r");
                } else if (octet == '\b') {
                    buf.append("\\b");
                } else if (octet == '\f') {
                    buf.append("\\f");
                } else {
                    buf.append('\\');
                    buf.append(oct[(octet >> 6) & 0x07]);
                    buf.append(oct[(octet >> 3) & 0x07]);
                    buf.append(oct[octet & 0x07]);
                }
            }
            buf.append('"');
        } else {
            buf.append('\'');
            for (byte octet1 : octets) {
                char octet = (char) octet1;
                buf.append(hex[(octet >> 4) & 0x0f]);
                buf.append(hex[octet & 0x0f]);
            }
            buf.append("'H");
        }
        return buf.toString();
    }
}
