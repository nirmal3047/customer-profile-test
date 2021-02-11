package com.ezreach.customer.profile.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.ezreach.customer.profile.entity.UserInput;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerProfileControllerTest {

	final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerProfileService mockService;

	//@Test
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
	
	//@Test
	//@DisplayName("Test createProfile success")
	public void shouldReturnHttpStatusCreated() throws Exception {
		UserInput userInput = new UserInput("05ABNTY3290P8ZA", "CPAA1234AB", "123456789012", 10000);
		ObjectMapper mapper = new ObjectMapper();
	    String jsonString = mapper.writeValueAsString(userInput);
		
	    //Reader header value from a file
	    String headerString = readFile("validToken.txt");
	    
		mockMvc.perform(post("/v1/customer/profile")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.header("authorization", headerString))
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("Test createProfile fail - Bad Request")
	public void shouldReturnHttpStatusBadRequest() throws Exception {
		//GSTIN is null
		UserInput userInput = new UserInput(null, "CPAA1234AB", "123456789012", 10000);
		ObjectMapper mapper = new ObjectMapper();
	    String jsonString = mapper.writeValueAsString(userInput);
		
	    //Reader header value from a file
	    String headerString = readFile("validToken.txt");
	    
		mockMvc.perform(post("/v1/customer/profile")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.header("authorization", headerString))
				.andExpect(status().isBadRequest());
	}
	
	//@Test
	//@DisplayName("Test createProfile fail - Token Expired")
	public void shouldReturnHttpStatusTokenExpired() throws Exception {
		UserInput userInput = new UserInput("05ABNTY3290P8ZA", "CPAA1234AB", "123456789012", 10000);
		ObjectMapper mapper = new ObjectMapper();
	    String jsonString = mapper.writeValueAsString(userInput);
		
	    //Reader header value from a file (expired token)
	    String headerString = readFile("expiredToken.txt");
	    
		mockMvc.perform(post("/v1/customer/profile")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.header("authorization", headerString))
				.andExpect(status().isInternalServerError());
	}
	
	//@Test
	//@DisplayName("Test fetchProfile - success")
	public void shouldReturnHttpStatusFound() {

	}

	//@Test
	//@DisplayName("Test deleteProfile - success")
	public void shouldReturnHttpStatus() {

	}

	//@Test
	//@DisplayName("Test updateProfile - success")
	public void testUpdateProfile() {

	}
}
