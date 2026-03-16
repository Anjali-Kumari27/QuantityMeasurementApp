package com.app.quantitymeasurement.exception;

/**
 * UC15: QuantityMeasurementException is a custom unchecked exception used for
 * all quantity-measurement-related failures.
 *
 * Responsibilities: - Represents invalid operations in the quantity system -
 * Centralizes error reporting in service and repository layers - Improves
 * consistency of error handling across the application
 *
 * Why Custom Exception: Using a domain-specific exception makes the code easier
 * to understand, debug, and maintain compared to throwing generic exceptions
 * everywhere.
 *
 * Architectural Role: This exception belongs to the exception layer and
 * supports centralized business-level error propagation.
 */
public class QuantityMeasurementException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public QuantityMeasurementException(String message) {
		super(message);
	}

	public QuantityMeasurementException(String message, Throwable cause) {
		super(message, cause);
	}
}