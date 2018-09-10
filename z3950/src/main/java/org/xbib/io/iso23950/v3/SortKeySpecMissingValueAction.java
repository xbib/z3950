package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Null;
import org.xbib.asn1.ASN1OctetString;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>SortKeySpec_missingValueAction</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * SortKeySpec_missingValueAction ::=
 * CHOICE {
 *   abort [1] IMPLICIT NULL
 *   null [2] IMPLICIT NULL
 *   missingValueData [3] IMPLICIT OCTET STRING
 * }
 * </pre>
 */
public final class SortKeySpecMissingValueAction extends ASN1Any {

    public ASN1Null c_abort;
    public ASN1Null c_null;
    public ASN1OctetString c_missingValueData;

    /**
     * Constructor for a SortKeySpec_missingValueAction from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public SortKeySpecMissingValueAction(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // Null out all choices

        c_abort = null;
        c_null = null;
        c_missingValueData = null;

        // Try choice abort
        if (ber.getTag() == 1 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_abort = new ASN1Null(ber, false);
            return;
        }

        // Try choice null
        if (ber.getTag() == 2 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_null = new ASN1Null(ber, false);
            return;
        }

        // Try choice missingValueData
        if (ber.getTag() == 3 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_missingValueData = new ASN1OctetString(ber, false);
            return;
        }

        throw new ASN1Exception("bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of SortKeySpec_missingValueAction.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        BEREncoding chosen = null;

        // Encoding choice: c_abort
        if (c_abort != null) {
            chosen = c_abort.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
        }

        // Encoding choice: c_null
        if (c_null != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_null.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 2);
        }

        // Encoding choice: c_missingValueData
        if (c_missingValueData != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_missingValueData.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 3);
        }

        // Check for error of having none of the choices set
        if (chosen == null) {
            throw new ASN1Exception("CHOICE not set");
        }

        return chosen;
    }

    /**
     * Generating a BER encoding of the object
     * and implicitly tagging it.
     * This method is for internal use only. You should use
     * the berEncode method that does not take a parameter.
     * This function should never be used, because this
     * production is a CHOICE.
     * It must never have an implicit tag.
     * An exception will be thrown if it is called.
     *
     * @param tagType the type of the tag.
     * @param tag      the tag.
     * @throws ASN1Exception if it cannot be BER encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        // This method must not be called!

        // Method is not available because this is a basic CHOICE
        // which does not have an explicit tag on it. So it is not
        // permitted to allow something else to apply an implicit
        // tag on it, otherwise the tag identifying which CHOICE
        // it is will be overwritten and lost.

        throw new ASN1EncodingException("SortKeySpec_missingValueAction: cannot implicitly tag");
    }

    /**
     * Returns a new String object containing a text representing
     * of the SortKeySpec_missingValueAction.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        boolean found = false;
        if (c_abort != null) {
            found = true;
            str.append("abort ");
            str.append(c_abort);
        }

        if (c_null != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: null> ");
            }
            found = true;
            str.append("null ");
            str.append(c_null);
        }

        if (c_missingValueData != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: missingValueData> ");
            }
            str.append("missingValueData ");
            str.append(c_missingValueData);
        }
        str.append("}");
        return str.toString();
    }
}
