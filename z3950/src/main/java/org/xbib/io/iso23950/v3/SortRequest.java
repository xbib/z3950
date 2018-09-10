package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>SortRequest</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * SortRequest ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   inputResultSetNames [3] IMPLICIT SEQUENCE OF InternationalString
 *   sortedResultSetName [4] IMPLICIT InternationalString
 *   sortSequence [5] IMPLICIT SEQUENCE OF SortKeySpec
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */
public final class SortRequest extends ASN1Any {
    public ReferenceId s_referenceId; // optional
    public InternationalString s_inputResultSetNames[];
    public InternationalString s_sortedResultSetName;
    public SortKeySpec s_sortSequence[];
    public OtherInformation s_otherInfo; // optional

    /**
     * Constructor for a SortRequest from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public SortRequest(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // SortRequest should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException
                    ("SortRequest: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;

        // Decoding: referenceId ReferenceId OPTIONAL

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        try {
            s_referenceId = new ReferenceId(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_referenceId = null; // no, not present
        }

        // Decoding: inputResultSetNames [3] IMPLICIT SEQUENCE OF InternationalString

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() != 3 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_inputResultSetNames");
        }

        try {
            BERConstructed cons = (BERConstructed) p;
            int parts = cons.numberComponents();
            s_inputResultSetNames = new InternationalString[parts];
            int n;
            for (n = 0; n < parts; n++) {
                s_inputResultSetNames[n] = new InternationalString(cons.elementAt(n), true);
            }
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("bad BER");
        }
        part++;

        // Decoding: sortedResultSetName [4] IMPLICIT InternationalString

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() != 4 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in sortedResultSetName\n");
        }

        s_sortedResultSetName = new InternationalString(p, false);
        part++;

        // Decoding: sortSequence [5] IMPLICIT SEQUENCE OF SortKeySpec

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() != 5 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in sortSequence\n");
        }

        try {
            BERConstructed cons = (BERConstructed) p;
            int parts = cons.numberComponents();
            s_sortSequence = new SortKeySpec[parts];
            int n;
            for (n = 0; n < parts; n++) {
                s_sortSequence[n] = new SortKeySpec(cons.elementAt(n), true);
            }
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("bad BER");
        }
        part++;

        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER

        s_otherInfo = null;

        // Decoding: otherInfo OtherInformation OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        try {
            s_otherInfo = new OtherInformation(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_otherInfo = null; // no, not present
        }

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception("bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the SortRequest.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of SortRequest, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numFields = 3; // number of mandatories
        if (s_referenceId != null) {
            numFields++;
        }
        if (s_otherInfo != null) {
            numFields++;
        }
        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding f2[];
        int p;
        if (s_referenceId != null) {
            fields[x++] = s_referenceId.berEncode();
        }
        f2 = new BEREncoding[s_inputResultSetNames.length];
        for (p = 0; p < s_inputResultSetNames.length; p++) {
            f2[p] = s_inputResultSetNames[p].berEncode();
        }
        fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 3, f2);
        fields[x++] = s_sortedResultSetName.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 4);
        f2 = new BEREncoding[s_sortSequence.length];
        for (p = 0; p < s_sortSequence.length; p++) {
            f2[p] = s_sortSequence[p].berEncode();
        }
        fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 5, f2);
        if (s_otherInfo != null) {
            fields[x] = s_otherInfo.berEncode();
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the SortRequest.
     */
    @Override
    public String toString() {
        int p;
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;
        if (s_referenceId != null) {
            str.append("referenceId ");
            str.append(s_referenceId);
            outputted++;
        }

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("inputResultSetNames ");
        str.append("{");
        for (p = 0; p < s_inputResultSetNames.length; p++) {
            if (p != 0) {
                str.append(", ");
            }
            str.append(s_inputResultSetNames[p]);
        }
        str.append("}");
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("sortedResultSetName ");
        str.append(s_sortedResultSetName);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("sortSequence ");
        str.append("{");
        for (p = 0; p < s_sortSequence.length; p++) {
            if (p != 0) {
                str.append(", ");
            }
            str.append(s_sortSequence[p]);
        }
        str.append("}");
        outputted++;

        if (s_otherInfo != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("otherInfo ");
            str.append(s_otherInfo);
        }
        str.append("}");
        return str.toString();
    }
}
