package com.apps.quantitymeasurement.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.model.QuantityMeasurementEntity;

public class QuantityMeasurementEntityTest {

	@Test
	void testQuantityEntity_SingleOperandConstruction() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity("CONVERT", "QuantityDTO(1.0, FEET, Length)",
				"QuantityDTO(12.0, INCHES, Length)");

		assertEquals("CONVERT", entity.getOperationType());
		assertEquals("QuantityDTO(1.0, FEET, Length)", entity.getFirstOperand());
		assertNull(entity.getSecondOperand());
		assertEquals("QuantityDTO(12.0, INCHES, Length)", entity.getResult());
		assertFalse(entity.isError());
	}

	@Test
	void testQuantityEntity_BinaryOperandConstruction() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity("ADD", "QuantityDTO(1.0, FEET, Length)",
				"QuantityDTO(12.0, INCHES, Length)", "QuantityDTO(2.0, FEET, Length)");

		assertEquals("ADD", entity.getOperationType());
		assertEquals("QuantityDTO(1.0, FEET, Length)", entity.getFirstOperand());
		assertEquals("QuantityDTO(12.0, INCHES, Length)", entity.getSecondOperand());
		assertEquals("QuantityDTO(2.0, FEET, Length)", entity.getResult());
		assertFalse(entity.isError());
	}

	@Test
	void testQuantityEntity_ErrorConstruction() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity("ADD",
				"QuantityDTO(0.0, CELSIUS, Temperature)", "QuantityDTO(32.0, FAHRENHEIT, Temperature)", null, true,
				"Temperature does not support arithmetic operations");

		assertEquals("ADD", entity.getOperationType());
		assertTrue(entity.isError());
		assertEquals("Temperature does not support arithmetic operations", entity.getErrorMessage());
	}

	@Test
	void testQuantityEntity_ToString_Success() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity("COMPARE", "QuantityDTO(1.0, FEET, Length)",
				"QuantityDTO(12.0, INCHES, Length)", "true");

		String text = entity.toString();

		assertTrue(text.contains("SUCCESS"));
		assertTrue(text.contains("COMPARE"));
		assertTrue(text.contains("1.0, FEET"));
		assertTrue(text.contains("12.0, INCHES"));
		assertTrue(text.contains("true"));
	}

	@Test
	void testQuantityEntity_ToString_Error() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity("DIVIDE", "QuantityDTO(10.0, FEET, Length)",
				"QuantityDTO(0.0, FEET, Length)", null, true, "Cannot divide by zero");

		String text = entity.toString();

		assertTrue(text.contains("ERROR"));
		assertTrue(text.contains("Cannot divide by zero"));
	}
}