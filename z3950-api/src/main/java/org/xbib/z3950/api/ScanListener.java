package org.xbib.z3950.api;

import org.xbib.asn1.BEREncoding;

@FunctionalInterface
public interface ScanListener {

    void onScan(BEREncoding result);
}
