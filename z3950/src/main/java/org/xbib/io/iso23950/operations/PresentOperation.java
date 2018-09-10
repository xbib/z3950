package org.xbib.io.iso23950.operations;

import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1External;
import org.xbib.asn1.ASN1GeneralString;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1ObjectIdentifier;
import org.xbib.asn1.io.BERReader;
import org.xbib.asn1.io.BERWriter;
import org.xbib.io.iso23950.Diagnostics;
import org.xbib.io.iso23950.ErrorRecord;
import org.xbib.io.iso23950.Record;
import org.xbib.io.iso23950.RecordListener;
import org.xbib.io.iso23950.SearchListener;
import org.xbib.io.iso23950.exceptions.MessageSizeTooSmallException;
import org.xbib.io.iso23950.exceptions.NoRecordsReturnedException;
import org.xbib.io.iso23950.exceptions.RequestTerminatedByAccessControlException;
import org.xbib.io.iso23950.exceptions.RequestTerminatedException;
import org.xbib.io.iso23950.exceptions.ZException;
import org.xbib.io.iso23950.v3.DefaultDiagFormat;
import org.xbib.io.iso23950.v3.ElementSetNames;
import org.xbib.io.iso23950.v3.InternationalString;
import org.xbib.io.iso23950.v3.NamePlusRecord;
import org.xbib.io.iso23950.v3.PresentRequest;
import org.xbib.io.iso23950.v3.PresentRequestRecordComposition;
import org.xbib.io.iso23950.v3.PresentResponse;
import org.xbib.io.iso23950.v3.PresentStatus;
import org.xbib.io.iso23950.v3.ResultSetId;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Present operation for Z39.50.
 */
public class PresentOperation extends AbstractOperation<PresentResponse, PresentRequest> {

    private static final Logger logger = Logger.getLogger(PresentOperation.class.getName());

    private final String resultSetName;

    private final String elementSetName;

    private final String preferredRecordSyntax;

    public PresentOperation(BERReader reader, BERWriter writer,
                            String resultSetName,
                            String elementSetName,
                            String preferredRecordSyntax) {
        super(reader, writer);
        this.resultSetName = resultSetName;
        this.elementSetName = elementSetName;
        this.preferredRecordSyntax = preferredRecordSyntax;
    }

    public void execute(int offset, int length, int total,
                        SearchListener searchListener, RecordListener recordListener) throws IOException {
        PresentRequest presentRequest = new PresentRequest();
        presentRequest.resultSetId = new ResultSetId();
        presentRequest.resultSetId.value = new InternationalString();
        presentRequest.resultSetId.value.value = new ASN1GeneralString(resultSetName);
        presentRequest.resultSetStartPoint = new ASN1Integer(offset);
        presentRequest.numberOfRecordsRequested = new ASN1Integer(length);
        presentRequest.recordComposition = new PresentRequestRecordComposition();
        presentRequest.recordComposition.simple = new ElementSetNames();
        presentRequest.recordComposition.simple.cGenericElementSetName = new InternationalString();
        presentRequest.recordComposition.simple.cGenericElementSetName.value = new ASN1GeneralString(elementSetName);
        presentRequest.preferredRecordSyntax = new ASN1ObjectIdentifier(makeOID(preferredRecordSyntax));
        long millis = System.currentTimeMillis();
        write(presentRequest);
        PresentResponse response = read();
        int nReturned = response.numberOfRecordsReturned != null ? response.numberOfRecordsReturned.get() : 0;
        int status = response.presentStatus.value != null ? response.presentStatus.value.get() : 0;
        if (searchListener != null) {
            searchListener.onResponse(status, total, nReturned,  System.currentTimeMillis() - millis);
        }
        if (status == PresentStatus.E_success) {
            for (int n = 0; n < nReturned; n++) {
                NamePlusRecord nr = response.records.c_responseRecords[n];
                try {
                    if (nr.record.retrievalRecord != null) {
                        ASN1External asn1External = new ASN1External(nr.record.retrievalRecord.berEncode(), true);
                        Record record = new Record(offset + n, asn1External.getcOctetAligned().getBytes());
                        if (recordListener != null) {
                            recordListener.onRecord(record);
                        }
                    } else if (nr.record.surrogateDiagnostic != null) {
                        DefaultDiagFormat diagFormat = nr.record.surrogateDiagnostic.defaultFormat;
                        if (diagFormat != null) {
                            logger.log(Level.WARNING, diagFormat.toString());
                        }
                        ASN1External asn1External = nr.record.surrogateDiagnostic.externallyDefined;
                        if (asn1External != null) {
                            ErrorRecord record = new ErrorRecord(offset + n, asn1External.getcOctetAligned().getBytes());
                            if (recordListener != null) {
                                recordListener.onRecord(record);
                            }
                        }
                    }
                } catch (ASN1Exception e) {
                    logger.log(Level.WARNING, e.getMessage(), e);
                    if (recordListener != null) {
                        ErrorRecord record = new ErrorRecord(offset + n, e.getMessage().getBytes(StandardCharsets.UTF_8));
                        recordListener.onRecord(record);
                    }
                }
            }
        } else {
            throw createZExceptionFrom(status, nReturned, response);
        }
    }

    private int[] makeOID(String str) throws NumberFormatException {
        String[] s = str.split("\\.");
        int[] a = new int[s.length];
        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(s[i]);
        }
        return a;
    }

    private ZException createZExceptionFrom(int status, int nReturned, PresentResponse response) {
        String message;
        switch (status) {
            case 1:
                message = "Some records were not returned (request was terminated by access control)";
                return new NoRecordsReturnedException(message, status);
            case 2:
                message = "Some records were not returned (message size is too small)";
                return new MessageSizeTooSmallException(message, status, nReturned);
            case 3:
                message = "Some records were not returned (request was terminated by control, at origin request)";
                return new RequestTerminatedByAccessControlException(message, status, nReturned);
            case 4:
                message = "Some records were not returned (request was terminated by control, by the target)";
                return new RequestTerminatedException(message, status, nReturned);
            case 5:
                return new NoRecordsReturnedException(response.toString(), status);
        }
        return new ZException(response.toString(), status, nReturned);
    }
}
