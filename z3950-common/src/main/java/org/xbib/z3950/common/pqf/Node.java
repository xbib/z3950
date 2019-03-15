package org.xbib.z3950.common.pqf;

/**
 * PQF abstract syntax tree.
 */
public abstract class Node {

    /**
     * Try to accept this node by a visitor.
     *
     * @param v the visitor
     */
    public abstract void accept(Visitor v);

}
