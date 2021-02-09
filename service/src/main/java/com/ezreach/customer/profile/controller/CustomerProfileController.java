package com.ezreach.customer.profile.controller;

import javax.validation.Valid;

import com.ezreach.customer.profile.entity.Customer;
import com.ezreach.customer.profile.exception.GstNotFoundException;
import com.ezreach.customer.profile.exception.GstServerDownException;
import com.ezreach.customer.profile.service.CustomerProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.ezreach.customer.profile.entity.UserInput;

import java.util.UUID;

@RestController
public class CustomerProfileController implements CustomerProfileControllerInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerProfileController.class);
    private CustomerProfileService customerProfileService;

	@Autowired
    public CustomerProfileController(CustomerProfileService customerProfileService) {
        this.customerProfileService = customerProfileService;
	}

    @Override
	public ResponseEntity<String> greeting() {
        return new ResponseEntity<String>("welcome", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> createProfile(@RequestHeader("authorization") Object header,
    		@Valid @RequestBody UserInput userInput, BindingResult bindingResult)
    				throws JsonProcessingException, GstServerDownException {
	    customerProfileService.createCustomerProfile(userInput);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @Override
    //Incomplete
    public ResponseEntity<Void> updateProfile(Object header, @Valid UserInput userInput,
            BindingResult bindingResult, UUID customerId) throws Exception {
        customerProfileService.updateCustomer(customerId, userInput);
	    return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Customer> fetchProfile(Object header, UUID customerId) throws Exception {
	    Customer customer = customerProfileService.findCustomer(customerId);
        return new ResponseEntity<Customer>(customer, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<Void> deleteProfile(Object header, UUID customerId) throws Exception {
        customerProfileService.deleteCustomer(customerId);
    	return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
