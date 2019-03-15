package org.xbib.z3950.common.exceptions;

/**
 *
 */
public class NoRecordsReturnedException extends ZException {

    private static final long serialVersionUID = -19157655707502087L;

    public NoRecordsReturnedException(String message, int status) {
        super(message, status);
    }

}
