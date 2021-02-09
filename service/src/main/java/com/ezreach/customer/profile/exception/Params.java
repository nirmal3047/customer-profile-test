package com.ezreach.customer.profile.exception;

import java.util.UUID;

public class Params {

    private String gstin;
    private UUID customerId;

    public Params() {}

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

	public UUID getCustomerId() {
		return customerId;
	}

	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}
}
