package org.xbib.z3950.groovy

import groovy.util.logging.Log
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.xbib.z3950.client.jdk.JDKZClient

import java.util.logging.Level

@Log
@Disabled("internal test")
class LVITest {

    @Test
    void testLVI() {
        String host = "localhost"
        int port = 2100
        String database = "lvi"
        String query = "@attr 1=4 linux"
        String preferredRecordSyntax = "xml"
        int from = 1
        int size = 1
        JDKZClient client = JDKZClient.builder()
                .setHost(host)
                .setPort(port)
                .setDatabases(Collections.singletonList(database))
                .setPreferredRecordSyntax(preferredRecordSyntax)
                .build()
        client.withCloseable {cl ->
            cl.searchPQF(query, from, size,
                    (status, total, returned, elapsedMillis) -> log.log(Level.INFO, "total records = " + total),
                    record -> log.log(Level.INFO, "found record " + record),
                    () -> log.log(Level.WARNING, "timeout"))
        }
    }
}
