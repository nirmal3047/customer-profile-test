package com.ezreach.customer.profile.exception;

import java.util.UUID;

public class CustomerNotFoundException extends Exception {

	private static final long serialVersionUID = 928686689097547628L;
	private UUID customerId;
	
	public CustomerNotFoundException(String message, UUID customerId) {
        super(message);
        this.customerId = customerId;
    }
	public UUID getCustomerId() {
		return customerId;
	}
	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}
}
