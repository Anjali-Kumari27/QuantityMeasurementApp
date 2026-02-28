# Quantity Measurement App
## UC8 – Refactoring Unit Enum to Standalone with Conversion Responsibility

---

**Branch:** feature/UC8-StandaloneUnit
**Date:** 21 February 2026

---

## Overview
- This use case refactors the design from UC1–UC7 by extracting the LengthUnit enum into a standalone top-level class.

- The LengthUnit enum now owns full responsibility for unit conversion (to and from base unit), while QuantityLength focuses only on comparison and arithmetic operations.

- This improves cohesion, reduces coupling, eliminates circular dependency risks, and establishes a scalable architectural pattern for future measurement categories.

---

## Project Structure
```
src  
 ├── main  
 │    └── java/
 │         └── com/
 │              └── apps/
 │                   └── quantitymeasurement
 │                         └── Feet.java
 │                         └── Inch.java
 │                         └── LengthUnit.java
 │                         └── QuantityLength.java
 │                         └── QuantityMeasurementApp.java
 │  
 └── test  
 │    └── java/
 │         └── com/
 │              └── apps/
 │                   └── quantitymeasurement
 │                         └── FeetEqualityTest.java
 │                         └── InchEqualityTest.java
 │                         └── QuantityLengthTest.java
 │                         └── TargetAdditionTest.java
 │                         └── UnitAdditionTest.java
 │                         └── UnitConversionTest.java
 │                         └── YardEqualityTest.java
 │
 ├── .gitignore
 └── pom.xml

```

---

## Features
1. LengthUnit extracted as standalone enum
2. Unit conversion responsibility centralized in LengthUnit
3. convertToBaseUnit() and convertFromBaseUnit() methods added
4. QuantityLength delegates all conversion logic
5. Eliminates circular dependency risk
6. Maintains backward compatibility with UC1–UC7
7. Supports FEET, INCHES, YARDS, CENTIMETERS
8. Scalable architecture for new measurement categories

---

## Example Operations
- Quantity(1.0, FEET).convertTo(INCHES) → Quantity(12.0, INCHES)
- Quantity(36.0, INCHES).equals(Quantity(1.0, YARDS)) → true
- Quantity(1.0, FEET).add(Quantity(12.0, INCHES), FEET) → Quantity(2.0, FEET)
- LengthUnit.INCHES.convertToBaseUnit(12.0) → 1.0 (feet)
- LengthUnit.YARDS.convertFromBaseUnit(3.0) → 1.0 (yard)

---

## Test Coverage
1. Standalone LengthUnit constant validation
2. Base unit conversion (toBaseUnit)
3. From base unit conversion
4. Equality after refactoring
5. Conversion after refactoring
6. Addition with implicit target
7. Addition with explicit target unit
8. Backward compatibility with UC1–UC7
9. Null unit validation
10. Invalid value validation (NaN, Infinite)
11. Round-trip conversion precision
12. Architectural scalability validation

---

## Key Concepts
- Single Responsibility Principle (SRP)
- Separation of Concerns
- Delegation pattern
- Dependency inversion
- Circular dependency elimination
- Enum encapsulation of data and behavior
- Refactoring best practices
- Architectural scalability
- Type safety using enums
- Immutability and thread safety of enum constants

---

## Design Strength
1. LengthUnit handles all conversion logic.
2. QuantityLength handles comparison and arithmetic only.
3. High cohesion and low coupling achieved.
4. New categories (WeightUnit, VolumeUnit, etc.) can follow same pattern.
5. No changes required to client code.
6. All previous functionality preserved.

---

🔗 Code Link
[StandaloneLengthUnitRefactor](https://github.com/Anjali-Kumari27/QuantityMeasurementApp/tree/feature/UC8-StandaloneUnit)
