package org.xbib.z3950.common.operations;

import org.xbib.asn1.ASN1Any;
import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.BEREncoding;
import org.xbib.asn1.io.BERReader;
import org.xbib.asn1.io.BERWriter;
import org.xbib.z3950.common.v3.Close;
import org.xbib.z3950.common.v3.InitializeRequest;
import org.xbib.z3950.common.v3.InitializeResponse;
import org.xbib.z3950.common.v3.PresentRequest;
import org.xbib.z3950.common.v3.PresentResponse;
import org.xbib.z3950.common.v3.ScanRequest;
import org.xbib.z3950.common.v3.ScanResponse;
import org.xbib.z3950.common.v3.SearchRequest;
import org.xbib.z3950.common.v3.SearchResponse;

import java.io.IOException;

/**
 * Class for representing a <code>PDU</code> from <code>Z39-50-APDU-1995</code>.
 * <pre>
 * PDU ::=
 * CHOICE {
 *   initRequest [20] IMPLICIT InitializeRequest
 *   initResponse [21] IMPLICIT InitializeResponse
 *   searchRequest [22] IMPLICIT SearchRequest
 *   searchResponse [23] IMPLICIT SearchResponse
 *   presentRequest [24] IMPLICIT PresentRequest
 *   presentResponse [25] IMPLICIT PresentResponse
 *   deleteResultSetRequest [26] IMPLICIT DeleteResultSetRequest
 *   deleteResultSetResponse [27] IMPLICIT DeleteResultSetResponse
 *   accessControlRequest [28] IMPLICIT AccessControlRequest
 *   accessControlResponse [29] IMPLICIT AccessControlResponse
 *   resourceControlRequest [30] IMPLICIT ResourceControlRequest
 *   resourceControlResponse [31] IMPLICIT ResourceControlResponse
 *   triggerResourceControlRequest [32] IMPLICIT TriggerResourceControlRequest
 *   resourceReportRequest [33] IMPLICIT ResourceReportRequest
 *   resourceReportResponse [34] IMPLICIT ResourceReportResponse
 *   scanRequest [35] IMPLICIT ScanRequest
 *   scanResponse [36] IMPLICIT ScanResponse
 *   sortRequest [43] IMPLICIT SortRequest
 *   sortResponse [44] IMPLICIT SortResponse
 *   segmentRequest [45] IMPLICIT Segment
 *   extendedServicesRequest [46] IMPLICIT ExtendedServicesRequest
 *   extendedServicesResponse [47] IMPLICIT ExtendedServicesResponse
 *   close [48] IMPLICIT Close
 * }
 * </pre>
 *
 * @param <IN> input parameter
 * @param <OUT> output parameter
 */
public class AbstractOperation<IN extends ASN1Any, OUT extends ASN1Any> {

    protected final BERReader reader;

    protected final BERWriter writer;

    AbstractOperation(BERReader reader, BERWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    protected void write(OUT protocolDataUnit) throws IOException {
        try {
            writer.write(encode(protocolDataUnit));
        } catch (ASN1Exception ex) {
            throw new IOException(ex);
        }
    }

    protected IN read() throws IOException {
        try {
            BEREncoding ber = reader.read();
            if (ber == null) {
                throw new IOException("read PDU error");
            }
            return decode(ber);
        } catch (ASN1Exception ex) {
            throw new IOException(ex);
        } catch (NullPointerException ex) {
            throw new IOException("connection read PDU error", ex);
        }
    }

    private BEREncoding encode(OUT data) throws ASN1Exception {
        if (data instanceof InitializeRequest) {
            return data.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 20);
        }
        if (data instanceof InitializeResponse) {
            return data.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 21);
        }
        if (data instanceof SearchRequest) {
            return data.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 22);
        }
        if (data instanceof SearchResponse) {
            return data.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 23);
        }
        if (data instanceof PresentRequest) {
            return data.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 24);
        }
        if (data instanceof PresentResponse) {
            return data.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 25);
        }
        if (data instanceof ScanRequest) {
            return data.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 35);
        }
        if (data instanceof ScanResponse) {
            return data.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 36);
        }
        if (data instanceof Close) {
            return data.berEncode(BEREncoding.CONTEXT_SPECIFIC_TAG, 48);
        }
        throw new ASN1Exception("bad PDU to write");
    }

    @SuppressWarnings("unchecked")
    private IN decode(BEREncoding ber) throws ASN1Exception {
        if (ber.getTagType() != BEREncoding.CONTEXT_SPECIFIC_TAG) {
            throw new ASN1Exception("bad BER encoding: choice not matched");
        }
        try {
            switch (ber.getTag()) {
                case 20:
                    return (IN) new InitializeRequest(ber, false);
                case 21:
                    return (IN) new InitializeResponse(ber, false);
                case 22:
                    return (IN) new SearchRequest(ber, false);
                case 23:
                    return (IN) new SearchResponse(ber, false);
                case 24:
                    return (IN) new PresentRequest(ber, false);
                case 25:
                    return (IN) new PresentResponse(ber, false);
                // 26 new DeleteResultSetRequest(ber, false);
                // 27 new DeleteResultSetResponse(ber, false);
                // 28 new AccessControlRequest(ber, false);
                // 29 new AccessControlResponse(ber, false);
                // 30 new ResourceControlRequest(ber, false);
                // 31 new ResourceControlResponse(ber, false);
                // 32 new TriggerResourceControlRequest(ber, false);
                // 33 new ResourceReportRequest(ber, false);
                // 34 new ResourceReportResponse(ber, false);
                case 35:
                    return (IN) new ScanRequest(ber, false);
                case 36:
                    return (IN) new ScanResponse(ber, false);
                // 43 new SortRequest(ber, false);
                // 44  new SortResponse(ber, false);
                // 45  new Segment(ber, false);
                // 46  new ExtendedServicesRequest(ber, false);
                // 47  new ExtendedServicesResponse(ber, false);
                case 48:
                    return (IN) new Close(ber, false);
            }
        } catch (Exception e) {
            // class cast exception if Close is returned, ignore
            return null;
        }
        throw new ASN1Exception("bad BER encoding: choice not matched");
    }

}
