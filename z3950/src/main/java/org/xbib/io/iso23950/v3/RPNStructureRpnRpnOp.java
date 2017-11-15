package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>RPNStructure_rpnRpnOp</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * RPNStructure_rpnRpnOp ::=
 * SEQUENCE {
 *   rpn1 RPNStructure
 *   rpn2 RPNStructure
 *   op Operator
 * }
 * </pre>
 */
public final class RPNStructureRpnRpnOp extends ASN1Any {

    public RPNStructure s_rpn1;
    public RPNStructure s_rpn2;
    public Operator s_op;


    /**
     * Default constructor for a RPNStructure_rpnRpnOp.
     */

    public RPNStructureRpnRpnOp() {
    }

    /**
     * Constructor for a RPNStructure_rpnRpnOp from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public RPNStructureRpnRpnOp(BEREncoding ber, boolean checkTag)
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
        // RPNStructure_rpnRpnOp should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("RPNStructure_rpnRpnOp: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;

        // Decoding: rpn1 RPNStructure

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("RPNStructure_rpnRpnOp: incomplete");
        }
        p = berConstructed.elementAt(part);

        s_rpn1 = new RPNStructure(p, true);
        part++;

        // Decoding: rpn2 RPNStructure

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("RPNStructure_rpnRpnOp: incomplete");
        }
        p = berConstructed.elementAt(part);

        s_rpn2 = new RPNStructure(p, true);
        part++;

        // Decoding: op Operator

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("RPNStructure_rpnRpnOp: incomplete");
        }
        p = berConstructed.elementAt(part);

        s_op = new Operator(p, true);
        part++;

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception(" RPNStructure_rpnRpnOp: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the RPNStructure_rpnRpnOp.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */

    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of RPNStructure_rpnRpnOp, implicitly tagged.
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

        int numFields = 3; // number of mandatories

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;

        // Encoding s_rpn1: RPNStructure

        fields[x++] = s_rpn1.berEncode();

        // Encoding s_rpn2: RPNStructure

        fields[x++] = s_rpn2.berEncode();

        // Encoding s_op: Operator

        fields[x] = s_op.berEncode();

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the RPNStructure_rpnRpnOp.
     */

    public String
    toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;

        str.append("rpn1 ");
        str.append(s_rpn1);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("rpn2 ");
        str.append(s_rpn2);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("op ");
        str.append(s_op);

        str.append("}");

        return str.toString();
    }

}
