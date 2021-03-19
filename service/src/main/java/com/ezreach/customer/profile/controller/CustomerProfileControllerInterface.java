package com.ezreach.customer.profile.controller;

import com.ezreach.customer.profile.entity.CustomerDto;
import com.ezreach.customer.profile.entity.UserInput;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/v1/customer")
public interface CustomerProfileControllerInterface {

    /**
     * This endpoint creates the customer profile based on details entered by the user
     * and data fetched from GST server and persists the profile in customer table in the database
     * @param header
     * @param userInput
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PostMapping("/profile")
    public ResponseEntity<Void> createProfile(@RequestHeader("Authorization") String header,
                                              @Valid @RequestBody UserInput userInput, BindingResult bindingResult)
            throws Exception;

    /**
     *
     * @param header
     * @param userInput
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PutMapping("/profile/{profileType}")
    public ResponseEntity<Void> updateProfile(@RequestHeader("Authorization") String header,
                                   @Valid @RequestBody UserInput userInput, BindingResult bindingResult,
                                   @PathVariable("profileType") String profileType) throws Exception;

    /**
     * This endpoint fetches the Customer details from database for given user id
     * @param header
     * @return
     * @throws Exception
     */
    @GetMapping("/profile")
    public ResponseEntity<CustomerDto> fetchProfile(@RequestHeader("Authorization") String header) throws Exception;
}
