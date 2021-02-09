package com.ezreach.customer.profile.service;

import com.ezreach.customer.profile.entity.Customer;
import com.ezreach.customer.profile.repository.CustomerDao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomerProfileServiceTest {

    @Autowired
    private CustomerProfileService customerProfileService;

    @MockBean
    private CustomerDao customerDao;

    @Test
    public void serviceIsNotNull() {
        assertThat(customerProfileService).isNotNull();
    }

    @Test
    @DisplayName("Test save customer")
    public void testSave() {
        Customer mockCustomer = new Customer(
    			UUID.randomUUID(),
    			"name",
    			"gstin",
    			"pan",
    			"udyogAadhaar",
    			"email",
    			"mobile",
    			10000,
    			"address",
    			UUID.randomUUID());
        doReturn(mockCustomer).when(customerDao).save(any());
        Customer returnedCustomer = customerProfileService.saveCustomer(mockCustomer);

        assertNotNull(returnedCustomer);
        assertEquals(mockCustomer, returnedCustomer);
    }

    /*@Test
    public void testFindCustomer() throws Exception {
    	UUID customerId = UUID.randomUUID();
    	customerProfileService.findCustomer(customerId);
    }

    @Test
    public void testUpdateCustomer() {
        //customerProfileService.updateCustomer();
    }*/

}