package com.ezreach.customer.profile.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ezreach.customer.profile.entity.Customer;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

@ExtendWith(SpringExtension.class)
@AutoConfigureEmbeddedDatabase(beanName="dataSource")
@SpringBootTest
public class CustomerRepoTest {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private CustomerDao customerDao;
    
    @Test
	public void testCustomerDaoIsNotNull() {
    	assertThat(customerDao).isNotNull();
    }
    
    public Customer returnCustomer(UUID userId) {
    	Customer customer = new Customer(
				UUID.randomUUID(),
    			"name",
    			"gstin",
    			"pan",
    			"udyogAadhaar",
    			"email",
    			"mobile",
    			10000,
				userId);
    	return customer;
    }
    
    @Test
    @DisplayName("test save customer - success")
    public void testSaveCustomer() {
    	UUID customerId = UUID.randomUUID();
    	Customer mockCustomer = returnCustomer(customerId);
    	Customer returnedCustomer = customerDao.save(mockCustomer);
    	assertEquals(mockCustomer.getCustomerId(), returnedCustomer.getCustomerId());
    }
    
    @Test
    @DisplayName("test find customer - success")
    public void testFindCustomer() {
    	UUID userId = UUID.randomUUID();
    	Customer mockCustomer = returnCustomer(userId);
    	
    	customerDao.save(mockCustomer);
    	Optional<Customer> returnedCustomer = customerDao.findByUserId(userId);
    	assertThat(returnedCustomer.isPresent());
    	assertEquals(mockCustomer.getUserId(), returnedCustomer.get().getUserId());
		assertEquals(mockCustomer.getCustomerId(), returnedCustomer.get().getCustomerId());
		assertEquals(mockCustomer.getCustomerId(), returnedCustomer.get().getCustomerId());
		assertEquals(mockCustomer.getName(), returnedCustomer.get().getName());
    }

}
