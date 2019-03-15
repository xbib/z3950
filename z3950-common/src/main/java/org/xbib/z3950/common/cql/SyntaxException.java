package org.xbib.z3950.common.cql;

/**
 * Syntax exception.
 */
public class SyntaxException extends RuntimeException {

    private static final long serialVersionUID = -3601569690699438132L;

    /**
     * Creates a new SyntaxException object.
     *
     * @param msg the message for this syntax exception
     */
    public SyntaxException(String msg) {
        super(msg);
    }

    /**
     * Creates a new SyntaxException object.
     *
     * @param t the throwable for this syntax exception
     */
    public SyntaxException(Throwable t) {
        super(t);
    }

    /**
     * Creates a new SyntaxException object.
     *
     * @param msg the message for this syntax exception
     * @param t   the throwable for this syntax exception
     */
    public SyntaxException(String msg, Throwable t) {
        super(msg, t);
    }
}
