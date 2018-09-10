package org.xbib.io.iso23950.operations;

import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.io.BERReader;
import org.xbib.asn1.io.BERWriter;
import org.xbib.io.iso23950.v3.Close;
import org.xbib.io.iso23950.v3.CloseReason;

import java.io.IOException;

public class CloseOperation extends AbstractOperation<Close, Close> {

    public CloseOperation(BERReader reader, BERWriter writer) {
        super(reader, writer);
    }

    public void execute(int reason) throws IOException {
        Close close = new Close();
        close.closeReason = new CloseReason();
        close.closeReason.value = new ASN1Integer(reason);
        close.referenceId = null;
        write(close);
        // do not wait, it may hang
        //waitClosePDU();
    }
}
