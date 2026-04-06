package com.app.quantitymeasurement.measurementservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.quantitymeasurement.measurementservice.service.QuantityMeasurementServiceImpl;

@RestController
public class FeignTestController {

    @Autowired
    private QuantityMeasurementServiceImpl quantityMeasurementService;

    @GetMapping("/api/convert/user-check")
    public String checkUserService() {
        return quantityMeasurementService.checkUserService();
    }
}