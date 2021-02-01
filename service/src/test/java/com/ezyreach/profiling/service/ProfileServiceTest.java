package com.ezyreach.profiling.service;

import com.ezyreach.profiling.entity.Customer;
import com.ezyreach.profiling.repository.CustomerDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @MockBean
    private CustomerDao customerDao;

    @Test
    public void serviceIsNotNull() {
        assertThat(profileService).isNotNull();
    }

    @Test
    @DisplayName("Test save customer")
    public void testSave() {
        Customer mockCustomer = new Customer("1","2","3","4","5",
                "6","7", 123.0);
        doReturn(mockCustomer).when(customerDao).save(any());
        Customer returnedCustomer = profileService.saveCustomer(mockCustomer);

        assertNotNull(returnedCustomer);
        assertEquals(mockCustomer, returnedCustomer, "should be same object");
    }

    public void test() {

    }
}