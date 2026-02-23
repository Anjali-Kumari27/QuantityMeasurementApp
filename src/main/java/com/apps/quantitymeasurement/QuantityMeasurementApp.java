
package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

	public static void main(String[] args) {

		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

		System.out.println("Input: " + q1 + " and " + q2);
		System.out.println("Output: Equal (" + q1.equals(q2) + ")");

		QuantityLength q3 = new QuantityLength(1.0, LengthUnit.INCH);
		QuantityLength q4 = new QuantityLength(1.0, LengthUnit.INCH);

		System.out.println("Input: " + q3 + " and " + q4);
		System.out.println("Output: Equal (" + q3.equals(q4) + ")");
		
		QuantityLength q5 = new QuantityLength(3.0, LengthUnit.FEET);
		QuantityLength q6 = new QuantityLength(1.0, LengthUnit.YARDS);

		System.out.println("Input: " + q5 + " and " + q6);
		System.out.println("Output: Equal (" + q5.equals(q6) + ")");
		
		QuantityLength q7 = new QuantityLength(1.0, LengthUnit.YARDS);
		QuantityLength q8 = new QuantityLength(1.0, LengthUnit.YARDS);

		System.out.println("Input: " + q7 + " and " + q8);
		System.out.println("Output: Equal (" + q7.equals(q8) + ")");
		
		QuantityLength q9 = new QuantityLength(1.0, LengthUnit.YARDS);
		QuantityLength q10 = new QuantityLength(36.0, LengthUnit.INCH);

		System.out.println("Input: " + q9 + " and " + q10);
		System.out.println("Output: Equal (" + q9.equals(q10) + ")");
		
		QuantityLength q11 = new QuantityLength(2.0, LengthUnit.CENTIMETERS);
		QuantityLength q12 = new QuantityLength(2.0, LengthUnit.CENTIMETERS);

		System.out.println("Input: " + q11 + " and " + q12);
		System.out.println("Output: Equal (" + q11.equals(q12) + ")");
		
		QuantityLength q13 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
		QuantityLength q14 = new QuantityLength(1.0, LengthUnit.FEET);

		System.out.println("Input: " + q13 + " and " + q14);
		System.out.println("Output: Equal (" + q13.equals(q14) + ")");
	}
}