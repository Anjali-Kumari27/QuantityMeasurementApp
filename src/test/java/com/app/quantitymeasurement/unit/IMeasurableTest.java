package com.app.quantitymeasurement.unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IMeasurableTest {

	@Test
	void testConvertTo_DefaultMethod() {
		double result = LengthUnit.FEET.convertTo(1.0, LengthUnit.INCHES);
		assertEquals(12.0, result);
	}

	@Test
	void testGetMeasurementType() {
		assertEquals("LengthUnit", LengthUnit.FEET.getMeasurementType());
		assertEquals("WeightUnit", WeightUnit.KILOGRAM.getMeasurementType());
	}
}