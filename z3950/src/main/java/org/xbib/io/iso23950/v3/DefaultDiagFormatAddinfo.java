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

    public ASN1VisibleString v2Addinfo;
    public InternationalString v3Addinfo;

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
    @Override
    public void berDecode(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        v2Addinfo = null;
        v3Addinfo = null;
        try {
            v2Addinfo = new ASN1VisibleString(ber, checkTag);
            return;
        } catch (ASN1Exception e) {
            // failed to decode, continue on
        }
        try {
            v3Addinfo = new InternationalString(ber, checkTag);
            return;
        } catch (ASN1Exception e) {
            // failed to decode, continue on
        }
        throw new ASN1Exception("bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of DefaultDiagFormat_addinfo.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        BEREncoding chosen = null;
        if (v2Addinfo != null) {
            chosen = v2Addinfo.berEncode();
        }
        if (v3Addinfo != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = v3Addinfo.berEncode();
        }
        if (chosen == null) {
            throw new ASN1Exception("CHOICE not set");
        }
        return chosen;
    }

    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        throw new ASN1EncodingException("cannot implicitly tag");
    }

    /**
     * Returns a new String object containing a text representing
     * of the DefaultDiagFormat_addinfo.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        boolean found = false;
        if (v2Addinfo != null) {
            found = true;
            str.append("v2Addinfo ");
            str.append(v2Addinfo);
        }
        if (v3Addinfo != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: v3Addinfo> ");
            }
            str.append("v3Addinfo ");
            str.append(v3Addinfo);
        }
        str.append("}");
        return str.toString();
    }
}
