package org.xbib.z3950.common.pqf;

import java.math.BigDecimal;

/**
 * PQF abstract syntax tree.
 */
public class AttrStr extends Node {

    private final Integer left;

    private final Integer right;

    private final String rightStr;

    public AttrStr(BigDecimal left, BigDecimal right) {
        this.left = left.intValue();
        this.right = right.intValue();
        this.rightStr = null;
    }

    public AttrStr(BigDecimal left, String right) {
        this.left = left.intValue();
        this.rightStr = right;
        this.right = null;
    }

    @Override
    public void accept(Visitor visitor) {
        if (rightStr != null) {
            visitor.visit(rightStr);
        }
        if (right != null) {
            visitor.visit(right);
        }
        if (left != null) {
            visitor.visit(left);
        }
        visitor.visit(this);
    }

    public Integer getLeft() {
        return left;
    }

    public Integer getRight() {
        return right;
    }

    public String getRightStr() {
        return rightStr;
    }
}
