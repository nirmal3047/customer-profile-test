package com.ezreach.customer.profile.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Header {

	@JsonProperty("AccessToken")
	private String accessToken;
	
	@JsonProperty("ExpiresIn")
	private String expiresIn;
	
	@JsonProperty("TokenType")
	private String tokenType;
	
	@JsonProperty("RefreshToken")
	private String refreshToken;
	
	@JsonProperty("IdToken")
	private String idToken;
	
	public Header() {}
	
	public Header(String accessToken, String expiresIn, String tokenType, String refreshToken,String idToken) {
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.tokenType = tokenType;
		this.refreshToken = refreshToken;
		this.idToken = idToken;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getIdToken() {
		return idToken;
	}
	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}
}
