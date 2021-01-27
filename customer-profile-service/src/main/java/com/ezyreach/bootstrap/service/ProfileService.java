package com.ezyreach.bootstrap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezyreach.bootstrap.entity.UserInput;

@Service
public class ProfileService {

	@Autowired
	public ProfileService() {
		
	}
	
	public String createCustomer(UserInput userInput) {
		return "created";
	}
}
