package org.xbib.z3950.common.cql;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Null;
import org.xbib.asn1.ASN1ObjectIdentifier;
import org.xbib.asn1.ASN1OctetString;
import org.xbib.cql.BooleanGroup;
import org.xbib.cql.BooleanOperator;
import org.xbib.cql.Identifier;
import org.xbib.cql.Index;
import org.xbib.cql.Modifier;
import org.xbib.cql.ModifierList;
import org.xbib.cql.PrefixAssignment;
import org.xbib.cql.Query;
import org.xbib.cql.Relation;
import org.xbib.cql.ScopedClause;
import org.xbib.cql.SearchClause;
import org.xbib.cql.SimpleName;
import org.xbib.cql.SingleSpec;
import org.xbib.cql.SortSpec;
import org.xbib.cql.SortedQuery;
import org.xbib.cql.Term;
import org.xbib.cql.Visitor;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * This is a RPN (Type-1 query) generator for CQL queries.
 *
 * @see <a href="http://www.loc.gov/z3950/agency/markup/09.html">RPN</a>
 */
public final class CQLRPNGenerator implements Visitor {

    /**
     * Context map.
     */
    private final Map<String, Map<String, String>> contexts;

    private final Stack<AttributeElement> attributeElements;

    private final Stack<ASN1Any> result;

    private RPNQuery rpnQuery;

    private final boolean wordListSupported;

    public CQLRPNGenerator() {
        this(null, true);
    }

    public CQLRPNGenerator(Collection<AttributeElement> attributeElements, boolean wordListSupported) {
        this.attributeElements = new Stack<>();
        if (attributeElements != null) {
            this.attributeElements.addAll(attributeElements);
        }
        this.result = new Stack<>();
        this.contexts = new HashMap<>();
        addContext("default", ResourceBundle.getBundle("org.xbib.z3950.common.cql.default"));
        addContext("cql", ResourceBundle.getBundle("org.xbib.z3950.common.cql.cql"));
        addContext("rec", ResourceBundle.getBundle("org.xbib.z3950.common.cql.rec"));
        addContext("bib", ResourceBundle.getBundle("org.xbib.z3950.common.cql.bib-1"));
        addContext("dc", ResourceBundle.getBundle("org.xbib.z3950.common.cql.dc"));
        addContext("gbv", ResourceBundle.getBundle("org.xbib.z3950.common.cql.gbv"));
        this.wordListSupported = wordListSupported;
    }

    public void addContext(String context, ResourceBundle bundle) {
        addContext(context, bundle.keySet().stream()
                .map(k -> Map.entry(k, bundle.getString(k)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    public void addContext(String context, Map<String, String> map) {
        contexts.put(context, map);
    }

    public RPNQuery getQueryResult() {
        return rpnQuery;
    }

    @Override
    public void visit(SortedQuery node) {
        if (node.getSortSpec() != null) {
            node.getSortSpec().accept(this);
        }
        if (node.getQuery() != null) {
            node.getQuery().accept(this);
        }
        if (!result.isEmpty()) {
            this.rpnQuery = new RPNQuery();
            rpnQuery.rpn = (RPNStructure) result.pop();
            // Z39.50 BIB-1: urn:oid:1.2.840.10003.3.1
            rpnQuery.attributeSetId = new AttributeSetId();
            rpnQuery.attributeSetId.value = new ASN1ObjectIdentifier(new int[]{1, 2, 840, 10003, 3, 1});
        } else {
            throw new SyntaxException("unable to generate RPN from CQL");
        }
    }

    @Override
    public void visit(Query node) {
        if (node.getPrefixAssignments() != null) {
            for (PrefixAssignment assignment : node.getPrefixAssignments()) {
                assignment.accept(this);
            }
        }
        if (node.getQuery() != null) {
            node.getQuery().accept(this);
        }
        if (node.getScopedClause() != null) {
            node.getScopedClause().accept(this);
        }
    }

    @Override
    public void visit(SortSpec node) {
        if (node.getSingleSpec() != null) {
            node.getSingleSpec().accept(this);
        }
        if (node.getSortSpec() != null) {
            node.getSortSpec().accept(this);
        }
    }

    @Override
    public void visit(SingleSpec node) {
        if (node.getIndex() != null) {
            node.getIndex().accept(this);
        }
        if (node.getModifierList() != null) {
            node.getModifierList().accept(this);
        }
    }

    @Override
    public void visit(PrefixAssignment node) {
        node.getPrefix().accept(this);
        node.getURI().accept(this);
    }

    @Override
    public void visit(ScopedClause node) {
        if (node.getScopedClause() != null) {
            node.getScopedClause().accept(this);
        }
        node.getSearchClause().accept(this);
        if (node.getBooleanGroup() != null) {
            node.getBooleanGroup().accept(this);
            RPNStructure rpn = new RPNStructure();
            rpn.c_rpnRpnOp = new RPNStructureRpnRpnOp();
            rpn.c_rpnRpnOp.s_op = new Operator();
            BooleanOperator op = node.getBooleanGroup().getOperator();
            switch (op) {
                case AND -> rpn.c_rpnRpnOp.s_op.andOp = new ASN1Null();
                case OR -> rpn.c_rpnRpnOp.s_op.orOp = new ASN1Null();
                case NOT -> rpn.c_rpnRpnOp.s_op.andNotOp = new ASN1Null();
                default -> {
                    throw new UnsupportedOperationException("op: " + op);
                }
            }
            rpn.c_rpnRpnOp.s_rpn1 = (RPNStructure) result.pop();
            rpn.c_rpnRpnOp.s_rpn2 = (RPNStructure) result.pop();
            result.push(rpn);
        }
    }

    @Override
    public void visit(BooleanGroup node) {
        if (node.getModifierList() != null) {
            node.getModifierList().accept(this);
        }
    }

    @Override
    public void visit(SearchClause node) {
        if (node.getQuery() != null) {
            node.getQuery().accept(this);
        }
        if (node.getTerm() != null) {
            node.getTerm().accept(this);
        }
        if (node.getIndex() != null) {
            node.getIndex().accept(this);
        }
        if (node.getRelation() != null) {
            node.getRelation().accept(this);
        }
        Operand operand = new Operand();
        operand.attrTerm = new AttributesPlusTerm();
        operand.attrTerm.term = new org.xbib.z3950.common.v3.Term();
        ASN1Any any = result.pop();
        if (any instanceof ASN1OctetString) {
            operand.attrTerm.term.c_general = (ASN1OctetString)any;
        } else {
            operand.attrTerm.term.c_general = new ASN1OctetString(node.getTerm().getValue());
        }
        operand.attrTerm.attributes = new AttributeList();
        operand.attrTerm.attributes.value = attributeElements.stream()
                .filter(ae -> ae.attributeValue != null)
                .toArray(AttributeElement[]::new);
        RPNStructure rpn = new RPNStructure();
        rpn.c_op = operand;
        result.push(rpn);
    }

    @Override
    public void visit(Relation node) {
        if (node.getModifierList() != null) {
            node.getModifierList().accept(this);
        }
        switch (node.getComparitor()) {
            case LESS -> // 2=1
                push(attributeElements, createAttributeElement(2, 1));
            case LESS_EQUALS -> // 2=2
                push(attributeElements, createAttributeElement(2, 2));
            case EQUALS -> // 2=3
                push(attributeElements, createAttributeElement(2, 3));
            case GREATER_EQUALS -> // 2=4
                push(attributeElements, createAttributeElement(2, 4));
            case GREATER -> // 2=5
                push(attributeElements, createAttributeElement(2, 5));
            case NOT_EQUALS ->
                push(attributeElements, createAttributeElement(2, 6));
            case ALL, ANY -> { // 4=6 word list
                push(attributeElements, createAttributeElement(2, 3));
                if (wordListSupported) {
                    replace(attributeElements, createAttributeElement(4, 6));
                }
            }
            default -> {
            }
        }
    }

    @Override
    public void visit(Modifier node) {
        if (node.getTerm() != null) {
            node.getTerm().accept(this);
        }
        if (node.getName() != null) {
            node.getName().accept(this);
        }
    }

    @Override
    public void visit(ModifierList node) {
        for (Modifier modifier : node.getModifierList()) {
            modifier.accept(this);
        }
    }

    @Override
    public void visit(Term term) {
        // the value
        ASN1OctetString s = transformTerm(term);
        result.push(s);
    }

    @Override
    public void visit(Identifier node) {
    }

    @Override
    public void visit(SimpleName node) {
        ASN1OctetString s = new ASN1OctetString(node.getName());
        result.push(s);
    }

    @Override
    public void visit(Index index) {
        String context = index.getContext();
        if (context == null) {
            context = "default"; // default context
        }

        int attributeType = 1; // use attribute set: bib-1 = 1
        Integer attributeValue = getUseAttr(context, index.getName());
        if (attributeValue == null) {
            throw new SyntaxException("undefined attribute value for " + index.getName());
        }

        // date --> use year attribute
        if (attributeValue == 31) {
            replace(attributeElements, createAttributeElement(4, 5));
        }
        push(attributeElements, createAttributeElement(attributeType, attributeValue));
    }

    private Integer getUseAttr(String context, String attrName) {
        if (!contexts.containsKey(context)) {
            throw new SyntaxException("unknown use attribute '" + attrName + "' for context " + context);
        }
        String string = contexts.get(context).get(attrName);
        return string != null ? Integer.parseInt(string) : null;
    }

    private ASN1OctetString transformTerm(Term term) {

        String v = term.getValue();
        // let's derive basic attributes from the search term syntax

        // position attribute = 3
        int attributeType = 3;
        int attributeValue = 3; // any position = 3
        if (v.startsWith("^")) {
            attributeValue = 1; // first posiiton
            v = v.substring(1);
        }
        push(attributeElements, createAttributeElement(attributeType, attributeValue));

        // structure attribute = 4
        attributeType = 4;
        attributeValue = 2; // phrase = 1, word = 2, word list = 6
        if (v.startsWith("\"") && v.endsWith("\"")) {
            attributeValue = 1; // phrase
            v = v.substring(1, v.length()-1);
        }
        push(attributeElements, createAttributeElement(attributeType, attributeValue));

        // truncation attribute = 5
        attributeType = 5;
        attributeValue = 100; // do not truncate = 5
        if (v.endsWith("*")) {
            if (v.startsWith("*")) {
                attributeValue = 3; // Left and right truncation = 3
                v = v.substring(1, v.length() - 1);
            } else {
                attributeValue = 1; // Right truncation  = 1
                v = v.substring(0, v.length() - 1);
            }
        } else if (v.startsWith("*")) {
            attributeValue = 2; // Left truncation = 2
            v = v.substring(1);
        }
        push(attributeElements, createAttributeElement(attributeType, attributeValue));

        // completeness attribute = 6
        attributeType = 6;
        attributeValue = 1; // not complete = 1
        if (v.startsWith("^") && v.endsWith("$")) {
            attributeValue = 3; // complete = 3
            v = v.substring(1, v.length() - 1);
        }
        push(attributeElements, createAttributeElement(attributeType, attributeValue));

        return new ASN1OctetString(v);
    }

    private static AttributeElement createAttributeElement(Integer attributeType, Integer attributeValue) {
        if (attributeType != null && attributeValue != null) {
            AttributeElement ae = new AttributeElement();
            ae.attributeType = new ASN1Integer(attributeType);
            ae.attributeValue = new AttributeElementAttributeValue();
            ae.attributeValue.numeric = new ASN1Integer(attributeValue);
            return ae;
        } else {
            return null;
        }
    }


    private static void push(Stack<AttributeElement> stack, AttributeElement attributeElement) {
        if (attributeElement != null) {
            stack.push(attributeElement);
        }
    }

    private static void replace(Stack<AttributeElement> stack, AttributeElement attributeElement) {
        if (attributeElement != null) {
            int pos = stack.indexOf(attributeElement);
            if (pos >= 0) {
                stack.remove(pos);
            }
            stack.push(attributeElement);
        }
    }
}
