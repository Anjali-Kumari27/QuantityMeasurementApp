package com.app.quantitymeasurement.unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class WeightUnitTest {

	@Test
	void testToBase_GramToKilogram() {
		assertEquals(1.0, WeightUnit.GRAM.toBase(1000.0));
	}

	@Test
	void testFromBase_KilogramToGram() {
		assertEquals(1000.0, WeightUnit.GRAM.fromBase(1.0));
	}

	@Test
	void testGetUnitName() {
		assertEquals("KILOGRAM", WeightUnit.KILOGRAM.getUnitName());
	}
}