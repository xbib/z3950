package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>CloseReason</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * CloseReason ::=
 * [211] IMPLICIT INTEGER
 * </pre>
 */
public final class CloseReason extends ASN1Any {

    public static final int E_FINISHED = 0;
    public static final int E_SHUTDOWN = 1;
    public static final int E_SYSTEM_PROBLEM = 2;
    public static final int E_COST_LIMIT = 3;
    public static final int E_RESOURCES = 4;
    public static final int E_SECURITY_VIOLATION = 5;
    public static final int E_PROTOCOL_ERROR = 6;
    public static final int E_LACK_OF_ACTIVITY = 7;
    public static final int E_PEER_ABORT = 8;
    public static final int E_UNSPECIFIED = 9;
    public ASN1Integer value;

    /**
     * Default constructor for a CloseReason.
     */
    public CloseReason() {
    }

    /**
     * Constructor for a CloseReason from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public CloseReason(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        if (checkTag) {
            if (ber.getTag() != 211 ||
                    ber.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
                throw new ASN1EncodingException("CloseReason: bad BER: tag=" + ber.getTag() + " expected 211");
            }
        }
        value = new ASN1Integer(ber, false);
    }

    /**
     * Returns a BER encoding of the CloseReason.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 211);
    }

    /**
     * Returns a BER encoding of CloseReason, implicitly tagged.
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
     * of the CloseReason.
     */
    @Override
    public String toString() {
        return value.toString();
    }
}
