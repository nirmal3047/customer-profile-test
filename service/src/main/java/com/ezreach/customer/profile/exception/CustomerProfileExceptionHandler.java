package com.ezreach.customer.profile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;

@ControllerAdvice
public class CustomerProfileExceptionHandler {

    @ExceptionHandler(GstServerDownException.class)
    public ResponseEntity<ErrorMessage> handleException(GstServerDownException gstServerDownException) {
        Params params = new Params();
        UUID errorId = UUID.randomUUID();
        ErrorMessage errorMessage = new ErrorMessage(errorId,"500","Server down", params);
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GstNotFoundException.class)
    public ResponseEntity<ErrorMessage> gstException(GstNotFoundException exception) {
        Params params = new Params();
        params.setGstin("gstin");
        UUID errorId = UUID.randomUUID();
        ErrorMessage errorMessage = new ErrorMessage(errorId,"404.001.001","Server down", params);
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorMessage> customerNotFoundHandler(CustomerNotFoundException exception) {
        Params params = new Params();
        params.setCustomerId(exception.getCustomerId());
        UUID errorId = UUID.randomUUID();
        ErrorMessage errorMessage = new ErrorMessage(errorId,"404.001.002","Customer Not FOund", params);
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
