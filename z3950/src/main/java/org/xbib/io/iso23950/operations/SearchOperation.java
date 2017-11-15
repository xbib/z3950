package org.xbib.io.iso23950.operations;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1Boolean;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1GeneralString;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Sequence;
import org.xbib.io.iso23950.ZClient;
import org.xbib.io.iso23950.v3.DatabaseName;
import org.xbib.io.iso23950.v3.InternationalString;
import org.xbib.io.iso23950.v3.OtherInformation1;
import org.xbib.io.iso23950.v3.PDU;
import org.xbib.io.iso23950.v3.PresentStatus;
import org.xbib.io.iso23950.v3.Query;
import org.xbib.io.iso23950.v3.RPNQuery;
import org.xbib.io.iso23950.v3.SearchRequest;
import org.xbib.io.iso23950.v3.SearchResponse;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for Z39.50 Search operation.
 */
public class SearchOperation {

    private int count = -1;

    private boolean status = false;

    private Map<ASN1Any, Integer> results = new HashMap<>();

    public boolean execute(ZClient client, RPNQuery rpn) throws IOException {
        try {
            SearchRequest search = new SearchRequest();
            search.s_query = new Query();
            search.s_query.c_type_1 = rpn;
            search.s_smallSetUpperBound = new ASN1Integer(0);
            search.s_largeSetLowerBound = new ASN1Integer(1);
            search.s_mediumSetPresentNumber = new ASN1Integer(0);
            search.s_replaceIndicator = new ASN1Boolean(true);
            search.s_resultSetName = new InternationalString();
            search.s_resultSetName.value = new ASN1GeneralString(client.getResultSetName());
            List<String> databases = client.getDatabases();
            DatabaseName dbs[] = new DatabaseName[databases.size()];
            for (int n = 0; n < databases.size(); n++) {
                dbs[n] = new DatabaseName();
                dbs[n].value = new InternationalString();
                dbs[n].value.value = new ASN1GeneralString(databases.get(n));
            }
            search.s_databaseNames = dbs;
            PDU pduRequest = new PDU();
            pduRequest.c_searchRequest = search;
            client.writePDU(pduRequest);
            PDU pduResponse = client.readPDU();
            SearchResponse response = pduResponse.c_searchResponse;
            count = response.s_resultCount.get();
            ASN1Boolean b = response.s_searchStatus;
            status = b != null && b.get();
            if (!status) {
                String message = "no message";
                if (response.s_records != null && response.s_records.c_nonSurrogateDiagnostic != null) {
                    try {
                        message = "ASN error, non-surrogate diagnostics: " +
                                response.s_records.c_nonSurrogateDiagnostic.berEncode();
                    } catch (ASN1Exception e) {
                        //
                    }
                }
                throw new IOException(client.getHost() + ": " + message);
            }
            PresentStatus presentStatus = response.s_presentStatus;
            if (presentStatus != null && presentStatus.value != null && presentStatus.value.get() == 5) {
                throw new IOException("present status is failure");
            }
            if (response.s_additionalSearchInfo != null && response.s_additionalSearchInfo.value[0] != null) {
                OtherInformation1 info = response.s_additionalSearchInfo.value[0];
                ASN1Sequence targetSeq = (ASN1Sequence) info.s_information.c_externallyDefinedInfo.getSingleASN1Type();
                ASN1Any[] targets = targetSeq.get();
                DatabaseName dbName;
                for (int i = 0; i < targets.length; i++) {
                    ASN1Sequence target = (ASN1Sequence) targets[i];
                    try {
                        ASN1Any[] details = target.get();
                        dbName = new DatabaseName(details[0].berEncode(), false);
                        if (!dbName.value.value.get().equalsIgnoreCase(databases.get(i))) {
                            String message = "database name listed in additional search info " +
                                    "doesn't match database name in names set.";
                            throw new IOException(client.getHost() + ": " + message);
                        }
                        ASN1Integer res = (ASN1Integer) details[1];
                        results.put(target, res.get());
                    } catch (ASN1Exception ex) {
                        // non-fatal String message = "Error in accessing additional search info.";
                        results.put(target, -1);
                    }
                }
            }
        } catch (SocketTimeoutException e) {
            throw new IOException(client.getHost() + ": timeout", e);
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

}
