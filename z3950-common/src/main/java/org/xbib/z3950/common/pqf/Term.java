package org.xbib.z3950.common.pqf;

import java.math.BigDecimal;

public class Term extends Node {

    private final String value;

    public Term(String value) {
        this.value = value;
    }

    public Term(BigDecimal value) {
        this.value = value.toString();
    }

    public String getValue() {
        return value;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String toString() {
        return value;
    }
}
