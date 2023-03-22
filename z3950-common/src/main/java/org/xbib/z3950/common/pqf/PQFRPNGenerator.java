package org.xbib.z3950.common.pqf;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Null;
import org.xbib.asn1.ASN1ObjectIdentifier;
import org.xbib.asn1.ASN1OctetString;
import org.xbib.z3950.common.v3.AttributeElement;
import org.xbib.z3950.common.v3.AttributeElementAttributeValue;
import org.xbib.z3950.common.v3.AttributeList;
import org.xbib.z3950.common.v3.AttributeSetId;
import org.xbib.z3950.common.v3.AttributesPlusTerm;
import org.xbib.z3950.common.v3.Operand;
import org.xbib.z3950.common.v3.Operator;
import org.xbib.z3950.common.v3.RPNQuery;
import org.xbib.z3950.common.v3.RPNStructure;
import org.xbib.z3950.common.v3.RPNStructureRpnRpnOp;

import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PQF abstract syntax tree.
 */
public class PQFRPNGenerator implements Visitor {

    private static final Logger logger = Logger.getLogger(PQFRPNGenerator.class.getName());

    private final Stack<ASN1Any> result;

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
            ASN1Any any = result.pop();
            if (any instanceof RPNStructure) {
                rpnQuery.rpn = (RPNStructure) any;
            } else if (any instanceof ASN1OctetString) {
                // TODO
                logger.log(Level.INFO, "found ASN1OctetString = " + ((ASN1OctetString) any).get());
            }
            if (pqf.getAttrSet() == null) {
                // Z39.50 BIB-1: urn:oid:1.2.840.10003.3.1
                rpnQuery.attributeSetId = new AttributeSetId();
                rpnQuery.attributeSetId.value = new ASN1ObjectIdentifier(new int[]{1, 2, 840, 10003, 3, 1});
            }
        } else {
            throw new SyntaxException("no valid PQF found");
        }
    }

    @Override
    public void visit(Query query) {
        Operand operand = new Operand();
        operand.attrTerm = new AttributesPlusTerm();
        operand.attrTerm.term = new org.xbib.z3950.common.v3.Term();
        Term term = query.getTerm();
        if (term != null) {
            Logger.getLogger("").log(Level.INFO, "query = " + query + " term = " + term + " value = " + term.getValue());
            operand.attrTerm.term.c_general = new ASN1OctetString(term.getValue());
        } else {
            operand.attrTerm.term.c_null = new ASN1Null();
        }
        Stack<AttributeElement> attrs = new Stack<>();
        ASN1Any any = !result.isEmpty() && result.peek() instanceof AttributeElement ? result.pop() : null;
        while (any != null) {
            attrs.push((AttributeElement) any);
            any = !result.isEmpty() && result.peek() instanceof AttributeElement ? result.pop() : null;
        }
        operand.attrTerm.attributes = new AttributeList();
        operand.attrTerm.attributes.value = attrs.toArray(new AttributeElement[0]);
        RPNStructure rpn = new RPNStructure();
        rpn.c_op = operand;
        Logger.getLogger("").log(Level.INFO, "push rpn = " + rpn);
        result.push(rpn);
    }

    @Override
    public void visit(Expression expr) {
        String op = expr.getOperator();
        RPNStructure rpn = new RPNStructure();
        rpn.c_rpnRpnOp = new RPNStructureRpnRpnOp();
        rpn.c_rpnRpnOp.s_op = new Operator();
        if ("@and".equals(op)) {
            rpn.c_rpnRpnOp.s_op.andOp = new ASN1Null();
        }
        if ("@or".equals(op)) {
            rpn.c_rpnRpnOp.s_op.orOp = new ASN1Null();
        }
        if ("@not".equals(op)) {
            rpn.c_rpnRpnOp.s_op.andNotOp = new ASN1Null();
        }
        ASN1Any rpn1 = result.pop();
        if (rpn1 instanceof RPNStructure) {
            Logger.getLogger("test").log(Level.INFO, "rpn1 = " + rpn1);
            rpn.c_rpnRpnOp.s_rpn1 = (RPNStructure) rpn1;
        } else {
            Logger.getLogger("test").log(Level.INFO, "debug rpn1: " + rpn1);
        }
        ASN1Any rpn2 = result.pop();
        if (rpn2 instanceof RPNStructure) {
            Logger.getLogger("test").log(Level.INFO, "rpn2 = " + rpn2);
            rpn.c_rpnRpnOp.s_rpn2 = (RPNStructure) rpn2;
        } else {
            Logger.getLogger("test").log(Level.INFO, "debug rpn2: " + rpn2);
        }
        Logger.getLogger("test").log(Level.INFO, "visit expr: rpn = " + rpn);
        result.push(rpn);
    }

    @Override
    public void visit(AttrStr attrspec) {
        AttributeElement ae = new AttributeElement();
        ae.attributeType = (ASN1Integer) result.pop();
        ae.attributeValue = new AttributeElementAttributeValue();
        ae.attributeValue.numeric = (ASN1Integer) result.pop();
        result.push(ae);
    }

    @Override
    public void visit(Term term) {
        Logger.getLogger("test").log(Level.INFO, "push term = " + term);
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
