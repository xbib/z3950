package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>Entry</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * Entry ::=
 * CHOICE {
 *   termInfo [1] IMPLICIT TermInfo
 *   surrogateDiagnostic [2] EXPLICIT DiagRec
 * }
 * </pre>
 */
public final class Entry extends ASN1Any {

    public TermInfo cTermInfo;
    public DiagRec cSurrogateDiagnostic;

    /**
     * Constructor for a Entry from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public Entry(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        BERConstructed tagwrapper;
        cTermInfo = null;
        cSurrogateDiagnostic = null;
        if (ber.getTag() == 1 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            cTermInfo = new TermInfo(ber, false);
            return;
        }
        if (ber.getTag() == 2 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagwrapper = (BERConstructed) ber;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("bad BER form");
            }
            if (tagwrapper.numberComponents() != 1) {
                throw new ASN1EncodingException("bad BER form");
            }
            cSurrogateDiagnostic = new DiagRec(tagwrapper.elementAt(0), true);
            return;
        }
        throw new ASN1Exception("bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of Entry.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        BEREncoding chosen = null;
        BEREncoding[] enc;
        if (cTermInfo != null) {
            chosen = cTermInfo.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
        }
        if (cSurrogateDiagnostic != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            enc = new BEREncoding[1];
            enc[0] = cSurrogateDiagnostic.berEncode();
            chosen = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 2, enc);
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
     * of the Entry.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        boolean found = false;
        if (cTermInfo != null) {
            found = true;
            str.append("termInfo ");
            str.append(cTermInfo);
        }
        if (cSurrogateDiagnostic != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: surrogateDiagnostic> ");
            }
            str.append("surrogateDiagnostic ");
            str.append(cSurrogateDiagnostic);
        }
        str.append("}");
        return str.toString();
    }
}
