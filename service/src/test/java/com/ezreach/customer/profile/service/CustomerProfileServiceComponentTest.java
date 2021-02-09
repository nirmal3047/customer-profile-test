package com.ezreach.customer.profile.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;

@SpringBootTest
@TestPropertySource(locations="classpath:application.properties")
public class CustomerProfileServiceComponentTest {

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
    public void demoTest(){
    	Object obj = customerProfileService.getDataFromGst("1");
    	assertThat(obj).isNotNull();
    }
}
