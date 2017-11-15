package org.xbib.io.iso23950.pqf;

import org.junit.Test;
import org.xbib.io.iso23950.ParserTest;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class PQFParserTest extends ParserTest {

    private static final Logger logger = Logger.getLogger(PQFParserTest.class.getName());

    @Test
    public void testSucceed() throws SyntaxException, IOException {
        int ok = 0;
        int errors = 0;
        for (String q : readFromResource("org/xbib/io/iso23950/pqf/pqf-must-succeed")) {
            PQFParser parser = new PQFParser(new StringReader(q));
            try {
                parser.parse();
                ok++;
            } catch (Exception e) {
                logger.log(Level.SEVERE,"failed: " + q, e);
                errors++;
            }
        }
        assertEquals(errors, 0);
        assertEquals(ok, 17);
    }

}
