package com.ezreach.customer.profile.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import javax.sql.DataSource;

import com.ezreach.customer.profile.configuration.TokenVerifier;
import com.ezreach.customer.profile.entity.Customer;
import com.ezreach.customer.profile.entity.TokenInfo;
import com.ezreach.customer.profile.entity.UserInput;
import com.ezreach.customer.profile.exception.CustomerNotFoundException;
import com.ezreach.customer.profile.service.CustomerProfileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureEmbeddedDatabase(beanName="dataSource")
public class CustomerProfileControllerTest {

	final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TokenVerifier tokenVerifier;

	@MockBean
	private CustomerProfileService mockService;

	@Test
	public void mockMvcIsNotNull() {
		assertThat(mockMvc).isNotNull();
	}

	public String readFile(String fileName) throws Exception {
        String data = null;
		Resource resource = new ClassPathResource(fileName);
        InputStream inputStream = resource.getInputStream();
        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            data = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("IOException", e);
        }
        return data;
	}
	
	public Customer returnMockCustomer(UUID customerId) {
		Customer mockCustomer = new Customer(
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
		return mockCustomer;
	}
	
	@Test
	@DisplayName("Test createProfile - success")
	public void shouldReturnHttpStatusCreated() throws Exception {
		//Reader header value from a file
	    String token = readFile("validToken.txt");
	    
		UserInput userInput = new UserInput("05ABNTY3290P8ZA", "CPAA1234AB", "123456789012", 10000);
		TokenInfo tokenInfo = tokenVerifier.verifyToken(token);
		
		ObjectMapper mapper = new ObjectMapper();
	    String jsonString = mapper.writeValueAsString(userInput);
	    
	    doNothing().when(mockService).createCustomerProfile(userInput, tokenInfo);
 
		mockMvc.perform(post("/v1/customer/profile")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.header("authorization", token))
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("Test createProfile fail - Bad Request")
	public void shouldReturnHttpStatusBadRequest() throws Exception {
		//Reader header value from a file
	    String token = readFile("validToken.txt");
	    
		//GSTIN is null
		UserInput userInput = new UserInput(null, "CPAA1234AB", "123456789012", 10000);
		TokenInfo tokenInfo = tokenVerifier.verifyToken(token);
		ObjectMapper mapper = new ObjectMapper();
	    String jsonString = mapper.writeValueAsString(userInput);
		
	    doNothing().when(mockService).createCustomerProfile(userInput, tokenInfo);
	    
		mockMvc.perform(post("/v1/customer/profile")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.header("authorization", token))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Test createProfile fail - Token Expired")
	public void shouldReturnHttpStatusTokenExpired() throws Exception {
		//Reader header value from a file (expired token)
	    String token = readFile("expiredToken.txt");
		
		UserInput userInput = new UserInput("05ABNTY3290P8ZA", "CPAA1234AB", "123456789012", 10000);
		TokenInfo tokenInfo = new TokenInfo(UUID.randomUUID(), "email@demo.com", "7788226644");
		ObjectMapper mapper = new ObjectMapper();
	    String jsonString = mapper.writeValueAsString(userInput);
		
	    doNothing().when(mockService).createCustomerProfile(userInput, tokenInfo);

		mockMvc.perform(post("/v1/customer/profile")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.header("authorization", token))
				.andExpect(status().isForbidden());
	}
	
	@Test
	@DisplayName("Test fetchProfile - success")
	public void shouldReturnHttpStatusFound() throws Exception {
		UUID customerId = UUID.fromString("42aa5557-3482-46ff-a754-c69b64d04e4b");
		Customer mockCustomer = returnMockCustomer(customerId);
		doReturn(mockCustomer).when(mockService).findCustomer(customerId);
		
		//Reader header value from a file (expired token)
	    String token = readFile("validToken.txt");
	    
		mockMvc.perform(get("/v1/customer/profile/{customerId}", customerId)
				.header("authorization", token))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound());
	}
	
	@Test
	@DisplayName("Test fetchProfile - failed - CustomerNotFound")
	public void shouldReturnHttpStatusNotFound() throws Exception {
		UUID customerId = UUID.fromString("42aa5557-3482-46ff-a754-c69b64d04e4b");

		CustomerNotFoundException exception = new CustomerNotFoundException("Customer Not Found");
		doThrow(exception).when(mockService).findCustomer(customerId);
		
		//Reader header value from a file (expired token)
	    String token = readFile("validToken.txt");
	    
		mockMvc.perform(get("/v1/customer/profile/{customerId}", customerId)
				.header("authorization", token))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Test deleteProfile - success")
	public void shouldReturnHttpStatusOk() throws Exception{
		UUID customerId = UUID.fromString("42aa5557-3482-46ff-a754-c69b64d04e4b");
		doNothing().when(mockService).deleteCustomer(customerId);
		
		//Reader header value from a file (expired token)
	    String token = readFile("validToken.txt");
	    
		mockMvc.perform(delete("/v1/customer/profile/{customerId}", customerId)
				.header("authorization", token))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Test updateProfile - success")
	public void testUpdateProfile() throws Exception {
		//Reader header value from a file (expired token)
	    String token = readFile("validToken.txt");
	    
		UserInput userInput = new UserInput("05ABNTY3290P8ZA", "CPAA1234AB", "123456789012", 10000);
		ObjectMapper mapper = new ObjectMapper();
	    String jsonString = mapper.writeValueAsString(userInput);
	    
		TokenInfo tokenInfo = tokenVerifier.verifyToken(token);
		UUID customerId = UUID.fromString("42aa5557-3482-46ff-a754-c69b64d04e4b");
		doNothing().when(mockService).updateCustomer(customerId, userInput, tokenInfo);

		mockMvc.perform(put("/v1/customer/profile/{customerId}", customerId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.header("authorization", token))
				.andExpect(status().isOk());
	}
}
