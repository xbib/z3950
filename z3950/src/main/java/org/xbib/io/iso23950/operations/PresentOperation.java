package org.xbib.io.iso23950.operations;

import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.ASN1External;
import org.xbib.asn1.ASN1GeneralString;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1ObjectIdentifier;
import org.xbib.io.iso23950.ErrorRecord;
import org.xbib.io.iso23950.Record;
import org.xbib.io.iso23950.RecordListener;
import org.xbib.io.iso23950.ResponseListener;
import org.xbib.io.iso23950.ZClient;
import org.xbib.io.iso23950.exceptions.MessageSizeTooSmallException;
import org.xbib.io.iso23950.exceptions.NoRecordsReturnedException;
import org.xbib.io.iso23950.exceptions.RequestTerminatedByAccessControlException;
import org.xbib.io.iso23950.exceptions.RequestTerminatedException;
import org.xbib.io.iso23950.exceptions.ZException;
import org.xbib.io.iso23950.v3.ElementSetNames;
import org.xbib.io.iso23950.v3.InternationalString;
import org.xbib.io.iso23950.v3.NamePlusRecord;
import org.xbib.io.iso23950.v3.PDU;
import org.xbib.io.iso23950.v3.PresentRequest;
import org.xbib.io.iso23950.v3.PresentRequestRecordComposition;
import org.xbib.io.iso23950.v3.PresentResponse;
import org.xbib.io.iso23950.v3.PresentStatus;
import org.xbib.io.iso23950.v3.ResultSetId;

import java.io.IOException;

/**
 * Present operation for Z39.50.
 */
public class PresentOperation {

    public void execute(ZClient client, int offset, int length,
                        ResponseListener responseListener, RecordListener recordListener) throws IOException {
        String resultSetName = client.getResultSetName();
        String elementSetName = client.getElementSetName();
        String preferredRecordSyntax = client.getPreferredRecordSyntax();
        PresentRequest pr = new PresentRequest();
        pr.s_resultSetId = new ResultSetId();
        pr.s_resultSetId.value = new InternationalString();
        pr.s_resultSetId.value.value = new ASN1GeneralString(resultSetName);
        pr.s_resultSetStartPoint = new ASN1Integer(offset);
        pr.s_numberOfRecordsRequested = new ASN1Integer(length);
        pr.s_recordComposition = new PresentRequestRecordComposition();
        pr.s_recordComposition.c_simple = new ElementSetNames();
        pr.s_recordComposition.c_simple.cGenericElementSetName = new InternationalString();
        pr.s_recordComposition.c_simple.cGenericElementSetName.value = new ASN1GeneralString(elementSetName);
        pr.s_preferredRecordSyntax = new ASN1ObjectIdentifier(makeOID(preferredRecordSyntax));
        PDU pdu = new PDU();
        pdu.c_presentRequest = pr;
        long millis = System.currentTimeMillis();
        client.writePDU(pdu);
        pdu = client.readPDU();
        PresentResponse response = pdu.c_presentResponse;
        int nReturned = response.s_numberOfRecordsReturned != null ? response.s_numberOfRecordsReturned.get() : 0;
        int status = response.s_presentStatus.value != null ? response.s_presentStatus.value.get() : 0;
        if (responseListener != null) {
            responseListener.onResponse(status, nReturned,  System.currentTimeMillis() - millis);
        }
        if (status == PresentStatus.E_success) {
            for (int n = 0; n < nReturned; n++) {
                NamePlusRecord nr = response.s_records.c_responseRecords[n];
                try {
                    if (nr.s_record.c_retrievalRecord != null) {
                        ASN1External asn1External = new ASN1External(nr.s_record.c_retrievalRecord.berEncode(), true);
                        Record record = new Record(offset + n, asn1External.getcOctetAligned().getBytes());
                        if (recordListener != null) {
                            recordListener.onRecord(record);
                        }
                    } else if (nr.s_record.c_surrogateDiagnostic != null) {
                        ASN1External asn1External =
                                new ASN1External(nr.s_record.c_surrogateDiagnostic.cDefaultFormat.berEncode(), true);
                        ErrorRecord record = new ErrorRecord(offset + n, asn1External.getcOctetAligned().getBytes());
                        if (recordListener != null) {
                            recordListener.onRecord(record);
                        }
                    }
                } catch (ASN1Exception e) {
                    throw new IOException("Present error: " + e.getMessage());
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
