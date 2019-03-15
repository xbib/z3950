package org.xbib.z3950.common.pqf;

/**
 * PQF abstract syntax tree.
 */
public class Expression extends Node {

    private String op;

    private Query q1;

    private Query q2;

    public Expression(String op, Query q1, Query q2) {
        this.op = op;
        this.q1 = q1;
        this.q2 = q2;
    }

    @Override
    public void accept(Visitor visitor) {
        q2.accept(visitor);
        q1.accept(visitor);
        visitor.visit(this);
    }

    public String getOperator() {
        return op;
    }
}
