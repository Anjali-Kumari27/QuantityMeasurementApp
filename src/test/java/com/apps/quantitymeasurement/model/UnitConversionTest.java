package com.apps.quantitymeasurement.model;

import com.apps.quantitymeasurement.model.Quantity;
import com.apps.quantitymeasurement.units.LengthUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitConversionTest {

	private static final double EPSILON = 1e-6;

	@Test
	void testConversion_FeetToInches() {
		Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES);

		assertEquals(12.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversion_InchesToFeet() {
		Quantity<LengthUnit> result = new Quantity<>(24.0, LengthUnit.INCHES).convertTo(LengthUnit.FEET);

		assertEquals(2.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversion_YardsToInches() {
		Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.YARDS).convertTo(LengthUnit.INCHES);

		assertEquals(36.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversion_InchesToYards() {
		Quantity<LengthUnit> result = new Quantity<>(72.0, LengthUnit.INCHES).convertTo(LengthUnit.YARDS);

		assertEquals(2.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversion_CentimetersToInches() {
		Quantity<LengthUnit> result = new Quantity<>(2.54, LengthUnit.CENTIMETERS).convertTo(LengthUnit.INCHES);

		assertEquals(1.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversion_FeetToYards() {
		Quantity<LengthUnit> result = new Quantity<>(6.0, LengthUnit.FEET).convertTo(LengthUnit.YARDS);

		assertEquals(2.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversion_ZeroValue() {
		Quantity<LengthUnit> result = new Quantity<>(0.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES);

		assertEquals(0.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversion_NegativeValue() {
		Quantity<LengthUnit> result = new Quantity<>(-1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES);

		assertEquals(-12.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversion_RoundTrip_PreservesValue() {

		Quantity<LengthUnit> original = new Quantity<>(5.0, LengthUnit.FEET);

		Quantity<LengthUnit> converted = original.convertTo(LengthUnit.INCHES);
		Quantity<LengthUnit> back = converted.convertTo(LengthUnit.FEET);

		assertEquals(original.getValue(), back.getValue(), EPSILON);
	}

	@Test
	void testConversion_SameUnit() {

		Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET).convertTo(LengthUnit.FEET);

		assertEquals(5.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversion_InvalidUnit_Throws() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(1.0, LengthUnit.FEET).convertTo(null));
	}

	@Test
	void testConversion_NaNOrInfinite_Throws() {

		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.NaN, LengthUnit.FEET));

		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.POSITIVE_INFINITY, LengthUnit.FEET));
	}
}