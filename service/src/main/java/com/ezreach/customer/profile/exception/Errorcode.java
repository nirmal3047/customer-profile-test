package com.ezreach.customer.profile.exception;

public class Errorcode {

	//Resource Not found error codes (404)
	public static final String GST_NOT_FOUND_CODE = "001";
	public static final String CUSTOMER_NOT_FOUND_CODE = "002";
	
	//Connection error code (500)
	public static final String GST_SERVER_DOWN_CODE = "001";
	
	//Bad request (400)
	public static final String BAD_REQUEST_CODE = "001";
	
	//Forbidden
	public static final String TOKEN_EXPIRED_CODE = "001";
}
