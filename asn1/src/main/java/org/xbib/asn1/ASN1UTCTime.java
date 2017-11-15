package org.xbib.asn1;

/**
 * ASN.1 UTCTime.
 * The <code>UTCTime</code> type denotes a "coordinated universal time"
 * or Greenwich Mean Time (GMT) value. The value includes the local
 * time precise to either minutes or seconds, and an offset from GMT
 * in hours and minutes.
 * It can take any of the following form:
 * YYMMDDhhmmZ
 * YYMMDDhhmm+hh'mm'
 * YYMMDDhhmm-hh'mm'
 * YYMMDDhhmmssZ
 * YYMMDDhhmmss+hh'mm'
 * YYMMDDhhmmss-hh'mm'
 * YYMMDDhhmmZ
 * YYMMDDhhmmZ
 */
public final class ASN1UTCTime extends ASN1VisibleString {
    /**
     * This constant is the UNIVERSAL tag value for UTCTime.
     */
    public static final int UTC_TIME_TAG = 0x17;

    /**
     * Constructor for an UTCTime object. It sets the tag to the
     * default value of UNIVERSAL 23 (0x17).
     *
     * @param value The string value.
     */
    public ASN1UTCTime(String value) {
        super(value);
    }

    /**
     * Constructor for a UTCTime object from a BER encoding.
     *
     * @param ber      The BER encoding to use.
     * @param checkTag If true, it checks the tag. Use false if is implicitly tagged.
     * @throws ASN1Exception If the BER encoding is incorrect.
     */
    public ASN1UTCTime(BEREncoding ber, boolean checkTag)
            throws ASN1Exception {
        super(ber, false);
        if (checkTag && (ber.tagGet() != UTC_TIME_TAG || ber.tagTypeGet() != BEREncoding.UNIVERSAL_TAG)) {
            throw new ASN1EncodingException
                    ("ASN.1 UTCTime: bad BER: tag=" + ber.tagGet() +
                            " expected " + UTC_TIME_TAG + "\n");
        }
    }

    /**
     * Returns a BER encoding with no implicit tag.
     *
     * @return The BER encoding
     * @throws ASN1Exception when the object is invalid and cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, UTC_TIME_TAG);
    }
}
