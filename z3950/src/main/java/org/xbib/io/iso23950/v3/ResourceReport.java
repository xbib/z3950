package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1External;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>ResourceReport</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * ResourceReport ::=
 * EXTERNAL
 * </pre>
 */
public final class ResourceReport extends ASN1Any {

    public ASN1External value;

    /**
     * Constructor for a ResourceReport from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public ResourceReport(BEREncoding ber, boolean checkTag)
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
    public void berDecode(BEREncoding ber, boolean checkTag)
            throws ASN1Exception {
        value = new ASN1External(ber, checkTag);
    }

    /**
     * Returns a BER encoding of the ResourceReport.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    public BEREncoding berEncode() throws ASN1Exception {
        return value.berEncode();
    }

    /**
     * Returns a BER encoding of ResourceReport, implicitly tagged.
     *
     * @param tagType The type of the implicit tag.
     * @param tag      The implicit tag.
     * @return The BER encoding of the object.
     * @throws ASN1Exception When invalid or cannot be encoded.
     */
    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        return value.berEncode(tagType, tag);
    }

    /**
     * Returns a new String object containing a text representing
     * of the ResourceReport.
     */

    public String toString() {
        return value.toString();
    }

}
