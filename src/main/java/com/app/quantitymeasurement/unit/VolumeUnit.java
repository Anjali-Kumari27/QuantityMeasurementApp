package com.app.quantitymeasurement.unit;

/**
 * VolumeUnit
 *
 * Base unit = LITRE
 */
public enum VolumeUnit implements IMeasurable {

	LITRE(1.0), MILLILITRE(0.001), GALLON(3.78541);

	private final double factor;

	VolumeUnit(double factor) {
		this.factor = factor;
	}

	@Override
	public String getUnitName() {
		return name();
	}

	@Override
	public String getMeasurementType() {
		return "VolumeUnit";
	}

	@Override
	public double toBase(double value) {
		return value * factor;
	}

	@Override
	public double fromBase(double baseValue) {
		return baseValue / factor;
	}
}