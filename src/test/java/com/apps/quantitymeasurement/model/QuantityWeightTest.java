package com.apps.quantitymeasurement.model;

import com.apps.quantitymeasurement.model.Quantity;
import com.apps.quantitymeasurement.units.LengthUnit;
import com.apps.quantitymeasurement.units.WeightUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityWeightTest {

	private static final double EPS = 1e-6;

	@Test
	void testEquality_KilogramToGram() {
		assertTrue(new Quantity<>(1.0, WeightUnit.KILOGRAM).equals(new Quantity<>(1000.0, WeightUnit.GRAM)));
	}

	@Test
	void testEquality_KilogramToPound() {
		double poundsFor1Kg = 1.0 / 0.453592;

		assertTrue(new Quantity<>(1.0, WeightUnit.KILOGRAM).equals(new Quantity<>(poundsFor1Kg, WeightUnit.POUND)));
	}

	@Test
	void testConversion_KilogramToGram() {
		Quantity<WeightUnit> result = new Quantity<>(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);

		assertEquals(1000.0, result.getValue(), EPS);
		assertEquals(WeightUnit.GRAM, result.getUnit());
	}

	@Test
	void testAddition_CrossUnit() {
		Quantity<WeightUnit> result = new Quantity<>(1.0, WeightUnit.KILOGRAM)
				.add(new Quantity<>(1000.0, WeightUnit.GRAM));

		assertEquals(2.0, result.getValue(), EPS);
		assertEquals(WeightUnit.KILOGRAM, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit() {
		Quantity<WeightUnit> result = new Quantity<>(1.0, WeightUnit.KILOGRAM)
				.add(new Quantity<>(1000.0, WeightUnit.GRAM), WeightUnit.GRAM);

		assertEquals(2000.0, result.getValue(), EPS);
		assertEquals(WeightUnit.GRAM, result.getUnit());
	}

	@Test
	void testWeightVsLength_Incompatible() {
		Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);

		assertFalse(weight.equals(length));
	}
}