package com.app.quantitymeasurement.unit;

import java.util.function.Function;

/**
 * UC15: TemperatureUnit defines supported temperature measurement units.
 *
 * Base Unit: - CELSIUS
 *
 * Responsibilities: - Converts temperature values to Celsius - Converts Celsius
 * values to target temperature unit - Provides unit name and measurement
 * category information
 *
 * Important Note: Temperature does not support arithmetic operations such as: -
 * Addition - Subtraction - Division
 *
 * Architectural Role: This enum implements IMeasurable but intentionally does
 * NOT implement SupportsArithmetic, allowing the service and quantity layers to
 * reject unsupported arithmetic operations.
 *
 * This is important because arithmetic on absolute temperatures is not
 * considered meaningful in this application design.
 */

public enum TemperatureUnit implements IMeasurable {

	CELSIUS(c -> c, c -> c),

	FAHRENHEIT(f -> (f - 32) * 5 / 9, c -> (c * 9 / 5) + 32),

	KELVIN(k -> k - 273.15, c -> c + 273.15);

	private final Function<Double, Double> toCelsius;
	private final Function<Double, Double> fromCelsius;

	TemperatureUnit(Function<Double, Double> toCelsius, Function<Double, Double> fromCelsius) {
		this.toCelsius = toCelsius;
		this.fromCelsius = fromCelsius;
	}

	@Override
	public double toBase(double value) {
		return toCelsius.apply(value);
	}

	@Override
	public double fromBase(double baseValue) {
		return fromCelsius.apply(baseValue);
	}

	@Override
	public String getMeasurementType() {
		return "Temperature";
	}

	@Override
	public String getUnitName() {
		return name();
	}

	@Override
	public IMeasurable getInstance(String unitName) {
		for (TemperatureUnit unit : TemperatureUnit.values()) {
			if (unit.name().equalsIgnoreCase(unitName)) {
				return unit;
			}
		}
		throw new IllegalArgumentException("Invalid temperature unit: " + unitName);
	}
}