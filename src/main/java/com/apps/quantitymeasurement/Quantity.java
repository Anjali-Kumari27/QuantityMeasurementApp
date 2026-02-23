package com.apps.quantitymeasurement;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * UC10: Generic Quantity class for ANY measurable unit category. U is the unit
 * enum type (LengthUnit, WeightUnit, etc.) implementing IMeasurable.
 */
public final class Quantity<U extends IMeasurable> {

	private static final double EPSILON = 1e-6;

	private final double value;
	private final U unit;

	public Quantity(double value, U unit) {
		validate(value, unit);
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public U getUnit() {
		return unit;
	}

	private static void validate(double value, IMeasurable unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}
	}

	private double toBaseValue() {
		return unit.convertToBaseUnit(value);
	}

	//  Equality
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Quantity<?> other = (Quantity<?>) obj;

		// Runtime category safety (length vs weight):
		if (this.unit.getClass() != other.unit.getClass()) {
			return false;
		}

		return Math.abs(this.toBaseValue() - other.toBaseValue()) < EPSILON;
	}

	@Override
	public int hashCode() {
		// hash based on normalized base value
		return Double.hashCode(toBaseValue());
	}

	@Override
	public String toString() {
		return "Quantity(" + value + ", " + unit.getUnitName() + ")";
	}

	// Conversion 
	public Quantity<U> convertTo(U targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}

		double base = unit.convertToBaseUnit(value);
		double converted = targetUnit.convertFromBaseUnit(base);

		// UC10: "Rounds result to two decimal places"
		converted = round(converted, 2);

		return new Quantity<>(converted, targetUnit);
	}

	//  Addition 
	// implicit target = this.unit (UC6 equivalent)
	public Quantity<U> add(Quantity<U> other) {
		return add(other, this.unit);
	}

	// explicit target (UC7 equivalent)
	public Quantity<U> add(Quantity<U> other, U targetUnit) {
		if (other == null) {
			throw new IllegalArgumentException("Other quantity cannot be null");
		}
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}

		double sumBase = this.toBaseValue() + other.toBaseValue();
		double sumTarget = targetUnit.convertFromBaseUnit(sumBase);

		// UC10 : "Rounds result to two decimal places"
		sumTarget = round(sumTarget, 2);

		return new Quantity<>(sumTarget, targetUnit);
	}

	private static double round(double value, int scale) {
		return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP).doubleValue();
	}
}