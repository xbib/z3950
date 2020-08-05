module org.xbib.z3950lib.client.netty {
    exports org.xbib.z3950.client.netty;
    requires transitive org.xbib.z3950lib.client.api;
    requires io.netty.buffer;
    requires io.netty.common;
    requires io.netty.handler;
    requires io.netty.transport;
    requires java.logging;
}
