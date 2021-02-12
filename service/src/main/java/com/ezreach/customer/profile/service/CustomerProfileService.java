package com.ezreach.customer.profile.service;

import com.ezreach.customer.profile.entity.Customer;
import com.ezreach.customer.profile.entity.TokenInfo;
import com.ezreach.customer.profile.entity.UserInput;
import com.ezreach.customer.profile.exception.CustomerNotFoundException;
import com.ezreach.customer.profile.exception.GstNotFoundException;
import com.ezreach.customer.profile.exception.GstServerDownException;
import com.ezreach.customer.profile.repository.CustomerDao;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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

    public Object getDataFromGst(String gstin) throws GstServerDownException, GstNotFoundException {
        try{
            return restTemplate.getForObject(baseUrl + "/" + gstin, Object.class);
        }
        catch (HttpClientErrorException.NotFound e) {
        	throw new GstNotFoundException("GST Not Found", gstin);
        }
        catch (HttpClientErrorException e) {
        	throw new GstServerDownException(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public Customer setCustomerDetails(UserInput userInput, Customer customer, TokenInfo tokenInfo)
    		throws Exception {
    	//Get data from GST server
        String gstin = userInput.getGstin();
        Object customerDetails = getDataFromGst(gstin);

        ObjectMapper mapper = new ObjectMapper();
        String gstDetails = mapper.writeValueAsString(customerDetails);
        
        //Getting the name of the organization from GST details
        JSONParser parser = new JSONParser();
        JSONObject jo = (JSONObject) parser.parse(gstDetails);  
        String name = jo.get("lgnm").toString();    //lgnm: Legal name of the organization

        //Creating customer profile
        customer.setGstin(gstin);
        customer.setPan(userInput.getPan());
        customer.setUdyogAadhaar(userInput.getUdyogAadhaar());
        customer.setEmail(tokenInfo.getEmail());
        customer.setTurnover(userInput.getTurnover());
        customer.setName(name);
        customer.setMobile(tokenInfo.getMobile());
        customer.setGstDetails(gstDetails);
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
    public void updateCustomer(UUID customerId, UserInput userInput, TokenInfo tokenInfo)
    		throws Exception {
        //finding existing customer in the database
    	Customer customer = findCustomer(customerId);
    	
    	//Updating customer profile
        customer = setCustomerDetails(userInput, customer, tokenInfo);
        
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
            throw new CustomerNotFoundException("Customer Not Found");
        }
    }

    @Transactional
    public void deleteCustomer(UUID customerId) throws Exception {
        Customer customer = findCustomer(customerId);
        customerDao.delete(customer);
    }
}
