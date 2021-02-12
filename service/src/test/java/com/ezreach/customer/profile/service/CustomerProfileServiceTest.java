package com.ezreach.customer.profile.service;

import com.ezreach.customer.profile.entity.Customer;
import com.ezreach.customer.profile.exception.CustomerNotFoundException;
import com.ezreach.customer.profile.repository.CustomerDao;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureEmbeddedDatabase(beanName="dataSource")
class CustomerProfileServiceTest {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
    private CustomerProfileService customerProfileService;

    @MockBean
    private CustomerDao customerDao;

    public Customer returnCustomer(UUID customerId) {
    	Customer customer = new Customer(
    			customerId,
    			"name",
    			"gstin",
    			"pan",
    			"udyogAadhaar",
    			"email",
    			"mobile",
    			10000,
    			"gst_details",
    			UUID.randomUUID());
    	return customer;
    }
    
    @Test
    public void serviceIsNotNull() {
        assertThat(customerProfileService).isNotNull();
    }

    @Test
    @DisplayName("Test save customer - success")
    public void testSave() {
    	UUID customerId = UUID.randomUUID();
        Customer mockCustomer = returnCustomer(customerId);
        
        doReturn(mockCustomer).when(customerDao).save(any());
        Customer returnedCustomer = customerProfileService.saveCustomer(mockCustomer);

        assertNotNull(returnedCustomer);
        assertEquals(mockCustomer, returnedCustomer);
    }

    @Test
    @DisplayName("Test find customer - success")
    public void testFindCustomerSuccess() throws CustomerNotFoundException {
    	UUID customerId = UUID.randomUUID();
        Customer mockCustomer = returnCustomer(customerId);
    	
    	doReturn(Optional.of(mockCustomer)).when(customerDao).findById(customerId);
    	Customer returnedCustomer = customerProfileService.findCustomer(customerId);
    	
    	assertNotNull(returnedCustomer);
        assertEquals(mockCustomer, returnedCustomer);
    }

	@Test
	@DisplayName("Test find customer - fail")
    public void testFindCustomerFail() throws CustomerNotFoundException {
		UUID customerId = UUID.randomUUID();
        Customer mockCustomer = returnCustomer(customerId);
        
    	doReturn(Optional.empty()).when(customerDao).findById(customerId);
    	boolean exceptionThrown = false;
    	try {
    		customerProfileService.findCustomer(customerId);
    	} catch(CustomerNotFoundException e) {
    		exceptionThrown = true;
    	}
    	assertTrue(exceptionThrown);
	}

}