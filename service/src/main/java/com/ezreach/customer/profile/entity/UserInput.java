package com.ezreach.customer.profile.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserInput {

	@NotNull
	private String name;

	@Size(min=15, max=15)
	private String gstin;
	
	@Size(min=10, max=10)
	private String pan;
	
	@Size(min=12, max=12)
	private String udyogAadhaar;
	
	@Min(0)
	private float turnover;
	
	public UserInput() {}
	
	public UserInput(@NotNull String name, @NotNull @Size(min=15, max=15) String gstin, @NotNull @Size(min=10, max=10) String pan,
					 @NotNull @Size(min=12, max=12) String udyogAadhaar, @NotNull @Min(0) float turnover) {
		this.name = name;
		this.gstin = gstin;
		this.pan = pan;
		this.udyogAadhaar = udyogAadhaar;
		this.turnover = turnover;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
