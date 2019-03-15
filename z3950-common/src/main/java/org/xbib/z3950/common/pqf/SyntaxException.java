package org.xbib.z3950.common.pqf;

/**
 * Syntax exception.
 */
public class SyntaxException extends RuntimeException {
    private static final long serialVersionUID = -962913398056374183L;

    /**
     * Creates a new SyntaxException object.
     *
     * @param msg the message for this syntax exception
     */
    public SyntaxException(String msg) {
        super(msg);
    }

}
