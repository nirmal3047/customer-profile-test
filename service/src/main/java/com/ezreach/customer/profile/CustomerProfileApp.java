package com.ezreach.customer.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ezreach.*"})
public class CustomerProfileApp {
	
	@Bean
	public RestTemplate createRestTemplate() {
		return new RestTemplate();
	}

    public static void main(String[] args) {
        SpringApplication.run(CustomerProfileApp.class, args);
    }

}
