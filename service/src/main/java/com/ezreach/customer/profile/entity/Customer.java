package com.ezreach.customer.profile.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="customer", schema="onboard")
public class Customer {

    @Id
    @Column(name="customer_id")
    @Type(type="pg-uuid")
    private UUID customerId;
    
    private String name;
    
    private String gstin;
    
    private String pan;
    
    @Column(name="udyog_aadhaar")
    private String udyogAadhaar;
    
    private String email;
    
    private String mobile;
    
    private double turnover;

    @Column(name="user_id")
    private UUID userId;

    public Customer() {}

    /**
     *
     * @param customerId randomly generated uuid at microservice
     * @param name Customer's name
     * @param gstin Customer's GST id number
     * @param pan Customer's PAN
     * @param udyogAadhaar Customer's Udyog Aadhaar number
     * @param email Customer's email id
     * @param mobile Customer's mobile number
     * @param turnover Customer's annual turnover
     * @param userId uuid provided by AWS Cognito
     */
    public Customer(UUID customerId, String name, String gstin, String pan, String udyogAadhaar,
                    String email, String mobile, double turnover, UUID userId) {
        this.customerId = customerId;
        this.name = name;
        this.gstin = gstin;
        this.pan = pan;
        this.udyogAadhaar = udyogAadhaar;
        this.email = email;
        this.mobile = mobile;
        this.turnover = turnover;
        this.userId = userId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
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

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}
}
