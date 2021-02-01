package com.ezyreach.profiling.controller;

import javax.validation.Valid;

import com.ezyreach.profiling.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezyreach.profiling.entity.UserInput;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/v1/customer")
public class ProfileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);
    private ProfileService profileService;

	@Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
	}
	
	@GetMapping("/greeting")
    public ResponseEntity<String> greeting(){
        return new ResponseEntity<String>("welcome", HttpStatus.OK);
    }
	
	@PostMapping("/profile")
    public ResponseEntity<Void> createProfile(
    		@Valid @RequestBody UserInput userInput, BindingResult bindingResult){
	    //profileService.createCustomerProfile(userInput);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
