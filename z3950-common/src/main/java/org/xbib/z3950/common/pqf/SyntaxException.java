package org.xbib.z3950.common.pqf;

/**
 * Syntax exception.
 */
@SuppressWarnings("serial")
public class SyntaxException extends RuntimeException {

    /**
     * Creates a new SyntaxException object.
     *
     * @param msg the message for this syntax exception
     */
    public SyntaxException(String msg) {
        super(msg);
    }

}
