package com.ezreach.customer.profile.controller;

import com.ezreach.customer.profile.entity.Customer;
import com.ezreach.customer.profile.entity.UserInput;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/v1/customer")
public interface CustomerProfileControllerInterface {

    @PostMapping("/profile")
    public ResponseEntity<Void> createProfile(@RequestHeader("authorization") String header,
                                              @Valid @RequestBody UserInput userInput, BindingResult bindingResult)
            throws Exception;

    @PutMapping("/profile/{customerId}")
    public ResponseEntity<Void> updateProfile(@RequestHeader("authorization") String header,
                                              @Valid @RequestBody UserInput userInput, BindingResult bindingResult,
                                              @PathVariable("customerId") UUID customerId) throws Exception;

    @GetMapping("/profile/{customerId}")
    public ResponseEntity<Customer> fetchProfile(@RequestHeader("authorization") String header,
    											@PathVariable("customerId") UUID customerId) throws Exception;

    @DeleteMapping("/profile/{customerId}")
    public ResponseEntity<Void> deleteProfile(@RequestHeader("authorization") String header,
    										@PathVariable("customerId") UUID customerId) throws Exception;
}
