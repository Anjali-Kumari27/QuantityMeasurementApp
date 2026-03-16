package com.app.quantitymeasurement.unit;

/**
 * UC15: WeightUnit defines supported weight measurement units.
 *
 * Base Unit: - KILOGRAM
 *
 * Responsibilities: - Converts weight values to the base unit - Converts base
 * unit values to the target weight unit - Provides unit name and measurement
 * category information
 *
 * Arithmetic Support: Weight supports arithmetic operations such as: - Addition
 * - Subtraction - Division
 *
 * Architectural Role: This enum works with the generic quantity model through
 * IMeasurable and SupportsArithmetic.
 */

/**
 * Base unit: KILOGRAM
 */
public enum WeightUnit implements IMeasurable {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double toKilogramFactor;

    WeightUnit(double toKilogramFactor) {
        this.toKilogramFactor = toKilogramFactor;
    }

    @Override
    public double toBase(double value) {
        return value * toKilogramFactor;
    }

    @Override
    public double fromBase(double baseValue) {
        return baseValue / toKilogramFactor;
    }

    @Override
    public String getUnitName() {
        return name();
    }

    @Override
    public String getMeasurementType() {
        return "Weight";
    }

    @Override
    public IMeasurable getInstance(String unitName) {
        for (WeightUnit unit : WeightUnit.values()) {
            if (unit.name().equalsIgnoreCase(unitName)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Invalid weight unit: " + unitName);
    }
}