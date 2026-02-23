package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitAdditionTest {

	private static final double EPSILON = 1e-6;

	@Test
	void testAddition_SameUnit_FeetPlusFeet() {
		QuantityLength result = new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(2.0, LengthUnit.FEET));

		assertEquals(3.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testAddition_SameUnit_InchPlusInch() {
		QuantityLength result = new QuantityLength(6.0, LengthUnit.INCHES).add(new QuantityLength(6.0, LengthUnit.INCHES));

		assertEquals(12.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.INCHES, result.getUnit());
	}

	@Test
	void testAddition_CrossUnit_FeetPlusInches() {
		QuantityLength result = new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12.0, LengthUnit.INCHES));

		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testAddition_CrossUnit_InchPlusFeet() {
		QuantityLength result = new QuantityLength(12.0, LengthUnit.INCHES).add(new QuantityLength(1.0, LengthUnit.FEET));

		assertEquals(24.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.INCHES, result.getUnit());
	}

	@Test
	void testAddition_CrossUnit_YardPlusFeet() {
		QuantityLength result = new QuantityLength(1.0, LengthUnit.YARDS).add(new QuantityLength(3.0, LengthUnit.FEET));

		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.YARDS, result.getUnit());
	}

	@Test
	void testAddition_CrossUnit_CentimeterPlusInch() {
		// 2.54 cm = 1 inch, so 2.54 cm + 1 inch = 2 inch = 5.08 cm
		QuantityLength result = new QuantityLength(2.54, LengthUnit.CENTIMETERS)
				.add(new QuantityLength(1.0, LengthUnit.INCHES));

		assertEquals(5.08, result.getValue(), 1e-4); // small tolerance ok
		assertEquals(LengthUnit.CENTIMETERS, result.getUnit());
	}

	@Test
	void testAddition_WithZero() {
		QuantityLength result = new QuantityLength(5.0, LengthUnit.FEET).add(new QuantityLength(0.0, LengthUnit.INCHES));

		assertEquals(5.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testAddition_NegativeValues() {
		QuantityLength result = new QuantityLength(5.0, LengthUnit.FEET).add(new QuantityLength(-2.0, LengthUnit.FEET));

		assertEquals(3.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testAddition_NullSecondOperand() {
		QuantityLength a = new QuantityLength(1.0, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> a.add(null));
	}

	@Test
	void testAddition_LargeValues() {
		QuantityLength result = new QuantityLength(1e6, LengthUnit.FEET).add(new QuantityLength(1e6, LengthUnit.FEET));

		assertEquals(2e6, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testAddition_SmallValues() {
		QuantityLength result = new QuantityLength(0.001, LengthUnit.FEET)
				.add(new QuantityLength(0.002, LengthUnit.FEET));

		assertEquals(0.003, result.getValue(), 1e-9);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testAddition_Commutativity_InFeetBase() {
		QuantityLength a = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength b = new QuantityLength(12.0, LengthUnit.INCHES);

		// since result unit depends on first operand, compare via base conversion
		double sum1Feet = a.add(b).convertTo(LengthUnit.FEET).getValue();
		double sum2Feet = b.add(a).convertTo(LengthUnit.FEET).getValue();

		assertEquals(sum1Feet, sum2Feet, EPSILON);
	}
}
