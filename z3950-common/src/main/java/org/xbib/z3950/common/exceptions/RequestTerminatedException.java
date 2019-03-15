package org.xbib.z3950.common.exceptions;

/**
 *
 */
public class RequestTerminatedException extends ZException {

    private static final long serialVersionUID = 686537068801065383L;

    public RequestTerminatedException(String message, int status, int number) {
        super(message, status, number);
    }
}
