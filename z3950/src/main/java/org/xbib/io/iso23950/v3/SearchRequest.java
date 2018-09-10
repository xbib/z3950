package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1Boolean;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1ObjectIdentifier;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>SearchRequest</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * SearchRequest ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   smallSetUpperBound [13] IMPLICIT INTEGER
 *   largeSetLowerBound [14] IMPLICIT INTEGER
 *   mediumSetPresentNumber [15] IMPLICIT INTEGER
 *   replaceIndicator [16] IMPLICIT BOOLEAN
 *   resultSetName [17] IMPLICIT InternationalString
 *   databaseNames [18] IMPLICIT SEQUENCE OF DatabaseName
 *   smallSetElementSetNames [100] EXPLICIT ElementSetNames OPTIONAL
 *   mediumSetElementSetNames [101] EXPLICIT ElementSetNames OPTIONAL
 *   preferredRecordSyntax [104] IMPLICIT OBJECT IDENTIFIER OPTIONAL
 *   query [21] EXPLICIT Query
 *   additionalSearchInfo [203] IMPLICIT OtherInformation OPTIONAL
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */
public final class SearchRequest extends ASN1Any {
    public ReferenceId referenceId; // optional
    public ASN1Integer smallSetUpperBound;
    public ASN1Integer largeSetLowerBound;
    public ASN1Integer mediumSetPresentNumber;
    public ASN1Boolean replaceIndicator;
    public InternationalString resultSetName;
    public DatabaseName[] databaseNames;
    private ElementSetNames smallSetElementSetNames; // optional
    private ElementSetNames mediumSetElementSetNames; // optional
    private ASN1ObjectIdentifier preferredRecordSyntax; // optional
    public Query query;
    private OtherInformation additionalSearchInfo; // optional
    public OtherInformation otherInfo; // optional

    /**
     * Default constructor for a SearchRequest.
     */
    public SearchRequest() {
    }

    /**
     * Constructor for a SearchRequest from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public SearchRequest(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        BERConstructed tagged;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        try {
            referenceId = new ReferenceId(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            referenceId = null; // no, not present
        }
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 13 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_smallSetUpperBound");
        }
        smallSetUpperBound = new ASN1Integer(p, false);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 14 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_largeSetLowerBound");
        }
        largeSetLowerBound = new ASN1Integer(p, false);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 15 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_mediumSetPresentNumber");
        }
        mediumSetPresentNumber = new ASN1Integer(p, false);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 16 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_replaceIndicator");
        }
        replaceIndicator = new ASN1Boolean(p, false);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 17 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_resultSetName");
        }
        resultSetName = new InternationalString(p, false);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 18 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in s_databaseNames");
        }
        try {
            BERConstructed cons = (BERConstructed) p;
            int parts = cons.numberComponents();
            databaseNames = new DatabaseName[parts];
            int n;
            for (n = 0; n < parts; n++) {
                databaseNames[n] = new DatabaseName(cons.elementAt(n), true);
            }
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("bad BER");
        }
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 100 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagged = (BERConstructed) p;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("bad BER encoding: s_smallSetElementSetNames tag bad");
            }
            if (tagged.numberComponents() != 1) {
                throw new ASN1EncodingException("bad BER encoding: s_smallSetElementSetNames tag bad");
            }
            smallSetElementSetNames = new ElementSetNames(tagged.elementAt(0), true);
            part++;
        }
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 101 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagged = (BERConstructed) p;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("bad BER encoding: mediumSetElementSetNames tag bad");
            }
            if (tagged.numberComponents() != 1) {
                throw new ASN1EncodingException("bad BER encoding: mediumSetElementSetNames tag bad");
            }
            mediumSetElementSetNames = new ElementSetNames(tagged.elementAt(0), true);
            part++;
        }
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 104 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            preferredRecordSyntax = new ASN1ObjectIdentifier(p, false);
            part++;
        }
        if (numParts <= part) {
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() != 21 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in query");
        }
        try {
            tagged = (BERConstructed) p;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("bad BER encoding: query tag bad");
        }
        if (tagged.numberComponents() != 1) {
            throw new ASN1EncodingException("bad BER encoding: query tag bad");
        }
        query = new Query(tagged.elementAt(0), true);
        part++;
        additionalSearchInfo = null;
        otherInfo = null;
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 203 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            additionalSearchInfo = new OtherInformation(p, false);
            part++;
        }
        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);
        try {
            otherInfo = new OtherInformation(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            otherInfo = null; // no, not present
        }
        if (part < numParts) {
            throw new ASN1Exception("bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the SearchRequest.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of SearchRequest, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numFields = 7; // number of mandatories
        if (referenceId != null) {
            numFields++;
        }
        if (smallSetElementSetNames != null) {
            numFields++;
        }
        if (mediumSetElementSetNames != null) {
            numFields++;
        }
        if (preferredRecordSyntax != null) {
            numFields++;
        }
        if (additionalSearchInfo != null) {
            numFields++;
        }
        if (otherInfo != null) {
            numFields++;
        }
        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding f2[];
        int p;
        BEREncoding enc[];
        if (referenceId != null) {
            fields[x++] = referenceId.berEncode();
        }
        fields[x++] = smallSetUpperBound.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 13);
        fields[x++] = largeSetLowerBound.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 14);
        fields[x++] = mediumSetPresentNumber.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 15);
        fields[x++] = replaceIndicator.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 16);
        fields[x++] = resultSetName.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 17);
        f2 = new BEREncoding[databaseNames.length];
        for (p = 0; p < databaseNames.length; p++) {
            f2[p] = databaseNames[p].berEncode();
        }
        fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 18, f2);
        if (smallSetElementSetNames != null) {
            enc = new BEREncoding[1];
            enc[0] = smallSetElementSetNames.berEncode();
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 100, enc);
        }
        if (mediumSetElementSetNames != null) {
            enc = new BEREncoding[1];
            enc[0] = mediumSetElementSetNames.berEncode();
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 101, enc);
        }
        if (preferredRecordSyntax != null) {
            fields[x++] = preferredRecordSyntax.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 104);
        }
        enc = new BEREncoding[1];
        enc[0] = query.berEncode();
        fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 21, enc);
        if (additionalSearchInfo != null) {
            fields[x++] = additionalSearchInfo.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 203);
        }
        if (otherInfo != null) {
            fields[x] = otherInfo.berEncode();
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the SearchRequest.
     */
    @Override
    public String toString() {
        int p;
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;
        if (referenceId != null) {
            str.append("referenceId ");
            str.append(referenceId);
            outputted++;
        }
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("smallSetUpperBound ");
        str.append(smallSetUpperBound);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("largeSetLowerBound ");
        str.append(largeSetLowerBound);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("mediumSetPresentNumber ");
        str.append(mediumSetPresentNumber);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("replaceIndicator ");
        str.append(replaceIndicator);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("resultSetName ");
        str.append(resultSetName);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("databaseNames ");
        str.append("{");
        for (p = 0; p < databaseNames.length; p++) {
            if (p != 0) {
                str.append(", ");
            }
            str.append(databaseNames[p]);
        }
        str.append("}");
        outputted++;

        if (smallSetElementSetNames != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("smallSetElementSetNames ");
            str.append(smallSetElementSetNames);
            outputted++;
        }

        if (mediumSetElementSetNames != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("mediumSetElementSetNames ");
            str.append(mediumSetElementSetNames);
            outputted++;
        }
        if (preferredRecordSyntax != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("preferredRecordSyntax ");
            str.append(preferredRecordSyntax);
            outputted++;
        }
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("query ");
        str.append(query);
        outputted++;
        if (additionalSearchInfo != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("additionalSearchInfo ");
            str.append(additionalSearchInfo);
            outputted++;
        }
        if (otherInfo != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("otherInfo ");
            str.append(otherInfo);
        }
        str.append("}");
        return str.toString();
    }
}
