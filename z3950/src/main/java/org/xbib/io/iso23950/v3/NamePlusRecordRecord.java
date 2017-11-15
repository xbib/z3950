package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1External;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>NamePlusRecord_record</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * NamePlusRecord_record ::=
 * CHOICE {
 *   retrievalRecord [1] EXPLICIT EXTERNAL
 *   surrogateDiagnostic [2] EXPLICIT DiagRec
 *   startingFragment [3] EXPLICIT FragmentSyntax
 *   intermediateFragment [4] EXPLICIT FragmentSyntax
 *   finalFragment [5] EXPLICIT FragmentSyntax
 * }
 * </pre>
 */
public final class NamePlusRecordRecord extends ASN1Any {

    public ASN1External c_retrievalRecord;
    public DiagRec c_surrogateDiagnostic;
    public FragmentSyntax c_startingFragment;
    public FragmentSyntax c_intermediateFragment;
    public FragmentSyntax c_finalFragment;

    /**
     * Constructor for a NamePlusRecord_record from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public NamePlusRecordRecord(BEREncoding ber, boolean checkTag)
            throws ASN1Exception {
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

    public void
    berDecode(BEREncoding ber, boolean checkTag)
            throws ASN1Exception {
        BERConstructed tagwrapper;

        // Null out all choices

        c_retrievalRecord = null;
        c_surrogateDiagnostic = null;
        c_startingFragment = null;
        c_intermediateFragment = null;
        c_finalFragment = null;

        // Try choice retrievalRecord
        if (ber.tagGet() == 1 &&
                ber.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagwrapper = (BERConstructed) ber;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException
                        ("NamePlusRecord_record: bad BER form\n");
            }
            if (tagwrapper.numberComponents() != 1) {
                throw new ASN1EncodingException
                        ("NamePlusRecord_record: bad BER form\n");
            }
            c_retrievalRecord = new ASN1External(tagwrapper.elementAt(0), true);
            return;
        }

        // Try choice surrogateDiagnostic
        if (ber.tagGet() == 2 &&
                ber.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagwrapper = (BERConstructed) ber;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException
                        ("NamePlusRecord_record: bad BER form\n");
            }
            if (tagwrapper.numberComponents() != 1) {
                throw new ASN1EncodingException
                        ("NamePlusRecord_record: bad BER form\n");
            }
            c_surrogateDiagnostic = new DiagRec(tagwrapper.elementAt(0), true);
            return;
        }

        // Try choice startingFragment
        if (ber.tagGet() == 3 &&
                ber.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagwrapper = (BERConstructed) ber;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException
                        ("NamePlusRecord_record: bad BER form\n");
            }
            if (tagwrapper.numberComponents() != 1) {
                throw new ASN1EncodingException
                        ("NamePlusRecord_record: bad BER form\n");
            }
            c_startingFragment = new FragmentSyntax(tagwrapper.elementAt(0), true);
            return;
        }

        // Try choice intermediateFragment
        if (ber.tagGet() == 4 &&
                ber.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagwrapper = (BERConstructed) ber;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException
                        ("NamePlusRecord_record: bad BER form\n");
            }
            if (tagwrapper.numberComponents() != 1) {
                throw new ASN1EncodingException
                        ("NamePlusRecord_record: bad BER form\n");
            }
            c_intermediateFragment = new FragmentSyntax(tagwrapper.elementAt(0), true);
            return;
        }

        // Try choice finalFragment
        if (ber.tagGet() == 5 &&
                ber.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagwrapper = (BERConstructed) ber;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException
                        ("NamePlusRecord_record: bad BER form\n");
            }
            if (tagwrapper.numberComponents() != 1) {
                throw new ASN1EncodingException
                        ("NamePlusRecord_record: bad BER form\n");
            }
            c_finalFragment = new FragmentSyntax(tagwrapper.elementAt(0), true);
            return;
        }

        throw new ASN1Exception("NamePlusRecord_record: bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of NamePlusRecord_record.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */

    public BEREncoding
    berEncode()
            throws ASN1Exception {
        BEREncoding chosen = null;

        BEREncoding enc[];

        // Encoding choice: c_retrievalRecord
        if (c_retrievalRecord != null) {
            enc = new BEREncoding[1];
            enc[0] = c_retrievalRecord.berEncode();
            chosen = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 1, enc);
        }

        // Encoding choice: c_surrogateDiagnostic
        if (c_surrogateDiagnostic != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            enc = new BEREncoding[1];
            enc[0] = c_surrogateDiagnostic.berEncode();
            chosen = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 2, enc);
        }

        // Encoding choice: c_startingFragment
        if (c_startingFragment != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            enc = new BEREncoding[1];
            enc[0] = c_startingFragment.berEncode();
            chosen = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 3, enc);
        }

        // Encoding choice: c_intermediateFragment
        if (c_intermediateFragment != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            enc = new BEREncoding[1];
            enc[0] = c_intermediateFragment.berEncode();
            chosen = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 4, enc);
        }

        // Encoding choice: c_finalFragment
        if (c_finalFragment != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            enc = new BEREncoding[1];
            enc[0] = c_finalFragment.berEncode();
            chosen = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 5, enc);
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

    public BEREncoding
    berEncode(int tagType, int tag)
            throws ASN1Exception {
        // This method must not be called!

        // Method is not available because this is a basic CHOICE
        // which does not have an explicit tag on it. So it is not
        // permitted to allow something else to apply an implicit
        // tag on it, otherwise the tag identifying which CHOICE
        // it is will be overwritten and lost.

        throw new ASN1EncodingException("NamePlusRecord_record: cannot implicitly tag");
    }

    /**
     * Returns a new String object containing a text representing
     * of the NamePlusRecord_record.
     */

    public String
    toString() {
        StringBuilder str = new StringBuilder("{");

        boolean found = false;

        if (c_retrievalRecord != null) {
            found = true;
            str.append("retrievalRecord ");
            str.append(c_retrievalRecord);
        }

        if (c_surrogateDiagnostic != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: surrogateDiagnostic> ");
            }
            found = true;
            str.append("surrogateDiagnostic ");
            str.append(c_surrogateDiagnostic);
        }

        if (c_startingFragment != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: startingFragment> ");
            }
            found = true;
            str.append("startingFragment ");
            str.append(c_startingFragment);
        }

        if (c_intermediateFragment != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: intermediateFragment> ");
            }
            found = true;
            str.append("intermediateFragment ");
            str.append(c_intermediateFragment);
        }

        if (c_finalFragment != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: finalFragment> ");
            }
            str.append("finalFragment ");
            str.append(c_finalFragment);
        }

        str.append("}");

        return str.toString();
    }

}
