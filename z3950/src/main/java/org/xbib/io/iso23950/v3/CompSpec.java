package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1Boolean;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1ObjectIdentifier;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>CompSpec</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * CompSpec ::=
 * SEQUENCE {
 *   selectAlternativeSyntax [1] IMPLICIT BOOLEAN
 *   generic [2] IMPLICIT Specification OPTIONAL
 *   dbSpecific [3] IMPLICIT SEQUENCE OF CompSpec_dbSpecific OPTIONAL
 *   recordSyntax [4] IMPLICIT SEQUENCE OF OBJECT IDENTIFIER OPTIONAL
 * }
 * </pre>
 */
public final class CompSpec extends ASN1Any {

    public ASN1Boolean selectAlternativeSyntax;
    public Specification sGeneric; // optional
    public CompSpecDbSpecific[] dbSpecifics; // optional
    public ASN1ObjectIdentifier[] recordSyntax; // optional

    /**
     * Default constructor for a CompSpec.
     */
    public CompSpec() {
    }

    /**
     * Constructor for a CompSpec from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public CompSpec(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("CompSpec: bad BER form\n");
        }
        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        if (numParts <= part) {
            throw new ASN1Exception("CompSpec: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 1 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("CompSpec: bad tag in s_selectAlternativeSyntax\n");
        }
        selectAlternativeSyntax = new ASN1Boolean(p, false);
        part++;
        sGeneric = null;
        dbSpecifics = null;
        recordSyntax = null;
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 2 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            sGeneric = new Specification(p, false);
            part++;
        }
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 3 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                BERConstructed cons = (BERConstructed) p;
                int parts = cons.numberComponents();
                dbSpecifics = new CompSpecDbSpecific[parts];
                int n;
                for (n = 0; n < parts; n++) {
                    dbSpecifics[n] = new CompSpecDbSpecific(cons.elementAt(n), true);
                }
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("Bad BER");
            }
            part++;
        }
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 4 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                BERConstructed cons = (BERConstructed) p;
                int parts = cons.numberComponents();
                recordSyntax = new ASN1ObjectIdentifier[parts];
                int n;
                for (n = 0; n < parts; n++) {
                    recordSyntax[n] = new ASN1ObjectIdentifier(cons.elementAt(n), true);
                }
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("Bad BER");
            }
            part++;
        }
        if (part < numParts) {
            throw new ASN1Exception("CompSpec: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the CompSpec.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of CompSpec, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numFields = 1;
        if (sGeneric != null) {
            numFields++;
        }
        if (dbSpecifics != null) {
            numFields++;
        }
        if (recordSyntax != null) {
            numFields++;
        }
        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding f2[];
        int p;
        fields[x++] = selectAlternativeSyntax.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
        if (sGeneric != null) {
            fields[x++] = sGeneric.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 2);
        }
        if (dbSpecifics != null) {
            f2 = new BEREncoding[dbSpecifics.length];
            for (p = 0; p < dbSpecifics.length; p++) {
                f2[p] = dbSpecifics[p].berEncode();
            }
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 3, f2);
        }
        if (recordSyntax != null) {
            f2 = new BEREncoding[recordSyntax.length];
            for (p = 0; p < recordSyntax.length; p++) {
                f2[p] = recordSyntax[p].berEncode();
            }
            fields[x] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 4, f2);
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the CompSpec.
     */
    @Override
    public String toString() {
        int p;
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;
        str.append("selectAlternativeSyntax ");
        str.append(selectAlternativeSyntax);
        outputted++;
        if (sGeneric != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("generic ");
            str.append(sGeneric);
            outputted++;
        }
        if (dbSpecifics != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("dbSpecific ");
            str.append("{");
            for (p = 0; p < dbSpecifics.length; p++) {
                if (p != 0) {
                    str.append(", ");
                }
                str.append(dbSpecifics[p]);
            }
            str.append("}");
            outputted++;
        }
        if (recordSyntax != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("recordSyntax ");
            str.append("{");
            for (p = 0; p < recordSyntax.length; p++) {
                if (p != 0) {
                    str.append(", ");
                }
                str.append(recordSyntax[p]);
            }
            str.append("}");
        }
        str.append("}");
        return str.toString();
    }
}
