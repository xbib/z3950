package org.xbib.z3950.groovy

import groovy.util.logging.Log
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.xbib.marc.MarcRecord
import org.xbib.z3950.client.jdk.JDKZClient

import java.nio.charset.StandardCharsets
import java.util.logging.Level

@Log
class LVITest {

    @Test
    void testLVI() {
        String host = "localhost"
        int port = 1210
        String database = "lvi"
        //String query = "@attr 1=1 Schmidt" // OK!
        //String query = "@attr 1=1 \"Schmidt, Heiner\"" // OK!
        //String query = "@attr 1=2 test" // unsupported?
        //String query = "@attr 1=3 test" // unsupported?
        //String query = "@attr 1=4 linux"
        //String query = "@attr 1=4 @attr 2=3 @attr 3=3 @attr 4=1 @attr 5=100 @attr 6=1 \"linux journal\"" // OK!
        //String query = "@attr 1=7 \"0136142230\"" // ISBN OK!
        //String query = "@attr 1=8 \"2251-6204\"" // ISSN OK!
        //String query = "@attr 1=8 \"22516204\"" // ISSN OK!
        //String query = "@attr 1=12 \"1016677359\"" // record ID OK!
        //String query = "@attr 1=12 \"(DE-101)1016677359\"" // record ID plus prefix OK!
        //String query = "@attr 1=12 \"(DE-600)2635378-7\"" // ZDB ID plus prefix NOT OK!
        //String query = "@attr 1=1016 \"2020\"" // any OK!
        String query = "@attr 1=1052 12-7" // ZDB-ID OK!
        String preferredRecordSyntax = "marc21"
        int from = 1
        int size = 1
        JDKZClient client = JDKZClient.builder()
                .setHost(host)
                .setPort(port)
                .setDatabases(Collections.singletonList(database))
                .setPreferredRecordSyntax(preferredRecordSyntax)
                .setElementSetName("F")
                .build()
        client.withCloseable {cl ->
            cl.searchPQF(query, from, size,
                    (status, total, returned, elapsedMillis) -> log.log(Level.INFO, "total records = " + total),
                    record -> dump(MarcRecord.from(record.asStream(), StandardCharsets.UTF_8)),
                    () -> log.log(Level.WARNING, "timeout"))
        }
    }

    static void dump(Iterable<MarcRecord> iterable) {
        iterable.forEach {log.log(Level.INFO, it.toString()) }
    }
}
