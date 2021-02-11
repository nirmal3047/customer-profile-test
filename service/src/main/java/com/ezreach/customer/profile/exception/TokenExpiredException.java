package com.ezreach.customer.profile.exception;

public class TokenExpiredException extends Exception {

	private static final long serialVersionUID = 5110194668503518455L;

	public TokenExpiredException(String message) {
		super(message);
	}
}
