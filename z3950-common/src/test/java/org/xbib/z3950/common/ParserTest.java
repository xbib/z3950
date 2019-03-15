package org.xbib.z3950.common;

import org.junit.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 */
public abstract class ParserTest extends Assert {

    /**
     * Helper method for reading a text file with queries.
     *
     * @param path the path
     *
     * @return a string iterable
     *
     * @throws IOException if the text file can not be read
     */
    protected Iterable<String> readFromResource(String path)
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
