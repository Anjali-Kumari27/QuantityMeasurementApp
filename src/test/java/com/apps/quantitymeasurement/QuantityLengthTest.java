package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityLengthTest {

	@Test
	void testEquality_FeetToFeet_SameValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);

		assertTrue(q1.equals(q2), "1.0 feet should equal 1.0 feet");
	}

	@Test
	void testEquality_InchToInch_SameValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.INCHES);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.INCHES);

		assertTrue(q1.equals(q2), "1.0 inch should equal 1.0 inch");
	}

	@Test
	void testEquality_FeetToInch_EquivalentValue() {
		QuantityLength feet = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength inch = new QuantityLength(12.0, LengthUnit.INCHES);

		assertTrue(feet.equals(inch), "1.0 feet should equal 12.0 inch");
	}

	@Test
	void testEquality_InchToFeet_EquivalentValue() {
		QuantityLength inch = new QuantityLength(12.0, LengthUnit.INCHES);
		QuantityLength feet = new QuantityLength(1.0, LengthUnit.FEET);

		assertTrue(inch.equals(feet), "12.0 inch should equal 1.0 feet (symmetry)");
	}

	@Test
	void testEquality_FeetToFeet_DifferentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(2.0, LengthUnit.FEET);

		assertFalse(q1.equals(q2), "1.0 feet should NOT equal 2.0 feet");
	}

	@Test
	void testEquality_InchToInch_DifferentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.INCHES);
		QuantityLength q2 = new QuantityLength(2.0, LengthUnit.INCHES);

		assertFalse(q1.equals(q2), "1.0 inch should NOT equal 2.0 inch");
	}

	@Test
	void testEquality_SameReference() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		assertTrue(q1.equals(q1), "Object should be equal to itself");
	}

	@Test
	void testEquality_NullComparison() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		assertFalse(q1.equals(null), "Quantity should NOT be equal to null");
	}

	@Test
	void testEquality_DifferentType() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		assertFalse(q1.equals("abc"), "Quantity should NOT be equal to different type");
	}

	@Test
	void testEquality_InvalidUnit() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength(1.0, null),
				"Null unit should throw exception");
	}

	@Test
	void testEquality_InvalidNumber_NaN() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength(Double.NaN, LengthUnit.FEET),
				"NaN should throw exception");
	}

	@Test
	void testEquality_InvalidNumber_Infinity() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityLength(Double.POSITIVE_INFINITY, LengthUnit.INCHES), "Infinity should throw exception");
	}
}