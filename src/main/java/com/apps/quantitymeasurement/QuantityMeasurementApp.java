
package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

	// Overloading method 1
	public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {

		double result = QuantityLength.convert(value, from, to);

		System.out.println("Input: convert(" + value + ", " + from + ", " + to + ")");
		System.out.println("Output: " + result);
		System.out.println();
	}

	// Overloading method 2
	public static void demonstrateLengthConversion(QuantityLength quantity, LengthUnit target) {

		QuantityLength converted = quantity.convertTo(target);

		System.out.println("Input: " + quantity);
		System.out.println("Converted To: " + converted);
		System.out.println();
	}

	public static void main(String[] args) {

		demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH);
		demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET);
		demonstrateLengthConversion(36.0, LengthUnit.INCH, LengthUnit.YARDS);
		demonstrateLengthConversion(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCH);

		QuantityLength length = new QuantityLength(2.0, LengthUnit.YARDS);
		demonstrateLengthConversion(length, LengthUnit.INCH);
	}
}