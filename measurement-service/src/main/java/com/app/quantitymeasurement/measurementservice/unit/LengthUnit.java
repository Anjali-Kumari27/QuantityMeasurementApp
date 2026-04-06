package com.app.quantitymeasurement.measurementservice.unit;

/**
 * LengthUnit
 *
 * Base unit = FEET
 */
public enum LengthUnit implements IMeasurable {

	FEET(1.0), INCHES(1.0 / 12.0), YARDS(3.0), CENTIMETERS(0.393701 / 12.0);

	private final double factor;

	LengthUnit(double factor) {
		this.factor = factor;
	}

	@Override
	public String getUnitName() {
		return name();
	}

	@Override
	public String getMeasurementType() {
		return "LengthUnit";
	}

	@Override
	public double toBase(double value) {
		return value * factor;
	}

	@Override
	public double fromBase(double baseValue) {
		return baseValue / factor;
	}
}