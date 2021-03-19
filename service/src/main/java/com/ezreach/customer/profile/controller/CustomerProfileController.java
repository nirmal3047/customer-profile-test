package com.ezreach.customer.profile.controller;

import com.ezreach.customer.profile.configuration.TokenVerifier;
import com.ezreach.customer.profile.entity.CustomerDto;
import com.ezreach.customer.profile.entity.TokenInfo;
import com.ezreach.customer.profile.exception.BadRequestException;
import com.ezreach.customer.profile.service.CustomerProfileService;
import com.ezreach.customer.profile.service.GstProfileService;

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

    private Logger LOGGER = LoggerFactory.getLogger(CustomerProfileController.class);
    private CustomerProfileService customerProfileService;
    private GstProfileService gstProfileService;
    private TokenVerifier tokenVerifier;

	@Autowired
    public CustomerProfileController(CustomerProfileService customerProfileService, TokenVerifier tokenVerifier,
    		GstProfileService gstProfileService) {
        this.customerProfileService = customerProfileService;
        this.gstProfileService = gstProfileService;
        this.tokenVerifier = tokenVerifier;
	}

    @Override
    public ResponseEntity<Void> createProfile(String header, UserInput userInput,
    										BindingResult bindingResult) throws Exception {
    	TokenInfo tokenInfo = tokenVerifier.verifyToken(header);
    	
    	if (bindingResult.hasErrors()) {
            LOGGER.info(bindingResult.toString());
    		int errorCount = bindingResult.getErrorCount();
            throw new BadRequestException("Invalid Input", errorCount);
        }
	    customerProfileService.createCustomerProfile(userInput, tokenInfo);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    
    @Override
    public ResponseEntity<Void> updateProfile(String header, UserInput userInput,
            					BindingResult bindingResult, String profileType) throws Exception {
    	TokenInfo tokenInfo = tokenVerifier.verifyToken(header);
    	UUID userId = tokenInfo.getUserId();
    	
    	if (bindingResult.hasErrors()) {
			LOGGER.info(bindingResult.toString());
    		int errorCount = bindingResult.getErrorCount();
            throw new BadRequestException("Invalid Input", errorCount);
        }
    	customerProfileService.updateCustomer(userId, userInput, tokenInfo);
    	
    	if(profileType.equals(new String("INVOICE"))) {
    		String gstin = userInput.getGstin();
    		gstProfileService.createGstProfile(userId, gstin);
    	}
    	else {
    		LOGGER.info("Input for profileType = {}. Should be INVOICE", profileType);
    		throw new BadRequestException("Invalid Profile Type", 1);
    	}
	    return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    
    @Override
    public ResponseEntity<CustomerDto> fetchProfile(String header) throws Exception {
    	TokenInfo tokenInfo = tokenVerifier.verifyToken(header);
    	UUID userId = tokenInfo.getUserId();
    	CustomerDto customer = customerProfileService.getCustomer(userId);
        return new ResponseEntity<CustomerDto>(customer, HttpStatus.FOUND);
    }
}
