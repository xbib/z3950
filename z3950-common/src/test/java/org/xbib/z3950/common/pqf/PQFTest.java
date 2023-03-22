package org.xbib.z3950.common.pqf;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.xbib.z3950.common.v3.RPNQuery;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

class PQFTest {

    private static final Logger logger = Logger.getLogger(PQFTest.class.getName());

    @Test
    @Disabled
    void testParser() throws SyntaxException {
        String q = "@and @attr 1=4 linux @attr 1=2 sybex";
        PQFParser parser = new PQFParser(new StringReader(q));
        parser.parse();
        PQF pqf = parser.getResult();
        logger.log(Level.INFO, "pqf = " + pqf.toString());
    }


    @Test
    void testRPN() {
        String q = "@and @attr 1=4 linux @attr 1=2 sybex";
        logger.log(Level.INFO, "rpn = " + createRPNQueryFromPQF(q));
    }

    private RPNQuery createRPNQueryFromPQF(String query) {
        PQFRPNGenerator generator = new PQFRPNGenerator();
        org.xbib.z3950.common.pqf.PQFParser parser = new org.xbib.z3950.common.pqf.PQFParser(new StringReader(query));
        parser.parse();
        parser.getResult().accept(generator);
        return generator.getResult();
    }
}
