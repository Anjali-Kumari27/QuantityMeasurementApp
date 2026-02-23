package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityWeightTest {

	private static final double EPS = 1e-6;

	@Test
	void testEquality_KilogramToGram() {
		assertTrue(new QuantityWeight(1.0, WeightUnit.KILOGRAM).equals(new QuantityWeight(1000.0, WeightUnit.GRAM)));
	}

	@Test
	void testEquality_KilogramToPound() {

		double poundsFor1Kg = 1.0 / 0.453592;

		assertTrue(new QuantityWeight(1.0, WeightUnit.KILOGRAM)
				.equals(new QuantityWeight(poundsFor1Kg, WeightUnit.POUND)));
	}

	@Test
	void testConversion_KilogramToGram() {
		QuantityWeight result = new QuantityWeight(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);

		assertEquals(1000.0, result.getValue(), EPS);
	}

	@Test
	void testAddition_CrossUnit() {
		QuantityWeight result = new QuantityWeight(1.0, WeightUnit.KILOGRAM)
				.add(new QuantityWeight(1000.0, WeightUnit.GRAM));

		assertEquals(2.0, result.getValue(), EPS);
		assertEquals(WeightUnit.KILOGRAM, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit() {
		QuantityWeight result = QuantityWeight.add(new QuantityWeight(1.0, WeightUnit.KILOGRAM),
				new QuantityWeight(1000.0, WeightUnit.GRAM), WeightUnit.GRAM);

		assertEquals(2000.0, result.getValue(), EPS);
	}

	@Test
	void testWeightVsLength_Incompatible() {
		assertFalse(new QuantityWeight(1.0, WeightUnit.KILOGRAM).equals(new QuantityLength(1.0, LengthUnit.FEET)));
	}
}