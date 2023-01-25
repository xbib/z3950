package org.xbib.sru.client.jdk.test;

import org.junit.jupiter.api.Test;
import org.xbib.marc.Marc;
import org.xbib.marc.MarcRecord;
import org.xbib.marc.MarcRecordIterator;
import org.xbib.sru.client.jdk.SRUClient;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LVITest {

    private static final Logger logger = Logger.getLogger(LVITest.class.getName());

    @Test
    public void testLVI() throws IOException, InterruptedException {
        SRUClient client = SRUClient.builder()
                .setBaseURL("https://sru.hbz-nrw.de/lvi")
                .setRecordSchema("marcxml")
                .build();
        client.searchRetrieve("bib.personalName = \"Smith\"",
                1,
                10,
                this::dumpRecords);
    }

    private void dumpRecords(InputStream inputStream) {
        MarcRecordIterator iterator = Marc.builder()
                .setInputStream(inputStream)
                .setCharset(StandardCharsets.UTF_8)
                .xmlRecordIterator();
        while (iterator.hasNext()) {
            MarcRecord marcRecord = iterator.next();
            logger.log(Level.INFO, marcRecord.toString());
        }
        // total number available after records are iterated, SRU numberOfRecords element may be located at bottom.
        logger.log(Level.INFO, "total number of records = " + iterator.getTotalNumberOfRecords());
    }
}
