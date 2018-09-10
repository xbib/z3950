package org.xbib.io.iso23950;

import org.xbib.asn1.BEREncoding;

/**
 *
 */
@FunctionalInterface
public interface ScanListener {

    void onScan(BEREncoding result);
}
