package com.ezreach.customer.profile.entity;

import java.util.UUID;

public class TokenInfo {

	private UUID userId;
	private String email;
	private String mobile;
	
	public TokenInfo() {}
	
	public TokenInfo(UUID userId, String email, String mobile) {
		this.userId = userId;
		this.email = email;
		this.mobile = mobile;
	}
	
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
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
}
