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

    public ReferenceId referenceId; // optional
    public ProtocolVersion protocolVersion;
    public Options options;
    public ASN1Integer preferredMessageSize;
    public ASN1Integer exceptionalRecordSize;
    public IdAuthentication idAuthentication; // optional
    public InternationalString implementationId; // optional
    public InternationalString implementationName; // optional
    public InternationalString implementationVersion; // optional
    public ASN1External userInformationField; // optional
    public OtherInformation otherInfo; // optional

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
    public InitializeRequest(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // InitializeRequest should be encoded by a constructed BER
        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("bad BER form");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;
        BERConstructed tagged;

        // Decoding: referenceId ReferenceId OPTIONAL

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        try {
            referenceId = new ReferenceId(p, true);
            part++; // yes, consumed
        } catch (ASN1Exception e) {
            referenceId = null; // no, not present
        }

        // Decoding: protocolVersion ProtocolVersion

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        protocolVersion = new ProtocolVersion(p, true);
        part++;

        // Decoding: options Options

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        options = new Options(p, true);
        part++;

        // Decoding: preferredMessageSize [5] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() != 5 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in preferredMessageSize");
        }

        preferredMessageSize = new ASN1Integer(p, false);
        part++;

        // Decoding: exceptionalRecordSize [6] IMPLICIT INTEGER

        if (numParts <= part) {
            // End of record, but still more elements to get
            throw new ASN1Exception("incomplete");
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() != 6 ||
                p.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1EncodingException("bad tag in exceptionalRecordSize");
        }

        exceptionalRecordSize = new ASN1Integer(p, false);
        part++;

        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER

        idAuthentication = null;
        implementationId = null;
        implementationName = null;
        implementationVersion = null;
        userInformationField = null;
        otherInfo = null;

        // Decoding: idAuthentication [7] EXPLICIT IdAuthentication OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 7 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagged = (BERConstructed) p;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("bad BER encoding: idAuthentication tag bad");
            }
            if (tagged.numberComponents() != 1) {
                throw new ASN1EncodingException("bad BER encoding: idAuthentication tag bad");
            }
            idAuthentication = new IdAuthentication(tagged.elementAt(0), true);
            part++;
        }
        // Decoding: implementationId [110] IMPLICIT InternationalString OPTIONAL
        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);
        if (p.getTag() == 110 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            implementationId = new InternationalString(p, false);
            part++;
        }
        // Decoding: implementationName [111] IMPLICIT InternationalString OPTIONAL
        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 111 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            implementationName = new InternationalString(p, false);
            part++;
        }

        // Decoding: implementationVersion [112] IMPLICIT InternationalString OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 112 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            implementationVersion = new InternationalString(p, false);
            part++;
        }

        // Decoding: userInformationField [11] EXPLICIT EXTERNAL OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 11 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            try {
                tagged = (BERConstructed) p;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("bad BER encoding: s_userInformationField tag bad");
            }
            if (tagged.numberComponents() != 1) {
                throw new ASN1EncodingException("bad BER encoding: s_userInformationField tag bad");
            }
            userInformationField = new ASN1External(tagged.elementAt(0), true);
            part++;
        }

        // Decoding: otherInfo OtherInformation OPTIONAL

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
        // Should not be any more parts
        if (part < numParts) {
            throw new ASN1Exception("bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the InitializeRequest.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
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

    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        // Calculate the number of fields in the encoding
        int numFields = 4; // number of mandatories
        if (referenceId != null) {
            numFields++;
        }
        if (idAuthentication != null) {
            numFields++;
        }
        if (implementationId != null) {
            numFields++;
        }
        if (implementationName != null) {
            numFields++;
        }
        if (implementationVersion != null) {
            numFields++;
        }
        if (userInformationField != null) {
            numFields++;
        }
        if (otherInfo != null) {
            numFields++;
        }
        // Encode it
        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;
        BEREncoding enc[];
        // Encoding s_referenceId: ReferenceId OPTIONAL
        if (referenceId != null) {
            fields[x++] = referenceId.berEncode();
        }
        // Encoding s_protocolVersion: ProtocolVersion
        fields[x++] = protocolVersion.berEncode();
        // Encoding s_options: Options
        fields[x++] = options.berEncode();
        // Encoding s_preferredMessageSize: INTEGER
        fields[x++] = preferredMessageSize.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 5);
        // Encoding s_exceptionalRecordSize: INTEGER
        fields[x++] = exceptionalRecordSize.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 6);
        // Encoding s_idAuthentication: IdAuthentication OPTIONAL
        if (idAuthentication != null) {
            enc = new BEREncoding[1];
            enc[0] = idAuthentication.berEncode();
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 7, enc);
        }
        // Encoding s_implementationId: InternationalString OPTIONAL
        if (implementationId != null) {
            fields[x++] = implementationId.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 110);
        }
        // Encoding s_implementationName: InternationalString OPTIONAL
        if (implementationName != null) {
            fields[x++] = implementationName.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 111);
        }
        // Encoding s_implementationVersion: InternationalString OPTIONAL
        if (implementationVersion != null) {
            fields[x++] = implementationVersion.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 112);
        }
        // Encoding s_userInformationField: EXTERNAL OPTIONAL

        if (userInformationField != null) {
            enc = new BEREncoding[1];
            enc[0] = userInformationField.berEncode();
            fields[x++] = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 11, enc);
        }
        // Encoding s_otherInfo: OtherInformation OPTIONAL
        if (otherInfo != null) {
            fields[x] = otherInfo.berEncode();
        }
        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the InitializeRequest.
     */
    @Override
    public String toString() {
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
        str.append("protocolVersion ");
        str.append(protocolVersion);
        outputted++;
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("options ");
        str.append(options);
        outputted++;
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("preferredMessageSize ");
        str.append(preferredMessageSize);
        outputted++;
        if (0 < outputted) {
            str.append(", ");
        }
        str.append("exceptionalRecordSize ");
        str.append(exceptionalRecordSize);
        outputted++;
        if (idAuthentication != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("idAuthentication ");
            str.append(idAuthentication);
            outputted++;
        }
        if (implementationId != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("implementationId ");
            str.append(implementationId);
            outputted++;
        }
        if (implementationName != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("implementationName ");
            str.append(implementationName);
            outputted++;
        }
        if (implementationVersion != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("implementationVersion ");
            str.append(implementationVersion);
            outputted++;
        }
        if (userInformationField != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("userInformationField ");
            str.append(userInformationField);
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
