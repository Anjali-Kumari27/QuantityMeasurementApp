package com.app.quantitymeasurement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application class
 *
 * This is the entry point of your UC17 application.
 *
 * What it does: - Starts Spring Boot - Initializes IoC container - Scans all
 * components (controller, service, repository)
 *
 * VERY IMPORTANT: - This replaces your UC16 main method
 */
@SpringBootApplication
public class QuantityMeasurementApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuantityMeasurementApplication.class, args);
	}
}