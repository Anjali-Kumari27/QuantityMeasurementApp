package com.app.quantitymeasurement.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/users/test")
    public String test() {
        return "User Service Working";
    }
}