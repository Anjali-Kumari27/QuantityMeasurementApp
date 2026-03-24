package com.app.quantitymeasurement.quantity;

import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.unit.IMeasurable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QuantityModel
 *
 * This is the core business logic class.
 *
 * It stores:
 * - numeric value
 * - measurable unit
 *
 * It performs:
 * - compare
 * - convert
 * - add
 * - subtract
 * - divide
 *
 * This is the main UC16 logic carried into UC17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityModel<T extends IMeasurable> {

    /**
     * Actual numeric value
     */
    private Double value;

    /**
     * Unit object
     */
    private T unit;

    /**
     * Convert current quantity into target unit
     */
    @SuppressWarnings("unchecked")
    public QuantityModel<T> convertTo(IMeasurable targetUnit) {
        validateTargetUnit(targetUnit);

        double convertedValue = unit.convertTo(value, targetUnit);
        return new QuantityModel<>(roundTwoDecimals(convertedValue), (T) targetUnit);
    }

    /**
     * Add another compatible quantity
     * Result comes in current unit
     */
    public QuantityModel<T> add(QuantityModel<T> other) {
        validateOther(other);
        unit.validateOperationSupport("add");

        double otherValueInCurrentUnit = other.getUnit().convertTo(other.getValue(), this.unit);
        double result = this.value + otherValueInCurrentUnit;

        return new QuantityModel<>(roundTwoDecimals(result), this.unit);
    }

    /**
     * Add another compatible quantity
     * Result comes in target unit
     */
    @SuppressWarnings("unchecked")
    public QuantityModel<T> add(QuantityModel<T> other, IMeasurable targetUnit) {
        validateOther(other);
        validateTargetUnit(targetUnit);
        unit.validateOperationSupport("add");

        double thisBase = this.unit.toBase(this.value);
        double otherBase = other.getUnit().toBase(other.getValue());

        double resultBase = thisBase + otherBase;
        double resultValue = targetUnit.fromBase(resultBase);

        return new QuantityModel<>(roundTwoDecimals(resultValue), (T) targetUnit);
    }

    /**
     * Subtract another compatible quantity
     * Result comes in current unit
     */
    public QuantityModel<T> subtract(QuantityModel<T> other) {
        validateOther(other);
        unit.validateOperationSupport("subtract");

        double otherValueInCurrentUnit = other.getUnit().convertTo(other.getValue(), this.unit);
        double result = this.value - otherValueInCurrentUnit;

        return new QuantityModel<>(roundTwoDecimals(result), this.unit);
    }

    /**
     * Subtract another compatible quantity
     * Result comes in target unit
     */
    @SuppressWarnings("unchecked")
    public QuantityModel<T> subtract(QuantityModel<T> other, IMeasurable targetUnit) {
        validateOther(other);
        validateTargetUnit(targetUnit);
        unit.validateOperationSupport("subtract");

        double thisBase = this.unit.toBase(this.value);
        double otherBase = other.getUnit().toBase(other.getValue());

        double resultBase = thisBase - otherBase;
        double resultValue = targetUnit.fromBase(resultBase);

        return new QuantityModel<>(roundTwoDecimals(resultValue), (T) targetUnit);
    }

    /**
     * Divide current quantity by another compatible quantity
     * Result is a numeric ratio
     */
    public double divide(QuantityModel<T> other) {
        validateOther(other);
        unit.validateOperationSupport("divide");

        double otherValueInCurrentUnit = other.getUnit().convertTo(other.getValue(), this.unit);

        if (Math.abs(otherValueInCurrentUnit) < 0.0001) {
            throw new ArithmeticException("Cannot divide by zero");
        }

        return roundTwoDecimals(this.value / otherValueInCurrentUnit);
    }

    /**
     * Compare quantities by converting both to base unit
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof QuantityModel<?> other)) {
            return false;
        }

        if (this.unit == null || other.unit == null) {
            return false;
        }

        if (!this.unit.getMeasurementType().equals(other.unit.getMeasurementType())) {
            return false;
        }

        double thisBase = this.unit.toBase(this.value);
        double otherBase = other.unit.toBase(other.value);

        return Math.abs(thisBase - otherBase) < 0.0001;
    }

    @Override
    public int hashCode() {
        if (unit == null || value == null) {
            return 0;
        }

        double baseValue = unit.toBase(value);
        return Double.hashCode(roundTwoDecimals(baseValue));
    }

    @Override
    public String toString() {
        return value + " " + (unit == null ? "" : unit.getUnitName());
    }

    /**
     * Validate second quantity before operation
     */
    private void validateOther(QuantityModel<T> other) {
        if (other == null) {
            throw new IllegalArgumentException("Other quantity cannot be null");
        }

        if (this.value == null || other.value == null) {
            throw new IllegalArgumentException("Quantity value cannot be null");
        }

        if (this.unit == null || other.unit == null) {
            throw new IllegalArgumentException("Quantity unit cannot be null");
        }

        if (!this.unit.getMeasurementType().equals(other.unit.getMeasurementType())) {
            throw new QuantityMeasurementException(
                    "Different measurement types are not allowed"
            );
        }
    }

    /**
     * Validate target unit for conversion / target-result operations
     */
    private void validateTargetUnit(IMeasurable targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        if (this.unit == null) {
            throw new IllegalArgumentException("Current unit cannot be null");
        }

        if (!this.unit.getMeasurementType().equals(targetUnit.getMeasurementType())) {
            throw new QuantityMeasurementException(
                    "Target unit measurement type must match current quantity type"
            );
        }
    }

    /**
     * Round to 2 decimal places
     */
    private double roundTwoDecimals(double number) {
        return Math.round(number * 100.0) / 100.0;
    }
}