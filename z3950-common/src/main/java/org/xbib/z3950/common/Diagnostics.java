package org.xbib.z3950.common;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Diagnostics for Z server.
 */
public class Diagnostics extends IOException {

    private static final ResourceBundle bundle =
            ResourceBundle.getBundle("org.xbib.z3950.common.diagnostics");

    private static final long serialVersionUID = -899201811019819079L;

    private int diagCode;

    private String message;

    public Diagnostics(int diagCode, String message) {
        super("" + diagCode + " " + bundle.getString(Integer.toString(diagCode)) + " " + message);
        this.diagCode = diagCode;
        this.message = message;
    }

    public int getDiagCode() {
        return diagCode;
    }

    public String getPlainText() {
       return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
