package org.xbib.z3950.common.pqf;

import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * PQF abstract syntax tree.
 */
public class Query extends Node {

    private static final Logger logger = Logger.getLogger(Query.class.getName());

    private String attrschema;

    private final LinkedList<AttrStr> attrspec = new LinkedList<>();

    private Setname setname;

    private Term term;

    private Expression expr;

    public Query(String attrschema, AttrStr attrspec, Query query) {
        this.attrschema = attrschema;
        this.attrspec.add(attrspec);
        this.attrspec.addAll(query.getAttrSpec());
        this.setname = query.setname;
        this.term = query.term;
        this.expr = query.expr;
    }

    public Query(AttrStr attrspec, Query query) {
        this.attrspec.add(attrspec);
        this.attrspec.addAll(query.getAttrSpec());
        this.setname = query.setname;
        this.term = query.term;
        this.expr = query.expr;
    }

    public Query(PQF pqf) {
        this.attrschema = pqf.getQuery().attrschema;
        this.attrspec.addAll(pqf.getQuery().attrspec);
        this.setname = pqf.getQuery().setname;
        this.term = pqf.getQuery().term;
        this.expr = pqf.getQuery().expr;
    }

    public Query(Term term) {
        this.term = term;
    }

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
        for (AttrStr attr : attrspec) {
            attr.accept(visitor);
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
        return "[Query: term=" + term + " attrschema=" + attrschema + " setname=" + setname + "]";
    }
}
