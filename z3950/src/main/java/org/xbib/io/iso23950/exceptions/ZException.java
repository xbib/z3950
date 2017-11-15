package org.xbib.io.iso23950.exceptions;

import java.io.IOException;

/**
 *
 */
public class ZException extends IOException {

    private static final long serialVersionUID = 8199318316215867038L;

    private final int status;

    private final int number;

    public ZException(String message) {
        this(message, -1, -1);
    }

    public ZException(String message, int status) {
        this(message, status, -1);
    }

    public ZException(String message, int status, int number) {
        super(message);
        this.status = status;
        this.number = number;
    }

    public ZException(Throwable throwable) {
        this(null, throwable);
    }

    public ZException(String message, Throwable throwable) {
        this(message, throwable, -1, -1);
    }

    public ZException(String message, Throwable throwable, int status, int number) {
        super(message, throwable);
        this.status = status;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int getStatus() {
        return status;
    }
}
