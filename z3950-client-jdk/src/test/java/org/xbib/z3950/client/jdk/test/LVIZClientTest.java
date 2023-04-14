package org.xbib.z3950.client.jdk.test;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.xbib.z3950.client.jdk.JDKZClient;
import org.xbib.z3950.common.operations.SortOperation;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class LVIZClientTest {

    private static final Logger logger = Logger.getLogger(LVIZClientTest.class.getName());

    @Disabled
    @Test
    void testLVI() {
        String query = "@attr 1=4 @attr 4=6 \"Köln strafrecht\"";
        int offset = 1;
        int size = 10;
        try (JDKZClient client = newZClient()) {
            List<SortOperation.SortParameter> sort = List.of(SortOperation.SortParameter.of("title")
                    .setAscending(true));
            logger.log(Level.INFO, "executing PQF " + query);
            int count = client.searchPQF(query, offset, size, sort,
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
        JDKZClient.Builder builder = JDKZClient.builder()
                .setHost("sru.hbz-nrw.de")
                .setPort(210)
                .setTimeout(30000L)
                .setDatabases(Collections.singletonList("LVI"))
                .setElementSetName(null)
                .setPreferredRecordSyntax("xml")
                .setResultSetName("default")
                .setEncoding("UTF-8")
                .setFormat("Marc21")
                .setType("Bibliographic");
        return builder.build();
    }
}
