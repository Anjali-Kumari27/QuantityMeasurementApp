package com.app.quantitymeasurement.measurementservice.unit;

/**
 * WeightUnit
 *
 * Base unit = KILOGRAM
 */
public enum WeightUnit implements IMeasurable {

	KILOGRAM(1.0), GRAM(0.001), POUND(0.453592);

	private final double factor;

	WeightUnit(double factor) {
		this.factor = factor;
	}

	@Override
	public String getUnitName() {
		return name();
	}

	@Override
	public String getMeasurementType() {
		return "WeightUnit";
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