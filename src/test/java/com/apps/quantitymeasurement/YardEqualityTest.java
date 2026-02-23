package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class YardEqualityTest {

    @Test
    void testEquality_YardToYard_SameValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.YARDS);

        assertTrue(q1.equals(q2), "1.0 yard should equal 1.0 yard");
    }

    @Test
    void testEquality_YardToYard_DifferentValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(2.0, LengthUnit.YARDS);

        assertFalse(q1.equals(q2), "1.0 yard should NOT equal 2.0 yards");
    }

    @Test
    void testEquality_YardToFeet_EquivalentValue() {
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);

        assertTrue(yard.equals(feet), "1.0 yard should equal 3.0 feet");
    }

    @Test
    void testEquality_FeetToYard_EquivalentValue() {
        QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);

        assertTrue(feet.equals(yard), "3.0 feet should equal 1.0 yard (symmetry)");
    }

    @Test
    void testEquality_YardToInches_EquivalentValue() {
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength inches = new QuantityLength(36.0, LengthUnit.INCHES);

        assertTrue(yard.equals(inches), "1.0 yard should equal 36.0 inches");
    }

    @Test
    void testEquality_InchesToYard_EquivalentValue() {
        QuantityLength inches = new QuantityLength(36.0, LengthUnit.INCHES);
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);

        assertTrue(inches.equals(yard), "36.0 inches should equal 1.0 yard (symmetry)");
    }

    @Test
    void testEquality_YardToFeet_NonEquivalentValue() {
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength feet = new QuantityLength(2.0, LengthUnit.FEET);

        assertFalse(yard.equals(feet), "1.0 yard should NOT equal 2.0 feet");
    }

    @Test
    void testEquality_CentimetersToCentimeters_SameValue() {
        QuantityLength c1 = new QuantityLength(2.0, LengthUnit.CENTIMETERS);
        QuantityLength c2 = new QuantityLength(2.0, LengthUnit.CENTIMETERS);

        assertTrue(c1.equals(c2), "2.0 cm should equal 2.0 cm");
    }

    @Test
    void testEquality_CentimetersToInches_EquivalentValue() {
        QuantityLength cm = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        QuantityLength inch = new QuantityLength(0.393701, LengthUnit.INCHES);

        assertTrue(cm.equals(inch), "1.0 cm should equal 0.393701 inch");
    }

    @Test
    void testEquality_CentimetersToFeet_NonEquivalentValue() {
        QuantityLength cm = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        QuantityLength ft = new QuantityLength(1.0, LengthUnit.FEET);

        assertFalse(cm.equals(ft), "1.0 cm should NOT equal 1.0 feet");
    }

    @Test
    void testEquality_MultiUnit_TransitiveProperty() {
        QuantityLength a = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength b = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength c = new QuantityLength(36.0, LengthUnit.INCHES);

        assertTrue(a.equals(b), "1 yard == 3 feet");
        assertTrue(b.equals(c), "3 feet == 36 inches");
        assertTrue(a.equals(c), "transitive: 1 yard == 36 inches");
    }

    @Test
    void testEquality_AllUnits_ComplexScenario() {
        QuantityLength yards = new QuantityLength(2.0, LengthUnit.YARDS);
        QuantityLength feet = new QuantityLength(6.0, LengthUnit.FEET);
        QuantityLength inches = new QuantityLength(72.0, LengthUnit.INCHES);

        assertTrue(yards.equals(feet), "2 yards should equal 6 feet");
        assertTrue(feet.equals(inches), "6 feet should equal 72 inches");
        assertTrue(yards.equals(inches), "2 yards should equal 72 inches");
    }
}
