package org.xbib.z3950.client.jdk.test;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.xbib.z3950.client.jdk.JDKZClient;

class SWBClientTest {

    private static final Logger logger = Logger.getLogger(SWBClientTest.class.getName());

    @Test
    void testCQL() {
        //String query = "bib.identifierISSN = 00280836";
        String query = "bib.any all test";
        int from = 1;
        int size = 10;
        try (JDKZClient client = newZClient()) {
            int count = client.searchCQL(query, from, size, null,
                    (status, total, returned, elapsedMillis) ->
                            logger.log(Level.INFO, "total results = " + total),
                    record -> logger.log(Level.INFO, "record = " + record),
                    () -> logger.log(Level.INFO, "timeout"));
            logger.log(Level.INFO, "returned records = " + count);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Test
    void testPQF() {
        String query = "@attr 1=8 \"00280836\"";
        int from = 1;
        int size = 10;
        try (JDKZClient client = newZClient()) {
            int count = client.searchPQF(query, from, size, null,
                    (status, total, returned, elapsedMillis) ->
                            logger.log(Level.INFO, "status = " + status + " total results = " + total),
                    record -> logger.log(Level.INFO, "record = " + record.toString(Charset.forName(client.getEncoding()))),
                    () -> logger.log(Level.WARNING, "timeout"));
            logger.log(Level.INFO, "returned records = " + count);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private JDKZClient newZClient() {
        JDKZClient.Builder builder = JDKZClient.builder()
                .setHost("z3950.bsz-bw.de")
                .setPort(20210)
                .setTimeout(10000L)
                .setDatabases(Collections.singletonList("swb_fl"))
                .setElementSetName("xf")
                .setPreferredRecordSyntax("marc21")
                .setResultSetName("default")
                .setEncoding("UTF-8")
                .setFormat("Marc21")
                .setType("Bibliographic")
                .wordListSupported(false);
        return builder.build();
    }
}
