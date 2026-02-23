package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {
    public static void main(String[] args) {

        System.out.println("Input: Quantity(1.0, FEET).convertTo(INCHES) -> Output: "
                + new QuantityLength(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES));

        System.out.println("Input: Quantity(1.0, FEET).add(Quantity(12.0, INCHES), FEET) -> Output: "
                + QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.FEET),
                        new QuantityLength(12.0, LengthUnit.INCHES),
                        LengthUnit.FEET));

        System.out.println("Input: Quantity(36.0, INCHES).equals(Quantity(1.0, YARDS)) -> Output: "
                + new QuantityLength(36.0, LengthUnit.INCHES).equals(new QuantityLength(1.0, LengthUnit.YARDS)));

        System.out.println("Input: Quantity(1.0, YARDS).add(Quantity(3.0, FEET), YARDS) -> Output: "
                + QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.YARDS),
                        new QuantityLength(3.0, LengthUnit.FEET),
                        LengthUnit.YARDS));

        System.out.println("Input: Quantity(2.54, CENTIMETERS).convertTo(INCHES) -> Output: "
                + new QuantityLength(2.54, LengthUnit.CENTIMETERS).convertTo(LengthUnit.INCHES));

        System.out.println("Input: Quantity(5.0, FEET).add(Quantity(0.0, INCHES), FEET) -> Output: "
                + QuantityLength.add(
                        new QuantityLength(5.0, LengthUnit.FEET),
                        new QuantityLength(0.0, LengthUnit.INCHES),
                        LengthUnit.FEET));

        System.out.println("Input: LengthUnit.FEET.convertToBaseUnit(12.0) -> Output: "
                + LengthUnit.FEET.convertToBaseUnit(12.0));

        System.out.println("Input: LengthUnit.INCHES.convertToBaseUnit(12.0) -> Output: "
                + LengthUnit.INCHES.convertToBaseUnit(12.0));
    }
}