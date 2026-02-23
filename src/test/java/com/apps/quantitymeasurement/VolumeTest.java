package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VolumeTest {

	private static final double EPS = 1e-6;

	// Equality
	@Test
	void testEquality_LitreToLitre_SameValue() {
		assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(1.0, VolumeUnit.LITRE)));
	}

	@Test
	void testEquality_LitreToMillilitre_EquivalentValue() {
		assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));
	}

	@Test
	void testEquality_MillilitreToLitre_EquivalentValue() {
		assertTrue(new Quantity<>(1000.0, VolumeUnit.MILLILITRE).equals(new Quantity<>(1.0, VolumeUnit.LITRE)));
	}

	@Test
	void testEquality_GallonToLitre_EquivalentValue() {
		assertTrue(new Quantity<>(1.0, VolumeUnit.GALLON).equals(new Quantity<>(3.78541, VolumeUnit.LITRE)));
	}

	@Test
	void testEquality_LitreToGallon_EquivalentValue() {
		// 1 litre ≈ 0.264172 gallon
		assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(0.264172, VolumeUnit.GALLON)));
	}

	@Test
	void testEquality_DifferentValue() {
		assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(2.0, VolumeUnit.LITRE)));
	}

	// Conversion
	@Test
	void testConversion_LitreToMillilitre() {
		Quantity<VolumeUnit> converted = new Quantity<>(1.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE);

		assertEquals(1000.00, converted.getValue(), EPS);
		assertEquals(VolumeUnit.MILLILITRE, converted.getUnit());
	}

	@Test
	void testConversion_GallonToLitre() {
		Quantity<VolumeUnit> converted = new Quantity<>(1.0, VolumeUnit.GALLON).convertTo(VolumeUnit.LITRE);

		// Note: Quantity.convertTo() rounds to 2 decimals in UC10 implementation
		assertEquals(3.79, converted.getValue(), EPS);
		assertEquals(VolumeUnit.LITRE, converted.getUnit());
	}

	@Test
	void testConversion_LitreToGallon() {
		Quantity<VolumeUnit> converted = new Quantity<>(1.0, VolumeUnit.LITRE).convertTo(VolumeUnit.GALLON);

		// 1 / 3.78541 ≈ 0.264172... rounded to 2 decimals => 0.26
		assertEquals(0.26, converted.getValue(), EPS);
		assertEquals(VolumeUnit.GALLON, converted.getUnit());
	}

	@Test
	void testConversion_SameUnit() {
		Quantity<VolumeUnit> converted = new Quantity<>(5.0, VolumeUnit.LITRE).convertTo(VolumeUnit.LITRE);

		assertEquals(5.00, converted.getValue(), EPS);
		assertEquals(VolumeUnit.LITRE, converted.getUnit());
	}

	// Addition (Implicit Target = first operand unit)
	@Test
	void testAddition_LitrePlusMillilitre_ImplicitTargetLitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
				.add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));

		assertEquals(2.00, result.getValue(), EPS);
		assertEquals(VolumeUnit.LITRE, result.getUnit());
	}

	@Test
	void testAddition_MillilitrePlusLitre_ImplicitTargetMillilitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
				.add(new Quantity<>(1.0, VolumeUnit.LITRE));

		assertEquals(2000.00, result.getValue(), EPS);
		assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
	}

	// Addition (Explicit target unit)
	@Test
	void testAddition_ExplicitTargetMillilitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE).add(new Quantity<>(1.0, VolumeUnit.GALLON),
				VolumeUnit.MILLILITRE);

		// 1 L + 3.78541 L = 4.78541 L => 4785.41 mL (rounded to 2 decimals)
		assertEquals(4785.41, result.getValue(), EPS);
		assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetGallon() {
		Quantity<VolumeUnit> result = new Quantity<>(500.0, VolumeUnit.MILLILITRE)
				.add(new Quantity<>(1.0, VolumeUnit.LITRE), VolumeUnit.GALLON);

		// 0.5 L + 1 L = 1.5 L => /3.78541 ≈ 0.396258... rounded => 0.40
		assertEquals(0.40, result.getValue(), EPS);
		assertEquals(VolumeUnit.GALLON, result.getUnit());
	}

	// Cross-category prevention (runtime)
	@Test
	void testVolumeVsLength_Incompatible() {
		Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);

		assertFalse(volume.equals((Object) length));
	}

	@Test
	void testVolumeVsWeight_Incompatible() {
		Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);

		assertFalse(volume.equals((Object) weight));
	}

	// Validation
	@Test
	void testNullUnit_Throws() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(1.0, (VolumeUnit) null));
	}

	@Test
	void testInvalidValue_Throws() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.NaN, VolumeUnit.LITRE));
	}
}