package com.ezreach.customer.profile.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.ezreach.customer.profile.exception.GstNotFoundException;
import com.ezreach.customer.profile.exception.GstServerDownException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

@SpringBootTest
@TestPropertySource(locations="classpath:application.properties")
@AutoConfigureEmbeddedDatabase(beanName="dataSource")
public class CustomerProfileServiceExternalApiTest {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
    private CustomerProfileService customerProfileService;

    private WireMockServer mockServer;
    private String host;
    private int port;

    @BeforeEach
    public void beforeEach() {
    	//start the wiremock server
    	host = "localhost";
    	port = 9999;
    	mockServer = new WireMockServer(port);
    	WireMock.configureFor(host, port);
    	mockServer.start();
    	
    	//configure mock response
    	ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder();
    	mockResponse.withStatus(200)
    				.withHeader("Content-Type", "application/json")
    				.withBodyFile("Taxpayer.json");
    	
    	mockServer.stubFor(WireMock.get("/gst/taxpayer/1")
    				.willReturn(mockResponse));
    	
    	
    	//Stubbing for GST Not Found
    	ResponseDefinitionBuilder mockResponseNotFound = new ResponseDefinitionBuilder();
    	mockResponseNotFound.withStatus(404);
    	mockServer.stubFor(WireMock.get("/gst/taxpayer/2")
    				.willReturn(mockResponseNotFound));
    }
    
    @AfterEach
    public void afterEach() {
    	mockServer.stop();
    }
    
    @Test
    @DisplayName("Fetch GST details - success")
    public void shouldReturnCustomerDetails() throws GstServerDownException, GstNotFoundException{
    	Object obj = customerProfileService.getDataFromGst("1");
    	assertThat(obj).isNotNull();
    }
    
    @Test
    @DisplayName("Fetch GST details - fail")
    public void shouldReturnNotFound() throws GstServerDownException, GstNotFoundException {
    	boolean exceptionThrown = false;
    	try {
    		Object obj = customerProfileService.getDataFromGst("2");
    	} catch (GstNotFoundException e) {
    		exceptionThrown = true;
    	}
    	assertTrue(exceptionThrown);
    }
}
