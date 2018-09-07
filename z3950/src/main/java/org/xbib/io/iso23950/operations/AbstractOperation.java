package org.xbib.io.iso23950.operations;

import org.xbib.asn1.ASN1Exception;
import org.xbib.asn1.BEREncoding;
import org.xbib.asn1.io.BERReader;
import org.xbib.asn1.io.BERWriter;
import org.xbib.io.iso23950.v3.PDU;

import java.io.IOException;

public class AbstractOperation {

    protected final BERReader reader;

    protected final BERWriter writer;

    AbstractOperation(BERReader reader, BERWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    protected void writePDU(PDU pdu) throws IOException {
        try {
            writer.write(pdu.berEncode());
        } catch (ASN1Exception ex) {
            throw new IOException(ex);
        }
    }

    protected PDU readPDU() throws IOException {
        try {
            BEREncoding ber = reader.read();
            if (ber == null) {
                throw new IOException("read PDU error");
            }
            return new PDU(ber, true);
        } catch (ASN1Exception ex) {
            throw new IOException(ex);
        } catch (NullPointerException ex) {
            throw new IOException("connection read PDU error", ex);
        }
    }

}
