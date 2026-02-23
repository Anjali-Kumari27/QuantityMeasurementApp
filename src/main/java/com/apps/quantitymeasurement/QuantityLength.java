package com.apps.quantitymeasurement;

/**
 * QuantityLength is an immutable value object for length measurements.
 * Supports: - UC3/UC4: equality across units by converting to base unit (feet)
 * - UC5: unit-to-unit conversion - UC6: addition returning result in unit of
 * first operand (instance add) - UC7: addition returning result in explicitly
 * specified target unit (static add)
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

	// Base conversion helpers

	private double toBaseFeet() {
		return unit.toFeet(value);
	}

	private static void validate(double value, LengthUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}
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

	/**
	 * Static conversion API: result = value * (source.factor / target.factor)
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
	 * Instance conversion API: returns a NEW QuantityLength in target unit.
	 */
	public QuantityLength convertTo(LengthUnit targetUnit) {
		double convertedValue = convert(this.value, this.unit, targetUnit);
		return new QuantityLength(convertedValue, targetUnit);
	}

	// UC6/UC7: Addition
	// UC7 core: add(a,b,targetUnit)
	// UC6 backward compatible: this.add(other) -> result in this.unit

	private static QuantityLength addInTargetUnit(QuantityLength a, QuantityLength b, LengthUnit targetUnit) {
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
	 * UC6: Instance add -> returns result in unit of FIRST operand (this.unit).
	 */
	public QuantityLength add(QuantityLength other) {
		return addInTargetUnit(this, other, this.unit);
	}

	/**
	 * UC7: Static add with explicit target unit.
	 */
	public static QuantityLength add(QuantityLength a, QuantityLength b, LengthUnit targetUnit) {
		return addInTargetUnit(a, b, targetUnit);
	}

	/**
	 * Overloaded add (raw values) with explicit target unit.
	 */
	public static QuantityLength add(double value1, LengthUnit unit1, double value2, LengthUnit unit2,
			LengthUnit targetUnit) {
		validate(value1, unit1);
		validate(value2, unit2);
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}

		QuantityLength a = new QuantityLength(value1, unit1);
		QuantityLength b = new QuantityLength(value2, unit2);
		return addInTargetUnit(a, b, targetUnit);
	}
}