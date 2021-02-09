package com.ezreach.customer.profile.controller;

import com.ezreach.customer.profile.entity.Customer;
import com.ezreach.customer.profile.entity.UserInput;
import com.ezreach.customer.profile.exception.GstNotFoundException;
import com.ezreach.customer.profile.exception.GstServerDownException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/v1/customer")
public interface CustomerProfileControllerInterface {

    @GetMapping("/greeting")
    public ResponseEntity<String> greeting();

    @PostMapping("/profile")
    public ResponseEntity<Void> createProfile(@RequestHeader("authorization") Object header,
                                              @Valid @RequestBody UserInput userInput, BindingResult bindingResult)
            throws JsonProcessingException, GstServerDownException;

    @PutMapping("/profile/{customerId}")
    public ResponseEntity<Void> updateProfile(@RequestHeader("authorization") Object header,
                                              @Valid @RequestBody UserInput userInput, BindingResult bindingResult,
                                              @PathVariable("customerId") UUID customerId) throws Exception;

    @GetMapping("/profile/{customerId}")
    public ResponseEntity<Customer> fetchProfile(@RequestHeader("authorization") Object header,
    											@PathVariable("customerId") UUID customerId) throws Exception;

    @DeleteMapping("/profile/{customerId}")
    public ResponseEntity<Void> deleteProfile(@RequestHeader("authorization") Object header,
    										@PathVariable("customerId") UUID customerId) throws Exception;
}
