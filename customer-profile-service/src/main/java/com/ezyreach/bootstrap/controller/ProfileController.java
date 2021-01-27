package com.ezyreach.bootstrap.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ezyreach.bootstrap.entity.UserInput;
import com.ezyreach.bootstrap.service.ProfileService;

@RestController
@RequestMapping("/v1/customer")
public class ProfileController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);
	private RestTemplate restTemplate;
	private ProfileService profileService;
	
	public ProfileController(RestTemplate restTemplate, ProfileService profileService) {
		this.restTemplate = restTemplate;
		this.profileService = profileService;
	}
	
	@GetMapping("/greeting")
    public ResponseEntity<String> greeting(){
        return new ResponseEntity<String>("welcome", HttpStatus.OK);
    }
	
	@PostMapping("/profile")
    public ResponseEntity<Void> createProfile(
    		@Valid @RequestBody UserInput userInput, BindingResult bindingResult){
		String s = profileService.createCustomer(userInput);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
