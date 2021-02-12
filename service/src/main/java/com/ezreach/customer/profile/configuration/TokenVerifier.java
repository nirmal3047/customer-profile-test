package com.ezreach.customer.profile.configuration;

import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ezreach.customer.profile.entity.Header;
import com.ezreach.customer.profile.entity.TokenInfo;
import com.ezreach.customer.profile.exception.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TokenVerifier {
	
	public TokenInfo verifyToken(String header) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
    	Header headerObj = mapper.readValue(header, Header.class);
    	
    	String idToken = headerObj.getIdToken();
		
		TokenInfo tokenInfo = new TokenInfo();
    	
		try {
    	    DecodedJWT jwt = JWT.decode(idToken);
    	    long expire = jwt.getExpiresAt().getTime();
    	    long now = System.currentTimeMillis();
    	    
    	    //Check if token has expired
    	    if(expire < now) {
    	    	throw new TokenExpiredException("Token Expired");
    	    }
    	    else {
    	    	String email = jwt.getClaim("email").asString();
        	    String mobile = jwt.getClaim("phone_number").asString().substring(3);
    	    	UUID sub = UUID.fromString(jwt.getSubject());
    	    	
    	    	tokenInfo.setUserId(sub);
    	    	tokenInfo.setEmail(email);
    	    	tokenInfo.setMobile(mobile);
    	    }
    		
    	} catch (JWTDecodeException exception){
    	    throw exception;
    	}
		return tokenInfo;
	}
}
