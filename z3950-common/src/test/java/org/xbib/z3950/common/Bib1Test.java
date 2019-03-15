package org.xbib.z3950.common;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 *
 */
public class Bib1Test {

    @Test
    public void testBibUse() {
        ResourceBundle bundle = ResourceBundle.getBundle("org.xbib.io.iso23950.bib-1");
        ArrayList<String> values = new ArrayList<>();
        for (String key : bundle.keySet()) {
            values.add(bundle.getString(key) + "=" + key);
        }
        Collections.sort(values);
        for (Object v : values) {
            //logger.info(v.toString());
        }
    }
}
