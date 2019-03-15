package org.xbib.asn1;

/**
 * ASN1EncodingException.
 */
public class ASN1EncodingException extends ASN1Exception {
    private static final long serialVersionUID = 2063392457642967553L;

    public ASN1EncodingException() {
        super("ASN.1 encoding exception");
    }

    public ASN1EncodingException(String message) {
        super(message);
    }
}
