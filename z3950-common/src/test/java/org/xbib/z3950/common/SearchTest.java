package org.xbib.z3950.common;

import org.junit.Test;
import org.xbib.z3950.common.exceptions.MessageSizeTooSmallException;
import org.xbib.z3950.common.exceptions.NoRecordsReturnedException;
import org.xbib.z3950.common.DefaultClient;

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
            DefaultClient client = DefaultClient.builder()
                    .setHost(host)
                    .setPort(port)
                    .setDatabases(Collections.singletonList(database))
                    .setPreferredRecordSyntax(preferredRecordSyntax)
                    .build();
            client.searchPQF(query, from, length,
                    (status, total, returned, elapsedMillis) -> logger.log(Level.INFO, "total records = " + total),
                    record -> logger.log(Level.INFO, "found record " + record));
            client.close();
        } catch (NoRecordsReturnedException | MessageSizeTooSmallException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
