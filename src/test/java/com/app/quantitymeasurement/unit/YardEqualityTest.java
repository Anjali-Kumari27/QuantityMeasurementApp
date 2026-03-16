package com.app.quantitymeasurement.unit;

import com.app.quantitymeasurement.quantity.Quantity;
import com.app.quantitymeasurement.unit.LengthUnit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class YardEqualityTest {

    @Test
    void testEquality_YardToYard_SameValue() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.YARDS);
        Quantity<LengthUnit> q2 = new Quantity<>(1.0, LengthUnit.YARDS);

        assertTrue(q1.equals(q2), "1.0 yard should equal 1.0 yard");
    }

    @Test
    void testEquality_YardToYard_DifferentValue() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.YARDS);
        Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.YARDS);

        assertFalse(q1.equals(q2), "1.0 yard should NOT equal 2.0 yards");
    }

    @Test
    void testEquality_YardToFeet_EquivalentValue() {
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARDS);
        Quantity<LengthUnit> feet = new Quantity<>(3.0, LengthUnit.FEET);

        assertTrue(yard.equals(feet), "1.0 yard should equal 3.0 feet");
    }

    @Test
    void testEquality_FeetToYard_EquivalentValue() {
        Quantity<LengthUnit> feet = new Quantity<>(3.0, LengthUnit.FEET);
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARDS);

        assertTrue(feet.equals(yard), "3.0 feet should equal 1.0 yard (symmetry)");
    }

    @Test
    void testEquality_YardToInches_EquivalentValue() {
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARDS);
        Quantity<LengthUnit> inches = new Quantity<>(36.0, LengthUnit.INCHES);

        assertTrue(yard.equals(inches), "1.0 yard should equal 36.0 inches");
    }

    @Test
    void testEquality_InchesToYard_EquivalentValue() {
        Quantity<LengthUnit> inches = new Quantity<>(36.0, LengthUnit.INCHES);
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARDS);

        assertTrue(inches.equals(yard), "36.0 inches should equal 1.0 yard (symmetry)");
    }

    @Test
    void testEquality_YardToFeet_NonEquivalentValue() {
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARDS);
        Quantity<LengthUnit> feet = new Quantity<>(2.0, LengthUnit.FEET);

        assertFalse(yard.equals(feet), "1.0 yard should NOT equal 2.0 feet");
    }

    @Test
    void testEquality_CentimetersToCentimeters_SameValue() {
        Quantity<LengthUnit> c1 = new Quantity<>(2.0, LengthUnit.CENTIMETERS);
        Quantity<LengthUnit> c2 = new Quantity<>(2.0, LengthUnit.CENTIMETERS);

        assertTrue(c1.equals(c2), "2.0 cm should equal 2.0 cm");
    }

    @Test
    void testEquality_CentimetersToInches_EquivalentValue() {
        Quantity<LengthUnit> cm = new Quantity<>(1.0, LengthUnit.CENTIMETERS);
        Quantity<LengthUnit> inch = new Quantity<>(0.393701, LengthUnit.INCHES);

        assertTrue(cm.equals(inch), "1.0 cm should equal 0.393701 inch");
    }

    @Test
    void testEquality_CentimetersToFeet_NonEquivalentValue() {
        Quantity<LengthUnit> cm = new Quantity<>(1.0, LengthUnit.CENTIMETERS);
        Quantity<LengthUnit> ft = new Quantity<>(1.0, LengthUnit.FEET);

        assertFalse(cm.equals(ft), "1.0 cm should NOT equal 1.0 feet");
    }

    @Test
    void testEquality_MultiUnit_TransitiveProperty() {
        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.YARDS);
        Quantity<LengthUnit> b = new Quantity<>(3.0, LengthUnit.FEET);
        Quantity<LengthUnit> c = new Quantity<>(36.0, LengthUnit.INCHES);

        assertTrue(a.equals(b), "1 yard == 3 feet");
        assertTrue(b.equals(c), "3 feet == 36 inches");
        assertTrue(a.equals(c), "transitive: 1 yard == 36 inches");
    }

    @Test
    void testEquality_AllUnits_ComplexScenario() {
        Quantity<LengthUnit> yards = new Quantity<>(2.0, LengthUnit.YARDS);
        Quantity<LengthUnit> feet = new Quantity<>(6.0, LengthUnit.FEET);
        Quantity<LengthUnit> inches = new Quantity<>(72.0, LengthUnit.INCHES);

        assertTrue(yards.equals(feet), "2 yards should equal 6 feet");
        assertTrue(feet.equals(inches), "6 feet should equal 72 inches");
        assertTrue(yards.equals(inches), "2 yards should equal 72 inches");
    }
}