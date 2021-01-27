package com.ezyreach.bootstrap.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProfileController.class)
@AutoConfigureMockMvc
public class ProfileControllerTest {

	private MockMvc mockMvc;
	
	@Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ProfileController()).build();
    }

	@Test
	public void controllerIsNotNull() {
		assertThat(mockMvc).isNotNull();
	}
	
	@Test
	public void shouldReturnHttpStatusOk() throws Exception {
		this.mockMvc.perform(get("/v1/customer/greeting")).andExpect(status().isOk());
	}
	
	/*@Test
	public void shouldReturnHttpStatusCreated() throws Exception {
		this.mockMvc.perform(post("/v1/customer/profile")).andExpect(status().isCreated());
	}*/
}
