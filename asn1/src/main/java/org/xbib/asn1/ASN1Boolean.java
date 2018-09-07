package org.xbib.asn1;

/**
 * Representation of an ASN.1 <code>BOOLEAN</code>.
 * The BOOLEAN type denotes a Boolean value: either true or false.
 */
public final class ASN1Boolean extends ASN1Any {

    /**
     * This constant is the ASN.1 UNIVERSAL tag value for BOOLEAN.
     */
    public static final int TAG = 0x01;
    /**
     * The value of the BOOLEAN is stored in this variable.
     */
    private boolean value;

    /**
     * Default constructor for an ASN.1 BOOLEAN object. It sets the tag
     * to the default of UNIVERSAL 1, and the value to bool.
     *
     * @param bool the value of the BOOLEAN.
     */

    public ASN1Boolean(boolean bool) {
        value = bool;
    }

    /**
     * Constructor for an ASN.1 BOOLEAN object from a BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception if the BER encoding is incorrect.
     */
    public ASN1Boolean(BEREncoding ber, boolean checkTag)
            throws ASN1Exception {
        super(ber, checkTag); // superclass will call berDecode
    }

    /**
     * Method for initializing the object from a BER encoding.
     *
     * @param berEncoding The BER encoding to use.
     * @param checkTag    If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1EncodingException if the BER encoding is incorrect.
     */
    @Override
    public void berDecode(BEREncoding berEncoding, boolean checkTag)
            throws ASN1EncodingException {
        if (checkTag && (berEncoding.tagGet() != TAG || berEncoding.tagTypeGet() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException
                    ("ASN.1 BOOLEAN: bad BER: tag=" + berEncoding.tagGet() +
                            " expected " + "TAG\n");
        }
        if (berEncoding instanceof BERPrimitive) {
            BERPrimitive ber = (BERPrimitive) berEncoding;
            int[] encoding = ber.getContentOctets();
            if (encoding.length != 1) {
                throw new ASN1EncodingException("ASN.1 BOOLEAN: invalid encoding, length = " + encoding.length);
            }
            value = encoding[0] != 0;
        } else {
            throw new ASN1EncodingException
                    ("ASN.1 BOOLEAN: bad BER: decoding constructed NOT IMPLEMENTED YET");
        }
    }

    /**
     * Returns a BER encoding of the BOOLEAN.
     *
     * @return The BER encoding of the BOOLEAN
     * @throws ASN1Exception when the BOOLEAN is invalid
     *                       and cannot be encoded.
     */
    @Override
    public BEREncoding berEncode()
            throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, TAG);
    }

    /**
     * Returns a BER encoding of the BOOLEAN. Implicitly tagged.
     *
     * @return The BER encoding of the BOOLEAN
     * @throws ASN1Exception when the BOOLEAN is invalid
     *                       and cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int[] encoding = new int[1];
        if (value) {
            encoding[0] = 0xff; // TRUE (in fact, any non-zero will do)
        } else {
            encoding[0] = 0x00; // FALSE
        }
        return new BERPrimitive(tagType, tag, encoding);
    }

    /**
     * Method to set the boolean's value.
     *
     * @param newVal the value to set the BOOLEAN to.
     * @return the boolean
     */
    public ASN1Boolean set(boolean newVal) {
        value = newVal;
        return this;
    }

    /**
     * Method to get the boolean's value.
     *
     * @return the BOOLEAN's current value.
     */
    public boolean get() {
        return value;
    }

    /**
     * Returns a new String object representing this ASN.1 object's value.
     */
    @Override
    public String toString() {
        return value ? "true" : "false";
    }
}
