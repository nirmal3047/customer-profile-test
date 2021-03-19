package com.ezreach.customer.profile.exception;

public class GstNotFoundException extends Exception {

	private static final long serialVersionUID = 6589411775586845418L;
	
	private String gstin;
 
	public GstNotFoundException(String message, String gstin) {
		super(message);
		this.gstin = gstin;
	}

	public String getGstin() {
		return gstin;
	}
	public void setGstin(String gstin) {
		this.gstin = gstin;
	}
}
