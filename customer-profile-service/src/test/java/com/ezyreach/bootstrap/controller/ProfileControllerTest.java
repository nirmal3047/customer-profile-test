package com.ezyreach.bootstrap.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class ProfileControllerTest {

	ProfileController controller = new ProfileController();
	
	public ProfileControllerTest() {}
	
	@Test
	@DisplayName("sanity check")
	public void greetingTest() {
		ResponseEntity<String> re = new ResponseEntity<String>("welcome", HttpStatus.OK);
		assertEquals(re, controller.greeting());
	}
}
