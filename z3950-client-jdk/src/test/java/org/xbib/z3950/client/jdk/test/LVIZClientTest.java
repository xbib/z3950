package org.xbib.z3950.client.jdk.test;

import org.junit.jupiter.api.Test;
import org.xbib.z3950.client.jdk.JDKZClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

class LVIZClientTest {

    private static final Logger logger = Logger.getLogger(LVIZClientTest.class.getName());

    @Test
    void testLVI() {
        String query = "@attr 1=4 linux";
        int from = 1;
        int size = 10;
        try (JDKZClient client = newZClient()) {
            logger.log(Level.INFO, "executing PQF " + query);
            int count = client.searchPQF(query, from, size,
                    (status, total, returned, elapsedMillis) ->
                            logger.log(Level.INFO, "total results = " + total + " millis = " + elapsedMillis),
                    record -> logger.log(Level.INFO, "record = " + record.toString(StandardCharsets.UTF_8)),
                    () -> logger.log(Level.WARNING, "timeout"));
            logger.log(Level.INFO, "record count = " + count);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private JDKZClient newZClient() {
        JDKZClient.Builder builder = JDKZClient.builder();
        builder.setHost("sru.hbz-nrw.de");
        builder.setPort(210);
        builder.setDatabases(Collections.singletonList("LVI"));
        builder.setElementSetName(null);
        builder.setPreferredRecordSyntax("xml");
        builder.setResultSetName("default");
        builder.setEncoding("utf-8");
        builder.setFormat("Marc21");
        builder.setType("Bibliographic");
        return builder.build();
    }
}
