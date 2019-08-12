package org.xbib.z3950.common.cql;

import org.junit.jupiter.api.Test;
import org.xbib.cql.CQLParser;

/**
 *
 */
class CQL2RPNTest {

    @Test
    void testCQL2RPN() {
        String cql = "dc.title = Test";
        CQLParser parser = new CQLParser(cql);
        parser.parse();
        CQLRPNGenerator generator = new CQLRPNGenerator();
        parser.getCQLQuery().accept(generator);
        String q = generator.getQueryResult().toString();
        System.err.println("cql2rpn = " + q);
    }
}
