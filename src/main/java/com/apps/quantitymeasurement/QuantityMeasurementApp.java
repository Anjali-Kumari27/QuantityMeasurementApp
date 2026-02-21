
package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {
	public static void main(String[] args) {

		// Feet equality check
		Feet f1 = new Feet(1.0);
		Feet f2 = new Feet(1.0);

		boolean result = f1.equals(f2);
		System.out.println("Input: " + f1 + " and " + f2);
		System.out.println("Output: Equal (" + result + ")");

	}
}