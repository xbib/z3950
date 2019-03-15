package org.xbib.z3950.common.pqf;

/**
 * PQF abstract syntax tree visitor.
 */
public interface Visitor {

    /**
     * Visit PQF.
     *
     * @param pqf the tree to visit.
     */
    void visit(PQF pqf);

    /**
     * Visit an query.
     *
     * @param query the query to visit.
     */
    void visit(Query query);

    /**
     * Visit an expression.
     *
     * @param expression the expression to visit.
     */
    void visit(Expression expression);

    void visit(AttrStr attrspec);

    void visit(Term term);

    void visit(Setname name);

    void visit(String str);

    void visit(Integer i);

}
