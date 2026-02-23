package com.apps.quantitymeasurement;

/**
 * Base unit: KILOGRAM
 */
public enum WeightUnit implements IMeasurable {

	// 1 g = 0.001 kg
	KILOGRAM(1.0), GRAM(0.001),

	// 1 lb ≈ 0.453592 kg
	POUND(0.453592);

	private final double toKilogramFactor;

	WeightUnit(double toKilogramFactor) {
		this.toKilogramFactor = toKilogramFactor;
	}

	@Override
	public double getConversionFactor() {
		return toKilogramFactor;
	}

	@Override
	public double convertToBaseUnit(double value) {
		return value * toKilogramFactor;
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		return baseValue / toKilogramFactor;
	}

	@Override
	public String getUnitName() {
		return name();
	}
}