package com.apps.quantitymeasurement;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

public class Quantity<U extends IMeasurable> {

	private static final double EPS = 1e-6;

	private final double value;
	private final U unit;

	public Quantity(double value, U unit) {
		if (unit == null)
			throw new IllegalArgumentException("Unit must not be null");
		if (!Double.isFinite(value))
			throw new IllegalArgumentException("Value must be a finite number");
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public U getUnit() {
		return unit;
	}

	// ============================
	// UC10: Conversion
	// ============================
	public Quantity<U> convertTo(U targetUnit) {
		validateTargetUnit(targetUnit);
		double base = unit.convertToBaseUnit(value);
		double converted = targetUnit.convertFromBaseUnit(base);
		return new Quantity<>(round2(converted), targetUnit);
	}

	// ============================
	// UC12: Add (UC13 refactor)
	// ============================
	public Quantity<U> add(Quantity<U> other) {
		return add(other, this.unit);
	}

	public Quantity<U> add(Quantity<U> other, U targetUnit) {
		validateTargetUnit(targetUnit);
		double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
		double result = targetUnit.convertFromBaseUnit(baseResult);
		return new Quantity<>(round2(result), targetUnit);
	}

	// ============================
	// UC12: Subtract (UC13 refactor)
	// ============================
	public Quantity<U> subtract(Quantity<U> other) {
		return subtract(other, this.unit);
	}

	public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
		validateTargetUnit(targetUnit);
		double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
		double result = targetUnit.convertFromBaseUnit(baseResult);
		return new Quantity<>(round2(result), targetUnit);
	}

	// ============================
	// UC12: Divide (UC13 refactor)
	// ============================
	public double divide(Quantity<U> other) {
		return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
	}

	// ==========================================================
	// UC13: Centralized arithmetic core (single source of truth)
	// ==========================================================
	private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation operation) {

		validateOperand(other);

		// NEW — Validate arithmetic support
		this.unit.validateOperationSupport(operation.name());
		other.unit.validateOperationSupport(operation.name());

		double base1 = this.unit.convertToBaseUnit(this.value);
		double base2 = other.unit.convertToBaseUnit(other.value);

		if (operation == ArithmeticOperation.DIVIDE && Math.abs(base2) < EPS) {
			throw new ArithmeticException("Division by zero quantity is not allowed");
		}

		return operation.compute(base1, base2);
	}

	// ============================
	// UC13: Centralized validation
	// ============================
	private void validateOperand(Quantity<U> other) {
		if (other == null) {
			throw new IllegalArgumentException("Other quantity must not be null");
		}
		if (other.unit == null) {
			throw new IllegalArgumentException("Other unit must not be null");
		}
		if (!Double.isFinite(other.value)) {
			throw new IllegalArgumentException("Other value must be a finite number");
		}
		// category check: Length vs Weight vs Volume (runtime safety)
		if (this.unit.getClass() != other.unit.getClass()) {
			throw new IllegalArgumentException("Cross-category operation is not allowed");
		}
	}

	private void validateTargetUnit(U targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit must not be null");
		}
		if (this.unit.getClass() != targetUnit.getClass()) {
			throw new IllegalArgumentException("Target unit is from different category");
		}
	}

	private static double round2(double x) {
		return Math.round(x * 100.0) / 100.0;
	}

	// ==========================================================
	// UC13: Enum-based operation dispatch (lambda version)
	// ==========================================================
	private enum ArithmeticOperation {
		ADD((a, b) -> a + b), SUBTRACT((a, b) -> a - b), DIVIDE((a, b) -> a / b);

		private final DoubleBinaryOperator operator;

		ArithmeticOperation(DoubleBinaryOperator operator) {
			this.operator = operator;
		}

		double compute(double left, double right) {
			return operator.applyAsDouble(left, right);
		}
	}

	// ============================
	// equals/hashCode/toString (unchanged behavior)
	// ============================
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