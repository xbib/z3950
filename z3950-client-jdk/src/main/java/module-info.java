module org.xbib.z3950lib.client.jdk {
    exports org.xbib.z3950.client.jdk;
    requires transitive org.xbib.z3950lib.client.api;
    requires java.logging;
}