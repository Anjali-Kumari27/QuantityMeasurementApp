package com.apps.quantitymeasurement;

/**
 * QuantityWeight represents weight measurements. Supports: - Equality -
 * Conversion - Addition (implicit + explicit target unit)
 */
public final class QuantityWeight {

	private static final double EPSILON = 1e-6;

	private final double value;
	private final WeightUnit unit;

	public QuantityWeight(double value, WeightUnit unit) {
		validate(value, unit);
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public WeightUnit getUnit() {
		return unit;
	}

	private static void validate(double value, WeightUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Weight unit cannot be null");
		}
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Weight value must be finite");
		}
	}

	private double toBaseKilogram() {
		return unit.convertToBaseUnit(value);
	}

	// Equality
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		QuantityWeight other = (QuantityWeight) obj;

		return Math.abs(this.toBaseKilogram() - other.toBaseKilogram()) < EPSILON;
	}

	@Override
	public int hashCode() {
		return Double.hashCode(toBaseKilogram());
	}

	@Override
	public String toString() {
		return "Quantity(" + value + ", " + unit.name() + ")";
	}

	// Conversion
	public QuantityWeight convertTo(WeightUnit targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}

		double inKg = unit.convertToBaseUnit(value);
		double converted = targetUnit.convertFromBaseUnit(inKg);

		return new QuantityWeight(converted, targetUnit);
	}

	// Addition

	private static QuantityWeight addInTargetUnit(QuantityWeight a, QuantityWeight b, WeightUnit targetUnit) {

		if (a == null || b == null) {
			throw new IllegalArgumentException("Operands cannot be null");
		}
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}

		double sumKg = a.toBaseKilogram() + b.toBaseKilogram();
		double sumTarget = targetUnit.convertFromBaseUnit(sumKg);

		return new QuantityWeight(sumTarget, targetUnit);
	}

	// UC6 equivalent: result in first operand's unit
	public QuantityWeight add(QuantityWeight other) {
		return addInTargetUnit(this, other, this.unit);
	}

	// UC7 equivalent: explicit target unit
	public static QuantityWeight add(QuantityWeight a, QuantityWeight b, WeightUnit targetUnit) {

		return addInTargetUnit(a, b, targetUnit);
	}
}