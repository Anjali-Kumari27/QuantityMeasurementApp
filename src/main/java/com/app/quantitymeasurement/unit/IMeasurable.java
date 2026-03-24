package com.app.quantitymeasurement.unit;

import com.app.quantitymeasurement.exception.QuantityMeasurementException;

/**
 * IMeasurable
 *
 * Common interface for all measurement unit enums.
 *
 * Examples:
 * - LengthUnit
 * - WeightUnit
 * - VolumeUnit
 * - TemperatureUnit
 *
 * Main responsibilities:
 * - convert to base unit
 * - convert from base unit
 * - validate arithmetic support
 */
public interface IMeasurable {

    /**
     * Unit name like FEET, GRAM, LITRE
     */
    String getUnitName();

    /**
     * Measurement category like LengthUnit, WeightUnit
     */
    String getMeasurementType();

    /**
     * Convert current value to base unit
     */
    double toBase(double value);

    /**
     * Convert base value back to current unit
     */
    double fromBase(double baseValue);

    /**
     * Convert current value directly to target unit
     */
    default double convertTo(double value, IMeasurable targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        if (!this.getMeasurementType().equals(targetUnit.getMeasurementType())) {
            throw new QuantityMeasurementException(
                    "Cannot convert between different measurement types"
            );
        }

        double baseValue = this.toBase(value);
        return targetUnit.fromBase(baseValue);
    }

    /**
     * Validate if arithmetic operation is supported
     *
     * By default supported.
     * TemperatureUnit will override this.
     */
    default void validateOperationSupport(String operation) {
        // default: supported
    }
}