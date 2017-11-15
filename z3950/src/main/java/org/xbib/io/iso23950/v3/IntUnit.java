package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>IntUnit</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * IntUnit ::=
 * SEQUENCE {
 *   value [1] IMPLICIT INTEGER
 *   unitUsed [2] IMPLICIT Unit
 * }
 * </pre>
 */
public final class IntUnit extends ASN1Any {

    public ASN1Integer s_value;
    public Unit s_unitUsed;

    /**
     * Constructor for a IntUnit from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public IntUnit(BEREncoding ber, boolean checkTag)
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
        // IntUnit should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("IntUnit: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;

        // Decoding: value [1] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("IntUnit: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() != 1 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("IntUnit: bad tag in s_value\n");
        }

        s_value = new ASN1Integer(p, false);
        part++;

        // Decoding: unitUsed [2] IMPLICIT Unit

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("IntUnit: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() != 2 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("IntUnit: bad tag in s_unitUsed\n");
        }

        s_unitUsed = new Unit(p, false);
        part++;

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception("IntUnit: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the IntUnit.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */

    public BEREncoding
    berEncode()
            throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of IntUnit, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */

    public BEREncoding
    berEncode(int tagType, int tag)
            throws ASN1Exception {
        // Calculate the number of fields in the encoding

        int numFields = 2; // number of mandatories

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;

        // Encoding s_value: INTEGER

        fields[x++] = s_value.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);

        // Encoding s_unitUsed: Unit

        fields[x] = s_unitUsed.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 2);

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the IntUnit.
     */

    public String
    toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;
        str.append("value ");
        str.append(s_value);
        outputted++;
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("unitUsed ");
        str.append(s_unitUsed);
        str.append("}");
        return str.toString();
    }

}
