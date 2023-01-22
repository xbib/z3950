package org.xbib.z3950.api;

@FunctionalInterface
public interface RecordListener {

    void onRecord(Record record);
}
