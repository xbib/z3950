package org.xbib.io.iso23950.pqf;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Null;
import org.xbib.asn1.ASN1ObjectIdentifier;
import org.xbib.asn1.ASN1OctetString;
import org.xbib.io.iso23950.v3.AttributeElement;
import org.xbib.io.iso23950.v3.AttributeElementAttributeValue;
import org.xbib.io.iso23950.v3.AttributeList;
import org.xbib.io.iso23950.v3.AttributeSetId;
import org.xbib.io.iso23950.v3.AttributesPlusTerm;
import org.xbib.io.iso23950.v3.Operand;
import org.xbib.io.iso23950.v3.Operator;
import org.xbib.io.iso23950.v3.RPNQuery;
import org.xbib.io.iso23950.v3.RPNStructure;
import org.xbib.io.iso23950.v3.RPNStructureRpnRpnOp;

import java.util.Stack;

/**
 * PQF abstract syntax tree.
 */
public class PQFRPNGenerator implements Visitor {

    private Stack<ASN1Any> result;

    private RPNQuery rpnQuery;

    public PQFRPNGenerator() {
        this.result = new Stack<>();
    }

    public RPNQuery getResult() {
        return rpnQuery;
    }

    @Override
    public void visit(PQF pqf) {
        if (!result.isEmpty()) {
            this.rpnQuery = new RPNQuery();
            rpnQuery.s_rpn = (RPNStructure) result.pop();
            if (pqf.getAttrSet() == null) {
                // Z39.50 BIB-1: urn:oid:1.2.840.10003.3.1
                rpnQuery.s_attributeSet = new AttributeSetId();
                rpnQuery.s_attributeSet.value = new ASN1ObjectIdentifier(new int[]{1, 2, 840, 10003, 3, 1});
            }
        } else {
            throw new SyntaxException("no valid PQF found");
        }
    }

    @Override
    public void visit(Query query) {
        Operand operand = new Operand();
        operand.c_attrTerm = new AttributesPlusTerm();
        operand.c_attrTerm.sTerm = new org.xbib.io.iso23950.v3.Term();
        operand.c_attrTerm.sTerm.c_general = new ASN1OctetString(query.getTerm().getValue());
        Stack<AttributeElement> attrs = new Stack<>();
        ASN1Any any = !result.isEmpty() && result.peek() instanceof AttributeElement ? result.pop() : null;
        while (any != null) {
            attrs.push((AttributeElement) any);
            any = !result.isEmpty() && result.peek() instanceof AttributeElement ? result.pop() : null;
        }
        operand.c_attrTerm.sAttributes = new AttributeList();
        operand.c_attrTerm.sAttributes.value = attrs.toArray(new AttributeElement[attrs.size()]);
        RPNStructure rpn = new RPNStructure();
        rpn.c_op = operand;
        if (attrs.size() > 0) {
            result.push(rpn);
        }
    }

    @Override
    public void visit(Expression expr) {
        String op = expr.getOperator();
        RPNStructure rpn = new RPNStructure();
        rpn.c_rpnRpnOp = new RPNStructureRpnRpnOp();
        rpn.c_rpnRpnOp.s_op = new Operator();
        if ("@and".equals(op)) {
            rpn.c_rpnRpnOp.s_op.c_and = new ASN1Null();
        }
        if ("@or".equals(op)) {
            rpn.c_rpnRpnOp.s_op.c_or = new ASN1Null();
        }
        if ("@not".equals(op)) {
            rpn.c_rpnRpnOp.s_op.c_and_not = new ASN1Null();
        }
        rpn.c_rpnRpnOp.s_rpn1 = (RPNStructure) result.pop();
        rpn.c_rpnRpnOp.s_rpn2 = (RPNStructure) result.pop();
        result.push(rpn);
    }

    @Override
    public void visit(AttrStr attrspec) {
        AttributeElement ae = new AttributeElement();
        ae.sAttributeType = (ASN1Integer) result.pop();
        ae.attributeValue = new AttributeElementAttributeValue();
        ae.attributeValue.cNumeric = (ASN1Integer) result.pop();
        result.push(ae);
    }

    @Override
    public void visit(Term term) {
        result.push(new ASN1OctetString(term.getValue()));
    }

    @Override
    public void visit(Setname name) {
        result.push(new ASN1OctetString(name.getValue()));
    }

    @Override
    public void visit(Integer i) {
        result.push(new ASN1Integer(i));
    }

    @Override
    public void visit(String str) {
        result.push(new ASN1OctetString(str));
    }
}
