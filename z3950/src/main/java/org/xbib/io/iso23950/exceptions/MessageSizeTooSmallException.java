package org.xbib.io.iso23950.exceptions;

/**
 *
 */
public class MessageSizeTooSmallException extends ZException {

    private static final long serialVersionUID = 546319297081247810L;

    public MessageSizeTooSmallException(String message, int status, int number) {
        super(message, status, number);
    }
}
