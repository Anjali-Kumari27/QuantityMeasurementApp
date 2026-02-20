/*  
 * QuantityMeasurementApp - UC1: Feet Equality
 * 
 * This class is responsible for checking the equality of two numerical values
 * measured in feet in the Quantity Measurement Application
 * 
 */
package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

	// Inner class for Feet measurement
	public static final class Feet {
		private final double value;

		public Feet(double value) {
			this.value = value;
		}

		public double getValue() {
			return value;
		}
		
		/*
		 * Override equals() method to compare two Feet objects based on their value
		 * 
		 * Important checks
		 * 1. Reference check:If both references point to the same object , return true
		 * 2. Null check
		 * 3. Type check
		 * 4. Value Comparison
		 * 
		 * 
		 * @param obj The object to compare with
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

			Feet other = (Feet) obj;
			return Double.compare(this.value, other.value) == 0;
		}

		@Override
		public int hashCode() {
			return Double.hashCode(value);
		}

		@Override
		public String toString() {
			return value + " ft";
		}
	}

	public static void main(String[] args) {
		Feet f1 = new Feet(1.0);
		Feet f2 = new Feet(1.0);

		boolean result = f1.equals(f2);
		System.out.println("Input: " + f1 + " and " + f2);
		System.out.println("Output: Equal (" + result + ")");
	}
}