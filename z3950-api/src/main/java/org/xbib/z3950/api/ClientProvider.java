package org.xbib.z3950.api;

public interface ClientProvider<C extends Client> {

    C getClient();
}
