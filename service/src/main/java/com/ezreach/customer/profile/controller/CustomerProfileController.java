package com.ezreach.customer.profile.controller;

import com.ezreach.customer.profile.configuration.TokenVerifier;
import com.ezreach.customer.profile.entity.Customer;
import com.ezreach.customer.profile.entity.Header;
import com.ezreach.customer.profile.entity.TokenInfo;
import com.ezreach.customer.profile.exception.BadRequestException;
import com.ezreach.customer.profile.service.CustomerProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.ezreach.customer.profile.entity.UserInput;

import java.util.UUID;

@RestController
public class CustomerProfileController implements CustomerProfileControllerInterface {

    private CustomerProfileService customerProfileService;
    private TokenVerifier tokenVerifier;

	@Autowired
    public CustomerProfileController(CustomerProfileService customerProfileService, TokenVerifier tokenVerifier) {
        this.customerProfileService = customerProfileService;
        this.tokenVerifier = tokenVerifier;
	}

    @Override
    public ResponseEntity<Void> createProfile(String header, UserInput userInput, BindingResult bindingResult)
    				throws Exception {
    	ObjectMapper mapper = new ObjectMapper();
    	Header headerObj = mapper.readValue(header, Header.class);
    	
    	String idToken = headerObj.getIdToken();
    	TokenInfo tokenInfo = tokenVerifier.verifyToken(idToken);
    	
    	if (bindingResult.hasErrors()) {
            throw new BadRequestException("Invalid Input");
        }
	    customerProfileService.createCustomerProfile(userInput, tokenInfo);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @Override
    //Incomplete
    public ResponseEntity<Void> updateProfile(String header, UserInput userInput,
            BindingResult bindingResult, UUID customerId) throws Exception {
    	ObjectMapper mapper = new ObjectMapper();
    	Header headerObj = mapper.readValue(header, Header.class);
    	
    	String idToken = headerObj.getIdToken();
    	TokenInfo tokenInfo = tokenVerifier.verifyToken(idToken);
    	
    	customerProfileService.updateCustomer(customerId, userInput, tokenInfo);
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
