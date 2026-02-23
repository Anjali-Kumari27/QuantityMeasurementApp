package com.apps.quantitymeasurement;

/**
 * QuantityLength represents an immutable length measurement. Supports equality
 * and unit-to-unit conversion.
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

	// EQUALITY

	private double toBaseFeet() {
		return unit.toFeet(value);
	}

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

	// CONVERSION

	/**
	 * Static conversion API
	 */
	public static double convert(double value, LengthUnit source, LengthUnit target) {
		validate(value, source);
		if (target == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}

		double valueInFeet = source.toFeet(value);
		return target.fromFeet(valueInFeet);
	}

	/**
	 * Instance conversion API (returns new object)
	 */
	public QuantityLength convertTo(LengthUnit targetUnit) {
		double convertedValue = convert(this.value, this.unit, targetUnit);
		return new QuantityLength(convertedValue, targetUnit);
	}

	// VALIDATION

	private static void validate(double value, LengthUnit unit) {
		if (unit == null)
			throw new IllegalArgumentException("Unit cannot be null");

		if (!Double.isFinite(value))
			throw new IllegalArgumentException("Value must be finite");
	}

	// unit ADDITION

	/**
	 * Instance method: Adds another length to this length and returns result in
	 * THIS unit (unit of first operand).
	 */
	public QuantityLength add(QuantityLength other) {
		if (other == null) {
			throw new IllegalArgumentException("Second operand cannot be null");
		}

		// both are already validated in constructor
		double sumFeet = this.toBaseFeet() + other.toBaseFeet();
		double sumInThisUnit = this.unit.fromFeet(sumFeet);

		return new QuantityLength(sumInThisUnit, this.unit);
	}

	/**
	 * Static method: Adds two QuantityLength values and returns result in
	 * targetUnit.
	 */
	public static QuantityLength add(QuantityLength a, QuantityLength b, LengthUnit targetUnit) {
		if (a == null || b == null) {
			throw new IllegalArgumentException("Operands cannot be null");
		}
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}

		double sumFeet = a.toBaseFeet() + b.toBaseFeet();
		double sumInTarget = targetUnit.fromFeet(sumFeet);

		return new QuantityLength(sumInTarget, targetUnit);
	}

	/**
	 * Overloaded add (raw values): Adds (value1, unit1) + (value2, unit2) and
	 * returns result in targetUnit.
	 */
	public static QuantityLength add(double value1, LengthUnit unit1, double value2, LengthUnit unit2,
			LengthUnit targetUnit) {

		// validate value+unit using existing validate()
		validate(value1, unit1);
		validate(value2, unit2);
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}

		QuantityLength a = new QuantityLength(value1, unit1);
		QuantityLength b = new QuantityLength(value2, unit2);

		return add(a, b, targetUnit);
	}
}