package org.xbib.io.iso23950.pqf;

/**
 *
 */
public class Setname extends Node {

    private final String value;

    public Setname(String value) {
        this.value = value;
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
