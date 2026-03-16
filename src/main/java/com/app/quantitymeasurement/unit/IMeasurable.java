package com.app.quantitymeasurement.unit;

/**
 * UC15: IMeasurable is the core abstraction for all measurable units.
 *
 * Responsibilities: 
 * - Defines the contract for all measurement unit implementations 
 * - Provides conversion to base unit and from base unit 
 * - Exposes unit name and measurement type
 * - Provides helper method to resolve unit instance from unit name 
 * - Supports common conversion logic through
 * default methods
 *
 * Implementations: 
 * - LengthUnit 
 * - WeightUnit 
 * - VolumeUnit 
 * - TemperatureUnit
 *
 * Architectural Role: This interface enables polymorphism and loose coupling in
 * the quantity measurement system.
 *
 * Design Principle: Supports Open-Closed Principle by allowing new measurement
 * categories to be added without changing service logic.
 */

public interface IMeasurable {

	String getUnitName();

	String getMeasurementType();

	double toBase(double value);

	double fromBase(double baseValue);

	IMeasurable getInstance(String unitName);

	default double convertTo(double value, IMeasurable targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}
		if (!this.getMeasurementType().equals(targetUnit.getMeasurementType())) {
			throw new IllegalArgumentException("Cannot convert between different measurement types");
		}
		double baseValue = this.toBase(value);
		return targetUnit.fromBase(baseValue);
	}
}