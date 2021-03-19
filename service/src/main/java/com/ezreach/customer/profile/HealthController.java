package com.ezreach.customer.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @Autowired
    public HealthController() {
    }

    @GetMapping(path = "/health")
    public String health(){
        return "Up";
    }
}
