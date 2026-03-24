package com.app.quantitymeasurement.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import org.junit.jupiter.api.Test;

class TemperatureUnitTest {

	@Test
	void testToBase_FahrenheitToCelsius() {
		assertEquals(0.0, TemperatureUnit.FAHRENHEIT.toBase(32.0));
	}

	@Test
	void testFromBase_CelsiusToFahrenheit() {
		assertEquals(32.0, TemperatureUnit.FAHRENHEIT.fromBase(0.0));
	}

	@Test
	void testValidateOperationSupport_ShouldThrowException() {
		assertThrows(QuantityMeasurementException.class, () -> TemperatureUnit.CELSIUS.validateOperationSupport("add"));
	}
}