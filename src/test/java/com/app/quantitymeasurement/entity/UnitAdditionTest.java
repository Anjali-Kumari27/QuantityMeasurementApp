package com.app.quantitymeasurement.entity;

import com.app.quantitymeasurement.quantity.Quantity;
import com.app.quantitymeasurement.unit.LengthUnit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitAdditionTest {

	private static final double EPSILON = 1e-6;

	@Test
	void testAddition_SameUnit_FeetPlusFeet() {
		Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(2.0, LengthUnit.FEET));

		assertEquals(3.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testAddition_SameUnit_InchPlusInch() {
		Quantity<LengthUnit> result = new Quantity<>(6.0, LengthUnit.INCHES)
				.add(new Quantity<>(6.0, LengthUnit.INCHES));

		assertEquals(12.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.INCHES, result.getUnit());
	}

	@Test
	void testAddition_CrossUnit_FeetPlusInches() {
		Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCHES));

		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testAddition_CrossUnit_InchPlusFeet() {
		Quantity<LengthUnit> result = new Quantity<>(12.0, LengthUnit.INCHES).add(new Quantity<>(1.0, LengthUnit.FEET));

		assertEquals(24.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.INCHES, result.getUnit());
	}

	@Test
	void testAddition_CrossUnit_YardPlusFeet() {
		Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.YARDS).add(new Quantity<>(3.0, LengthUnit.FEET));

		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.YARDS, result.getUnit());
	}

	@Test
	void testAddition_CrossUnit_CentimeterPlusInch() {
		// 2.54 cm = 1 inch, so 2.54 cm + 1 inch = 2 inch = 5.08 cm
		Quantity<LengthUnit> result = new Quantity<>(2.54, LengthUnit.CENTIMETERS)
				.add(new Quantity<>(1.0, LengthUnit.INCHES));

		assertEquals(5.08, result.getValue(), 1e-4);
		assertEquals(LengthUnit.CENTIMETERS, result.getUnit());
	}

	@Test
	void testAddition_WithZero() {
		Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET).add(new Quantity<>(0.0, LengthUnit.INCHES));

		assertEquals(5.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testAddition_NegativeValues() {
		Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET).add(new Quantity<>(-2.0, LengthUnit.FEET));

		assertEquals(3.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testAddition_NullSecondOperand() {
		Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> a.add(null));
	}

	@Test
	void testAddition_LargeValues() {
		Quantity<LengthUnit> result = new Quantity<>(1e6, LengthUnit.FEET).add(new Quantity<>(1e6, LengthUnit.FEET));

		assertEquals(2e6, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testAddition_SmallValues() {
		Quantity<LengthUnit> result = new Quantity<>(0.001, LengthUnit.FEET)
				.add(new Quantity<>(0.002, LengthUnit.FEET));

		assertEquals(0.00, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testAddition_Commutativity_InFeetBase() {
		Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCHES);

		// since result unit depends on first operand, compare via base conversion
		double sum1Feet = a.add(b).convertTo(LengthUnit.FEET).getValue();
		double sum2Feet = b.add(a).convertTo(LengthUnit.FEET).getValue();

		assertEquals(sum1Feet, sum2Feet, EPSILON);
	}
}