package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {
	public static void main(String[] args) {

		System.out.println("Input: Quantity(1.0, FEET).convertTo(INCHES) -> Output: "
				+ new QuantityLength(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES));

		System.out.println("Input: Quantity(1.0, FEET).add(Quantity(12.0, INCHES), FEET) -> Output: "
				+ QuantityLength.add(new QuantityLength(1.0, LengthUnit.FEET),
						new QuantityLength(12.0, LengthUnit.INCHES), LengthUnit.FEET));

		System.out.println("Input: Quantity(36.0, INCHES).equals(Quantity(1.0, YARDS)) -> Output: "
				+ new QuantityLength(36.0, LengthUnit.INCHES).equals(new QuantityLength(1.0, LengthUnit.YARDS)));

		System.out.println("Input: Quantity(1.0, YARDS).add(Quantity(3.0, FEET), YARDS) -> Output: "
				+ QuantityLength.add(new QuantityLength(1.0, LengthUnit.YARDS),
						new QuantityLength(3.0, LengthUnit.FEET), LengthUnit.YARDS));

		System.out.println("Input: Quantity(2.54, CENTIMETERS).convertTo(INCHES) -> Output: "
				+ new QuantityLength(2.54, LengthUnit.CENTIMETERS).convertTo(LengthUnit.INCHES));

		System.out.println("Input: Quantity(5.0, FEET).add(Quantity(0.0, INCHES), FEET) -> Output: "
				+ QuantityLength.add(new QuantityLength(5.0, LengthUnit.FEET),
						new QuantityLength(0.0, LengthUnit.INCHES), LengthUnit.FEET));

		System.out.println(
				"Input: LengthUnit.FEET.convertToBaseUnit(12.0) -> Output: " + LengthUnit.FEET.convertToBaseUnit(12.0));

		System.out.println("Input: LengthUnit.INCHES.convertToBaseUnit(12.0) -> Output: "
				+ LengthUnit.INCHES.convertToBaseUnit(12.0));

		
		
		
		//  UC9 Weight
        System.out.println("Weight Equality:");
		System.out.println(
				new QuantityWeight(1.0, WeightUnit.KILOGRAM).equals(new QuantityWeight(1000.0, WeightUnit.GRAM)));

		System.out.println(
				new QuantityWeight(1.0, WeightUnit.KILOGRAM).equals(new QuantityWeight(2.20462, WeightUnit.POUND)));

		System.out.println();

		System.out.println("Weight Conversion:");
		System.out.println(new QuantityWeight(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM));

		System.out.println(new QuantityWeight(2.0, WeightUnit.POUND).convertTo(WeightUnit.KILOGRAM));

		System.out.println();

		System.out.println("Weight Addition:");
		System.out
				.println(new QuantityWeight(1.0, WeightUnit.KILOGRAM).add(new QuantityWeight(1000.0, WeightUnit.GRAM)));

		System.out.println(QuantityWeight.add(new QuantityWeight(1.0, WeightUnit.KILOGRAM),
				new QuantityWeight(1000.0, WeightUnit.GRAM), WeightUnit.GRAM));
	}
}