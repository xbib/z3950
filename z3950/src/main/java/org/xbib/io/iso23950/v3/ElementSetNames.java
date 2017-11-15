package org.xbib.io.iso23950.v3;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1EncodingException;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.BERConstructed;
import org.xbib.asn1.BEREncoding;

/**
 * Class for representing a <code>ElementSetNames</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * ElementSetNames ::=
 * CHOICE {
 *   genericElementSetName [0] IMPLICIT InternationalString
 *   databaseSpecific [1] IMPLICIT SEQUENCE OF ElementSetNames_databaseSpecific
 * }
 * </pre>
 */
public final class ElementSetNames extends ASN1Any {

    public InternationalString cGenericElementSetName;
    public ElementSetNamesDatabaseSpecific[] cDatabaseSpecific;

    /**
     * Default constructor for a ElementSetNames.
     */

    public ElementSetNames() {
    }

    /**
     * Constructor for a ElementSetNames from a BER encoding.
     *
     * @param ber       the BER encoding.
     * @param checkTag will check tag if true, use false
     *                  if the BER has been implicitly tagged. You should
     *                  usually be passing true.
     * @throws ASN1Exception if the BER encoding is bad.
     */

    public ElementSetNames(BEREncoding ber, boolean checkTag) throws ASN1Exception {
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
        cGenericElementSetName = null;
        cDatabaseSpecific = null;
        if (ber.tagGet() == 0 &&
                ber.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            cGenericElementSetName = new InternationalString(ber, false);
            return;
        }
        if (ber.tagGet() == 1 &&
                ber.tagTypeGet() == BEREncoding.CONTEXT_SPECIFIC_TAG) {
            BEREncoding berData;
            berData = ber;
            BERConstructed berConstructed;
            try {
                berConstructed = (BERConstructed) berData;
            } catch (ClassCastException e) {
                throw new ASN1EncodingException("ElementSetNames: bad BER form");
            }
            int numParts = berConstructed.numberComponents();
            int p;
            cDatabaseSpecific = new ElementSetNamesDatabaseSpecific[numParts];
            for (p = 0; p < numParts; p++) {
                cDatabaseSpecific[p] = new ElementSetNamesDatabaseSpecific(berConstructed.elementAt(p), true);
            }
            return;
        }
        throw new ASN1Exception("ElementSetNames: bad BER encoding: choice not matched");
    }

    /**
     * Returns a BER encoding of ElementSetNames.
     *
     * @return The BER encoding.
     * @throws ASN1Exception Invalid or cannot be encoded.
     */
    public BEREncoding berEncode() throws ASN1Exception {
        BEREncoding chosen = null;
        BEREncoding[] f2;
        int p;
        if (cGenericElementSetName != null) {
            chosen = cGenericElementSetName.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 0);
        }
        if (cDatabaseSpecific != null) {
            if (chosen != null) {
                throw new ASN1Exception("CHOICE multiply set");
            }
            f2 = new BEREncoding[cDatabaseSpecific.length];
            for (p = 0; p < cDatabaseSpecific.length; p++) {
                f2[p] = cDatabaseSpecific[p].berEncode();
            }
            chosen = new BERConstructed(BEREncoding.CONTEXT_SPECIFIC_TAG, 1, f2);
        }
        if (chosen == null) {
            throw new ASN1Exception("CHOICE not set");
        }
        return chosen;
    }

    public BEREncoding berEncode(int tagType, int tag) throws ASN1Exception {
        throw new ASN1EncodingException("ElementSetNames: cannot implicitly tag");
    }

    /**
     * Returns a new String object containing a text representing
     * of the ElementSetNames.
     */
    public String toString() {
        int p;
        StringBuilder str = new StringBuilder("{");
        boolean found = false;
        if (cGenericElementSetName != null) {
            found = true;
            str.append("genericElementSetName ");
            str.append(cGenericElementSetName);
        }
        if (cDatabaseSpecific != null) {
            if (found) {
                str.append("<ERROR: multiple CHOICE: databaseSpecific> ");
            }
            str.append("databaseSpecific ");
            str.append("{");
            for (p = 0; p < cDatabaseSpecific.length; p++) {
                str.append(cDatabaseSpecific[p]);
            }
            str.append("}");
        }
        str.append("}");
        return str.toString();
    }
}
