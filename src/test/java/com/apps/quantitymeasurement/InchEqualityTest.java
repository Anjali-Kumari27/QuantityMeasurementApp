package com.apps.quantitymeasurement;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InchEqualityTest {

    @Test
    void testEquality_SameValue() {
        Inch i1 = new Inch(1.0);
        Inch i2 = new Inch(1.0);

        assertTrue(i1.equals(i2),
                "1.0 inch should be equal to 1.0 inch");
    }

    @Test
    void testEquality_DifferentValue() {
        Inch i1 = new Inch(1.0);
        Inch i2 = new Inch(2.0);

        assertFalse(i1.equals(i2),
                "1.0 inch should NOT be equal to 2.0 inch");
    }

    @Test
    void testEquality_NullComparison() {
        Inch i1 = new Inch(1.0);

        assertFalse(i1.equals(null),
                "Inch should NOT be equal to null");
    }

    @Test
    void testEquality_NonNumericInput() {
        Inch i1 = new Inch(1.0);

        assertFalse(i1.equals("abc"),
                "Inch should NOT be equal to non-Inch object");
    }

    @Test
    void testEquality_SameReference() {
        Inch i1 = new Inch(1.0);

        assertTrue(i1.equals(i1),
                "Object should be equal to itself (reflexive property)");
    }

    @Test
    void testEquality_InvalidNumber() {
        assertThrows(IllegalArgumentException.class,
                () -> new Inch(Double.NaN),
                "NaN value should throw exception");
    }
}