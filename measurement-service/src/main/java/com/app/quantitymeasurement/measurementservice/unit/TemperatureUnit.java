package com.app.quantitymeasurement.measurementservice.unit;

import com.app.quantitymeasurement.measurementservice.exception.QuantityMeasurementException;

/**
 * TemperatureUnit
 *
 * Base unit = CELSIUS
 *
 * Important: - compare and convert are allowed - arithmetic is not allowed
 */
public enum TemperatureUnit implements IMeasurable {

	CELSIUS, FAHRENHEIT, KELVIN;

	@Override
	public String getUnitName() {
		return name();
	}

	@Override
	public String getMeasurementType() {
		return "TemperatureUnit";
	}

	@Override
	public double toBase(double value) {
		return switch (this) {
		case CELSIUS -> value;
		case FAHRENHEIT -> (value - 32) * 5 / 9;
		case KELVIN -> value - 273.15;
		};
	}

	@Override
	public double fromBase(double baseValue) {
		return switch (this) {
		case CELSIUS -> baseValue;
		case FAHRENHEIT -> (baseValue * 9 / 5) + 32;
		case KELVIN -> baseValue + 273.15;
		};
	}

	@Override
	public void validateOperationSupport(String operation) {
		throw new QuantityMeasurementException("Temperature does not support " + operation + " operation");
	}
}