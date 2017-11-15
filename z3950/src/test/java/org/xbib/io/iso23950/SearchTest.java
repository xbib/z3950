package org.xbib.io.iso23950;

import org.junit.Test;
import org.xbib.io.iso23950.exceptions.MessageSizeTooSmallException;
import org.xbib.io.iso23950.exceptions.NoRecordsReturnedException;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class SearchTest {

    private static final Logger logger = Logger.getLogger(SearchTest.class.getName());

    @Test
    public void testCopac() throws Exception {
        String host = "z3950.copac.ac.uk";
        int port = 210;
        String database = "COPAC";
        String query = "@attr 1=1 smith";
        String preferredRecordSyntax = "1.2.840.10003.5.109.10"; // xml // "1.2.840.10003.5.10"; // MARC
        int from = 1;
        int length = 1;
        try {
            ZClient client = ZClient.builder()
                    .setHost(host)
                    .setPort(port)
                    .setDatabases(Collections.singletonList(database))
                    .setPreferredRecordSyntax(preferredRecordSyntax)
                    .build();
            client.executePQF(query, from, length,
                    (status, recordCount, elapsedMillis) -> logger.log(Level.INFO, "records = " + recordCount),
                    record -> logger.log(Level.INFO, "found record " + record));
            client.close();
        } catch (NoRecordsReturnedException | MessageSizeTooSmallException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
