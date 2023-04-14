package org.xbib.z3950.common.operations;

import java.nio.charset.Charset;
import org.xbib.asn1.ASN1Boolean;
import org.xbib.asn1.ASN1GeneralString;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.io.BERReader;
import org.xbib.asn1.io.BERWriter;
import org.xbib.cql.CQLParser;
import org.xbib.z3950.common.Diagnostics;
import org.xbib.z3950.common.cql.CQLRPNGenerator;
import org.xbib.z3950.common.pqf.PQFParser;
import org.xbib.z3950.common.pqf.PQFRPNGenerator;
import org.xbib.z3950.common.v3.DatabaseName;
import org.xbib.z3950.common.v3.InternationalString;
import org.xbib.z3950.common.v3.PresentStatus;
import org.xbib.z3950.common.v3.Query;
import org.xbib.z3950.common.v3.RPNQuery;
import org.xbib.z3950.common.v3.SearchRequest;
import org.xbib.z3950.common.v3.SearchResponse;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Z39.50 Search operation.
 */
public class SearchOperation extends AbstractOperation<SearchResponse, SearchRequest> {

    private static final Logger logger = Logger.getLogger(SearchOperation.class.getName());

    private final String resultSetName;

    private final List<String> databases;

    private final String host;

    private int count;

    private boolean status;

    public SearchOperation(BERReader reader, BERWriter writer,
                           String resultSetName,
                           List<String> databases,
                           String host) {
        super(reader, writer);
        this.resultSetName = resultSetName;
        this.databases = databases;
        this.host = host;
        this.count = -1;
        this.status = false;
    }

    public boolean executePQF(String pqf, Charset charset) throws IOException {
        return execute(createRPNQueryFromPQF(pqf, charset));
    }

    public boolean executeCQL(String cql, boolean wordListSupported) throws IOException {
        return execute(createRPNQueryFromCQL(cql, wordListSupported));
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
            logger.log(Level.FINER, search.toString());
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
            } else {
                logger.log(Level.WARNING, "no search response returned for host " + host + " " + databases);
            }
        } catch (IOException e) {
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

    private RPNQuery createRPNQueryFromCQL(String query, boolean wordListSupported) {
        CQLRPNGenerator generator = new CQLRPNGenerator(null, wordListSupported);
        CQLParser parser = new CQLParser(query);
        parser.parse();
        parser.getCQLQuery().accept(generator);
        return generator.getQueryResult();
    }

    private RPNQuery createRPNQueryFromPQF(String query, Charset charset) {
        PQFRPNGenerator generator = new PQFRPNGenerator(charset);
        PQFParser parser = new PQFParser(new StringReader(query));
        parser.parse();
        parser.getResult().accept(generator);
        return generator.getResult();
    }
}
