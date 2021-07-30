package org.xbib.z3950.common.operations;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1Boolean;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1GeneralString;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.asn1.io.BERReader;
import org.xbib.asn1.io.BERWriter;
import org.xbib.cql.CQLParser;
import org.xbib.z3950.common.Diagnostics;
import org.xbib.z3950.common.cql.CQLRPNGenerator;
import org.xbib.z3950.common.pqf.PQFParser;
import org.xbib.z3950.common.pqf.PQFRPNGenerator;
import org.xbib.z3950.common.v3.DatabaseName;
import org.xbib.z3950.common.v3.InternationalString;
import org.xbib.z3950.common.v3.OtherInformation1;
import org.xbib.z3950.common.v3.PresentStatus;
import org.xbib.z3950.common.v3.Query;
import org.xbib.z3950.common.v3.RPNQuery;
import org.xbib.z3950.common.v3.SearchRequest;
import org.xbib.z3950.common.v3.SearchResponse;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base class for Z39.50 Search operation.
 */
public class SearchOperation extends AbstractOperation<SearchResponse, SearchRequest> {

    private static final Logger logger = Logger.getLogger(SearchOperation.class.getName());

    private int count = -1;

    private boolean status = false;

    private final Map<ASN1Any, Integer> results;

    private final String resultSetName;

    private final List<String> databases;

    private final String host;

    public SearchOperation(BERReader reader, BERWriter writer,
                           String resultSetName,
                           List<String> databases,
                           String host) {
        super(reader, writer);
        this.resultSetName = resultSetName;
        this.databases = databases;
        this.host = host;
        this.results = new HashMap<>();
    }

    public boolean executePQF(String pqf) throws IOException {
        return execute(createRPNQueryFromPQF(pqf));
    }

    public boolean executeCQL(String cql) throws IOException {
        return execute(createRPNQueryFromCQL(cql));
    }

    public boolean execute(RPNQuery rpn) throws IOException {
        try {
            SearchRequest search = new SearchRequest();
            search.query = new Query();
            search.query.c_type_1 = rpn;
            search.smallSetUpperBound = new ASN1Integer(0);
            search.largeSetLowerBound = new ASN1Integer(1);
            search.mediumSetPresentNumber = new ASN1Integer(0);
            search.replaceIndicator = new ASN1Boolean(true);
            search.resultSetName = new InternationalString();
            search.resultSetName.value = new ASN1GeneralString(resultSetName);
            DatabaseName[] dbs = new DatabaseName[databases.size()];
            for (int n = 0; n < databases.size(); n++) {
                dbs[n] = new DatabaseName();
                dbs[n].value = new InternationalString();
                dbs[n].value.value = new ASN1GeneralString(databases.get(n));
            }
            search.databaseNames = dbs;
            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, search.toString());
            }
            write(search);
            SearchResponse response = read();
            if (response != null) {
                if (response.resultCount != null) {
                    count = response.resultCount.get();
                }
                ASN1Boolean b = response.s_searchStatus;
                status = b != null && b.get();
                if (!status) {
                    if (response.s_records != null && response.s_records.c_nonSurrogateDiagnostic != null) {
                        int code = response.s_records.c_nonSurrogateDiagnostic.condition.get();
                        String addInfo = response.s_records.c_nonSurrogateDiagnostic.addinfo.v2Addinfo.get();
                        throw new Diagnostics(code, addInfo);
                    }
                    throw new IOException(host + ": error, without diagnostic");
                }
                PresentStatus presentStatus = response.s_presentStatus;
                if (presentStatus != null && presentStatus.value != null && presentStatus.value.get() == 5) {
                    throw new IOException("present status is failure");
                }
                if (response.s_additionalSearchInfo != null && response.s_additionalSearchInfo.value[0] != null) {
                    OtherInformation1 info = response.s_additionalSearchInfo.value[0];
                    ASN1Sequence targetSeq = (ASN1Sequence) info.information.c_externallyDefinedInfo.getSingleASN1Type();
                    ASN1Any[] targets = targetSeq.get();
                    DatabaseName dbName;
                    for (int i = 0; i < targets.length; i++) {
                        ASN1Sequence target = (ASN1Sequence) targets[i];
                        try {
                            ASN1Any[] details = target.get();
                            dbName = new DatabaseName(details[0].berEncode(), false);
                            if (!dbName.value.value.get().equalsIgnoreCase(databases.get(i))) {
                                String message = "database name listed in additional search info " +
                                        "doesn't match database name in names set";
                                throw new IOException(host + ": " + message);
                            }
                            ASN1Integer res = (ASN1Integer) details[1];
                            results.put(target, res.get());
                        } catch (ASN1Exception ex) {
                            logger.log(Level.WARNING, ex.getMessage(), ex);
                            // non-fatal, e.g. String message = "Error in accessing additional search info.";
                            results.put(target, -1);
                        }
                    }
                }
            } else {
                logger.log(Level.WARNING, "no search response returned for host " + host + " " + databases);
            }
        } catch (IOException e) {
            // add host in IOException message
            throw new IOException(host + ": " + e.getMessage(), e);
        }
        return status;
    }

    public int getCount() {
        return count;
    }

    public boolean isSuccess() {
        return status;
    }

    public Map<ASN1Any, Integer> getResults() {
        return results;
    }

    private RPNQuery createRPNQueryFromCQL(String query) {
        CQLRPNGenerator generator = new CQLRPNGenerator();
        CQLParser parser = new CQLParser(query);
        parser.parse();
        parser.getCQLQuery().accept(generator);
        return generator.getQueryResult();
    }

    private RPNQuery createRPNQueryFromPQF(String query) {
        PQFRPNGenerator generator = new PQFRPNGenerator();
        PQFParser parser = new PQFParser(new StringReader(query));
        parser.parse();
        parser.getResult().accept(generator);
        return generator.getResult();
    }
}
