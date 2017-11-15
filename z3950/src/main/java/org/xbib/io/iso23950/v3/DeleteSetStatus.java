package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>DeleteSetStatus</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * DeleteSetStatus ::=
 * [33] IMPLICIT INTEGER
 * </pre>
 */
public final class DeleteSetStatus extends ASN1Any {

    public static final int E_SUCCESS = 0;
    public static final int E_RESULT_SET_DID_NOT_EXIST = 1;
    public static final int E_PREVIOUSLY_DELETED_BY_TARGET = 2;
    public static final int E_SYSTEM_PROBLEM_AT_TARGET = 3;
    public static final int E_ACCESS_NOT_ALLOWED = 4;
    public static final int E_RESOURCE_CONTROL_AT_ORIGIN = 5;
    public static final int E_RESOURCE_CONTROL_AT_TARGET = 6;
    public static final int E_BULK_DELETE_NOT_SUPPORTED = 7;
    public static final int E_NOT_ALL_RSLT_SETS_DELETED_ON_BULK_DLTE = 8;
    public static final int E_NOT_ALL_REQUESTED_RESULT_SETS_DELETED = 9;
    public static final int E_RESULT_SET_IN_USE = 10;
    public ASN1Integer value;
    /**
     * Constructor for a DeleteSetStatus from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public DeleteSetStatus(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
    public void berDecode(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        if (checkTag) {
            if (ber.tagGet() != 33 ||
                    ber.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
                throw new ASN1EncodingException("DeleteSetStatus: bad BER: tag=" + ber.tagGet() + " expected 33\n");
            }
        }
        value = new ASN1Integer(ber, false);
    }

    /**
     * Returns a BER encoding of the DeleteSetStatus.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */

    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 33);
    }

    /**
     * Returns a BER encoding of DeleteSetStatus, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        return value.berEncode(tagType, tag);
    }

    /**
     * Returns a new String object containing a text representing
     * of the DeleteSetStatus.
     */

    public String
    toString() {
        return value.toString();
    }

}
