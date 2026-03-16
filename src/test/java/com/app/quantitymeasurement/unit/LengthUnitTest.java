package com.app.quantitymeasurement.unit;

import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.quantity.Quantity;

import static org.junit.jupiter.api.Assertions.*;

public class LengthUnitTest {

	private static final double EPS = 1e-6;

	// Unit conversion tests

	@Test
	void testConvertToBaseUnit_FeetToFeet() {
		assertEquals(5.0, LengthUnit.FEET.toBase(5.0), EPS);
	}

	@Test
	void testConvertToBaseUnit_InchesToFeet() {
		assertEquals(1.0, LengthUnit.INCHES.toBase(12.0), EPS);
	}

	@Test
	void testConvertToBaseUnit_YardsToFeet() {
		assertEquals(3.0, LengthUnit.YARDS.toBase(1.0), EPS);
	}

	@Test
	void testConvertToBaseUnit_CentimetersToFeet() {
		// 30.48 cm = 1 foot
		assertEquals(1.0, LengthUnit.CENTIMETERS.toBase(30.48), 1e-3);
	}

	@Test
	void testConvertFromBaseUnit_FeetToInches() {
		assertEquals(12.0, LengthUnit.INCHES.fromBase(1.0), EPS);
	}

	@Test
	void testConvertFromBaseUnit_FeetToYards() {
		assertEquals(1.0, LengthUnit.YARDS.fromBase(3.0), EPS);
	}

	@Test
	void testConvertFromBaseUnit_FeetToCentimeters() {
		assertEquals(30.48, LengthUnit.CENTIMETERS.fromBase(1.0), 1e-3);
	}

	// Quantity delegation tests

	@Test
	void testQuantityLengthRefactored_Equality() {
		assertTrue(new Quantity<>(1.0, LengthUnit.FEET).equals(new Quantity<>(12.0, LengthUnit.INCHES)));
	}

	@Test
	void testQuantityLengthRefactored_ConvertTo() {
		Quantity<LengthUnit> converted = new Quantity<>(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES);

		assertEquals(12.0, converted.getValue(), EPS);
		assertEquals(LengthUnit.INCHES, converted.getUnit());
	}

	@Test
	void testQuantityLengthRefactored_AddWithTargetUnit() {
		Quantity<LengthUnit> sum = new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCHES),
				LengthUnit.YARDS);

		// Quantity class rounds to 2 decimals
		assertEquals(0.67, sum.getValue(), 0.01);
		assertEquals(LengthUnit.YARDS, sum.getUnit());
	}
}