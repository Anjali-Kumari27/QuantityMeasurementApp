package com.apps.quantitymeasurement;

/*
 QuantityMeasurementApp - UC2: Inch Equality
 * 
 * This class is responsible for checking the equality of two numerical values
 * measured in inch in the Quantity Measurement Application
 * 
 */
//class for Inch measurement
public final class Inch {

	private final double value;

	public Inch(double value) {
		if (Double.isNaN(value) || Double.isInfinite(value)) {
			throw new IllegalArgumentException("Invalid numeric value for Inch");
		}
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	/*
	 * Override equals() method to compare two Feet objects based on their value
	 * 
	 * Important checks 1. Reference check:If both references point to the same
	 * object , return true 2. Null check 3. Type check 4. Value Comparison
	 * 
	 * 
	 * @param obj The object to compare with
	 * 
	 * @return true if both Feet objects have the same value, otherwise false
	 */

	@Override
	public boolean equals(Object obj) {
		// same reference
		if (this == obj)
			return true;

		// null check
		if (obj == null)
			return false;

		// type check
		if (getClass() != obj.getClass())
			return false;

		Inch other = (Inch) obj;
		return Double.compare(this.value, other.value) == 0;
	}

	@Override
	public int hashCode() {
		return Double.hashCode(value);
	}

	@Override
	public String toString() {
		return value + " inch ";
	}

}
