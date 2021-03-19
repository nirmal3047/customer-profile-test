package com.ezreach.customer.profile.exception;

public class CustomerNotFoundException extends Exception {

	private static final long serialVersionUID = 928686689097547628L;
	
	public CustomerNotFoundException(String message) {
        super(message);
    }
}
