package org.xbib.z3950.groovy

import groovy.util.logging.Log
import org.junit.jupiter.api.Test
import org.xbib.z3950.client.jdk.JDKZClient

import java.util.logging.Level

@Log
class COPACTest {

    @Test
    void testCOPAC() {
        String host = "z3950.copac.ac.uk"
        int port = 210
        String database = "COPAC"
        String query = "@attr 1=1 smith"
        // "1.2.840.10003.5.10"; // MARC
        String preferredRecordSyntax = "1.2.840.10003.5.109.10" // xml
        int from = 1
        int length = 1
        JDKZClient client = JDKZClient.builder()
                .setHost(host)
                .setPort(port)
                .setDatabases(Collections.singletonList(database))
                .setPreferredRecordSyntax(preferredRecordSyntax)
                .build()
        client.withCloseable {cl ->
            cl.searchPQF(query, from, length,
                    (status, total, returned, elapsedMillis) -> log.log(Level.INFO, "total records = " + total),
                    record -> log.log(Level.INFO, "found record " + record), // MODS
                    () -> log.log(Level.WARNING, "timeout"))
        }
    }
}
