package com.ezreach.customer.profile.configuration;

import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ezreach.customer.profile.entity.TokenInfo;

public class TokenVerifier {
	
	public TokenInfo verifyToken(String token) throws Exception {
		TokenInfo tokenInfo = new TokenInfo();
    	
		try {
    	    DecodedJWT jwt = JWT.decode(token);
    	    long expire = jwt.getExpiresAt().getTime();
    	    long now = System.currentTimeMillis();
    	    
    	    //Check if token has expired
    	    if(expire < now) {
    	    	throw new Exception();
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
    	    System.out.println("Error in Jwt");
    	}
		return tokenInfo;
	}
}
