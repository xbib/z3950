package org.xbib.z3950.api;

/**
 * Client provider.
 * @param <C> the client type parameter
 */
public interface ClientProvider<C extends Client> {

    C getClient();
}
