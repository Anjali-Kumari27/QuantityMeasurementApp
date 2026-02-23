package com.apps.quantitymeasurement;

/**
 * WeightUnit enum for weight measurements. Base unit: KILOGRAM
 */
public enum WeightUnit {

	// 1 g = 0.001 kg
	KILOGRAM(1.0), GRAM(0.001),

	// 1 lb ≈ 0.453592 kg
	POUND(0.453592);

	private final double toKilogramFactor;

	WeightUnit(double toKilogramFactor) {
		this.toKilogramFactor = toKilogramFactor;
	}

	/**
	 * Convert value in THIS unit to base unit (kilogram).
	 */
	public double convertToBaseUnit(double value) {
		return value * toKilogramFactor;
	}

	/**
	 * Convert value from base unit (kilogram) to THIS unit.
	 */
	public double convertFromBaseUnit(double baseValue) {
		return baseValue / toKilogramFactor;
	}

	public double getConversionFactor() {
		return toKilogramFactor;
	}
}