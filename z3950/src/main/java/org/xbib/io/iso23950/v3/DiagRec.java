package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1External;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>DiagRec</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * DiagRec ::=
 * CHOICE {
 *   defaultFormat DefaultDiagFormat
 *   externallyDefined EXTERNAL
 * }
 * </pre>
 */

public final class DiagRec extends ASN1Any {

    public DefaultDiagFormat defaultFormat;
    public ASN1External externallyDefined;

    /**
     * Constructor for a DiagRec from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public DiagRec(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        defaultFormat = null;
        externallyDefined = null;
        try {
            defaultFormat = new DefaultDiagFormat(ber, checkTag);
            return;
        } catch (ASN1Exception e) {
            // failed to decode, continue on
        }
        try {
            externallyDefined = new ASN1External(ber, checkTag);
            return;
        } catch (ASN1Exception e) {
            // failed to decode, continue on
        }
        throw new ASN1Exception("bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of DiagRec.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        BEREncoding chosen = null;
        if (defaultFormat != null) {
            chosen = defaultFormat.berEncode();
        }
        if (externallyDefined != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = externallyDefined.berEncode();
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
     * of the DiagRec.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        boolean found = false;
        if (defaultFormat != null) {
            found = true;
            str.append("defaultFormat ");
            str.append(defaultFormat);
        }
        if (externallyDefined != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: externallyDefined> ");
            }
            str.append("externallyDefined ");
            str.append(externallyDefined);
        }
        str.append("}");
        return str.toString();
    }
}
