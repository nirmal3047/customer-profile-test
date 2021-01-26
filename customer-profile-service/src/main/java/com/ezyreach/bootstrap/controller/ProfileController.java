package com.ezyreach.bootstrap.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/customer")
public class ProfileController {

	public ProfileController() {	
	}
	
	@GetMapping("/greeting")
    public ResponseEntity<String> greeting(){
        return new ResponseEntity<String>("welcome", HttpStatus.OK);
    }
	
	@PostMapping("/profile")
    public String createProfile(){
        return "welcome";
    }
}
