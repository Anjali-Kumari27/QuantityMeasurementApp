package com.app.quantitymeasurement.unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LengthUnitTest {

	@Test
	void testToBase_InchesToFeet() {
		assertEquals(1.0, LengthUnit.INCHES.toBase(12.0));
	}

	@Test
	void testFromBase_FeetToInches() {
		assertEquals(12.0, LengthUnit.INCHES.fromBase(1.0));
	}

	@Test
	void testGetUnitName() {
		assertEquals("FEET", LengthUnit.FEET.getUnitName());
	}
}