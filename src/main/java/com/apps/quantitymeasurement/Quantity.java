package com.apps.quantitymeasurement;

import java.util.Objects;

public class Quantity<U extends IMeasurable> {

	private static final double EPS = 1e-6;

	private final double value;
	private final U unit;

	public Quantity(double value, U unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit must not be null");
		}
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be a finite number");
		}
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public U getUnit() {
		return unit;
	}

	//  UC10 
	public Quantity<U> convertTo(U targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit must not be null");
		}
		ensureSameCategory(targetUnit);

		double baseValue = unit.convertToBaseUnit(value);
		double converted = targetUnit.convertFromBaseUnit(baseValue);
		return new Quantity<>(round2(converted), targetUnit);
	}

	public Quantity<U> add(Quantity<U> other) {
		return add(other, this.unit);
	}

	public Quantity<U> add(Quantity<U> other, U targetUnit) {
		validateOther(other);
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit must not be null");
		}
		ensureSameCategory(targetUnit);

		double base1 = this.unit.convertToBaseUnit(this.value);
		double base2 = other.unit.convertToBaseUnit(other.value);

		double baseSum = base1 + base2;
		double result = targetUnit.convertFromBaseUnit(baseSum);

		return new Quantity<>(round2(result), targetUnit);
	}

	// ============================================================
	// UC12: SUBTRACTION
	// ============================================================

	/** Subtracts other from this, result unit = this.unit (implicit). */
	public Quantity<U> subtract(Quantity<U> other) {
		return subtract(other, this.unit);
	}

	/** Subtracts other from this, result expressed in targetUnit. */
	public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
		validateOther(other);
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit must not be null");
		}
		ensureSameCategory(targetUnit);

		double base1 = this.unit.convertToBaseUnit(this.value);
		double base2 = other.unit.convertToBaseUnit(other.value);

		double baseDiff = base1 - base2;
		double result = targetUnit.convertFromBaseUnit(baseDiff);

		return new Quantity<>(round2(result), targetUnit);
	}

	// ============================================================
	// UC12: DIVISION (dimensionless)
	// ============================================================

	/**
	 * Divides this by other (same category), returns dimensionless ratio double.
	 */
	public double divide(Quantity<U> other) {
		validateOther(other);

		double base1 = this.unit.convertToBaseUnit(this.value);
		double base2 = other.unit.convertToBaseUnit(other.value);

		if (Math.abs(base2) < EPS) {
			throw new ArithmeticException("Division by zero quantity is not allowed");
		}

		return base1 / base2;
	}

	// helper methods
	private void validateOther(Quantity<U> other) {
		if (other == null) {
			throw new IllegalArgumentException("Other quantity must not be null");
		}
		if (other.unit == null) {
			throw new IllegalArgumentException("Other unit must not be null");
		}
		if (!Double.isFinite(other.value)) {
			throw new IllegalArgumentException("Other value must be a finite number");
		}
		// category safety (length vs weight vs volume)
		if (this.unit.getClass() != other.unit.getClass()) {
			throw new IllegalArgumentException("Cross-category operation is not allowed");
		}
	}

	private void ensureSameCategory(U targetUnit) {
		if (this.unit.getClass() != targetUnit.getClass()) {
			throw new IllegalArgumentException("Target unit is from different category");
		}
	}

	private static double round2(double x) {
		return Math.round(x * 100.0) / 100.0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Quantity<?> other = (Quantity<?>) obj;

		if (this.unit.getClass() != other.unit.getClass())
			return false;

		double base1 = this.unit.convertToBaseUnit(this.value);
		double base2 = ((IMeasurable) other.unit).convertToBaseUnit(other.value);

		return Double.compare(base1, base2) == 0;
	}

	@Override
	public int hashCode() {
		double base = unit.convertToBaseUnit(value);
		return Objects.hash(base, unit.getClass());
	}

	@Override
	public String toString() {
		return "Quantity(" + value + ", " + unit.getUnitName() + ")";
	}
}