package org.xbib.z3950.common.operations;

import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.io.BERReader;
import org.xbib.asn1.io.BERWriter;
import org.xbib.z3950.common.v3.Close;
import org.xbib.z3950.common.v3.CloseReason;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CloseOperation extends AbstractOperation<Close, Close> {

    private static final Logger logger = Logger.getLogger(CloseOperation.class.getName());

    public CloseOperation(BERReader reader, BERWriter writer) {
        super(reader, writer);
    }

    public void execute(int reason) throws IOException {
        Close close = new Close();
        close.closeReason = new CloseReason();
        close.closeReason.value = new ASN1Integer(reason);
        close.referenceId = null;
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, close.toString());
        }
        write(close);
        // do not wait, it may hang
        //waitClosePDU();
    }
}
