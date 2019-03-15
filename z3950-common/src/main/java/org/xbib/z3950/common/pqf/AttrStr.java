package org.xbib.z3950.common.pqf;

/**
 * PQF abstract syntax tree.
 */
public class AttrStr extends Node {

    private Integer left;
    private Integer right;
    private String rightStr;

    public AttrStr(Integer left, Integer right) {
        this.left = left;
        this.right = right;
    }

    public AttrStr(Integer left, String right) {
        this.left = left;
        this.rightStr = right;
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
