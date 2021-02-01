package com.ezyreach.profiling.service;

import com.ezyreach.profiling.entity.Customer;
import com.ezyreach.profiling.entity.UserInput;
import com.ezyreach.profiling.repository.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class ProfileService {

    private CustomerDao customerDao;
    private RestTemplate restTemplate;

    @Value("${gst.baseUrl}")
    private String baseUrl;

    @Autowired
    public ProfileService(CustomerDao customerDao, RestTemplate restTemplate) {
        this.customerDao = customerDao;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public Customer saveCustomer(Customer customer) {
        return customerDao.save(customer);
    }

    public Object getDataFromGst(String gstin) {
        try{
            return restTemplate.getForObject(baseUrl + "/" + gstin, Object.class);
        }
        catch (HttpClientErrorException e) {}
        return null;
    }

    @Transactional
    public void createCustomerProfile(UserInput userInput) {
        //Get data from GST server
        String gstin = userInput.getGstin();
        Object customerDetails = getDataFromGst(gstin);

        //Creating customer profile
        Customer customer = new Customer();
        customer.setCustomerId(UUID.randomUUID().toString());
        customer.setGstin(gstin);
        customer.setPan(userInput.getPan());
        customer.setUdyogAadhaar(userInput.getUdyogAadhaar());
        customer.setEmail(null);
        customer.setName(null);
        customer.setMobile(null);

        //Saving the customer in database
        saveCustomer(customer);
    }
}
