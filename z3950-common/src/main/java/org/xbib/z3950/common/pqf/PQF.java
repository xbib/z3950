package org.xbib.z3950.common.pqf;

/**
 * PQF abstract syntax tree.
 */
public class PQF extends Node {

    private final String attrset;
    private final Query query;

    public PQF(String attrset, Query query) {
        this.attrset = attrset;
        this.query = query;
    }

    public PQF(Query query) {
        this.attrset = null; // default
        this.query = query;
    }

    public void accept(Visitor visitor) {
        query.accept(visitor);
        visitor.visit(this);
    }

    public String getAttrSet() {
        return attrset;
    }

    @Override
    public String toString() {
        return "[PQF: attrset=" + attrset + " query=" + query + "]";
    }
}
