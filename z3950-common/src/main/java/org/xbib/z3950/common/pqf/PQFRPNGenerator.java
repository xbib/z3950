package org.xbib.z3950.common.pqf;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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

    private final Charset charset;

    private final Stack<ASN1Any> result;

    private RPNQuery rpnQuery;

    public PQFRPNGenerator() {
        this(StandardCharsets.ISO_8859_1);
    }

    public PQFRPNGenerator(Charset charset) {
        this.result = new Stack<>();
        this.charset = charset;
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
        // check for expression
        ASN1Any any = !result.isEmpty() && result.peek() instanceof RPNStructure ? result.pop() : null;
        if (any != null) {
            // RPN structure is ready
            result.push(any);
            return;
        }

        // new attribute plus term
        Operand operand = new Operand();
        operand.attrTerm = new AttributesPlusTerm();
        operand.attrTerm.term = new org.xbib.z3950.common.v3.Term();
        Stack<AttributeElement> attrs = new Stack<>();
        any = !result.isEmpty() && result.peek() instanceof AttributeElement ? result.pop() : null;
        while (any != null) {
            attrs.push((AttributeElement) any);
            any = !result.isEmpty() && result.peek() instanceof AttributeElement ? result.pop() : null;
        }
        operand.attrTerm.attributes = new AttributeList();
        operand.attrTerm.attributes.value = attrs.toArray(new AttributeElement[0]);

        any = !result.empty() && result.peek() instanceof ASN1OctetString ? result.pop() : null;
        if (any != null) {
            if (any instanceof ASN1OctetString) {
                operand.attrTerm.term.c_general = (ASN1OctetString) any;
            }
        }
        RPNStructure rpn = new RPNStructure();
        rpn.c_op = operand;
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
            rpn.c_rpnRpnOp.s_rpn1 = (RPNStructure) rpn1;
        }
        ASN1Any rpn2 = result.pop();
        if (rpn2 instanceof RPNStructure) {
            rpn.c_rpnRpnOp.s_rpn2 = (RPNStructure) rpn2;
        }
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
        result.push(new ASN1OctetString(term.getValue(), charset));
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
