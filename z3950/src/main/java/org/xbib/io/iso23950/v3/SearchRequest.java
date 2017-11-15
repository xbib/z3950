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
    public ReferenceId s_referenceId; // optional
    public ASN1Integer s_smallSetUpperBound;
    public ASN1Integer s_largeSetLowerBound;
    public ASN1Integer s_mediumSetPresentNumber;
    public ASN1Boolean s_replaceIndicator;
    public InternationalString s_resultSetName;
    public DatabaseName s_databaseNames[];
    public ElementSetNames s_smallSetElementSetNames; // optional
    public ElementSetNames s_mediumSetElementSetNames; // optional
    public ASN1ObjectIdentifier s_preferredRecordSyntax; // optional
    public Query s_query;
    public OtherInformation s_additionalSearchInfo; // optional
    public OtherInformation s_otherInfo; // optional
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

    public void berDecode(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        // SearchRequest should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("SearchRequest: bad BER form\n");
        }
        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        BERConstructed tagged;
        if (numParts <= part) {
            throw new ASN1Exception("SearchRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        try {
            s_referenceId = new ReferenceId(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_referenceId = null; // no, not present
        }
        if (numParts <= part) {
            throw new ASN1Exception("SearchRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() != 13 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("SearchRequest: bad tag in s_smallSetUpperBound\n");
        }
        s_smallSetUpperBound = new ASN1Integer(p, false);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("SearchRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() != 14 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("SearchRequest: bad tag in s_largeSetLowerBound\n");
        }
        s_largeSetLowerBound = new ASN1Integer(p, false);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("SearchRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() != 15 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("SearchRequest: bad tag in s_mediumSetPresentNumber\n");
        }
        s_mediumSetPresentNumber = new ASN1Integer(p, false);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("SearchRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() != 16 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("SearchRequest: bad tag in s_replaceIndicator\n");
        }
        s_replaceIndicator = new ASN1Boolean(p, false);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("SearchRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() != 17 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("SearchRequest: bad tag in s_resultSetName\n");
        }
        s_resultSetName = new InternationalString(p, false);
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("SearchRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() != 18 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("SearchRequest: bad tag in s_databaseNames\n");
        }
        try {
            BERConstructed cons = (BERConstructed) p;
            int parts = cons.numberComponents();
            s_databaseNames = new DatabaseName[parts];
            int n;
            for (n = 0; n < parts; n++) {
                s_databaseNames[n] = new DatabaseName(cons.elementAt(n), true);
            }
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("Bad BER");
        }
        part++;
        if (numParts <= part) {
            throw new ASN1Exception("SearchRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() == 100 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagged = (BERConstructed) p;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("SearchRequest: bad BER encoding: s_smallSetElementSetNames tag bad\n");
            }
            if (tagged.numberComponents() != 1) {
                throw new ASN1EncodingException("SearchRequest: bad BER encoding: s_smallSetElementSetNames tag bad\n");
            }
            s_smallSetElementSetNames = new ElementSetNames(tagged.elementAt(0), true);
            part++;
        }
        if (numParts <= part) {
            throw new ASN1Exception("SearchRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() == 101 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagged = (BERConstructed) p;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("SearchRequest: bad BER encoding: s_mediumSetElementSetNames tag bad\n");
            }
            if (tagged.numberComponents() != 1) {
                throw new ASN1EncodingException("SearchRequest: bad BER encoding: s_mediumSetElementSetNames tag bad\n");
            }
            s_mediumSetElementSetNames = new ElementSetNames(tagged.elementAt(0), true);
            part++;
        }
        if (numParts <= part) {
            throw new ASN1Exception("SearchRequest: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 104 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_preferredRecordSyntax = new ASN1ObjectIdentifier(p, false);
            part++;
        }
        if (numParts <= part) {
            throw new ASN1Exception("SearchRequest: incomplete");
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() != 21 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("SearchRequest: bad tag in s_query\n");
        }
        try {
            tagged = (BERConstructed) p;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("SearchRequest: bad BER encoding: s_query tag bad\n");
        }
        if (tagged.numberComponents() != 1) {
            throw new ASN1EncodingException("SearchRequest: bad BER encoding: s_query tag bad\n");
        }
        s_query = new Query(tagged.elementAt(0), true);
        part++;
        s_additionalSearchInfo = null;
        s_otherInfo = null;
        if (numParts <= part) {
            return;
        }
        p = berConstructed.elementAt(part);
        if (p.tagGet() == 203 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_additionalSearchInfo = new OtherInformation(p, false);
            part++;
        }
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
        if (part < numParts) {
            throw new ASN1Exception("SearchRequest: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the SearchRequest.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
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
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        int numFields = 7; // number of mandatories
        if (s_referenceId != null) {
            numFields++;
        }
        if (s_smallSetElementSetNames != null) {
            numFields++;
        }
        if (s_mediumSetElementSetNames != null) {
            numFields++;
        }
        if (s_preferredRecordSyntax != null) {
            numFields++;
        }
        if (s_additionalSearchInfo != null) {
            numFields++;
        }
        if (s_otherInfo != null) {
            numFields++;
        }
        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding f2[];
        int p;
        BEREncoding enc[];
        if (s_referenceId != null) {
            fields[x++] = s_referenceId.berEncode();
        }
        fields[x++] = s_smallSetUpperBound.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 13);
        fields[x++] = s_largeSetLowerBound.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 14);
        fields[x++] = s_mediumSetPresentNumber.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 15);
        fields[x++] = s_replaceIndicator.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 16);
        fields[x++] = s_resultSetName.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 17);
        f2 = new BEREncoding[s_databaseNames.length];
        for (p = 0; p < s_databaseNames.length; p++) {
            f2[p] = s_databaseNames[p].berEncode();
        }
        fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 18, f2);
        if (s_smallSetElementSetNames != null) {
            enc = new BEREncoding[1];
            enc[0] = s_smallSetElementSetNames.berEncode();
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 100, enc);
        }
        if (s_mediumSetElementSetNames != null) {
            enc = new BEREncoding[1];
            enc[0] = s_mediumSetElementSetNames.berEncode();
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 101, enc);
        }
        if (s_preferredRecordSyntax != null) {
            fields[x++] = s_preferredRecordSyntax.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 104);
        }
        enc = new BEREncoding[1];
        enc[0] = s_query.berEncode();
        fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 21, enc);
        if (s_additionalSearchInfo != null) {
            fields[x++] = s_additionalSearchInfo.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 203);
        }
        if (s_otherInfo != null) {
            fields[x] = s_otherInfo.berEncode();
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the SearchRequest.
     */

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
        str.append("smallSetUpperBound ");
        str.append(s_smallSetUpperBound);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("largeSetLowerBound ");
        str.append(s_largeSetLowerBound);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("mediumSetPresentNumber ");
        str.append(s_mediumSetPresentNumber);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("replaceIndicator ");
        str.append(s_replaceIndicator);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("resultSetName ");
        str.append(s_resultSetName);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("databaseNames ");
        str.append("{");
        for (p = 0; p < s_databaseNames.length; p++) {
            if (p != 0) {
                str.append(", ");
            }
            str.append(s_databaseNames[p]);
        }
        str.append("}");
        outputted++;

        if (s_smallSetElementSetNames != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("smallSetElementSetNames ");
            str.append(s_smallSetElementSetNames);
            outputted++;
        }

        if (s_mediumSetElementSetNames != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("mediumSetElementSetNames ");
            str.append(s_mediumSetElementSetNames);
            outputted++;
        }

        if (s_preferredRecordSyntax != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("preferredRecordSyntax ");
            str.append(s_preferredRecordSyntax);
            outputted++;
        }

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("query ");
        str.append(s_query);
        outputted++;

        if (s_additionalSearchInfo != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("additionalSearchInfo ");
            str.append(s_additionalSearchInfo);
            outputted++;
        }

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
