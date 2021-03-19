package com.ezreach.customer.profile.exception;

public class BadRequestException extends Exception {

	private static final long serialVersionUID = -5778215125426612281L;
	private int errorCount;

	public BadRequestException() {}
	
	public BadRequestException(String message, int errorCount) {
		super(message);
		this.errorCount = errorCount;
	}

	public int getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}
}
