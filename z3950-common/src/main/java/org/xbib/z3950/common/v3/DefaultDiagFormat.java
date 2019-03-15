package org.xbib.z3950.common.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1ObjectIdentifier;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>DefaultDiagFormat</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * DefaultDiagFormat ::=
 * SEQUENCE {
 *   diagnosticSetId OBJECT IDENTIFIER
 *   condition INTEGER
 *   addinfo DefaultDiagFormat_addinfo
 * }
 * </pre>
 */
public final class DefaultDiagFormat extends ASN1Any {

    public ASN1ObjectIdentifier diagnosticSetId;
    public ASN1Integer condition;
    public DefaultDiagFormatAddinfo addinfo;

    /**
     * Constructor for a DefaultDiagFormat from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public DefaultDiagFormat(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
            throw new ASN1EncodingException("bad BER form");
        }
        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        diagnosticSetId = new ASN1ObjectIdentifier(p, true);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        condition = new ASN1Integer(p, true);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        addinfo = new DefaultDiagFormatAddinfo(p, true);
        part++;
        if (part < numParts) {
            throw new ASN1Exception("bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the DefaultDiagFormat.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of DefaultDiagFormat, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numFields = 3; // number of mandatories
        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        fields[x++] = diagnosticSetId.berEncode();
        fields[x++] = condition.berEncode();
        fields[x] = addinfo.berEncode();
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the DefaultDiagFormat.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;
        str.append("diagnosticSetId ");
        str.append(diagnosticSetId);
        outputted++;
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("condition ");
        str.append(condition);
        outputted++;
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("addinfo ");
        str.append(addinfo);
        str.append("}");
        return str.toString();
    }
}
