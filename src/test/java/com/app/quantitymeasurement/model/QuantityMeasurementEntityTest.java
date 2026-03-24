package com.app.quantitymeasurement.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QuantityMeasurementEntityTest {

	@Test
	void testCompareConstructor() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(1.0, "FEET", "LengthUnit", 12.0, "INCHES",
				"LengthUnit", "compare", "true");

		assertEquals("compare", entity.getOperation());
		assertEquals("true", entity.getResultString());
		assertFalse(entity.isError());
	}

	@Test
	void testArithmeticConstructor() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(1.0, "FEET", "LengthUnit", 12.0, "INCHES",
				"LengthUnit", "add", 2.0, "FEET", "LengthUnit");

		assertEquals("add", entity.getOperation());
		assertEquals(2.0, entity.getResultValue());
		assertEquals("FEET", entity.getResultUnit());
	}

	@Test
	void testErrorConstructor() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(1.0, "FEET", "LengthUnit", 1.0, "KILOGRAM",
				"WeightUnit", "add", "Different measurement types", true);

		assertTrue(entity.isError());
		assertEquals("Different measurement types", entity.getErrorMessage());
	}
}