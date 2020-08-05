package org.xbib.z3950.common.cql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.xbib.asn1.ASN1Integer;
import org.xbib.cql.CQLParser;
import org.xbib.z3950.common.v3.AttributeElement;
import org.xbib.z3950.common.v3.AttributeElementAttributeValue;
import java.util.Collections;

class CQL2RPNTest {

    @Test
    void testDublinCoreContext() {
        String cql = "dc.title = Test";
        CQLParser parser = new CQLParser(cql);
        parser.parse();
        CQLRPNGenerator generator = new CQLRPNGenerator();
        parser.getCQLQuery().accept(generator);
        String q = generator.getQueryResult().toString();
        assertEquals("{attributeSetId 1.2.840.10003.3.1, rpn {op {attrTerm {attributes {{attributeType 2, attributeValue {numeric 3}}{attributeType 4, attributeValue {numeric 2}}{attributeType 5, attributeValue {numeric 100}}{attributeType 1, attributeValue {numeric 4}}}, term {general \"Test\"}}}}}", q);
    }

    @Test
    void testPhrase() {
        String cql = "dc.title = \"a phrase\"";
        CQLParser parser = new CQLParser(cql);
        parser.parse();
        CQLRPNGenerator generator = new CQLRPNGenerator();
        parser.getCQLQuery().accept(generator);
        String q = generator.getQueryResult().toString();
        assertEquals("{attributeSetId 1.2.840.10003.3.1, rpn {op {attrTerm {attributes {{attributeType 2, attributeValue {numeric 3}}{attributeType 4, attributeValue {numeric 1}}{attributeType 5, attributeValue {numeric 100}}{attributeType 1, attributeValue {numeric 4}}}, term {general \"a phrase\"}}}}}", q);
    }

    @Test
    void testWithAttribute() {
        AttributeElement ae = new AttributeElement();
        ae.attributeType = new ASN1Integer(2);
        String cql = "dc.title = \"a phrase\"";
        CQLParser parser = new CQLParser(cql);
        parser.parse();
        CQLRPNGenerator generator = new CQLRPNGenerator(Collections.singleton(ae));
        parser.getCQLQuery().accept(generator);
        String q = generator.getQueryResult().toString();
        assertEquals("{attributeSetId 1.2.840.10003.3.1, rpn {op {attrTerm {attributes {{attributeType 2, attributeValue {numeric 3}}{attributeType 4, attributeValue {numeric 1}}{attributeType 5, attributeValue {numeric 100}}{attributeType 1, attributeValue {numeric 4}}}, term {general \"a phrase\"}}}}}", q);
    }
}
