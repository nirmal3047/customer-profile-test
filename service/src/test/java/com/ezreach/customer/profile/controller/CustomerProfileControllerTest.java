package com.ezreach.customer.profile.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.ezreach.customer.profile.entity.UserInput;
import com.ezreach.customer.profile.service.CustomerProfileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerProfileControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerProfileService mockService;

	@Test
	public void mockMvcIsNotNull() {
		assertThat(mockMvc).isNotNull();
	}

	@Test
	public void shouldReturnHttpStatusOk() throws Exception {
		mockMvc.perform(get("/v1/customer/greeting")).andExpect(status().isOk());
	}
	
	@Test
	public void shouldReturnWelcome() throws Exception {
		mockMvc.perform(get("/v1/customer/greeting"))
					.andExpect(content().string(containsString("welcome")));
	}
	
	@Test
	@DisplayName("Test createProfile")
	public void shouldReturnHttpStatusCreated() throws Exception {
		UserInput userInput = new UserInput("gstin", "pan", "udyogAadhaar", 123);
		ObjectMapper mapper = new ObjectMapper();
	    String jsonString = mapper.writeValueAsString(userInput);
		
		mockMvc.perform(post("/v1/customer/profile")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.header("key", "value"))
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("Test fetchProfile")
	public void shouldReturnHttpStatusFound() {

	}

	@Test
	@DisplayName("Test deleteProfile")
	public void shouldReturnHttpStatus() {

	}

	@Test
	@DisplayName("Test updateProfile")
	public void testUpdateProfile() {

	}
}
