
package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {
	public static void main(String[] args) {

		System.out.println(QuantityLength.add(new QuantityLength(1.0, LengthUnit.FEET),
				new QuantityLength(2.0, LengthUnit.FEET), LengthUnit.FEET));

		System.out.println(new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12.0, LengthUnit.INCH)));

		System.out.println(new QuantityLength(12.0, LengthUnit.INCH).add(new QuantityLength(1.0, LengthUnit.FEET)));

		System.out.println(new QuantityLength(1.0, LengthUnit.YARDS).add(new QuantityLength(3.0, LengthUnit.FEET)));

		System.out.println(new QuantityLength(36.0, LengthUnit.INCH).add(new QuantityLength(1.0, LengthUnit.YARDS)));

		System.out.println(
				new QuantityLength(2.54, LengthUnit.CENTIMETERS).add(new QuantityLength(1.0, LengthUnit.INCH)));

		System.out.println(new QuantityLength(5.0, LengthUnit.FEET).add(new QuantityLength(0.0, LengthUnit.INCH)));

		System.out.println(new QuantityLength(5.0, LengthUnit.FEET).add(new QuantityLength(-2.0, LengthUnit.FEET)));
	}
}