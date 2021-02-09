package com.ezreach.customer.profile.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserInput {

	@NotNull
	@Size(min=15, max=15)
	private String gstin;
	
	@NotNull
	@Size(min=10, max=10)
	private String pan;
	
	@NotNull
	@Size(min=12, max=12)
	private String udyogAadhaar;
	
	@NotNull
	@Min(0)
	private float turnover;
	
	public UserInput() {}
	
	public UserInput(String gstin, String pan, String udyogAadhaar, float turnover) {
		super();
		this.gstin = gstin;
		this.pan = pan;
		this.udyogAadhaar = udyogAadhaar;
		this.turnover = turnover;
	}
	
	public String getGstin() {
		return gstin;
	}
	public void setGsting(String gstin) {
		this.gstin = gstin;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getUdyogAadhaar() {
		return udyogAadhaar;
	}
	public void setUdyogAadhaar(String udyogAadhaar) {
		this.udyogAadhaar = udyogAadhaar;
	}
	public float getTurnover() {
		return turnover;
	}
	public void setTurnover(float turnover) {
		this.turnover = turnover;
	}
}
