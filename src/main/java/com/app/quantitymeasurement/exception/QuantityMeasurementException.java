package com.app.quantitymeasurement.exception;

/**
 * QuantityMeasurementException
 *
 * Custom runtime exception used in service/controller layers.
 */
public class QuantityMeasurementException extends RuntimeException {

	public QuantityMeasurementException(String message) {
		super(message);
	}

	public QuantityMeasurementException(String message, Throwable cause) {
		super(message, cause);
	}
}