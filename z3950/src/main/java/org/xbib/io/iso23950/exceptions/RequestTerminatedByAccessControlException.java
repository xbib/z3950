package org.xbib.io.iso23950.exceptions;

/**
 *
 */
public class RequestTerminatedByAccessControlException extends ZException {

    private static final long serialVersionUID = -54961304899756308L;

    public RequestTerminatedByAccessControlException(String message, int status, int number) {
        super(message, status, number);
    }
}
