package com.ezreach.customer.profile;

import cognito.AwsCognitoJwtAuthFilter;
import com.ezreach.customer.profile.configuration.TokenVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.MalformedURLException;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ezreach.*"})
public class CustomerProfileApp {

    public static void main(String[] args) {
        SpringApplication.run(CustomerProfileApp.class, args);
    }

    @Value("${gst.baseUrl}")
    private String GST_BASE_URL;

    @Value("${aws.cognito.region}")
    private String AWS_COGNITO_REGION;

    @Value("${aws.cognito.poolId}")
    private String AWS_COGNOTO_USER_POOL_ID;

    @Bean
    public AwsCognitoJwtAuthFilter getAwsCognitoJwtAuthFilterBean() throws MalformedURLException {
        return new AwsCognitoJwtAuthFilter(AWS_COGNOTO_USER_POOL_ID, AWS_COGNITO_REGION);
    }

    @Bean
    public TokenVerifier createTokenVerifier() {
        return new TokenVerifier();
    }

    @Bean
    public WebClient gerWebClientBuilder() {
        return WebClient.builder().baseUrl(GST_BASE_URL).build();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

}
