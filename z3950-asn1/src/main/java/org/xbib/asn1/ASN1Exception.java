package org.xbib.asn1;

import java.io.IOException;

/**
 * ASN1Exception.
 */
public class ASN1Exception extends IOException {
    private static final long serialVersionUID = -339688617660261367L;

    public ASN1Exception() {
        super("ASN.1 exception");
    }

    public ASN1Exception(String message) {
        super(message);
    }
}
