package com.app.quantitymeasurement.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.quantity.QuantityModel;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.LengthUnit;
import com.app.quantitymeasurement.unit.TemperatureUnit;
import com.app.quantitymeasurement.unit.VolumeUnit;
import com.app.quantitymeasurement.unit.WeightUnit;

class QuantityConversionTest {

	@Test
	void testConvert_FeetToInches() {
		QuantityModel<IMeasurable> quantity = new QuantityModel<>(1.0, LengthUnit.FEET);

		QuantityModel<IMeasurable> result = quantity.convertTo(LengthUnit.INCHES);

		assertEquals(12.0, result.getValue());
		assertEquals(LengthUnit.INCHES, result.getUnit());
	}

	@Test
	void testConvert_KgToGram() {
		QuantityModel<IMeasurable> quantity = new QuantityModel<>(1.0, WeightUnit.KILOGRAM);

		QuantityModel<IMeasurable> result = quantity.convertTo(WeightUnit.GRAM);

		assertEquals(1000.0, result.getValue());
	}

	@Test
	void testConvert_LitreToMillilitre() {
		QuantityModel<IMeasurable> quantity = new QuantityModel<>(1.0, VolumeUnit.LITRE);

		QuantityModel<IMeasurable> result = quantity.convertTo(VolumeUnit.MILLILITRE);

		assertEquals(1000.0, result.getValue());
	}

	@Test
	void testConvert_CelsiusToFahrenheit() {
		QuantityModel<IMeasurable> quantity = new QuantityModel<>(0.0, TemperatureUnit.CELSIUS);

		QuantityModel<IMeasurable> result = quantity.convertTo(TemperatureUnit.FAHRENHEIT);

		assertEquals(32.0, result.getValue());
	}
}