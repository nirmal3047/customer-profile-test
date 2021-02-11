package com.ezreach.customer.profile.exception;

import java.util.Map;
import java.util.UUID;

public class ErrorMessage {

    private UUID id;
    private String errorCode;
    private String message;
    private Map<String, String> params;

    public ErrorMessage(UUID id, String errorCode, String message, Map<String, String> params) {
        this.id = id;
        this.errorCode = errorCode;
        this.message = message;
        this.params = params;
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

    public Map<String, String> getParam() {
        return params;
    }

    public void setParam(Map<String, String> params) {
        this.params = params;
    }
}
