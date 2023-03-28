package org.xbib.z3950.client.jdk.test;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.xbib.z3950.client.jdk.JDKZClient;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * GBV test.
 */
class GBVZClientTest {

    private static final Logger logger = Logger.getLogger(GBVZClientTest.class.getName());

    @Test
    void testGBV() {
        String query = "@attr 1=4 linux";
        int from = 1;
        int size = 10;
        try (JDKZClient client = newZClient("GBV")) {
            logger.log(Level.INFO, "executing PQF " + query);
            int count = client.searchPQF(query, from, size, null,
                    (status, total, returned, elapsedMillis) ->
                            logger.log(Level.INFO, "total results = " + total + " millis = " + elapsedMillis),
                    record -> logger.log(Level.INFO, "record = " + record.toString(StandardCharsets.UTF_8)),
                    () -> logger.log(Level.WARNING, "timeout"));
            logger.log(Level.INFO, "record count = " + count);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Test
    @Disabled("unsupported search")
    void testControlNumberZDB() {
        String serviceName = "GBV";
        String query = "bib.controlNumberZDB = 1413423-8";
        int from = 1;
        int size = 2;
        try (JDKZClient client = newZClient(serviceName)) {
            logger.log(Level.INFO, "executing CQL " + query);
            int count = client.searchCQL(query, from, size, null,
                    (status, total, returned, elapsedMillis) ->
                            logger.log(Level.INFO, serviceName + " total results = " + total),
                    record -> logger.log(Level.INFO, "record = " + record),
                    () -> logger.log(Level.WARNING, "timeout"));
            logger.log(Level.INFO, "returned records = " + count);
            assertEquals(1, count);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }


    private JDKZClient newZClient(String name) throws IOException {
        return newZClient(getProperties(name));
    }

    private Properties getProperties(String name) throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getResourceAsStream(name + ".properties")) {
            properties.load(inputStream);
        }
        return properties;
    }

    private static JDKZClient newZClient(Properties properties) {
        JDKZClient.Builder builder = JDKZClient.builder();
        if (properties.containsKey("host")) {
            builder.setHost(properties.getProperty("host"));
        }
        if (properties.containsKey("port")) {
            builder.setPort(Integer.parseInt(properties.getProperty("port")));
        }
        if (properties.containsKey("user")) {
            builder.setUser(properties.getProperty("user"));
        }
        if (properties.containsKey("pass")) {
            builder.setPass(properties.getProperty("pass"));
        }
        if (properties.containsKey("database")) {
            builder.setDatabases(Collections.singletonList(properties.getProperty("database")));
        }
        if (properties.containsKey("elementsetname")) {
            builder.setElementSetName(properties.getProperty("elementsetname"));
        }
        if (properties.containsKey("preferredrecordsyntax")) {
            builder.setPreferredRecordSyntax(properties.getProperty("preferredrecordsyntax"));
        }
        if (properties.containsKey("resultsetname")) {
            builder.setResultSetName(properties.getProperty("resultsetname"));
        }
        if (properties.containsKey("encoding")) {
            builder.setEncoding(properties.getProperty("encoding"));
        }
        if (properties.containsKey("format")) {
            builder.setFormat(properties.getProperty("format"));
        }
        if (properties.containsKey("type")) {
            builder.setType(properties.getProperty("type"));
        }
        return builder.build();
    }

}
