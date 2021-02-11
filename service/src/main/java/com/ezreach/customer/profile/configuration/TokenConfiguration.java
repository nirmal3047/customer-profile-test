package com.ezreach.customer.profile.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfiguration {

	@Bean
	public TokenVerifier createTokenVerifier() {
		return new TokenVerifier();
	}
}
