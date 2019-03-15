package org.xbib.z3950.common.cql;

import org.junit.Test;
import org.xbib.cql.CQLParser;

/**
 *
 */
public class CQL2RPNTest {

    @Test
    public void testCQL2RPN() {
        String cql = "dc.title = Test";
        CQLParser parser = new CQLParser(cql);
        parser.parse();
        CQLRPNGenerator generator = new CQLRPNGenerator();
        parser.getCQLQuery().accept(generator);
        String q = generator.getQueryResult().toString();
        System.err.println("cql2rpn = " + q);
    }
}
