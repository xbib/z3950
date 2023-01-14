package org.xbib.z3950.common.operations;

import org.xbib.asn1.ASN1BitString;
import org.xbib.asn1.ASN1GeneralString;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.io.BERReader;
import org.xbib.asn1.io.BERWriter;
import org.xbib.z3950.common.v3.IdAuthentication;
import org.xbib.z3950.common.v3.IdAuthenticationIdPass;
import org.xbib.z3950.common.v3.InitializeRequest;
import org.xbib.z3950.common.v3.InitializeResponse;
import org.xbib.z3950.common.v3.InternationalString;
import org.xbib.z3950.common.v3.Options;
import org.xbib.z3950.common.v3.ProtocolVersion;
import org.xbib.z3950.api.InitListener;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Z39.50 Init operation.
 */
public class InitOperation extends AbstractOperation<InitializeResponse, InitializeRequest> {

    private static final Logger logger = Logger.getLogger(InitOperation.class.getName());

    private final String user;

    private final String pass;

    private String targetInfo;

    public InitOperation(BERReader reader, BERWriter writer, String user, String pass) {
        super(reader, writer);
        this.user = user;
        this.pass = pass;
    }

    public boolean execute(Integer preferredMessageSize, InitListener initListener) throws IOException {
        InitializeRequest init = new InitializeRequest();
        boolean[] version = new boolean[3];
        version[0] = true; // any version, should alwasy be true
        version[1] = true; // Z39.50 version 2
        version[2] = true; // Z39.50 version 3
        init.protocolVersion = new ProtocolVersion();
        init.protocolVersion.value = new ASN1BitString(version);
        boolean[] options = new boolean[15];
        options[0] = true; // search
        options[1] = true; // present
        options[2] = false;  // delete set
        options[3] = false; // resource-report
        options[4] = false; // trigger resource control
        options[5] = false;  // resource control
        options[6] = false; // access control
        options[7] = true; // scan
        options[8] = false; // sort
        options[9] = false; // (unused)
        options[10] = false; // extended-services  
        options[11] = false; // level 1 segmentation
        options[12] = false; // level 2 segmentation
        options[13] = false; // concurrent operations
        options[14] = true; // named result sets
        init.options = new Options();
        init.options.value = new ASN1BitString(options);
        init.preferredMessageSize = new ASN1Integer(preferredMessageSize);
        init.exceptionalRecordSize = new ASN1Integer(preferredMessageSize * 2);
        init.implementationId = new InternationalString();
        init.implementationId.value = new ASN1GeneralString("1");
        init.implementationName = new InternationalString();
        init.implementationName.value = new ASN1GeneralString("Java ZClient");
        init.implementationVersion = new InternationalString();
        init.implementationVersion.value = new ASN1GeneralString("1.00");
        if (user != null) {
            init.idAuthentication = new IdAuthentication();
            init.idAuthentication.idPass = new IdAuthenticationIdPass();
            init.idAuthentication.idPass.s_userId = new InternationalString();
            init.idAuthentication.idPass.s_userId.value = new ASN1GeneralString(user);
            if (pass != null) {
                init.idAuthentication.idPass.s_password = new InternationalString();
                init.idAuthentication.idPass.s_password.value = new ASN1GeneralString(pass);
            }
        }
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, init.toString());
        }
        write(init);
        InitializeResponse initResp = read();
        if (initResp.implementationName != null) {
            targetInfo = initResp.implementationName.toString();
            if (initResp.implementationVersion != null) {
                targetInfo += " - " + initResp.implementationVersion;
            }
        } else {
            targetInfo = "server";
        }
        int targetVersion = 0;
        if (initResp.protocolVersion != null) {
            for (int n = 0; n < initResp.protocolVersion.value.get().length; n++) {
                if (initResp.protocolVersion.value.get()[n]) {
                    targetVersion = n + 1;
                }
            }
            if (targetVersion > 0) {
                targetInfo += " (Version " + targetVersion + ")";
            }
        } else {
            targetInfo += " (Version unknown)";
        }
        if (initResp.userInformationField != null && initResp.userInformationField.getSingleASN1Type() != null) {
            targetInfo += "\n" + initResp.userInformationField.getSingleASN1Type().toString();
        }
        if (initResp.otherInfo != null) {
            targetInfo += "\n" + initResp.otherInfo.toString();
        }
        targetInfo = targetInfo.replaceAll("\"", "");
        if (initListener != null) {
            initListener.onInit(targetVersion, targetInfo);
        }
        return !initResp.result.get();
    }

    public String getTargetInfo() {
        return targetInfo;
    }
}
