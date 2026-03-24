package com.app.quantitymeasurement.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.quantity.QuantityModel;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.LengthUnit;
import com.app.quantitymeasurement.unit.WeightUnit;

class QuantityEqualityTest {

	@Test
	void testEquals_SameUnitSameValue() {
		QuantityModel<IMeasurable> q1 = new QuantityModel<>(1.0, LengthUnit.FEET);
		QuantityModel<IMeasurable> q2 = new QuantityModel<>(1.0, LengthUnit.FEET);

		assertEquals(q1, q2);
	}

	@Test
	void testEquals_DifferentCompatibleUnits() {
		QuantityModel<IMeasurable> q1 = new QuantityModel<>(1.0, LengthUnit.FEET);
		QuantityModel<IMeasurable> q2 = new QuantityModel<>(12.0, LengthUnit.INCHES);

		assertEquals(q1, q2);
	}

	@Test
	void testEquals_DifferentMeasurementTypes() {
		QuantityModel<IMeasurable> q1 = new QuantityModel<>(1.0, LengthUnit.FEET);
		QuantityModel<IMeasurable> q2 = new QuantityModel<>(1.0, WeightUnit.KILOGRAM);

		assertNotEquals(q1, q2);
	}
}