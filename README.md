# Quantity Measurement App
## UC12 – Subtraction & Division Operations

---

**Branch:** feature/UC12-ArithmeticOperations
**Date:** 23 February 2026

---

## Overview
UC12 adds two new arithmetic operations to the generic Quantity<U extends IMeasurable>:

1) Subtraction
   - Works within the SAME category (Length/Weight/Volume)
   - Supports cross-unit subtraction (ex: 10 FEET - 6 INCHES)
   - Returns a new Quantity in:
     - implicit unit (this.unit), OR
     - explicit target unit

2) Division
   - Works within the SAME category
   - Supports cross-unit division (ex: 24 INCHES ÷ 2 FEET)
   - Returns a dimensionless double (ratio)

Immutability is preserved: original Quantity objects never change.

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
 │                         └── IMeasurable.java
 │                         └── Inch.java
 │                         └── LengthUnit.java
 │                         └── QuantityLength.java
 │                         └── QuantityMeasurementApp.java
 │                         └── QuantityWeight.java
 │                         └── VolumeUnit.java
 │                         └── WeightUnit.java
 │  
 └── test  
 │    └── java/
 │         └── com/
 │              └── apps/
 │                   └── quantitymeasurement
 │                         └── FeetEqualityTest.java
 │                         └── GenericQuantityTest.java
 │                         └── InchEqualityTest.java
 │                         └── QuantityLengthTest.java
 │                         └── QuantityWeightTest.java
 │                         └── QuantityWeightTest.java
 │                         └── SubtractionDivisionTest.java
 │                         └── TargetAdditionTest.java
 │                         └── UnitAdditionTest.java
 │                         └── UnitConversionTest.java
 │                         └── VolumeTest.java
 │                         └── YardEqualityTest.java
 │
 ├── .gitignore
 └── pom.xml

```

---

## Features
1. Subtraction:
- subtract(other) -> result in this.unit
- subtract(other, targetUnit) -> result in targetUnit
- supports negative and zero results
- supports all categories (length, weight, volume)

2. Division:
- divide(other) -> returns double ratio
- validates divisor is not zero
- supports all categories (length, weight, volume)

3. Safety:
- prevents cross-category ops (length vs weight, etc.)
- rejects null args, null target unit
- rejects NaN / Infinity values
- consistent rounding for Quantity results (ex: 2 decimal places)

---

## Example Output (Expected)
- Subtraction (Implicit Unit):
new Quantity<>(10.0, FEET).subtract(new Quantity<>(6.0, INCHES))
→ Quantity(9.5, FEET)

new Quantity<>(10.0, KILOGRAM).subtract(new Quantity<>(5000.0, GRAM))
→ Quantity(5.0, KILOGRAM)

new Quantity<>(5.0, LITRE).subtract(new Quantity<>(500.0, MILLILITRE))
→ Quantity(4.5, LITRE)

- Subtraction (Explicit Target Unit):
new Quantity<>(10.0, FEET).subtract(new Quantity<>(6.0, INCHES), INCHES)
→ Quantity(114.0, INCHES)

Division:
new Quantity<>(10.0, FEET).divide(new Quantity<>(2.0, FEET))
→ 5.0

new Quantity<>(24.0, INCHES).divide(new Quantity<>(2.0, FEET))
→ 1.0

- Error Cases
new Quantity<>(10.0, FEET).subtract(null)
→ throws IllegalArgumentException

new Quantity<>(10.0, FEET).divide(new Quantity<>(0.0, FEET))
→ throws ArithmeticException

new Quantity<>(10.0, FEET).subtract(new Quantity<>(5.0, KILOGRAM))
→ throws IllegalArgumentException (cross-category)

---

## Test Coverage
1. Subtraction:
- same unit subtraction
- cross-unit subtraction
- explicit target unit subtraction
- negative result
- zero result
- null operand / null target unit
- NaN / Infinity validation
- cross-category prevention
- immutability check

2. Division:
- same unit division
- cross-unit division
- ratio > 1, < 1, == 1
- division by zero handling
- null operand
- NaN / Infinity validation
- cross-category prevention
- immutability check

---

## SOLID / Design Check (Quick)
- SRP: Quantity handles arithmetic; units handle conversion.
- OCP: new categories only add new Unit enums (no change to Quantity logic).
- DRY: subtraction/division reuse base-unit normalization.
- Note (Possible improvement): If validation logic is repeated across add/subtract/divide,
   it can be extracted into private helper methods inside Quantity (still SRP-safe).

---

🔗 Code Link
[SubtractionDivision](https://github.com/Anjali-Kumari27/QuantityMeasurementApp/tree/feature/UC12-ArithmeticOperations)
