package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>IdAuthentication_idPass</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * IdAuthentication_idPass ::=
 * SEQUENCE {
 *   groupId [0] IMPLICIT InternationalString OPTIONAL
 *   userId [1] IMPLICIT InternationalString OPTIONAL
 *   password [2] IMPLICIT InternationalString OPTIONAL
 * }
 * </pre>
 */
public final class IdAuthenticationIdPass extends ASN1Any {

    public InternationalString s_groupId; // optional
    public InternationalString s_userId; // optional
    public InternationalString s_password; // optional

    /**
     * Default constructor for a IdAuthentication_idPass.
     */
    public IdAuthenticationIdPass() {
    }

    /**
     * Constructor for a IdAuthentication_idPass from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public IdAuthenticationIdPass(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        // IdAuthentication_idPass should be encoded by a constructed BER

        BERConstructed berConstructed;
        try {
            berConstructed = (BERConstructed) ber;
        } catch (ClassCastException e) {
            throw new ASN1EncodingException("IdAuthentication_idPass: bad BER form\n");
        }

        // Prepare to decode the components

        int numParts = berConstructed.numberComponents();
        int part = 0;
        BEREncoding p;

        // Remaining elements are optional, set variables
        // to null (not present) so can return at endStream of BER

        s_groupId = null;
        s_userId = null;
        s_password = null;

        // Decoding: groupId [0] IMPLICIT InternationalString OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 0 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_groupId = new InternationalString(p, false);
            part++;
        }

        // Decoding: userId [1] IMPLICIT InternationalString OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 1 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_userId = new InternationalString(p, false);
            part++;
        }

        // Decoding: password [2] IMPLICIT InternationalString OPTIONAL

        if (numParts <= part) {
            return; // no more data, but ok (rest is optional)
        }
        p = berConstructed.elementAt(part);

        if (p.getTag() == 2 &&
                p.getTagType() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            s_password = new InternationalString(p, false);
            part++;
        }

        // Should not be any more parts

        if (part < numParts) {
            throw new ASN1Exception("IdAuthentication_idPass: bad BER: extra data " + part + "/" + numParts + " processed");
        }
    }

    /**
     * Returns a BER encoding of the IdAuthentication_idPass.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return berEncode(BEREncoding.UNIVERSAL_TAG, ASN1Sequence.SEQUENCE_TAG);
    }

    /**
     * Returns a BER encoding of IdAuthentication_idPass, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        // Calculate the number of fields in the encoding

        int numFields = 0; // number of mandatories
        if (s_groupId != null) {
            numFields++;
        }
        if (s_userId != null) {
            numFields++;
        }
        if (s_password != null) {
            numFields++;
        }

        // Encode it

        BEREncoding fields[] = new BEREncoding[numFields];
        int x = 0;

        // Encoding s_groupId: InternationalString OPTIONAL

        if (s_groupId != null) {
            fields[x++] = s_groupId.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 0);
        }

        // Encoding s_userId: InternationalString OPTIONAL

        if (s_userId != null) {
            fields[x++] = s_userId.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 1);
        }

        // Encoding s_password: InternationalString OPTIONAL

        if (s_password != null) {
            fields[x] = s_password.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 2);
        }

        return new BERConstructed(tagType, tag, fields);
    }

    /**
     * Returns a new String object containing a text representing
     * of the IdAuthentication_idPass.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        int outputted = 0;
        if (s_groupId != null) {
            str.append("groupId ");
            str.append(s_groupId);
            outputted++;
        }

        if (s_userId != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("userId ");
            str.append(s_userId);
            outputted++;
        }

        if (s_password != null) {
            if (0 < outputted) {
                str.append(", ");
            }
            str.append("password ");
            str.append(s_password);
        }

        str.append("}");

        return str.toString();
    }

}