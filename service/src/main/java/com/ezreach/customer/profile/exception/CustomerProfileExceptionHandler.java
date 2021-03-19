package com.ezreach.customer.profile.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@ControllerAdvice
public class CustomerProfileExceptionHandler {

	@Value("${customerProfile.exceptionId}") 
    private String apiErrorCode;
	
	private Errorcode errorcode = new Errorcode();
	
	@ExceptionHandler(GstServerDownException.class)
    public ResponseEntity<ErrorMessage> handleGstServerDown(GstServerDownException gstServerDownException) {
		Map<String, String> params = new HashMap<String,String>();
		
        UUID errorId = UUID.randomUUID();
        String errorCode = "503" + "." + apiErrorCode + "." + errorcode.GST_SERVER_DOWN_CODE;
        
        ErrorMessage errorMessage = new ErrorMessage(errorId, errorCode, gstServerDownException.getMessage(), params);
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(GstNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleGstNotFOund(GstNotFoundException gstNotFoundException) {
    	Map<String, String> params = new HashMap<String,String>();
		params.put("gstin", gstNotFoundException.getGstin());
		
        UUID errorId = UUID.randomUUID();
        String errorCode = "404" + "." + apiErrorCode + "." + errorcode.GST_NOT_FOUND_CODE;
        
        ErrorMessage errorMessage = new ErrorMessage(errorId, errorCode, gstNotFoundException.getMessage(), params);
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleCustomerNotFound(CustomerNotFoundException exception) {
    	Map<String, String> params = new HashMap<String,String>();
		
        UUID errorId = UUID.randomUUID();
        String errorCode = "404" + "." + apiErrorCode + "." + errorcode.CUSTOMER_NOT_FOUND_CODE;
        
        ErrorMessage errorMessage = new ErrorMessage(errorId, errorCode, exception.getMessage(), params);
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> handleBadRequest(BadRequestException badRequestException) {
    	Map<String, String> params = new HashMap<String, String>();
    	Integer count = badRequestException.getErrorCount();
		params.put("errorCount", count.toString());
		
        UUID errorId = UUID.randomUUID();
        String errorCode = "400" + "." + apiErrorCode + "." + errorcode.BAD_REQUEST_CODE;
        
        ErrorMessage errorMessage = new ErrorMessage(errorId, errorCode, badRequestException.getMessage(), params);
    	return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorMessage> handleTokenExpired(TokenExpiredException tokenExpiredException) {
    	Map<String, String> params = new HashMap<String, String>();
		
        UUID errorId = UUID.randomUUID();
        String errorCode = "403" + "." + apiErrorCode + "." + errorcode.TOKEN_EXPIRED_CODE;
        
        ErrorMessage errorMessage = new ErrorMessage(errorId, errorCode, tokenExpiredException.getMessage(), params);
    	return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadDatabaseConnectionException.class)
    public ResponseEntity<ErrorMessage> handleBadGateway(TokenExpiredException tokenExpiredException) {
        Map<String, String> params = new HashMap<String, String>();

        UUID errorId = UUID.randomUUID();
        String errorCode = "502" + "." + apiErrorCode + "." + errorcode.BAD_DATABASE_CONNECTION_CODE;

        String message = "Database connection could not be made";
        ErrorMessage errorMessage = new ErrorMessage(errorId, errorCode, message, params);
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_GATEWAY);
    }
}
