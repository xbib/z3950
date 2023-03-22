package org.xbib.z3950.common.pqf;

import org.junit.jupiter.api.Test;
import org.xbib.z3950.common.v3.RPNQuery;

import java.io.StringReader;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PQFTest {

    @Test
    void testParser() throws SyntaxException {
        String q = "@and @attr 1=4 linux @attr 1=2 sybex";
        PQFParser parser = new PQFParser(new StringReader(q));
        parser.parse();
        PQF pqf = parser.getResult();
        assertEquals("[PQF: attrset=null query=[Query: expr=[Expression: op=@and,query1=[Query: term=linux attrschema=null setname=null],query2=[Query: term=sybex attrschema=null setname=null]]]]", pqf.toString());
    }

    @Test
    void testRPN1() {
        String q = "@and @attr 1=4 linux @attr 1=2 sybex";
        assertEquals("{attributeSetId 1.2.840.10003.3.1, rpn {rpnRpnOp {rpn1 {op {attrTerm {attributes {{attributeType 1, attributeValue {numeric 4}}}, term {general \"linux\"}}}}, rpn2 {op {attrTerm {attributes {{attributeType 1, attributeValue {numeric 2}}}, term {general \"sybex\"}}}}, op {and null}}}}",
                createRPNQueryFromPQF(q).toString());
    }

    @Test
    void testRPN2() {
        String q = "@and @attr 1=4 @attr 2=3 \"linux\" @attr 1=2 sybex";
        assertEquals("{attributeSetId 1.2.840.10003.3.1, rpn {rpnRpnOp {rpn1 {op {attrTerm {attributes {{attributeType 2, attributeValue {numeric 3}}{attributeType 1, attributeValue {numeric 4}}}, term {general \"linux\"}}}}, rpn2 {op {attrTerm {attributes {{attributeType 1, attributeValue {numeric 2}}}, term {general \"sybex\"}}}}, op {and null}}}}",
                createRPNQueryFromPQF(q).toString());
    }

    @Test
    void testRPN3() {
        String q = "@attr 1=31 2023";
        assertEquals("{attributeSetId 1.2.840.10003.3.1, rpn {op {attrTerm {attributes {{attributeType 1, attributeValue {numeric 31}}}, term {general \"2023\"}}}}}",
                createRPNQueryFromPQF(q).toString());
    }

    private RPNQuery createRPNQueryFromPQF(String query) {
        PQFRPNGenerator generator = new PQFRPNGenerator();
        org.xbib.z3950.common.pqf.PQFParser parser = new org.xbib.z3950.common.pqf.PQFParser(new StringReader(query));
        parser.parse();
        parser.getResult().accept(generator);
        return generator.getResult();
    }
}
