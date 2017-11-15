package org.xbib.io.iso23950.operations;

import org.xbib.asn1.ASN1BitString;
import org.xbib.asn1.ASN1GeneralString;
import org.xbib.asn1.ASN1Integer;
import org.xbib.io.iso23950.InitListener;
import org.xbib.io.iso23950.ZClient;
import org.xbib.io.iso23950.v3.IdAuthentication;
import org.xbib.io.iso23950.v3.IdAuthenticationIdPass;
import org.xbib.io.iso23950.v3.InitializeRequest;
import org.xbib.io.iso23950.v3.InitializeResponse;
import org.xbib.io.iso23950.v3.InternationalString;
import org.xbib.io.iso23950.v3.Options;
import org.xbib.io.iso23950.v3.PDU;
import org.xbib.io.iso23950.v3.ProtocolVersion;

import java.io.IOException;

/**
 * A Z39.50 Init operation.
 */
public class InitOperation {

    public boolean execute(ZClient client, Integer preferredMessageSize,
                           InitListener initListener) throws IOException {
        InitializeRequest init = new InitializeRequest();
        boolean[] version = new boolean[3];
        version[0] = true; // any version, should alwasy be true
        version[1] = true; // Z39.50 version 2
        version[2] = true; // Z39.50 version 3
        init.s_protocolVersion = new ProtocolVersion();
        init.s_protocolVersion.value = new ASN1BitString(version);
        boolean[] options = new boolean[15];
        options[0] = true; // search
        options[1] = true; // present
        options[2] = true;  // delete set
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
        init.s_options = new Options();
        init.s_options.value = new ASN1BitString(options);
        init.s_preferredMessageSize = new ASN1Integer(preferredMessageSize);
        init.s_exceptionalRecordSize = new ASN1Integer(preferredMessageSize * 2);
        init.s_implementationId = new InternationalString();
        init.s_implementationId.value = new ASN1GeneralString("1");
        init.s_implementationName = new InternationalString();
        init.s_implementationName.value = new ASN1GeneralString("Java ZClient");
        init.s_implementationVersion = new InternationalString();
        init.s_implementationVersion.value = new ASN1GeneralString("1.00");
        if (client.getUser() != null) {
            init.s_idAuthentication = new IdAuthentication();
            init.s_idAuthentication.c_idPass = new IdAuthenticationIdPass();
            init.s_idAuthentication.c_idPass.s_userId = new InternationalString();
            init.s_idAuthentication.c_idPass.s_userId.value = new ASN1GeneralString(client.getUser());
            if (client.getPass() != null) {
                init.s_idAuthentication.c_idPass.s_password = new InternationalString();
                init.s_idAuthentication.c_idPass.s_password.value = new ASN1GeneralString(client.getPass());
            }
            /*if (group != null) {
                init.s_idAuthentication.c_idPass.s_groupId = new InternationalString();
                init.s_idAuthentication.c_idPass.s_groupId.value = new ASN1GeneralString(group);
            }*/
        }
        PDU pduOut = new PDU();
        pduOut.c_initRequest = init;
        client.writePDU(pduOut);
        PDU pduIn = client.readPDU();
        InitializeResponse initResp = pduIn.c_initResponse;
        String targetInfo;
        if (initResp.s_implementationName != null) {
            targetInfo = initResp.s_implementationName.toString();
            if (initResp.s_implementationVersion != null) {
                targetInfo += " - " + initResp.s_implementationVersion.toString();
            }
        } else {
            targetInfo = "server";
        }
        int targetVersion = 0;
        if (initResp.s_protocolVersion != null) {
            for (int n = 0; n < initResp.s_protocolVersion.value.get().length; n++) {
                if (initResp.s_protocolVersion.value.get()[n]) {
                    targetVersion = n + 1;
                }
            }
            if (targetVersion > 0) {
                targetInfo += " (Version " + targetVersion + ")";
            }
        } else {
            targetInfo += " (Version unknown)";
        }
        if (initResp.s_userInformationField != null) {
            if (initResp.s_userInformationField.getSingleASN1Type() != null) {
                targetInfo += "\n" + initResp.s_userInformationField.getSingleASN1Type().toString();
            }
        }
        if (initResp.s_otherInfo != null) {
            targetInfo += "\n" + initResp.s_otherInfo.toString();
        }
        targetInfo = targetInfo.replaceAll("\"", "");
        if (initListener != null) {
            initListener.onInit(targetVersion, targetInfo);
        }
        return !initResp.s_result.get();
    }
}
