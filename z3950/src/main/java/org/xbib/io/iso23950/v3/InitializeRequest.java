package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1External;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>InitializeRequest</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * InitializeRequest ::=
 * SEQUENCE {
 *   referenceId ReferenceId OPTIONAL
 *   protocolVersion ProtocolVersion
 *   options Options
 *   preferredMessageSize [5] IMPLICIT INTEGER
 *   exceptionalRecordSize [6] IMPLICIT INTEGER
 *   idAuthentication [7] EXPLICIT IdAuthentication OPTIONAL
 *   implementationId [110] IMPLICIT InternationalString OPTIONAL
 *   implementationName [111] IMPLICIT InternationalString OPTIONAL
 *   implementationVersion [112] IMPLICIT InternationalString OPTIONAL
 *   userInformationField [11] EXPLICIT EXTERNAL OPTIONAL
 *   otherInfo OtherInformation OPTIONAL
 * }
 * </pre>
 */
public final class InitializeRequest extends ASN1Any {

    public ReferenceId s_referenceId; // optional
    public ProtocolVersion s_protocolVersion;
    public Options s_options;
    public ASN1Integer s_preferredMessageSize;
    public ASN1Integer s_exceptionalRecordSize;
    public IdAuthentication s_idAuthentication; // optional
    public InternationalString s_implementationId; // optional
    public InternationalString s_implementationName; // optional
    public InternationalString s_implementationVersion; // optional
    public ASN1External s_userInformationField; // optional
    public OtherInformation s_otherInfo; // optional
    /**
     * Default constructor for a InitializeRequest.
     */
    public InitializeRequest() {
    }
    /**
     * Constructor for a InitializeRequest from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public InitializeRequest(BEREncoding ber, boolean checkTag)
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
        // InitializeRequest should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("InitializeRequest: bad BER form");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        BERConstructed tagged;

        // Decoding: referenceId ReferenceId OPTIONAL

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("InitializeRequest: incomplete");
        }
        p = berConstructed.elementAt(part);

        try {
            s_referenceId = new ReferenceId(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            s_referenceId = null; // no, not present
        }

        // Decoding: protocolVersion ProtocolVersion

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("InitializeRequest: incomplete");
        }
        p = berConstructed.elementAt(part);

        s_protocolVersion = new ProtocolVersion(p, true);
        part++;

        // Decoding: options Options

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("InitializeRequest: incomplete");
        }
        p = berConstructed.elementAt(part);

        s_options = new Options(p, true);
        part++;

        // Decoding: preferredMessageSize [5] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("InitializeRequest: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() != 5 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("InitializeRequest: bad tag in s_preferredMessageSize");
        }

        s_preferredMessageSize = new ASN1Integer(p, false);
        part++;

        // Decoding: exceptionalRecordSize [6] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("InitializeRequest: incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() != 6 ||
                p.tagTypeGet() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("InitializeRequest: bad tag in s_exceptionalRecordSize");
        }

        s_exceptionalRecordSize = new ASN1Integer(p, false);
        part++;

        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER

        s_idAuthentication = null;
        s_implementationId = null;
        s_implementationName = null;
        s_implementationVersion = null;
        s_userInformationField = null;
        s_otherInfo = null;

        // Decoding: idAuthentication [7] EXPLICIT IdAuthentication OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 7 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagged = (BERConstructed) p;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("InitializeRequest: bad BER encoding: s_idAuthentication tag bad");
            }
            if (tagged.numberComponents() != 1) {
                throw new ASN1EncodingException("InitializeRequest: bad BER encoding: s_idAuthentication tag bad");
            }

            s_idAuthentication = new IdAuthentication(tagged.elementAt(0), true);
            part++;
        }

        // Decoding: implementationId [110] IMPLICIT InternationalString OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 110 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_implementationId = new InternationalString(p, false);
            part++;
        }

        // Decoding: implementationName [111] IMPLICIT InternationalString OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 111 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_implementationName = new InternationalString(p, false);
            part++;
        }

        // Decoding: implementationVersion [112] IMPLICIT InternationalString OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 112 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_implementationVersion = new InternationalString(p, false);
            part++;
        }

        // Decoding: userInformationField [11] EXPLICIT EXTERNAL OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.tagGet() == 11 &&
                p.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagged = (BERConstructed) p;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("InitializeRequest: bad BER encoding: s_userInformationField tag bad");
            }
            if (tagged.numberComponents() != 1) {
                throw new ASN1EncodingException("InitializeRequest: bad BER encoding: s_userInformationField tag bad");
            }

            s_userInformationField = new ASN1External(tagged.elementAt(0), true);
            part++;
        }

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
            throw new ASN1Exception("InitializeRequest: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the InitializeRequest.
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
     * Returns a BER encoding of InitializeRequest, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     * @see org.xbib.asn1.BEREncoding#UNIVERSAL_TAG
     * @see org.xbib.asn1.BEREncoding#APPLICATION_TAG
     * @see org.xbib.asn1.BEREncoding#CONTEXT_SPECIFIC_TAG
     * @see org.xbib.asn1.BEREncoding#PRIVATE_TAG
     */

    public BEREncoding
    berEncode(int tagType, int tag)
            throws ASN1Exception {
        // Calculate the number of fields in the encoding

        int numFields = 4; // number of mandatories
        if (s_referenceId != null) {
            numFields++;
        }
        if (s_idAuthentication != null) {
            numFields++;
        }
        if (s_implementationId != null) {
            numFields++;
        }
        if (s_implementationName != null) {
            numFields++;
        }
        if (s_implementationVersion != null) {
            numFields++;
        }
        if (s_userInformationField != null) {
            numFields++;
        }
        if (s_otherInfo != null) {
            numFields++;
        }

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding enc[];

        // Encoding s_referenceId: ReferenceId OPTIONAL

        if (s_referenceId != null) {
            fields[x++] = s_referenceId.berEncode();
        }

        // Encoding s_protocolVersion: ProtocolVersion

        fields[x++] = s_protocolVersion.berEncode();

        // Encoding s_options: Options

        fields[x++] = s_options.berEncode();

        // Encoding s_preferredMessageSize: INTEGER

        fields[x++] = s_preferredMessageSize.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 5);

        // Encoding s_exceptionalRecordSize: INTEGER

        fields[x++] = s_exceptionalRecordSize.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 6);

        // Encoding s_idAuthentication: IdAuthentication OPTIONAL

        if (s_idAuthentication != null) {
            enc = new BEREncoding[1];
            enc[0] = s_idAuthentication.berEncode();
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 7, enc);
        }

        // Encoding s_implementationId: InternationalString OPTIONAL

        if (s_implementationId != null) {
            fields[x++] = s_implementationId.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 110);
        }

        // Encoding s_implementationName: InternationalString OPTIONAL

        if (s_implementationName != null) {
            fields[x++] = s_implementationName.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 111);
        }

        // Encoding s_implementationVersion: InternationalString OPTIONAL

        if (s_implementationVersion != null) {
            fields[x++] = s_implementationVersion.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 112);
        }

        // Encoding s_userInformationField: EXTERNAL OPTIONAL

        if (s_userInformationField != null) {
            enc = new BEREncoding[1];
            enc[0] = s_userInformationField.berEncode();
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 11, enc);
        }

        // Encoding s_otherInfo: OtherInformation OPTIONAL

        if (s_otherInfo != null) {
            fields[x++] = s_otherInfo.berEncode();
        }

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the InitializeRequest.
     */

    public String
    toString() {
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
        str.append("protocolVersion ");
        str.append(s_protocolVersion);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("options ");
        str.append(s_options);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("preferredMessageSize ");
        str.append(s_preferredMessageSize);
        outputted++;

        if (0 < outputted) {
            str.append(", ");
        }
        str.append("exceptionalRecordSize ");
        str.append(s_exceptionalRecordSize);
        outputted++;

        if (s_idAuthentication != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("idAuthentication ");
            str.append(s_idAuthentication);
            outputted++;
        }

        if (s_implementationId != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("implementationId ");
            str.append(s_implementationId);
            outputted++;
        }

        if (s_implementationName != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("implementationName ");
            str.append(s_implementationName);
            outputted++;
        }

        if (s_implementationVersion != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("implementationVersion ");
            str.append(s_implementationVersion);
            outputted++;
        }

        if (s_userInformationField != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("userInformationField ");
            str.append(s_userInformationField);
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
