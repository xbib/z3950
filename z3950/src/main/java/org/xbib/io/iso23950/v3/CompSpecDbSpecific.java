package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>CompSpec_dbSpecific</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * CompSpec_dbSpecific ::=
 * SEQUENCE {
 *   db [1] EXPLICIT DatabaseName
 *   spec [2] IMPLICIT Specification
 * }
 * </pre>
 */
public final class CompSpecDbSpecific extends ASN1Any {

    public DatabaseName sDb;

    public Specification sSpec;

    /**
     * Constructor for a CompSpec_dbSpecific from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public CompSpecDbSpecific(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
            throw new ASN1EncodingException("CompSpec_dbSpecific: bad BER form\n");
        }
        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        BERConstructed tagged;
        if (numParts <= part) {
            throw new ASN1Exception("CompSpec_dbSpecific: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 1 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException
                    ("CompSpec_dbSpecific: bad tag in s_db\n");
        }
        try {
            tagged = (BERConstructed) p;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException
                    ("CompSpec_dbSpecific: bad BER encoding: s_db tag bad\n");
        }
        if (tagged.numberComponents() != 1) {
            throw new ASN1EncodingException
                    ("CompSpec_dbSpecific: bad BER encoding: s_db tag bad\n");
        }
        sDb = new DatabaseName(tagged.elementAt(0), true);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("CompSpec_dbSpecific: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 2 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException
                    ("CompSpec_dbSpecific: bad tag in s_spec\n");
        }
        sSpec = new Specification(p, false);
        part++;
        if (part < numParts) {
            throw new ASN1Exception("CompSpec_dbSpecific: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the CompSpec_dbSpecific.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of CompSpec_dbSpecific, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numFields = 2;
        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding enc[];
        enc = new BEREncoding[1];
        enc[0] = sDb.berEncode();
        fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 1, enc);
        fields[x] = sSpec.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 2);
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the CompSpec_dbSpecific.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;
        str.append("db ");
        str.append(sDb);
        outputted++;
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("spec ");
        str.append(sSpec);
        str.append("}");
        return str.toString();
    }
}
