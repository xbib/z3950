package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1External;
import org.xbib.asn1.ASN1GeneralizedTime;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Null;
import org.xbib.asn1.ASN1ObjectIdentifier;
import org.xbib.asn1.ASN1OctetString;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>Term</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * Term ::=
 * CHOICE {
 *   general [45] IMPLICIT OCTET STRING
 *   numeric [215] IMPLICIT INTEGER
 *   characterString [216] IMPLICIT InternationalString
 *   oid [217] IMPLICIT OBJECT IDENTIFIER
 *   dateTime [218] IMPLICIT GeneralizedTime
 *   external [219] IMPLICIT EXTERNAL
 *   integerAndUnit [220] IMPLICIT IntUnit
 *   null [221] IMPLICIT NULL
 * }
 * </pre>
 */
public final class Term extends ASN1Any {

    public ASN1OctetString c_general;
    public ASN1Integer c_numeric;
    public InternationalString c_characterString;
    public ASN1ObjectIdentifier c_oid;
    public ASN1GeneralizedTime c_dateTime;
    public ASN1External c_external;
    public IntUnit c_integerAndUnit;
    public ASN1Null c_null;
    public Term() {
    }
    /**
     * Constructor for a Term from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public Term(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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

        c_general = null;
        c_numeric = null;
        c_characterString = null;
        c_oid = null;
        c_dateTime = null;
        c_external = null;
        c_integerAndUnit = null;
        c_null = null;

        // Try choice general
        if (ber.getTag() == 45 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_general = new ASN1OctetString(ber, false);
            return;
        }

        // Try choice numeric
        if (ber.getTag() == 215 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_numeric = new ASN1Integer(ber, false);
            return;
        }

        // Try choice characterString
        if (ber.getTag() == 216 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_characterString = new InternationalString(ber, false);
            return;
        }

        // Try choice oid
        if (ber.getTag() == 217 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_oid = new ASN1ObjectIdentifier(ber, false);
            return;
        }

        // Try choice dateTime
        if (ber.getTag() == 218 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_dateTime = new ASN1GeneralizedTime(ber, false);
            return;
        }

        // Try choice external
        if (ber.getTag() == 219 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_external = new ASN1External(ber, false);
            return;
        }

        // Try choice integerAndUnit
        if (ber.getTag() == 220 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_integerAndUnit = new IntUnit(ber, false);
            return;
        }
        if (ber.getTag() == 221 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_null = new ASN1Null(ber, false);
            return;
        }
        throw new ASN1Exception("Term: bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of Term.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        BEREncoding chosen = null;

        // Encoding choice: c_general
        if (c_general != null) {
            chosen = c_general.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 45);
        }

        // Encoding choice: c_numeric
        if (c_numeric != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_numeric.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 215);
        }

        // Encoding choice: c_characterString
        if (c_characterString != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_characterString.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 216);
        }

        // Encoding choice: c_oid
        if (c_oid != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_oid.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 217);
        }

        // Encoding choice: c_dateTime
        if (c_dateTime != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_dateTime.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 218);
        }

        // Encoding choice: c_external
        if (c_external != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_external.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 219);
        }

        // Encoding choice: c_integerAndUnit
        if (c_integerAndUnit != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_integerAndUnit.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 220);
        }

        // Encoding choice: c_null
        if (c_null != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_null.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 221);
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

        throw new ASN1EncodingException("Term: cannot implicitly tag");
    }

    /**
     * Returns a new String object containing a text representing
     * of the Term.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");

        boolean found = false;

        if (c_general != null) {
            found = true;
            str.append("general ");
            str.append(c_general);
        }

        if (c_numeric != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: numeric> ");
            }
            found = true;
            str.append("numeric ");
            str.append(c_numeric);
        }

        if (c_characterString != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: characterString> ");
            }
            found = true;
            str.append("characterString ");
            str.append(c_characterString);
        }

        if (c_oid != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: oid> ");
            }
            found = true;
            str.append("oid ");
            str.append(c_oid);
        }

        if (c_dateTime != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: dateTime> ");
            }
            found = true;
            str.append("dateTime ");
            str.append(c_dateTime);
        }

        if (c_external != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: external> ");
            }
            found = true;
            str.append("external ");
            str.append(c_external);
        }

        if (c_integerAndUnit != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: integerAndUnit> ");
            }
            found = true;
            str.append("integerAndUnit ");
            str.append(c_integerAndUnit);
        }

        if (c_null != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: null> ");
            }
            found = true;
            str.append("null ");
            str.append(c_null);
        }
        str.append("}");
        return str.toString();
    }
}
