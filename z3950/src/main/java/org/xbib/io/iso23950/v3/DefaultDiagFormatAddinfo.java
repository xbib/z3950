package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1VisibleString;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>DefaultDiagFormat_addinfo</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * DefaultDiagFormat_addinfo ::=
 * CHOICE {
 *   v2Addinfo VisibleString
 *   v3Addinfo InternationalString
 * }
 * </pre>
 */
public final class DefaultDiagFormatAddinfo extends ASN1Any {

    public ASN1VisibleString cV2Addinfo;
    public InternationalString cV3Addinfo;

    /**
     * Constructor for a DefaultDiagFormat_addinfo from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public DefaultDiagFormatAddinfo(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        cV2Addinfo = null;
        cV3Addinfo = null;
        try {
            cV2Addinfo = new ASN1VisibleString(ber, checkTag);
            return;
        } catch (ASN1Exception e) {
            // failed to decode, continue on
        }
        try {
            cV3Addinfo = new InternationalString(ber, checkTag);
            return;
        } catch (ASN1Exception e) {
            // failed to decode, continue on
        }
        throw new ASN1Exception("DefaultDiagFormat_addinfo: bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of DefaultDiagFormat_addinfo.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    public BEREncoding berEncode() throws ASN1Exception {
        BEREncoding chosen = null;
        if (cV2Addinfo != null) {
            chosen = cV2Addinfo.berEncode();
        }
        if (cV3Addinfo != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = cV3Addinfo.berEncode();
        }
        if (chosen == null) {
            throw new ASN1Exception("CHOICE not set");
        }
        return chosen;
    }

    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        throw new ASN1EncodingException("DefaultDiagFormat_addinfo: cannot implicitly tag");
    }

    /**
     * Returns a new String object containing a text representing
     * of the DefaultDiagFormat_addinfo.
     */
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        boolean found = false;
        if (cV2Addinfo != null) {
            found = true;
            str.append("v2Addinfo ");
            str.append(cV2Addinfo);
        }
        if (cV3Addinfo != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: v3Addinfo> ");
            }
            str.append("v3Addinfo ");
            str.append(cV3Addinfo);
        }
        str.append("}");
        return str.toString();
    }
}
