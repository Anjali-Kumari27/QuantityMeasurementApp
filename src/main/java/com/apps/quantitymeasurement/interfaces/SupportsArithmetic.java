package com.apps.quantitymeasurement.interfaces;

/**
 * UC15: SupportsArithmetic is a marker interface used to indicate whether a
 * measurable unit supports arithmetic operations.
 *
 * Purpose: - Identifies units that allow addition, subtraction, and division -
 * Helps the Quantity and Service layers reject unsupported operations
 *
 * Implemented By: - LengthUnit - WeightUnit - VolumeUnit
 *
 * Not Implemented By: - TemperatureUnit
 *
 * Architectural Benefit: This keeps arithmetic support checks simple and avoids
 * hardcoding category-specific rules in multiple places.
 */
public interface SupportsArithmetic {
}