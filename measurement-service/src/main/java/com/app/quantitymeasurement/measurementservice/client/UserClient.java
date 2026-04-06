package com.app.quantitymeasurement.measurementservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users/test")
    String testUserService();
}