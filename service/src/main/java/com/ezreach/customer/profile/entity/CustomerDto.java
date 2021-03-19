package com.ezreach.customer.profile.entity;

public class CustomerDto {

    private String name;
    private String gstin;
    private String pan;
    private String udyogAadhaar;
    private String email;
    private String mobile;
    private double turnover;
    private String gstDetails;
    
    public CustomerDto() {}
    
	public CustomerDto(String name, String gstin, String pan, String udyogAadhaar, String email,
			String mobile, double turnover, String gstDetails) {
		this.name = name;
		this.gstin = gstin;
		this.pan = pan;
		this.udyogAadhaar = udyogAadhaar;
		this.email = email;
		this.mobile = mobile;
		this.turnover = turnover;
		this.gstDetails = gstDetails;
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
	public void setGstin(String gstin) {
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public double getTurnover() {
		return turnover;
	}
	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}
	public String getGstDetails() {
		return gstDetails;
	}
	public void setGstDetails(String gstDetails) {
		this.gstDetails = gstDetails;
	}
    
}
