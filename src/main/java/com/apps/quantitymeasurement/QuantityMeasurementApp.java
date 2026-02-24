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

		// UC9 Weight
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

		// Length
		Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> l2 = new Quantity<>(12.0, LengthUnit.INCHES);

		System.out.println("Length Equality: " + l1.equals(l2));
		System.out.println("Length Convert: " + l1.convertTo(LengthUnit.INCHES));
		System.out.println("Length Add: " + l1.add(l2, LengthUnit.FEET));

		System.out.println();

		// Weight
		Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

		System.out.println("Weight Equality: " + w1.equals(w2));
		System.out.println("Weight Convert: " + w1.convertTo(WeightUnit.GRAM));
		System.out.println("Weight Add: " + w1.add(w2, WeightUnit.KILOGRAM));

		System.out.println();

		// Cross-category (won't compile if generics used correctly)
		// l1.equals(w1)
		// Compile error because types differ (Quantity<LengthUnit> vs
		// Quantity<WeightUnit>)

		// Runtime check example (only if you force raw type / Object)
		System.out.println("Cross-category prevention (runtime): " + l1.equals((Object) w1));

		// Volume (UC11)
		Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> v3 = new Quantity<>(1.0, VolumeUnit.GALLON);

		System.out.println("Volume Equality (1L == 1000mL): " + v1.equals(v2));
		System.out.println("Volume Convert (1L -> mL): " + v1.convertTo(VolumeUnit.MILLILITRE));
		System.out.println("Volume Convert (1 gal -> L): " + v3.convertTo(VolumeUnit.LITRE));
		System.out.println("Volume Add (1L + 1000mL -> L): " + v1.add(v2, VolumeUnit.LITRE));
		System.out.println("Volume Add (1L + 1gal -> mL): " + v1.add(v3, VolumeUnit.MILLILITRE));

		// UC12 Subtraction (Length)
		// 9.50 feet -> prints 9.5 FEET due to rounding
		System.out.println(l1 + " - " + l2 + " = " + l1.subtract(l2));

		// 114.0 INCHES
		System.out.println(l1 + " - " + l2 + " in INCHES = " + l1.subtract(l2, LengthUnit.INCHES));

		// UC12 Division (Length)
		System.out.println(new Quantity<>(24.0, LengthUnit.INCHES).divide(new Quantity<>(2.0, LengthUnit.FEET))); // 1.0

		// UC12 Subtraction (Weight)
		// 5.0 KILOGRAM
		System.out.println(w1 + " - " + w2 + " = " + w1.subtract(w2));

		// 5000.0 GRAM
		System.out.println(w1 + " - " + w2 + " in GRAM = " + w1.subtract(w2, WeightUnit.GRAM));

		// UC12 Division (Weight)
		System.out.println(new Quantity<>(2000.0, WeightUnit.GRAM).divide(new Quantity<>(1.0, WeightUnit.KILOGRAM)));

		// UC12 Subtraction (Volume)
		// 4.5 LITRE
		System.out.println(v1 + " - " + v2 + " = " + v1.subtract(v2));

		// 3000.0 MILLILITRE
		System.out.println(v1 + " - " + new Quantity<>(2.0, VolumeUnit.LITRE) + " in MILLILITRE = "
				+ v1.subtract(new Quantity<>(2.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE));

		// UC12 Division (Volume)
		// 0.5
		System.out.println(new Quantity<>(5.0, VolumeUnit.LITRE).divide(new Quantity<>(10.0, VolumeUnit.LITRE)));

		
		
		
		// ===== Temperature Equality =====
		Quantity<TemperatureUnit> t1 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);

		Quantity<TemperatureUnit> t2 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);

		System.out.println("0°C equals 32°F? " + t1.equals(t2));

		// ===== Temperature Conversion =====
		System.out.println(
				"100°C to F = " + new Quantity<>(100.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT));

		// ===== Unsupported Arithmetic =====
		try {
			t1.add(new Quantity<>(50.0, TemperatureUnit.CELSIUS));
		} catch (UnsupportedOperationException e) {
			System.out.println("Expected error: " + e.getMessage());
		}
	}

}