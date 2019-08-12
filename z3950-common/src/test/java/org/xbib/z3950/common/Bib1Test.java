package org.xbib.z3950.common;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 *
 */
class Bib1Test {

    private static final Logger logger = Logger.getLogger(Bib1Test.class.getName());

    @Test
    void testBibUse() {
        ResourceBundle bundle = ResourceBundle.getBundle("org.xbib.z3950.common.bib-1");
        ArrayList<String> values = new ArrayList<>();
        for (String key : bundle.keySet()) {
            values.add(bundle.getString(key) + "=" + key);
        }
        Collections.sort(values);
        for (Object v : values) {
            logger.info(v.toString());
        }
    }
}
