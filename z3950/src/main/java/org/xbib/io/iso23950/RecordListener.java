package org.xbib.io.iso23950;

/**
 *
 */
@FunctionalInterface
public interface RecordListener {

    void onRecord(Record record);
}
