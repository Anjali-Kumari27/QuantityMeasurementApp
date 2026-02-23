package com.apps.quantitymeasurement;

/**
 * UC8: Standalone LengthUnit enum. Responsible for converting values to/from
 * the base unit FEET.
 */
public enum LengthUnit {

	// 1 cm = 0.393701 inch, and 1 inch = 1/12
	// feet
	FEET(1.0), INCHES(1.0 / 12.0), YARDS(3.0), CENTIMETERS(0.393701 / 12.0); 

	private final double toFeetFactor;

	LengthUnit(double toFeetFactor) {
		this.toFeetFactor = toFeetFactor;
	}

	/**
	 * Converts a value in THIS unit to base unit (FEET). Example:
	 * INCHES.convertToBaseUnit(12) = 1 feet
	 */
	public double convertToBaseUnit(double value) {
		return value * toFeetFactor;
	}

	/**
	 * Converts a value in base unit (FEET) to THIS unit. Example:
	 * INCHES.convertFromBaseUnit(1) = 12 inches
	 */
	public double convertFromBaseUnit(double baseFeetValue) {
		return baseFeetValue / toFeetFactor;
	}

	public double getConversionFactor() {
		return toFeetFactor;
	}
}