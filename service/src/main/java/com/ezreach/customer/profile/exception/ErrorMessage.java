package com.ezreach.customer.profile.exception;

import java.util.UUID;

public class ErrorMessage {

    private UUID id;
    private String errorCode;
    private String message;
    private Params param;

    public ErrorMessage(UUID id, String errorCode, String message, Params param) {
        this.id = id;
        this.errorCode = errorCode;
        this.message = message;
        this.param = param;
    }

    public ErrorMessage() { }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Params getParam() {
        return param;
    }

    public void setParam(Params param) {
        this.param = param;
    }
}
