package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TargetAdditionTest {

	private static final double EPSILON = 1e-6;

	@Test
	void testAddition_ExplicitTargetUnit_Feet() {
		QuantityLength result = QuantityLength.add(new QuantityLength(1.0, LengthUnit.FEET),
				new QuantityLength(12.0, LengthUnit.INCHES), LengthUnit.FEET);

		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_Inches() {
		QuantityLength result = QuantityLength.add(new QuantityLength(1.0, LengthUnit.FEET),
				new QuantityLength(12.0, LengthUnit.INCHES), LengthUnit.INCHES);

		assertEquals(24.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.INCHES, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_Yards() {
		QuantityLength result = QuantityLength.add(new QuantityLength(1.0, LengthUnit.FEET),
				new QuantityLength(12.0, LengthUnit.INCHES), LengthUnit.YARDS);

		assertEquals(2.0 / 3.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.YARDS, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_Commutativity() {
		QuantityLength a = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength b = new QuantityLength(12.0, LengthUnit.INCHES);

		QuantityLength r1 = QuantityLength.add(a, b, LengthUnit.YARDS);
		QuantityLength r2 = QuantityLength.add(b, a, LengthUnit.YARDS);

		assertEquals(r1.getValue(), r2.getValue(), EPSILON);
		assertEquals(LengthUnit.YARDS, r1.getUnit());
		assertEquals(LengthUnit.YARDS, r2.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_NullTargetUnit() {
		assertThrows(IllegalArgumentException.class, () -> QuantityLength.add(new QuantityLength(1.0, LengthUnit.FEET),
				new QuantityLength(12.0, LengthUnit.INCHES), null));
	}
}