package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TemperatureUnitTest {

	private static final double EPS = 1e-6;

	// =========================
	// Equality Tests
	// =========================

	@Test
	void testEquality_CelsiusToCelsius() {
		assertTrue(new Quantity<>(0.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(0.0, TemperatureUnit.CELSIUS)));
	}

	@Test
	void testEquality_CelsiusToFahrenheit_ZeroPoint() {
		assertTrue(
				new Quantity<>(0.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)));
	}

	@Test
	void testEquality_CelsiusToFahrenheit_BoilingPoint() {
		assertTrue(new Quantity<>(100.0, TemperatureUnit.CELSIUS)
				.equals(new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT)));
	}

	@Test
	void testEquality_CelsiusToKelvin() {
		assertTrue(new Quantity<>(0.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(273.15, TemperatureUnit.KELVIN)));
	}

	@Test
	void testEquality_NegativeForty() {
		assertTrue(new Quantity<>(-40.0, TemperatureUnit.CELSIUS)
				.equals(new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT)));
	}

	// =========================
	// Conversion Tests
	// =========================

	@Test
	void testConversion_CelsiusToFahrenheit() {
		Quantity<TemperatureUnit> result = new Quantity<>(100.0, TemperatureUnit.CELSIUS)
				.convertTo(TemperatureUnit.FAHRENHEIT);

		assertEquals(212.0, result.getValue(), EPS);
	}

	@Test
	void testConversion_FahrenheitToCelsius() {
		Quantity<TemperatureUnit> result = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)
				.convertTo(TemperatureUnit.CELSIUS);

		assertEquals(0.0, result.getValue(), EPS);
	}

	@Test
	void testConversion_CelsiusToKelvin() {
		Quantity<TemperatureUnit> result = new Quantity<>(0.0, TemperatureUnit.CELSIUS)
				.convertTo(TemperatureUnit.KELVIN);

		assertEquals(273.15, result.getValue(), EPS);
	}

	@Test
	void testConversion_RoundTrip() {
		Quantity<TemperatureUnit> original = new Quantity<>(50.0, TemperatureUnit.CELSIUS);

		Quantity<TemperatureUnit> roundTrip = original.convertTo(TemperatureUnit.FAHRENHEIT)
				.convertTo(TemperatureUnit.CELSIUS);

		assertEquals(50.0, roundTrip.getValue(), EPS);
	}

	// =========================
	// Unsupported Operations
	// =========================

	@Test
	void testUnsupported_Addition() {
		assertThrows(UnsupportedOperationException.class, () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS)
				.add(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
	}

	@Test
	void testUnsupported_Subtraction() {
		assertThrows(UnsupportedOperationException.class, () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS)
				.subtract(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
	}

	@Test
	void testUnsupported_Division() {
		assertThrows(UnsupportedOperationException.class, () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS)
				.divide(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
	}

	// =========================
	// Cross Category Safety
	// =========================

	@Test
	void testTemperatureVsLength() {
		Quantity<TemperatureUnit> temp = new Quantity<>(100.0, TemperatureUnit.CELSIUS);

		Quantity<LengthUnit> length = new Quantity<>(100.0, LengthUnit.FEET);

		assertFalse(temp.equals(length));
	}

	@Test
	void testTemperatureVsWeight() {
		Quantity<TemperatureUnit> temp = new Quantity<>(50.0, TemperatureUnit.CELSIUS);

		Quantity<WeightUnit> weight = new Quantity<>(50.0, WeightUnit.KILOGRAM);

		assertFalse(temp.equals(weight));
	}

	// =========================
	// Edge Cases
	// =========================

	@Test
	void testAbsoluteZero() {
		assertTrue(
				new Quantity<>(-273.15, TemperatureUnit.CELSIUS).equals(new Quantity<>(0.0, TemperatureUnit.KELVIN)));
	}

	@Test
	void testDifferentValues_NotEqual() {
		assertFalse(
				new Quantity<>(50.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(100.0, TemperatureUnit.CELSIUS)));
	}
}