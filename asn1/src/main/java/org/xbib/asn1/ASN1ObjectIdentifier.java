package org.xbib.asn1;

/**
 * Representation of an ASN.1 OBJECT IDENTIFIER.
 * The <code>OBJECT IDENTIFIER</code> type denotes an object identifier,
 * which is a sequence of integer components. An OBJECT IDENTIFIER can
 * have any number of components, whch are generally non-negative.
 * This type is a non-string type.
 */
public final class ASN1ObjectIdentifier extends ASN1Any {
    /**
     * This constant is the ASN.1 UNIVERSAL tag value for OBJECT IDENTIFIER.
     */
    public static final int OBJECT_IDENTIFIER_TAG = 0x06;

    /**
     * The components of the OBJECT IDENTIFER are stored in this
     * variable as an array of integers.
     */
    private int[] oid;

    /**
     * Constructor for an ASN.1 OBJECT IDENTIFER object. The tag is set
     * to the default tag of UNIVERSAL 6, and the given OID value.
     * @param oidValue OID
     */
    public ASN1ObjectIdentifier(int[] oidValue) {
        oid = oidValue;
    }

    /**
     * Constructor for an ASN.1 OBJECT IDENTIFIER object from a BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception If the BER encoding is incorrect.
     */
    public ASN1ObjectIdentifier(BEREncoding ber, boolean checkTag)
            throws ASN1Exception {
        super(ber, checkTag);
    }

    /**
     * Method for initializing the object from a BER encoding.
     *
     * @param berEncoding The BER encoding to use.
     * @param checkTag    If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1EncodingException If the BER encoding cannot be decoded.
     */
    @Override
    public void berDecode(BEREncoding berEncoding, boolean checkTag) throws ASN1EncodingException {
        if (checkTag && (berEncoding.getTag() != OBJECT_IDENTIFIER_TAG ||
                berEncoding.getTagType() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException("bad BER: tag=" + berEncoding.getTag() +
                            " expected " + OBJECT_IDENTIFIER_TAG + "\n");
        }
        if (!(berEncoding instanceof BERPrimitive)) {
            throw new ASN1EncodingException("bad form, constructed");
        }
        BERPrimitive ber = (BERPrimitive) berEncoding;
        int[] encoding = ber.getContentOctets();
        if (encoding.length < 2) {
            throw new ASN1EncodingException("invalid encoding, length = " +
                            encoding.length);
        }
        int numComponents = 2;
        for (int index = 1; index < encoding.length; index++) {
            if ((encoding[index] & 0x80) == 0) {
                numComponents++;
            }
        }
        oid = new int[numComponents];
        oid[0] = encoding[0] / 40;
        oid[1] = encoding[0] % 40;
        int index = 1;
        for (int component = 2; component < numComponents; component++) {
            oid[component] = 0;
            int octet;
            do {
                octet = encoding[index++];
                oid[component] <<= 7;
                oid[component] |= (octet & 0x7f);
            } while ((octet & 0x80) != 0);
        }
    }

    /**
     * Returns a BER encoding of the OBJECT IDENTIFIER.
     * The current implementation rejects negative OID components (should it?)
     *
     * @return The BER encoding of the OBJECT IDENTIFIER
     * @throws ASN1Exception when the OBJECT IDENTIFIER is invalid
     *                       and cannot be encoded. According to X.208, an OBJECT
     *                       IDENTIFIER must have at least two components, the first
     *                       has values of (0, 1, or 2) and the second between 0 and 39
     *                       inclusive.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, OBJECT_IDENTIFIER_TAG);
    }

    /**
     * Returns a BER encoding of the OBJECT IDENTIFIER.
     * The current implementation rejects negative OID components.
     *
     * @return The BER encoding of the OBJECT IDENTIFIER
     * @throws ASN1Exception when the OBJECT IDENTIFIER is invalid
     *                       and cannot be encoded. According to X.208, an OBJECT
     *                       IDENTIFIER must have at least two components, the first
     *                       has values of (0, 1, or 2) and the second between 0 and 39
     *                       inclusive.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        if (oid.length < 2) {
            throw new ASN1Exception("less than 2 components, violates X.208");
        }
        if (oid[0] < 0 || 2 < oid[0]) {
            throw new ASN1Exception("First component invalid, value = " + oid[0]);
        }
        if (oid[1] < 0 || 39 < oid[1]) {
            throw new ASN1Exception("Second component invalid, value = " + oid[1]);
        }
        int numBytes = 1;
        for (int index = 2; index < oid.length; index++) {
            int tmpValue = oid[index];
            if (tmpValue < 0) {
                throw new ASN1Exception("component " + (index + 1) +
                                " is negative, value = " + tmpValue);
            }
            do {
                numBytes++;
                tmpValue >>= 7;
            } while (tmpValue != 0);
        }
        int[] octets = new int[numBytes];
        octets[0] = (40 * oid[0]) + oid[1];
        int bcount = 0;
        for (int index = 2; index < oid.length; index++) {
            int numberBytes = 0;
            int tmpValue = oid[index];
            do {
                numberBytes++;
                tmpValue >>= 7;
            } while (tmpValue != 0);
            tmpValue = oid[index];
            for (int digit = numberBytes - 1; 0 <= digit; digit--) {
                octets[++bcount] = (tmpValue >> (digit * 7)) & 0x7f;
                if (digit != 0) {
                    octets[bcount] |= 0x80;
                }
            }
        }
        return new BERPrimitive(tagType, tag, octets);
    }

    /**
     * Method to set the OBJECT IDENTIFIER's value.
     *
     * @param newVal the value to set the OBJECT IDENTIFIER to.
     * @return this object identifier
     */
    public ASN1ObjectIdentifier set(int[] newVal) {
        oid = newVal;
        return this;
    }

    /**
     * Method to get the OBJECT IDENTIFIER's value. The returned value
     * should not be modified in any way.
     *
     * @return the OBJECT IDENTIFIER's current value.
     */
    public int[] get() {
        return oid;
    }

    /**
     * Returns a new String object representing this ASN.1 object's value.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int index = 0; index < oid.length; index++) {
            if (index != 0) {
                str.append('.');
            }
            str.append(String.valueOf(oid[index]));
        }
        return new String(str);
    }
}
