package com.apps.quantitymeasurement.model;

import com.apps.quantitymeasurement.model.Quantity;
import com.apps.quantitymeasurement.units.LengthUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TargetAdditionTest {

	private static final double EPSILON = 1e-6;

	@Test
	void testAddition_ExplicitTargetUnit_Feet() {
		Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCHES),
				LengthUnit.FEET);

		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_Inches() {
		Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCHES),
				LengthUnit.INCHES);

		assertEquals(24.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.INCHES, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_Yards() {
		Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCHES),
				LengthUnit.YARDS);

		assertEquals(0.67, result.getValue(), EPSILON);
		assertEquals(LengthUnit.YARDS, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_Commutativity() {
		Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCHES);

		Quantity<LengthUnit> r1 = a.add(b, LengthUnit.YARDS);
		Quantity<LengthUnit> r2 = b.add(a, LengthUnit.YARDS);

		assertEquals(r1.getValue(), r2.getValue(), EPSILON);
		assertEquals(LengthUnit.YARDS, r1.getUnit());
		assertEquals(LengthUnit.YARDS, r2.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_NullTargetUnit() {
		assertThrows(IllegalArgumentException.class,
				() -> new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCHES), null));
	}
}