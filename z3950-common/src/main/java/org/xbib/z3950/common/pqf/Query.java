package org.xbib.z3950.common.pqf;

import java.util.LinkedList;

/**
 * PQF abstract syntax tree.
 */
public class Query extends Node {

    private String attrschema;

    private final LinkedList<AttrStr> attrspec = new LinkedList<>();

    private Query querystruct;

    private Setname setname;

    private Term term;

    private Expression expr;

    private PQF pqf;

    // ATTR CHARSTRING1 attrstr querystruct
    public Query(String attrschema, AttrStr attrspec, Query querystruct) {
        this.attrschema = attrschema;
        this.attrspec.add(attrspec);
        this.querystruct = querystruct;
        this.term = querystruct.getTerm();
        this.attrspec.addAll(querystruct.getAttrSpec());
    }

    // ATTR attrspec querystruct
    public Query(AttrStr attrspec, Query querystruct) {
        this.attrspec.add(attrspec);
        this.querystruct = querystruct;
        this.term = querystruct.getTerm();
        this.attrspec.addAll(querystruct.getAttrSpec());
    }

    // TERM TERMTYPE pqf
    public Query(PQF pqf) {
        this.pqf = pqf;
    }

    // simple
    public Query(Term term) {
        this.term = term;
    }

    // complex
    public Query(Expression expr) {
        this.expr = expr;
    }

    public Query(Setname setname) {
        this.setname = setname;
    }

    public void accept(Visitor visitor) {
        if (term != null) {
            term.accept(visitor);
        }
        if (setname != null) {
            setname.accept(visitor);
        }
        if (expr != null) {
            expr.accept(visitor);
        }
        if (querystruct != null) {
            querystruct.accept(visitor);
        }
        for (AttrStr attr : attrspec) {
            attr.accept(visitor);
        }
        if (pqf != null) {
            pqf.accept(visitor);
        }
        visitor.visit(this);
    }

    public String getSchema() {
        return attrschema;
    }

    public Setname getSetname() {
        return setname;
    }

    public Term getTerm() {
        return term;
    }

    public LinkedList<AttrStr> getAttrSpec() {
        return attrspec;
    }

    @Override
    public String toString() {
        if (expr != null) {
            return "[Query: expr=" + expr + "]";
        }
        return "[Query: term=" + term + " attrschema=" + attrschema + " setname=" + setname +
                " querystruct=" + querystruct + "]";
    }
}
