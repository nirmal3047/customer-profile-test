package com.ezreach.customer.profile.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ezreach.customer.profile.entity.Customer;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase(beanName="dataSource")
public class CustomerRepoTest {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private CustomerDao customerDao;
    
    @Test
	public void testCustomerDaoisNotNull() {
    	assertThat(customerDao).isNotNull();
    }
    
    /*@Test
    public void testSaveCustomer() {
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
    	Customer returnedCustomer = customerDao.save(mockCustomer);
    	assertEquals(mockCustomer, returnedCustomer);
    }*/
    
    public void testFindCustomer() {
    	UUID customerId = UUID.randomUUID();
    	Customer mockCustomer = new Customer(
    			customerId,
    			"name",
    			"gstin",
    			"pan",
    			"udyogAadhaar",
    			"email",
    			"mobile",
    			10000,
    			"address",
    			UUID.randomUUID());
    	customerDao.save(mockCustomer);
    	Optional<Customer> returnedCustomer = customerDao.findById(customerId);
    	assertThat(returnedCustomer.isPresent());
    	assertEquals(mockCustomer, returnedCustomer.get());
    }

}
