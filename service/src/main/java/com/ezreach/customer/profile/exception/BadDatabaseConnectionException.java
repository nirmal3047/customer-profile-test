package com.ezreach.customer.profile.exception;

public class BadDatabaseConnectionException extends Exception {

    private static final long serialVersionUID = 3768317115628632783L;

    public BadDatabaseConnectionException(String message) {
        super(message);
    }
}
