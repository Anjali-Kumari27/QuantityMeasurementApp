package com.apps.quantitymeasurement;

/**
 * Base unit: FEET
 */
public enum LengthUnit implements IMeasurable {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    
    // 1 cm = 0.393701 inch
    CENTIMETERS(0.393701 / 12.0); 

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    @Override
    public double getConversionFactor() {
        return toFeetFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return value * toFeetFactor;     
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toFeetFactor; 
    }

    @Override
    public String getUnitName() {
        return name();
    }
}