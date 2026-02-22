package com.apps.quantitymeasurement;

public final class QuantityLength {

	private final double value;
	private final LengthUnit unit;

	public QuantityLength(double value, LengthUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		if (Double.isNaN(value) || Double.isInfinite(value)) {
			throw new IllegalArgumentException("Invalid numeric value");
		}
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public LengthUnit getUnit() {
		return unit;
	}

	private double toBaseFeet() {
		return unit.toFeet(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		QuantityLength other = (QuantityLength) obj;

		return Double.compare(this.toBaseFeet(), other.toBaseFeet()) == 0;
	}

	@Override
	public int hashCode() {
		// hash should be based on converted/base value to keep equals-hashCode contract
		return Double.hashCode(toBaseFeet());
	}

	@Override
	public String toString() {
		return "Quantity(" + value + ", " + unit.name().toLowerCase() + ")";
	}
}