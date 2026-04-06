package com.app.quantitymeasurement.measurementservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/api/convert/test")
	public String test() {
		return "Measurement Service Working";
	}
}