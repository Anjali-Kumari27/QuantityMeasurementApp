package com.apps.quantitymeasurement;

/**
 * UC8: QuantityLength delegates all unit conversion to LengthUnit.
 * QuantityLength focuses on: - validation - equals() - convertTo() - add()
 * (UC6) and add(a,b,targetUnit) (UC7)
 */
public final class QuantityLength {

	private static final double EPSILON = 1e-6;

	private final double value;
	private final LengthUnit unit;

	public QuantityLength(double value, LengthUnit unit) {
		validate(value, unit);
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public LengthUnit getUnit() {
		return unit;
	}

	// Validation (UC5)
	private static void validate(double value, LengthUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}
	}

	// Base conversion (delegation to unit)
	private double toBaseFeet() {
		return unit.convertToBaseUnit(value);
	}

	// UC3/UC4: Equality
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		QuantityLength other = (QuantityLength) obj;
		return Math.abs(this.toBaseFeet() - other.toBaseFeet()) < EPSILON;
	}

	@Override
	public int hashCode() {
		return Double.hashCode(toBaseFeet());
	}

	@Override
	public String toString() {
		return "Quantity(" + value + ", " + unit.name() + ")";
	}

	// UC5: Conversion
	public static double convert(double value, LengthUnit source, LengthUnit target) {
		validate(value, source);
		if (target == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}

		double inFeet = source.convertToBaseUnit(value);
		return target.convertFromBaseUnit(inFeet);
	}

	public QuantityLength convertTo(LengthUnit targetUnit) {
		double convertedValue = convert(this.value, this.unit, targetUnit);
		return new QuantityLength(convertedValue, targetUnit);
	}

	// UC6/UC7: Addition

	private static QuantityLength addInTargetUnit(QuantityLength a, QuantityLength b, LengthUnit targetUnit) {
		if (a == null || b == null) {
			throw new IllegalArgumentException("Operands cannot be null");
		}
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}

		double sumFeet = a.toBaseFeet() + b.toBaseFeet();
		double sumInTarget = targetUnit.convertFromBaseUnit(sumFeet);

		return new QuantityLength(sumInTarget, targetUnit);
	}

	// UC6: result in unit of first operand
	public QuantityLength add(QuantityLength other) {
		return addInTargetUnit(this, other, this.unit);
	}

	// UC7: explicit target unit
	public static QuantityLength add(QuantityLength a, QuantityLength b, LengthUnit targetUnit) {
		return addInTargetUnit(a, b, targetUnit);
	}

	// Overloaded add with raw values
	public static QuantityLength add(double value1, LengthUnit unit1, double value2, LengthUnit unit2,
			LengthUnit targetUnit) {
		validate(value1, unit1);
		validate(value2, unit2);
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}

		return addInTargetUnit(new QuantityLength(value1, unit1), new QuantityLength(value2, unit2), targetUnit);
	}
}