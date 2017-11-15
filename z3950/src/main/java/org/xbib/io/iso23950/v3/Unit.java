package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>Unit</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * Unit ::=
 * SEQUENCE {
 *   unitSystem [1] EXPLICIT InternationalString OPTIONAL
 *   unitType [2] EXPLICIT StringOrNumeric OPTIONAL
 *   unit [3] EXPLICIT StringOrNumeric OPTIONAL
 *   scaleFactor [4] IMPLICIT INTEGER OPTIONAL
 * }
 * </pre>
 */
public final class Unit extends ASN1Any {
    public InternationalString s_unitSystem; // optional
    public StringOrNumeric s_unitType; // optional
    public StringOrNumeric s_unit; // optional
    public ASN1Integer s_scaleFactor; // optional

    /**
     * Constructor for a Unit from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public Unit(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
    public void berDecode(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("Unit: bad BER form\n");
        }
        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        BERConstructed tagged;
        s_unitSystem = null;
        s_unitType = null;
        s_unit = null;
        s_scaleFactor = null;
        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() == 1 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagged = (BERConstructed) p;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException
                        ("Unit: bad BER encoding: s_unitSystem tag bad\n");
            }
            if (tagged.numberComponents() != 1) {
                throw new ASN1EncodingException
                        ("Unit: bad BER encoding: s_unitSystem tag bad\n");
            }

            s_unitSystem = new InternationalString(tagged.elementAt(0), true);
            part++;
        }

        // Decoding: unitType [2] EXPLICIT StringOrNumeric OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 2 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagged = (BERConstructed) p;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException
                        ("Unit: bad BER encoding: s_unitType tag bad\n");
            }
            if (tagged.numberComponents() != 1) {
                throw new ASN1EncodingException
                        ("Unit: bad BER encoding: s_unitType tag bad\n");
            }

            s_unitType = new StringOrNumeric(tagged.elementAt(0), true);
            part++;
        }

        // Decoding: unit [3] EXPLICIT StringOrNumeric OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 3 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagged = (BERConstructed) p;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException
                        ("Unit: bad BER encoding: s_unit tag bad\n");
            }
            if (tagged.numberComponents() != 1) {
                throw new ASN1EncodingException
                        ("Unit: bad BER encoding: s_unit tag bad\n");
            }

            s_unit = new StringOrNumeric(tagged.elementAt(0), true);
            part++;
        }

        // Decoding: scaleFactor [4] IMPLICIT INTEGER OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 4 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_scaleFactor = new ASN1Integer(p, false);
            part++;
        }

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception("Unit: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the Unit.
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
     * Returns a BER encoding of Unit, implicitly tagged.
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

        int numFields = 0; // number of mandatories
        if (s_unitSystem != null) {
            numFields++;
        }
        if (s_unitType != null) {
            numFields++;
        }
        if (s_unit != null) {
            numFields++;
        }
        if (s_scaleFactor != null) {
            numFields++;
        }

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding enc[];

        // Encoding s_unitSystem: InternationalString OPTIONAL

        if (s_unitSystem != null) {
            enc = new BEREncoding[1];
            enc[0] = s_unitSystem.berEncode();
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 1, enc);
        }

        // Encoding s_unitType: StringOrNumeric OPTIONAL

        if (s_unitType != null) {
            enc = new BEREncoding[1];
            enc[0] = s_unitType.berEncode();
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 2, enc);
        }

        // Encoding s_unit: StringOrNumeric OPTIONAL

        if (s_unit != null) {
            enc = new BEREncoding[1];
            enc[0] = s_unit.berEncode();
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 3, enc);
        }

        // Encoding s_scaleFactor: INTEGER OPTIONAL

        if (s_scaleFactor != null) {
            fields[x] = s_scaleFactor.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 4);
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the Unit.
     */
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;

        if (s_unitSystem != null) {
            str.append("unitSystem ");
            str.append(s_unitSystem);
            outputted++;
        }

        if (s_unitType != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("unitType ");
            str.append(s_unitType);
            outputted++;
        }

        if (s_unit != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("unit ");
            str.append(s_unit);
            outputted++;
        }

        if (s_scaleFactor != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("scaleFactor ");
            str.append(s_scaleFactor);
        }
        str.append("}");
        return str.toString();
    }

}
