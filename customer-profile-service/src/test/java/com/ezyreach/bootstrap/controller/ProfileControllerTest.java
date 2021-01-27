package com.ezyreach.bootstrap.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.ezyreach.bootstrap.entity.UserInput;
import com.ezyreach.bootstrap.service.ProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProfileController.class)
@AutoConfigureMockMvc
public class ProfileControllerTest {

	private static MockMvc mockMvc;
	
	@MockBean
    private static ProfileService mockService;
	
	@MockBean
    private static ProfileService mockService;
	
	@BeforeClass
    public static void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(
        		new ProfileController(restTemplate, mockService)).build();
    }

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
	public void shouldReturnHttpStatusCreated() throws Exception {
		UserInput userInput = new UserInput("05ABNTY3290P8ZA", "CPAA1234AB", "123456789012", 10000);
		ObjectMapper mapper = new ObjectMapper();
	    String jsonString = mapper.writeValueAsString(userInput);
		
		mockMvc.perform(post("/v1/customer/profile").
				contentType(MediaType.APPLICATION_JSON)
				.content(jsonString))
				.andExpect(status().isCreated());
	}
	
	
}
