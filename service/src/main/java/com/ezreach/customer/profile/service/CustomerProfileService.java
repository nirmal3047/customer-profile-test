package com.ezreach.customer.profile.service;

import com.ezreach.customer.profile.entity.Customer;
import com.ezreach.customer.profile.entity.CustomerDto;
import com.ezreach.customer.profile.entity.GstProfile;
import com.ezreach.customer.profile.entity.TokenInfo;
import com.ezreach.customer.profile.entity.UserInput;
import com.ezreach.customer.profile.exception.BadDatabaseConnectionException;
import com.ezreach.customer.profile.exception.CustomerNotFoundException;
import com.ezreach.customer.profile.exception.GstNotFoundException;
import com.ezreach.customer.profile.repository.CustomerDao;

import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerProfileService {

    private CustomerDao customerDao;
    private GstProfileService gstProfileService;

    private static final Logger LOGGER  = LoggerFactory.getLogger(CustomerProfileService.class);

    @Autowired
    public CustomerProfileService(CustomerDao customerDao, GstProfileService gstProfileService) {
        this.customerDao = customerDao;
        this.gstProfileService = gstProfileService;
    }

    @Transactional
    public Customer saveCustomer(Customer customer) throws Exception {
        try {
            return customerDao.save(customer);
        } catch (JDBCConnectionException exception) {
            LOGGER.info(exception.getMessage());
            throw new BadDatabaseConnectionException("da");
        } catch (Exception exception) {
            LOGGER.info(exception.getMessage());
            throw exception;
        }
    }

    public Customer setCustomerDetails(UserInput userInput, Customer customer, TokenInfo tokenInfo)
    		throws Exception {
        //Creating customer profile
        customer.setGstin(userInput.getGstin());
        customer.setPan(userInput.getPan());
        customer.setUdyogAadhaar(userInput.getUdyogAadhaar());
        customer.setEmail(tokenInfo.getEmail());
        customer.setTurnover(userInput.getTurnover());
        customer.setName(userInput.getName());
        customer.setMobile(tokenInfo.getMobile());
        customer.setUserId(tokenInfo.getUserId());
        return customer;
    }
    
    @Transactional
    public void createCustomerProfile(UserInput userInput, TokenInfo tokenInfo)
    		throws Exception {
        //Creating customer profile
        Customer customer = new Customer();
        customer = setCustomerDetails(userInput, customer, tokenInfo);
        customer.setCustomerId(UUID.randomUUID());
 
        //Saving the customer in database
        saveCustomer(customer);
    }

    @Transactional
    public void updateCustomer(UUID userId, UserInput userInput, TokenInfo tokenInfo)
    		throws Exception {
        //finding existing customer in the database
    	Customer customer = findCustomer(userId);
    	
    	//Updating customer profile
        customer = setCustomerDetails(userInput, customer, tokenInfo);
        
        //Updating the customer in database
        saveCustomer(customer);
    }

    /**
     * Finds Customer in database if it exists, otherwise throws CustomerNotFoundException
     * @param userId
     * @return
     * @throws Exception
     */
    @Transactional
    public Customer findCustomer(UUID userId) throws Exception {
        Optional<Customer> customer = customerDao.findByUserId(userId);
        if(! customer.isPresent()) {
            LOGGER.info("Customer with user id {} not found in the database", userId);
            throw new CustomerNotFoundException("Customer Not Found");
        }
        return customer.get();
    }
    
    /** 
     * Returns Customer's Ezreach Profile and GST profile bundled in CustomerDto
     * @param userId
     * @return
     * @throws Exception
     */
    public CustomerDto getCustomer(UUID userId) throws Exception {
    	CustomerDto customerDto = new CustomerDto();
    	Customer customer = findCustomer(userId);
        
        try {
        	GstProfile gstProfile = gstProfileService.getGstProfile(userId);
        	customerDto.setGstDetails(gstProfile.getGstDetails());
        } catch (GstNotFoundException gstNotFoundException) {
        	LOGGER.info("GST profile not created yet!");
        }
        customerDto.setEmail(customer.getEmail());
        customerDto.setGstin(customer.getGstin());
        customerDto.setMobile(customer.getMobile());
        customerDto.setName(customer.getName());
        customerDto.setPan(customer.getPan());
        customerDto.setUdyogAadhaar(customer.getUdyogAadhaar());

        return customerDto;
    }

}
