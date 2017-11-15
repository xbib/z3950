package org.xbib.io.iso23950;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Diagnostics for Z server.
 */
public class Diagnostics extends IOException {

    private static final ResourceBundle bundle =
            ResourceBundle.getBundle("org.xbib.io.iso23950.diagnostics");

    private static final long serialVersionUID = -899201811019819079L;

    private int diagCode;

    private String message;

    private String details;

    public Diagnostics(int diagCode) {
        super("" + diagCode);
        this.diagCode = diagCode;
    }

    public Diagnostics(int diagCode, String message) {
        super("" + diagCode + " " + message);
        this.diagCode = diagCode;
        this.message = message;
    }

    public Diagnostics(int diagCode, String message, String details) {
        super("" + diagCode + " " + message + " " + details);
        this.diagCode = diagCode;
        this.message = message;
        this.details = details;
    }

    public String getPlainText() {
        String[] s = bundle.getString(Integer.toString(diagCode)).split("\\|");
        if (message == null) {
            message = s.length > 0 ? s[1] : "<undefined>";
        }
        if (details == null) {
            details = s.length > 1 ? s[2] : "<undefined>";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("diag code=").append(diagCode)
                .append(" message=").append(message)
                .append(" details=").append(details);
        return sb.toString();
    }

    @Override
    public String toString() {
        return getPlainText();
    }
}
