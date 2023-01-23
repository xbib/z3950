package org.xbib.z3950.common.pqf;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 */
class PQFParserTest {

    private static final Logger logger = Logger.getLogger(PQFParserTest.class.getName());

    @Test
    void testSucceed() throws SyntaxException, IOException {
        int ok = 0;
        int errors = 0;
        for (String q : readFromResource("pqf-must-succeed")) {
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
        assertEquals(ok, 19);
    }

    /**
     * Helper method for reading a text file with queries.
     *
     * @param path the path
     *
     * @return a string iterable
     *
     * @throws IOException if the text file can not be read
     */
    private Iterable<String> readFromResource(String path)
            throws IOException {
        final ArrayList<String> lines = new ArrayList<>();
        InputStream in = getClass().getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.length() > 0) {
                lines.add(line);
            }
        }
        br.close();
        return lines;
    }
}
