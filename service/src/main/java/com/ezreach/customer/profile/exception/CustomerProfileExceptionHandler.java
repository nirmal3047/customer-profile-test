package com.ezreach.customer.profile.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//import com.indexdata.mkjsf;

@ControllerAdvice
public class CustomerProfileExceptionHandler {

	@Value("${customerProfile.exceptionId}") 
    private String apiErrorCode;
	
	private Errorcode errorcode = new Errorcode();
	
	@ExceptionHandler(GstServerDownException.class)
    public ResponseEntity<ErrorMessage> handleException(GstServerDownException gstServerDownException) {
		Map<String, String> params = new HashMap<String,String>();
		params.put("", "");
		
        UUID errorId = UUID.randomUUID();
        String errorCode = "500" + "." + apiErrorCode + "." + errorcode.GST_SERVER_DOWN_CODE;
        
        ErrorMessage errorMessage = new ErrorMessage(errorId, errorCode, "GST Server down", params);
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GstNotFoundException.class)
    public ResponseEntity<ErrorMessage> gstException(GstNotFoundException exception) {
    	Map<String, String> params = new HashMap<String,String>();
		params.put("gstin", "gstin");
		
        UUID errorId = UUID.randomUUID();
        String errorCode = "404" + "." + apiErrorCode + "." + errorcode.GST_NOT_FOUND_CODE;
        
        ErrorMessage errorMessage = new ErrorMessage(errorId, errorCode, "GST Not Found", params);
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorMessage> customerNotFoundHandler(CustomerNotFoundException exception) {
    	Map<String, String> params = new HashMap<String,String>();
		//params.put("", "");
		
        UUID errorId = UUID.randomUUID();
        String errorCode = "404" + "." + apiErrorCode + "." + errorcode.CUSTOMER_NOT_FOUND_CODE;
        
        ErrorMessage errorMessage = new ErrorMessage(errorId, errorCode, "Customer Not Found", params);
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }
    
    public ResponseEntity<ErrorMessage> badRequesthandler(BadRequestException exception) {
    	Map<String, String> params = new HashMap<String,String>();
		params.put("", "");
		
        UUID errorId = UUID.randomUUID();
        String errorCode = "400" + "." + apiErrorCode + "." + errorcode.BAD_REQUEST_CODE;
        
        ErrorMessage errorMessage = new ErrorMessage(errorId, errorCode, "Invalid Input", params);
    	return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
