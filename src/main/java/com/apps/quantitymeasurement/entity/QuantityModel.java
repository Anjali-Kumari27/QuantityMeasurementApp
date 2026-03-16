package com.apps.quantitymeasurement.model;

import com.apps.quantitymeasurement.interfaces.IMeasurable;

/**
 * UC15: QuantityModel is the internal service-layer model used for processing
 * quantity data inside business logic.
 *
 * Responsibilities: - Represents a quantity using internal domain-friendly
 * structure - Serves as the bridge between DTO input and generic Quantity logic
 * - Keeps service operations independent of external DTO representation
 *
 * Architectural Role: This class belongs to the model layer and is meant for
 * internal use inside the service layer.
 *
 * Why Separate from DTO: - DTO is for layer communication - Model is for
 * internal computation and validation
 *
 * This separation improves maintainability and supports clean layering.
 */
public class QuantityModel<U extends IMeasurable> {

	private final double value;
	private final U unit;

	public QuantityModel(double value, U unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
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

	public String getMeasurementType() {
		return unit.getMeasurementType();
	}

	@Override
	public String toString() {
		return "QuantityModel(" + value + ", " + unit.getUnitName() + ")";
	}
}