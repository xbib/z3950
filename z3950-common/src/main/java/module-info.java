module org.xbib.z3950lib.common {
    exports org.xbib.z3950.common;
    exports org.xbib.z3950.common.cql;
    exports org.xbib.z3950.common.exceptions;
    exports org.xbib.z3950.common.operations;
    exports org.xbib.z3950.common.pqf;
    exports org.xbib.z3950.common.v3;
    requires org.xbib.z3950lib.api;
    requires org.xbib.z3950lib.asn;
    requires org.xbib.cql;
    requires java.logging;
}