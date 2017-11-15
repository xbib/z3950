package org.xbib.io.iso23950;

/**
 *
 */
@FunctionalInterface
public interface InitListener {

    void onInit(int version, String info);
}
