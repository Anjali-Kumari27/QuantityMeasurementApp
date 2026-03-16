package com.app.quantitymeasurement.unit;

/**
 * UC15: LengthUnit defines supported length measurement units.
 *
 * Base Unit: - FEET
 *
 * Responsibilities: - Converts length values to the common base unit - Converts
 * base unit values back to target length unit - Provides unit name and
 * measurement category information
 *
 * Architectural Role: This enum is a part of the measurable unit system and
 * implements IMeasurable so that it can participate in generic quantity
 * operations.
 *
 * Arithmetic Support: Length units support arithmetic operations such as: -
 * Addition - Subtraction - Division
 *
 * Design Principle: Follows polymorphism by allowing all measurable units to be
 * handled through the common IMeasurable contract.
 */

public enum LengthUnit implements IMeasurable {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(0.393701 / 12.0);

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    @Override
    public double toBase(double value) {
        return value * toFeetFactor;
    }

    @Override
    public double fromBase(double baseValue) {
        return baseValue / toFeetFactor;
    }

    @Override
    public String getUnitName() {
        return name();
    }

    @Override
    public String getMeasurementType() {
        return "Length";
    }

    @Override
    public IMeasurable getInstance(String unitName) {
        for (LengthUnit unit : LengthUnit.values()) {
            if (unit.name().equalsIgnoreCase(unitName)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Invalid length unit: " + unitName);
    }
}