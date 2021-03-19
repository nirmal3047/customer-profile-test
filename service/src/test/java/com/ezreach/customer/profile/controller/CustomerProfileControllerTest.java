package com.ezreach.customer.profile.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
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
import com.ezreach.customer.profile.entity.*;
import com.ezreach.customer.profile.exception.CustomerNotFoundException;
import com.ezreach.customer.profile.service.CustomerProfileService;
import com.ezreach.customer.profile.service.GstProfileService;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerProfileControllerTest.class);
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TokenVerifier tokenVerifier;

	@MockBean
	private CustomerProfileService mockService;

	@MockBean
	private GstProfileService mockGstProfileService;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void mockMvcIsNotNull() {
		assertThat(mockMvc).isNotNull();
	}

	//Reading tokens stored in file
	public String readFile(String fileName) throws Exception {
        String data = null;
		Resource resource = new ClassPathResource(fileName);
        InputStream inputStream = resource.getInputStream();
        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            data = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.info("IOException", e);
        }
        return data;
	}
	
	public Customer returnMockCustomer(UUID userId) {
		Customer mockCustomer = new Customer(
				UUID.randomUUID(), "name", "gstin", "pan", "udyogAadhaar", "email",
    			"mobile", 10000, userId);
		return mockCustomer;
	}

	public CustomerDto returnMockCustomerDto() {
		CustomerDto mockCustomerDto = new CustomerDto("name", "gstin", "pan", "udyogAadhar",
				"email", "mobile", 1000, "gstDetails");
		return mockCustomerDto;
	}
	
	@Test
	@DisplayName("Test createProfile - success")
	public void shouldReturnHttpStatusCreated() throws Exception {
		//Reader header value from a file
	    String token = readFile("validToken.txt");
	    
		UserInput userInput = new UserInput(
				"ABC Ltd","05ABNTY3290P8ZA", "CPAA1234AB", "123456789012", 10000);
		TokenInfo tokenInfo = tokenVerifier.verifyToken(token);

	    String jsonString = mapper.writeValueAsString(userInput);
	    
	    doNothing().when(mockService).createCustomerProfile(userInput, tokenInfo);
 
		mockMvc.perform(post("/v1/customer/profile")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.header("Authorization", token))
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("Test createProfile fail - Bad Request")
	public void shouldReturnHttpStatusBadRequest() throws Exception {
		//Reader header value from a file
	    String token = readFile("validToken.txt");
	    
		//Name is null
		UserInput userInput = new UserInput(null, "05ABNTY3290P8ZA", "CPAA1234AB", "123456789012", 10000);
		TokenInfo tokenInfo = tokenVerifier.verifyToken(token);
	    String jsonString = mapper.writeValueAsString(userInput);
		
	    doNothing().when(mockService).createCustomerProfile(userInput, tokenInfo);
	    
		mockMvc.perform(post("/v1/customer/profile")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.header("Authorization", token))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Test createProfile fail - Token Expired")
	public void shouldReturnHttpStatusTokenExpired() throws Exception {
		//Reader header value from a file (expired token)
	    String token = readFile("expiredToken.txt");
		
		UserInput userInput = new UserInput(
				"ABC pvt ltd","05ABNTY3290P8ZA", "CPAA1234AB", "123456789012", 10000);
		TokenInfo tokenInfo = new TokenInfo(UUID.randomUUID(), "email@demo.com", "7788226644");
	    String jsonString = mapper.writeValueAsString(userInput);
		
	    doNothing().when(mockService).createCustomerProfile(userInput, tokenInfo);

		mockMvc.perform(post("/v1/customer/profile")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.header("Authorization", token))
				.andExpect(status().isForbidden());
	}
	
	@Test
	@DisplayName("Test fetchProfile - success")
	public void shouldReturnHttpStatusFound() throws Exception {
		//Reader header value from a file (valid token)
		String token = readFile("validToken.txt");
		TokenInfo tokenInfo = tokenVerifier.verifyToken(token);
		UUID userId = tokenInfo.getUserId();

		CustomerDto mockCustomerDto = returnMockCustomerDto();
		doReturn(mockCustomerDto).when(mockService).getCustomer(userId);
	    
		mockMvc.perform(get("/v1/customer/profile")
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", token))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound());
	}
	
	@Test
	@DisplayName("Test fetchProfile - failed - CustomerNotFound")
	public void shouldReturnHttpStatusNotFound() throws Exception {
		//Reader header value from a file (expired token)
		String token = readFile("validToken.txt");
		TokenInfo tokenInfo = tokenVerifier.verifyToken(token);
		UUID userId = tokenInfo.getUserId();

		//Throwing exception when input for findCustomer is other than the required userId
		CustomerNotFoundException exception = new CustomerNotFoundException("Customer Not Found");
		doThrow(exception).when(mockService).getCustomer(userId);
	    
		mockMvc.perform(get("/v1/customer/profile")
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", token))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Test updateProfile - success")
	public void testUpdateProfile_ShouldReturnHttpStatusCreated() throws Exception {
		//Reader header value from a file (expired token)
	    String token = readFile("validToken.txt");
	    TokenInfo tokenInfo = tokenVerifier.verifyToken(token);
	    UUID userId = tokenInfo.getUserId();

		UserInput userInput = new UserInput(
				"ABC pvt ltd","05ABNTY3290P8ZA", "CPAA1234AB", "123456789012", 10000);
	    String jsonString = mapper.writeValueAsString(userInput);

		//First create customer profile
		doNothing().when(mockService).createCustomerProfile(userInput, tokenInfo);

		//Update the profile
		doNothing().when(mockService).updateCustomer(userId, userInput, tokenInfo);

		//Create GST profile
		String gstin = userInput.getGstin();
		GstProfile gstProfile = new GstProfile(UUID.randomUUID(), userId,"dummy gst details");
		doReturn(gstProfile).when(mockGstProfileService).createGstProfile(userId, gstin);

		mockMvc.perform(put("/v1/customer/profile/{profileType}", "INVOICE")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.header("Authorization", token))
				.andExpect(status().isCreated());
	}
}
