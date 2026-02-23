package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LengthUnitTest {

	private static final double EPS = 1e-6;

	// tests

	@Test
	void testConvertToBaseUnit_FeetToFeet() {
		assertEquals(5.0, LengthUnit.FEET.convertToBaseUnit(5.0), EPS);
	}

	@Test
	void testConvertToBaseUnit_InchesToFeet() {
		assertEquals(1.0, LengthUnit.INCHES.convertToBaseUnit(12.0), EPS);
	}

	@Test
	void testConvertToBaseUnit_YardsToFeet() {
		assertEquals(3.0, LengthUnit.YARDS.convertToBaseUnit(1.0), EPS);
	}

	@Test
	void testConvertToBaseUnit_CentimetersToFeet() {
		// 30.48 cm = 1 feet
		assertEquals(1.0, LengthUnit.CENTIMETERS.convertToBaseUnit(30.48), 1e-3);
	}

	@Test
	void testConvertFromBaseUnit_FeetToInches() {
		assertEquals(12.0, LengthUnit.INCHES.convertFromBaseUnit(1.0), EPS);
	}

	@Test
	void testConvertFromBaseUnit_FeetToYards() {
		assertEquals(1.0, LengthUnit.YARDS.convertFromBaseUnit(3.0), EPS);
	}

	@Test
	void testConvertFromBaseUnit_FeetToCentimeters() {
		assertEquals(30.48, LengthUnit.CENTIMETERS.convertFromBaseUnit(1.0), 1e-3);
	}

	// QuantityLength delegation tests

	@Test
	void testQuantityLengthRefactored_Equality() {
		assertTrue(new QuantityLength(1.0, LengthUnit.FEET).equals(new QuantityLength(12.0, LengthUnit.INCHES)));
	}

	@Test
	void testQuantityLengthRefactored_ConvertTo() {
		QuantityLength converted = new QuantityLength(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES);
		assertEquals(12.0, converted.getValue(), EPS);
		assertEquals(LengthUnit.INCHES, converted.getUnit());
	}

	@Test
	void testQuantityLengthRefactored_AddWithTargetUnit() {
		QuantityLength sum = QuantityLength.add(new QuantityLength(1.0, LengthUnit.FEET),
				new QuantityLength(12.0, LengthUnit.INCHES), LengthUnit.YARDS);

		assertEquals(2.0 / 3.0, sum.getValue(), EPS);
		assertEquals(LengthUnit.YARDS, sum.getUnit());
	}
}