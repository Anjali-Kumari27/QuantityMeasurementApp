package com.apps.quantitymeasurement;

/**
 * UC11: Volume units implementing IMeasurable
 * Base unit: LITRE
 *
 * Conversion factors are relative to LITRE.
 * - 1 LITRE = 1.0 L
 * - 1 MILLILITRE = 0.001 L
 * - 1 GALLON (US) ≈ 3.78541 L
 */
public enum VolumeUnit implements IMeasurable {

    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78541);

    private final double toLitreFactor;

    VolumeUnit(double toLitreFactor) {
        this.toLitreFactor = toLitreFactor;
    }

    @Override
    public double getConversionFactor() {
        return toLitreFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        // converts this unit -> litre
        return value * toLitreFactor;
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        // converts litre -> this unit
        return baseValue / toLitreFactor;
    }

    @Override
    public String getUnitName() {
        return name();
    }
}