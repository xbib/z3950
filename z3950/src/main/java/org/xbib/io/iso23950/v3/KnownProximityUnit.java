package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.BEREncoding;


/**
 * Class for representing a <code>KnownProximityUnit</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * KnownProximityUnit ::=
 * INTEGER
 * </pre>
 */
public final class KnownProximityUnit extends ASN1Any {

    public static final int E_character = 1;
    public static final int E_word = 2;
    public static final int E_sentence = 3;
    public static final int E_paragraph = 4;
    public static final int E_section = 5;
    public static final int E_chapter = 6;
    public static final int E_document = 7;
    public static final int E_element = 8;
    public static final int E_subelement = 9;
    public static final int E_elementType = 10;
    public static final int E_byte = 11;
    public ASN1Integer value;
    /**
     * Constructor for a KnownProximityUnit from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */
    public KnownProximityUnit(BEREncoding ber, boolean checkTag)
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
    @Override
    public void berDecode(BEREncoding ber, boolean checkTag) throws ASN1Exception {
        value = new ASN1Integer(ber, checkTag);
    }

    /**
     * Returns a BER encoding of the KnownProximityUnit.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode() throws ASN1Exception {
        return value.berEncode();
    }

    /**
     * Returns a BER encoding of KnownProximityUnit, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    @Override
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        return value.berEncode(tagType, tag);
    }

    /**
     * Returns a new String object containing a text representing
     * of the KnownProximityUnit.
     */
    @Override
    public String toString() {
        return value.toString();
    }

}
