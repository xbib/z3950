package org.xbib.z3950.api;

@FunctionalInterface
public interface InitListener {

    void onInit(int version, String info);
}
