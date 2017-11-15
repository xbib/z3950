package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1Boolean;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>ProximityOperator</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * ProximityOperator ::=
 * SEQUENCE {
 *   exclusion [1] IMPLICIT BOOLEAN OPTIONAL
 *   distance [2] IMPLICIT INTEGER
 *   ordered [3] IMPLICIT BOOLEAN
 *   relationType [4] IMPLICIT INTEGER
 *   proximityUnitCode [5] EXPLICIT ProximityOperator_proximityUnitCode
 * }
 * </pre>
 */
public final class ProximityOperator extends ASN1Any {
    // Enumerated constants for relationType
    public static final int E_lessThan = 1;
    public static final int E_lessThanOrEqual = 2;
    public static final int E_equal = 3;
    public static final int E_greaterThanOrEqual = 4;
    public static final int E_greaterThan = 5;
    public static final int E_notEqual = 6;
    public ASN1Boolean s_exclusion; // optional
    public ASN1Integer s_distance;
    public ASN1Boolean s_ordered;
    public ASN1Integer s_relationType;
    public ProximityOperatorProximityUnitCode s_proximityUnitCode;
    /**
     * Constructor for a ProximityOperator from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public ProximityOperator(BEREncoding ber, boolean checkTag)
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
        // ProximityOperator should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException
                    ("ProximityOperator: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        BERConstructed tagged;

        // Decoding: exclusion [1] IMPLICIT BOOLEAN OPTIONAL

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("ProximityOperator: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 1 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_exclusion = new ASN1Boolean(p, false);
            part++;
        }

        // Decoding: distance [2] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("ProximityOperator: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() != 2 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException
                    ("ProximityOperator: bad tag in s_distance\n");
        }

        s_distance = new ASN1Integer(p, false);
        part++;

        // Decoding: ordered [3] IMPLICIT BOOLEAN

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("ProximityOperator: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() != 3 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException
                    ("ProximityOperator: bad tag in s_ordered\n");
        }

        s_ordered = new ASN1Boolean(p, false);
        part++;

        // Decoding: relationType [4] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("ProximityOperator: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() != 4 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException
                    ("ProximityOperator: bad tag in s_relationType\n");
        }

        s_relationType = new ASN1Integer(p, false);
        part++;

        // Decoding: proximityUnitCode [5] EXPLICIT ProximityOperator_proximityUnitCode

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("ProximityOperator: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() != 5 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException
                    ("ProximityOperator: bad tag in s_proximityUnitCode\n");
        }

        try {
            tagged = (BERConstructed) p;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException
                    ("ProximityOperator: bad BER encoding: s_proximityUnitCode tag bad\n");
        }
        if (tagged.numberComponents() != 1) {
            throw new ASN1EncodingException
                    ("ProximityOperator: bad BER encoding: s_proximityUnitCode tag bad\n");
        }

        s_proximityUnitCode = new ProximityOperatorProximityUnitCode(tagged.elementAt(0), true);
        part++;

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception("ProximityOperator: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the ProximityOperator.
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
     * Returns a BER encoding of ProximityOperator, implicitly tagged.
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

        int numFields = 4; // number of mandatories
        if (s_exclusion != null) {
            numFields++;
        }

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding enc[];

        // Encoding s_exclusion: BOOLEAN OPTIONAL

        if (s_exclusion != null) {
            fields[x++] = s_exclusion.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
        }

        // Encoding s_distance: INTEGER

        fields[x++] = s_distance.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 2);

        // Encoding s_ordered: BOOLEAN

        fields[x++] = s_ordered.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 3);

        // Encoding s_relationType: INTEGER

        fields[x++] = s_relationType.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 4);

        // Encoding s_proximityUnitCode: ProximityOperator_proximityUnitCode

        enc = new BEREncoding[1];
        enc[0] = s_proximityUnitCode.berEncode();
        fields[x] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 5, enc);

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the ProximityOperator.
     */

    public String
    toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;

        if (s_exclusion != null) {
            str.append("exclusion ");
            str.append(s_exclusion);
            outputted++;
        }

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("distance ");
        str.append(s_distance);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("ordered ");
        str.append(s_ordered);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("relationType ");
        str.append(s_relationType);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("proximityUnitCode ");
        str.append(s_proximityUnitCode);

        str.append("}");

        return str.toString();
    }

}
