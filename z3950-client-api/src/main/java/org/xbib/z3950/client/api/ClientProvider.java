package org.xbib.z3950.client.api;

/**
 * Client provider.
 * @param <C> the client type parameter
 */
public interface ClientProvider<C extends Client> {

    C getClient();
}
