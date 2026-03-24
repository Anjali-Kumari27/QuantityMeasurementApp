package com.app.quantitymeasurement.unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VolumeUnitTest {

	@Test
	void testToBase_MilliLitreToLitre() {
		assertEquals(1.0, VolumeUnit.MILLILITRE.toBase(1000.0));
	}

	@Test
	void testFromBase_LitreToMilliLitre() {
		assertEquals(1000.0, VolumeUnit.MILLILITRE.fromBase(1.0));
	}

	@Test
	void testGetUnitName() {
		assertEquals("LITRE", VolumeUnit.LITRE.getUnitName());
	}
}