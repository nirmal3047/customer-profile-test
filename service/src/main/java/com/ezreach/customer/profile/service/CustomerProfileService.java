package com.ezreach.customer.profile.service;

import com.ezreach.customer.profile.entity.Customer;
import com.ezreach.customer.profile.entity.UserInput;
import com.ezreach.customer.profile.exception.CustomerNotFoundException;
import com.ezreach.customer.profile.exception.GstServerDownException;
import com.ezreach.customer.profile.repository.CustomerDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerProfileService {

    private CustomerDao customerDao;
    private RestTemplate restTemplate;

    @Value("${gst.baseUrl}")
    private String baseUrl;

    @Autowired
    public CustomerProfileService(CustomerDao customerDao, RestTemplate restTemplate) {
        this.customerDao = customerDao;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public Customer saveCustomer(Customer customer) {

        return customerDao.save(customer);
    }

    public Object getDataFromGst(String gstin) throws GstServerDownException {
        try{
            return restTemplate.getForObject(baseUrl + "/" + gstin, Object.class);
        }
        catch (HttpClientErrorException e) {
        	throw new GstServerDownException(HttpStatus.INTERNAL_SERVER_ERROR);
        	}
    }

    @Transactional
    public void createCustomerProfile(UserInput userInput)
    		throws JsonProcessingException, GstServerDownException {
        //Get data from GST server
        String gstin = userInput.getGstin();
        Object customerDetails = getDataFromGst(gstin);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(customerDetails);

        //Creating customer profile
        Customer customer = new Customer();
        customer.setCustomerId(UUID.randomUUID());
        customer.setGstin(gstin);
        customer.setPan(userInput.getPan());
        customer.setUdyogAadhaar(userInput.getUdyogAadhaar());
        customer.setEmail("email");
        customer.setTurnover(userInput.getTurnover());
        customer.setName(null);
        customer.setMobile(null);
        customer.setGstDetails(jsonString);
        customer.setUserId(UUID.randomUUID()); //To be edited

        //Saving the customer in database
        saveCustomer(customer);
    }



    @Transactional
    public void updateCustomer(UUID customerId, UserInput userInput)
    		throws CustomerNotFoundException, JsonProcessingException {
        //finding existing customer in the database
    	Customer customer = findCustomer(customerId);
    	
    	//Get data from GST server
        String gstin = userInput.getGstin();
        Object customerDetails = getDataFromGst(gstin);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(customerDetails);
        
        customer.setPan(userInput.getPan());
        customer.setUdyogAadhaar(userInput.getUdyogAadhaar());
        customer.setEmail("email");
        customer.setTurnover(userInput.getTurnover());
        customer.setName(null);
        customer.setMobile(null);
        customer.setGstDetails(jsonString);
        customer.setUserId(UUID.randomUUID()); //To be edited
        
        //Updating the customer in database
        saveCustomer(customer);
    }

    @Transactional
    public Customer findCustomer(UUID customerId) throws CustomerNotFoundException {
        Optional<Customer> customer = customerDao.findById(customerId);
        if(customer.isPresent()){
            return customer.get();
        }
        else {
            throw new CustomerNotFoundException("Customer not found in the database", customerId);
        }
    }

    @Transactional
    public void deleteCustomer(UUID customerId) throws Exception {
        Customer customer = findCustomer(customerId);
        customerDao.delete(customer);
    }
}
