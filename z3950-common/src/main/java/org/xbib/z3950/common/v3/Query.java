package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1OctetString;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>Query</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * Query ::=
 * CHOICE {
 *   type-0 [0] EXPLICIT ANY
 *   type-1 [1] IMPLICIT RPNQuery
 *   type-2 [2] EXPLICIT OCTET STRING
 *   type-100 [100] EXPLICIT OCTET STRING
 *   type-101 [101] IMPLICIT RPNQuery
 *   type-102 [102] EXPLICIT OCTET STRING
 * }
 * </pre>
 */
public final class Query extends ASN1Any {

    public ASN1Any c_type_0;
    public RPNQuery c_type_1;
    public ASN1OctetString c_type_2;
    public ASN1OctetString c_type_100;
    public RPNQuery c_type_101;
    public ASN1OctetString c_type_102;

    /**
     * Default constructor for a Query.
     */
    public Query() {
    }

    /**
     * Constructor for a Query from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public Query(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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

        // Null out all choices

        c_type_0 = null;
        c_type_1 = null;
        c_type_2 = null;
        c_type_100 = null;
        c_type_101 = null;
        c_type_102 = null;

        // Try choice type-0
        if (ber.getTag() == 0 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagwrapper = (BERConstructed) ber;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("Query: bad BER form\n");
            }
            if (tagwrapper.numberComponents() != 1) {
                throw new ASN1EncodingException("Query: bad BER form\n");
            }
            c_type_0 = new ASN1Any(tagwrapper.elementAt(0), true);
            return;
        }

        // Try choice type-1
        if (ber.getTag() == 1 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_type_1 = new RPNQuery(ber, false);
            return;
        }

        // Try choice type-2
        if (ber.getTag() == 2 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagwrapper = (BERConstructed) ber;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException
                        ("Query: bad BER form\n");
            }
            if (tagwrapper.numberComponents() != 1) {
                throw new ASN1EncodingException
                        ("Query: bad BER form\n");
            }
            c_type_2 = new ASN1OctetString(tagwrapper.elementAt(0), true);
            return;
        }

        // Try choice type-100
        if (ber.getTag() == 100 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagwrapper = (BERConstructed) ber;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException
                        ("Query: bad BER form\n");
            }
            if (tagwrapper.numberComponents() != 1) {
                throw new ASN1EncodingException
                        ("Query: bad BER form\n");
            }
            c_type_100 = new ASN1OctetString(tagwrapper.elementAt(0), true);
            return;
        }

        // Try choice type-101
        if (ber.getTag() == 101 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            c_type_101 = new RPNQuery(ber, false);
            return;
        }

        // Try choice type-102
        if (ber.getTag() == 102 &&
                ber.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagwrapper = (BERConstructed) ber;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException
                        ("Query: bad BER form\n");
            }
            if (tagwrapper.numberComponents() != 1) {
                throw new ASN1EncodingException
                        ("Query: bad BER form\n");
            }
            c_type_102 = new ASN1OctetString(tagwrapper.elementAt(0), true);
            return;
        }
        throw new ASN1Exception("Query: bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of Query.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        BEREncoding chosen = null;

        BEREncoding enc[];

        // Encoding choice: c_type_0
        if (c_type_0 != null) {
            enc = new BEREncoding[1];
            enc[0] = c_type_0.berEncode();
            chosen = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 0, enc);
        }

        // Encoding choice: c_type_1
        if (c_type_1 != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_type_1.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
        }

        // Encoding choice: c_type_2
        if (c_type_2 != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            enc = new BEREncoding[1];
            enc[0] = c_type_2.berEncode();
            chosen = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 2, enc);
        }

        // Encoding choice: c_type_100
        if (c_type_100 != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            enc = new BEREncoding[1];
            enc[0] = c_type_100.berEncode();
            chosen = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 100, enc);
        }

        // Encoding choice: c_type_101
        if (c_type_101 != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            chosen = c_type_101.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 101);
        }

        // Encoding choice: c_type_102
        if (c_type_102 != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            enc = new BEREncoding[1];
            enc[0] = c_type_102.berEncode();
            chosen = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 102, enc);
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

        throw new ASN1EncodingException("cannot implicitly tag");
    }

    /**
     * Returns a new String object containing a text representing
     * of the Query.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");

        boolean found = false;

        if (c_type_0 != null) {
            found = true;
            str.append("type-0 ");
            str.append(c_type_0);
        }

        if (c_type_1 != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: type-1> ");
            }
            found = true;
            str.append("type-1 ");
            str.append(c_type_1);
        }

        if (c_type_2 != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: type-2> ");
            }
            found = true;
            str.append("type-2 ");
            str.append(c_type_2);
        }

        if (c_type_100 != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: type-100> ");
            }
            found = true;
            str.append("type-100 ");
            str.append(c_type_100);
        }

        if (c_type_101 != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: type-101> ");
            }
            found = true;
            str.append("type-101 ");
            str.append(c_type_101);
        }

        if (c_type_102 != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: type-102> ");
            }
            found = true;
            str.append("type-102 ");
            str.append(c_type_102);
        }

        str.append("}");
        return str.toString();
    }
}
