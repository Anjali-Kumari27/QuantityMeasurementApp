package com.app.quantitymeasurement.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QuantityMeasurementExceptionTest {

	@Test
	void testConstructor_WithMessage() {
		QuantityMeasurementException ex = new QuantityMeasurementException("Sample error");
		assertEquals("Sample error", ex.getMessage());
	}

	@Test
	void testConstructor_WithMessageAndCause() {
		Throwable cause = new RuntimeException("Root cause");
		QuantityMeasurementException ex = new QuantityMeasurementException("Wrapped error", cause);

		assertEquals("Wrapped error", ex.getMessage());
		assertEquals(cause, ex.getCause());
	}
}