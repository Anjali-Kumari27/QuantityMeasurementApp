package com.apps.quantitymeasurement.units;


import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.model.Quantity;
import com.apps.quantitymeasurement.units.LengthUnit;

import static org.junit.jupiter.api.Assertions.*;

public class InchEqualityTest {

    @Test
    void testEquality_SameValue() {
		Quantity<LengthUnit> i1 = new Quantity<>(1.0, LengthUnit.INCHES);
		Quantity<LengthUnit> i2 = new Quantity<>(1.0, LengthUnit.INCHES);

        assertTrue(i1.equals(i2),
                "1.0 inch should be equal to 1.0 inch");
    }

    @Test
    void testEquality_DifferentValue() {
    		Quantity<LengthUnit> i1 = new Quantity<>(1.0, LengthUnit.INCHES);
		Quantity<LengthUnit> i2 = new Quantity<>(2.0, LengthUnit.INCHES);

        assertFalse(i1.equals(i2),
                "1.0 inch should NOT be equal to 2.0 inch");
    }

    @Test
    void testEquality_NullComparison() {
		Quantity<LengthUnit> i1 = new Quantity<>(1.0, LengthUnit.INCHES);

        assertFalse(i1.equals(null),
                "Inch should NOT be equal to null");
    }

    @Test
    void testEquality_NonNumericInput() {
    		Quantity<LengthUnit> i1 = new Quantity<>(1.0, LengthUnit.INCHES);

        assertFalse(i1.equals("abc"),
                "Inch should NOT be equal to non-Inch object");
    }

    @Test
    void testEquality_SameReference() {
    		Quantity<LengthUnit> i1 = new Quantity<>(1.0, LengthUnit.INCHES);

        assertTrue(i1.equals(i1),
                "Object should be equal to itself (reflexive property)");
    }

    @Test
    void testEquality_InvalidNumber() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, LengthUnit.FEET),
                "NaN value should throw exception");
    }
}