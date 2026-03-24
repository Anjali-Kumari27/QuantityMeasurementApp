package com.app.quantitymeasurement.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.quantity.QuantityModel;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.LengthUnit;
import com.app.quantitymeasurement.unit.TemperatureUnit;
import com.app.quantitymeasurement.unit.WeightUnit;

class QuantityArithmeticTest {

	@Test
	void testAdd_Length() {
		QuantityModel<IMeasurable> q1 = new QuantityModel<>(1.0, LengthUnit.FEET);
		QuantityModel<IMeasurable> q2 = new QuantityModel<>(12.0, LengthUnit.INCHES);

		QuantityModel<IMeasurable> result = q1.add(q2);

		assertEquals(2.0, result.getValue());
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testSubtract_Length() {
		QuantityModel<IMeasurable> q1 = new QuantityModel<>(2.0, LengthUnit.FEET);
		QuantityModel<IMeasurable> q2 = new QuantityModel<>(12.0, LengthUnit.INCHES);

		QuantityModel<IMeasurable> result = q1.subtract(q2);

		assertEquals(1.0, result.getValue());
	}

	@Test
	void testDivide_Length() {
		QuantityModel<IMeasurable> q1 = new QuantityModel<>(2.0, LengthUnit.FEET);
		QuantityModel<IMeasurable> q2 = new QuantityModel<>(1.0, LengthUnit.FEET);

		assertEquals(2.0, q1.divide(q2));
	}

	@Test
	void testAdd_DifferentMeasurementTypes_ShouldThrowException() {
		QuantityModel<IMeasurable> q1 = new QuantityModel<>(1.0, LengthUnit.FEET);
		QuantityModel<IMeasurable> q2 = new QuantityModel<>(1.0, WeightUnit.KILOGRAM);

		assertThrows(QuantityMeasurementException.class, () -> q1.add((QuantityModel<IMeasurable>) q2));
	}

	@Test
	void testAdd_Temperature_ShouldThrowException() {
		QuantityModel<IMeasurable> q1 = new QuantityModel<>(10.0, TemperatureUnit.CELSIUS);
		QuantityModel<IMeasurable> q2 = new QuantityModel<>(20.0, TemperatureUnit.CELSIUS);

		assertThrows(QuantityMeasurementException.class, () -> q1.add(q2));
	}
}