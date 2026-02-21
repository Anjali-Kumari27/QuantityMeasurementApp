package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FeetEqualityTest {

	// ---------------- FEET TESTS -------------------
	@Test
	void testEquality_SameValue() {
		Feet f1 = new Feet(1.0);
		Feet f2 = new Feet(1.0);

		assertTrue(f1.equals(f2), "1.0 ft should be equal to 1.0 ft");
	}

	@Test
	void testEquality_DifferentValue() {
		Feet f1 = new Feet(1.0);
		Feet f2 = new Feet(2.0);

		assertFalse(f1.equals(f2), "1.0 ft should NOT be equal to 2.0 ft");
	}

	@Test
	void testEquality_NullComparison() {
		Feet f1 = new Feet(1.0);

		assertFalse(f1.equals(null), "1.0 ft should NOT be equal to null");
	}

	@Test
	void testEquality_NonNumericInput() {
		Feet f1 = new Feet(1.0);

		// equals takes Object, so "non-numeric input" means comparing with a non-Feet
		// object
		assertFalse(f1.equals("abc"), "Feet should NOT be equal to a non-Feet object");
	}

	@Test
	void testEquality_SameReference() {
		Feet f1 = new Feet(1.0);

		assertTrue(f1.equals(f1), "Object should be equal to itself (reflexive)");
	}

}
