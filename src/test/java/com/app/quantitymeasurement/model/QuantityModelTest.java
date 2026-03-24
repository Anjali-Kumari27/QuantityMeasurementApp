package com.app.quantitymeasurement.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.quantity.QuantityModel;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.LengthUnit;

class QuantityModelTest {

	@Test
	void testNoArgsConstructor() {
		QuantityModel<IMeasurable> model = new QuantityModel<>();
		assertNull(model.getValue());
		assertNull(model.getUnit());
	}

	@Test
	void testAllArgsConstructor() {
		QuantityModel<IMeasurable> model = new QuantityModel<>(10.0, LengthUnit.FEET);

		assertEquals(10.0, model.getValue());
		assertEquals(LengthUnit.FEET, model.getUnit());
	}

	@Test
	void testToString() {
		QuantityModel<IMeasurable> model = new QuantityModel<>(10.0, LengthUnit.FEET);
		assertTrue(model.toString().contains("10.0"));
		assertTrue(model.toString().contains("FEET"));
	}
}