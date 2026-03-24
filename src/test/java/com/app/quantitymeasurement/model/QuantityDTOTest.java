package com.app.quantitymeasurement.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QuantityDTOTest {

	@Test
	void testConstructor_WithAllFields() {
		QuantityDTO dto = new QuantityDTO(1.0, "FEET", "LengthUnit");

		assertEquals(1.0, dto.getValue());
		assertEquals("FEET", dto.getUnit());
		assertEquals("LengthUnit", dto.getMeasurementType());
	}

	@Test
	void testToString() {
		QuantityDTO dto = new QuantityDTO(12.0, "INCHES", "LengthUnit");
		assertTrue(dto.toString().contains("12"));
		assertTrue(dto.toString().contains("INCHES"));
	}

	@Test
	void testGetValue_WhenNull_ShouldReturnZero() {
		QuantityDTO dto = new QuantityDTO();
		dto.setValue(null);

		assertEquals(0.0, dto.getValue());
	}
}