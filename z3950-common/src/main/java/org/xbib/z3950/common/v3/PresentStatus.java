package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>PresentStatus</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * PresentStatus ::=
 * [27] IMPLICIT INTEGER
 * </pre>
 */
public final class PresentStatus extends ASN1Any {
    /**
     * success -	All of the expected response records are available.
     * partial-1 -	Not all of the expected response records can be returned because the request was terminated by access control.
     * partial-2 -	Not all of the expected response records can be returned because they will not fit within the preferred message size.
     * partial-3 -	Not all of the expected response records can be returned because the request was terminated by resource control, at origin request.
     * partial-4 -	Not all of the expected response records can be returned because the request was terminated by resource control, by the target.
     * failure - None of the expected response records can be returned. One or more non-surrogate diagnostic records is returned
     */
    public static final int E_success = 0;
    public static final int E_partial_1 = 1;
    public static final int E_partial_2 = 2;
    public static final int E_partial_3 = 3;
    public static final int E_partial_4 = 4;
    public static final int E_failure = 5;
    public ASN1Integer value;

    /**
     * Constructor for a PresentStatus from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public PresentStatus(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
            if (ber.getTag() != 27 ||
                    ber.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
                throw new ASN1EncodingException("bad BER: tag=" + ber.getTag() + " expected 27");
            }
        }

        value = new ASN1Integer(ber, false);
    }

    /**
     * Returns a BER encoding of the PresentStatus.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 27);
    }

    /**
     * Returns a BER encoding of PresentStatus, implicitly tagged.
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
     * of the PresentStatus.
     */
    @Override
    public String toString() {
        return value.toString();
    }
}
