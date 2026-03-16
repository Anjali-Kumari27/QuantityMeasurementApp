package com.app.quantitymeasurement.entity;

import com.app.quantitymeasurement.quantity.Quantity;
import com.app.quantitymeasurement.unit.LengthUnit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityLengthTest {

	@Test
	void testEquality_FeetToFeet_SameValue() {
		Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(1.0, LengthUnit.FEET);

		assertTrue(q1.equals(q2), "1.0 feet should equal 1.0 feet");
	}

	@Test
	void testEquality_InchToInch_SameValue() {
		Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.INCHES);
		Quantity<LengthUnit> q2 = new Quantity<>(1.0, LengthUnit.INCHES);

		assertTrue(q1.equals(q2), "1.0 inch should equal 1.0 inch");
	}

	@Test
	void testEquality_FeetToInch_EquivalentValue() {
		Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> inch = new Quantity<>(12.0, LengthUnit.INCHES);

		assertTrue(feet.equals(inch), "1.0 feet should equal 12.0 inch");
	}

	@Test
	void testEquality_InchToFeet_EquivalentValue() {
		Quantity<LengthUnit> inch = new Quantity<>(12.0, LengthUnit.INCHES);
		Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);

		assertTrue(inch.equals(feet), "12.0 inch should equal 1.0 feet (symmetry)");
	}

	@Test
	void testEquality_FeetToFeet_DifferentValue() {
		Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.FEET);

		assertFalse(q1.equals(q2), "1.0 feet should NOT equal 2.0 feet");
	}

	@Test
	void testEquality_InchToInch_DifferentValue() {
		Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.INCHES);
		Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.INCHES);

		assertFalse(q1.equals(q2), "1.0 inch should NOT equal 2.0 inch");
	}

	@Test
	void testEquality_SameReference() {
		Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
		assertTrue(q1.equals(q1), "Object should be equal to itself");
	}

	@Test
	void testEquality_NullComparison() {
		Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
		assertFalse(q1.equals(null), "Quantity should NOT be equal to null");
	}

	@Test
	void testEquality_DifferentType() {
		Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
		assertFalse(q1.equals("abc"), "Quantity should NOT be equal to different type");
	}

	@Test
	void testEquality_InvalidUnit() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(1.0, (LengthUnit) null),
				"Null unit should throw exception");
	}

	@Test
	void testEquality_InvalidNumber_NaN() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.NaN, LengthUnit.FEET),
				"NaN should throw exception");
	}

	@Test
	void testEquality_InvalidNumber_Infinity() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.POSITIVE_INFINITY, LengthUnit.INCHES),
				"Infinity should throw exception");
	}
}