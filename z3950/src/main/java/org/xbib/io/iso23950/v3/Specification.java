package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1ObjectIdentifier;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>Specification</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * Specification ::=
 * SEQUENCE {
 *   schema [1] IMPLICIT OBJECT IDENTIFIER OPTIONAL
 *   elementSpec [2] EXPLICIT Specification_elementSpec OPTIONAL
 * }
 * </pre>
 */
public final class Specification extends ASN1Any {

    public ASN1ObjectIdentifier s_schema; // optional
    public SpecificationElementSpec s_elementSpec; // optional


    /**
     * Constructor for a Specification from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public Specification(BEREncoding ber, boolean checkTag)
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
        // Specification should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException
                    ("Specification: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        BERConstructed tagged;

        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER

        s_schema = null;
        s_elementSpec = null;

        // Decoding: schema [1] IMPLICIT OBJECT IDENTIFIER OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 1 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_schema = new ASN1ObjectIdentifier(p, false);
            part++;
        }

        // Decoding: elementSpec [2] EXPLICIT Specification_elementSpec OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 2 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagged = (BERConstructed) p;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("Specification: bad BER encoding: s_elementSpec tag bad\n");
            }
            if (tagged.numberComponents() != 1) {
                throw new ASN1EncodingException("Specification: bad BER encoding: s_elementSpec tag bad\n");
            }

            s_elementSpec = new SpecificationElementSpec(tagged.elementAt(0), true);
            part++;
        }

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception("Specification: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the Specification.
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
     * Returns a BER encoding of Specification, implicitly tagged.
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
        if (s_schema != null) {
            numFields++;
        }
        if (s_elementSpec != null) {
            numFields++;
        }

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding enc[];

        // Encoding s_schema: OBJECT IDENTIFIER OPTIONAL

        if (s_schema != null) {
            fields[x++] = s_schema.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
        }

        // Encoding s_elementSpec: Specification_elementSpec OPTIONAL

        if (s_elementSpec != null) {
            enc = new BEREncoding[1];
            enc[0] = s_elementSpec.berEncode();
            fields[x] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 2, enc);
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the Specification.
     */
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;
        if (s_schema != null) {
            str.append("schema ");
            str.append(s_schema);
            outputted++;
        }

        if (s_elementSpec != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("elementSpec ");
            str.append(s_elementSpec);
        }

        str.append("}");

        return str.toString();
    }

}
