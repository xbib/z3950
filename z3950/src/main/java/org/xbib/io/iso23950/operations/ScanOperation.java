package org.xbib.io.iso23950.operations;

import org.xbib.asn1.ASN1GeneralString;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1ObjectIdentifier;
import org.xbib.asn1.io.BERReader;
import org.xbib.asn1.io.BERWriter;
import org.xbib.io.iso23950.ScanListener;
import org.xbib.io.iso23950.pqf.PQFParser;
import org.xbib.io.iso23950.pqf.PQFRPNGenerator;
import org.xbib.io.iso23950.v3.AttributeSetId;
import org.xbib.io.iso23950.v3.AttributesPlusTerm;
import org.xbib.io.iso23950.v3.DatabaseName;
import org.xbib.io.iso23950.v3.InternationalString;
import org.xbib.io.iso23950.v3.RPNQuery;
import org.xbib.io.iso23950.v3.ScanRequest;
import org.xbib.io.iso23950.v3.ScanResponse;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class ScanOperation extends AbstractOperation<ScanResponse, ScanRequest> {

    private final List<String> databases;

    public ScanOperation(BERReader reader, BERWriter writer,
                         List<String> databases) {
        super(reader, writer);
        this.databases = databases;
    }

    public void executePQF(int nTerms, int step, int position, String pqf, ScanListener listener) throws IOException {
        execute(nTerms, step, position, createRPNQueryFromPQF(pqf).rpn.c_op.attrTerm, listener);
    }

    public void execute(int nTerms, int step, int position, AttributesPlusTerm term, ScanListener listener) throws IOException {
        ScanRequest scanRequest = new ScanRequest();
        scanRequest.attributeSet = new AttributeSetId();
        // Z39.50 BIB-1: urn:oid:1.2.840.10003.3.1
        scanRequest.attributeSet.value =
                new ASN1ObjectIdentifier(new int[]{1, 2, 840, 10003, 3, 1});
        DatabaseName[] databaseNames = new DatabaseName[databases.size()];
        for (int n = 0; n < databases.size(); n++) {
            databaseNames[n] = new DatabaseName();
            databaseNames[n].value = new InternationalString();
            databaseNames[n].value.value = new ASN1GeneralString(databases.get(n));
        }
        scanRequest.databaseNames = databaseNames;
        scanRequest.numberOfTermsRequested = new ASN1Integer(nTerms);
        scanRequest.stepSize = new ASN1Integer(step);
        scanRequest.preferredPositionInResponse = new ASN1Integer(position);
        scanRequest.termListAndStartPoint = term;
        write(scanRequest);
        ScanResponse scanResponse = read();
        if (scanResponse != null) {
            if (scanResponse.scanStatus.get() == 0) {
                for (int n = 0; n < scanResponse.entries.s_entries.length; n++) {
                    if (listener != null) {
                        listener.onScan(scanResponse.entries.s_entries[n].cTermInfo.berEncode());
                    }
                }
            }
        }
    }

    private RPNQuery createRPNQueryFromPQF(String query) {
        PQFRPNGenerator generator = new PQFRPNGenerator();
        PQFParser parser = new PQFParser(new StringReader(query));
        parser.parse();
        parser.getResult().accept(generator);
        return generator.getResult();
    }

}
