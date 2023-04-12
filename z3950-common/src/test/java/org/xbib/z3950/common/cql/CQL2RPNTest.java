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
        assertEquals("{attributeSetId 1.2.840.10003.3.1, rpn {op {attrTerm {attributes {{attributeType 3, attributeValue {numeric 3}}{attributeType 4, attributeValue {numeric 2}}{attributeType 5, attributeValue {numeric 100}}{attributeType 6, attributeValue {numeric 1}}{attributeType 1, attributeValue {numeric 4}}{attributeType 2, attributeValue {numeric 3}}}, term {general \"Test\"}}}}}", q);
    }

    @Test
    void testPhraseWithDoubleQuotes() {
        String cql = "dc.title = \"a phrase\"";
        CQLParser parser = new CQLParser(cql);
        parser.parse();
        CQLRPNGenerator generator = new CQLRPNGenerator();
        parser.getCQLQuery().accept(generator);
        String q = generator.getQueryResult().toString();
        assertEquals("{attributeSetId 1.2.840.10003.3.1, rpn {op {attrTerm {attributes {{attributeType 3, attributeValue {numeric 3}}{attributeType 4, attributeValue {numeric 1}}{attributeType 5, attributeValue {numeric 100}}{attributeType 6, attributeValue {numeric 1}}{attributeType 1, attributeValue {numeric 4}}{attributeType 2, attributeValue {numeric 3}}}, term {general \"a phrase\"}}}}}", q);
    }

    @Test
    void testWithCustomAttribute() {
        AttributeElement ae = new AttributeElement();
        ae.attributeType = new ASN1Integer(7);
        ae.attributeValue = new AttributeElementAttributeValue();
        ae.attributeValue.numeric = new ASN1Integer(4);
        String cql = "dc.title = \"a phrase\"";
        CQLParser parser = new CQLParser(cql);
        parser.parse();
        CQLRPNGenerator generator = new CQLRPNGenerator(Collections.singleton(ae));
        parser.getCQLQuery().accept(generator);
        String q = generator.getQueryResult().toString();
        assertEquals("{attributeSetId 1.2.840.10003.3.1, rpn {op {attrTerm {attributes {{attributeType 7, attributeValue {numeric 4}}{attributeType 3, attributeValue {numeric 3}}{attributeType 4, attributeValue {numeric 1}}{attributeType 5, attributeValue {numeric 100}}{attributeType 6, attributeValue {numeric 1}}{attributeType 1, attributeValue {numeric 4}}{attributeType 2, attributeValue {numeric 3}}}, term {general \"a phrase\"}}}}}", q);
    }

    @Test
    void testRecContext() {
        String cql = "rec.id = 123";
        CQLParser parser = new CQLParser(cql);
        parser.parse();
        CQLRPNGenerator generator = new CQLRPNGenerator();
        parser.getCQLQuery().accept(generator);
        String q = generator.getQueryResult().toString();
        assertEquals("{attributeSetId 1.2.840.10003.3.1, rpn {op {attrTerm {attributes {{attributeType 3, attributeValue {numeric 3}}{attributeType 4, attributeValue {numeric 2}}{attributeType 5, attributeValue {numeric 100}}{attributeType 6, attributeValue {numeric 1}}{attributeType 1, attributeValue {numeric 12}}{attributeType 2, attributeValue {numeric 3}}}, term {general \"123\"}}}}}", q);
    }

    @Test
    void testAll() {
        String cql = "bib.any all \"zeitschrift umweltrecht\"";
        CQLParser parser = new CQLParser(cql);
        parser.parse();
        CQLRPNGenerator generator = new CQLRPNGenerator();
        parser.getCQLQuery().accept(generator);
        String q = generator.getQueryResult().toString();
        assertEquals("{attributeSetId 1.2.840.10003.3.1, rpn {op {attrTerm {attributes {{attributeType 3, attributeValue {numeric 3}}{attributeType 5, attributeValue {numeric 100}}{attributeType 6, attributeValue {numeric 1}}{attributeType 1, attributeValue {numeric 1016}}{attributeType 2, attributeValue {numeric 3}}{attributeType 4, attributeValue {numeric 6}}}, term {general \"zeitschrift umweltrecht\"}}}}}", q);
    }

    @Test
    void testDate() {
        String cql = "bib.datePublication = 2023";
        CQLParser parser = new CQLParser(cql);
        parser.parse();
        CQLRPNGenerator generator = new CQLRPNGenerator();
        parser.getCQLQuery().accept(generator);
        String q = generator.getQueryResult().toString();
        assertEquals("{attributeSetId 1.2.840.10003.3.1, rpn {op {attrTerm {attributes {{attributeType 3, attributeValue {numeric 3}}{attributeType 5, attributeValue {numeric 100}}{attributeType 6, attributeValue {numeric 1}}{attributeType 4, attributeValue {numeric 5}}{attributeType 1, attributeValue {numeric 31}}{attributeType 2, attributeValue {numeric 3}}}, term {general \"2023\"}}}}}", q);
    }
}
