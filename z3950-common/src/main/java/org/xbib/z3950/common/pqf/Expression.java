package org.xbib.z3950.common.pqf;

/**
 * PQF abstract syntax tree.
 */
public class Expression extends Node {

    private final String op;

    private final Query query1;

    private final Query query2;

    public Expression(String op, Query query1, Query query2) {
        this.op = op;
        this.query1 = query1;
        this.query2 = query2;
    }

    @Override
    public void accept(Visitor visitor) {
        query2.accept(visitor);
        query1.accept(visitor);
        visitor.visit(this);
    }

    public String getOperator() {
        return op;
    }

    public Query getQuery1() {
        return query1;
    }

    public Query getQuery2() {
        return query2;
    }

    @Override
    public String toString() {
        return "[Expression: op=" + op + ",query1=" + query1 + ",query2=" + query2 + "]";
    }
}
