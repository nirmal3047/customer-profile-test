package com.ezreach.customer.profile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class GstServerDownException extends HttpClientErrorException {

	private static final long serialVersionUID = 4060063303948366505L;

	public GstServerDownException(HttpStatus statusCode) {
		super(statusCode);
	}
}
