package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

	public static void main(String[] args) {

		// UC5: Conversion Demo
		System.out.println("Input: convert(1.0, FEET, INCH) -> Output: "
				+ QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCH));

		System.out.println("Input: convert(3.0, YARDS, FEET) -> Output: "
				+ QuantityLength.convert(3.0, LengthUnit.YARDS, LengthUnit.FEET));

		System.out.println("Input: convert(36.0, INCH, YARDS) -> Output: "
				+ QuantityLength.convert(36.0, LengthUnit.INCH, LengthUnit.YARDS));

		System.out.println("Input: convert(1.0, CENTIMETERS, INCH) -> Output: "
				+ QuantityLength.convert(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCH));

		System.out.println();

		// UC6: Addition Demo (result in first operand unit)
		System.out.println(
				"UC6: " + new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12.0, LengthUnit.INCH))); // 2
																													// feet

		System.out.println(
				"UC6: " + new QuantityLength(12.0, LengthUnit.INCH).add(new QuantityLength(1.0, LengthUnit.FEET))); // 24
																													// inches

		System.out.println();

		// UC7: Addition Demo (explicit target unit)
		QuantityLength a = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength b = new QuantityLength(12.0, LengthUnit.INCH);

		System.out.println("UC7: add(" + a + ", " + b + ", FEET) -> " + QuantityLength.add(a, b, LengthUnit.FEET));

		System.out.println("UC7: add(" + a + ", " + b + ", INCH) -> " + QuantityLength.add(a, b, LengthUnit.INCH));

		System.out.println("UC7: add(" + a + ", " + b + ", YARDS) -> " + QuantityLength.add(a, b, LengthUnit.YARDS));
	}
}