package com.ezyreach.bootstrap.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezyreach.bootstrap.entity.UserInput;

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
    public ResponseEntity<Void> createProfile(
    		@Valid @RequestBody UserInput userInput, BindingResult bindingResult){
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
