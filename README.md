# Quantity Measurement App
## UC7 – Addition with Target Unit Specification

---

**Branch:** feature/UC7-TargetUnitAddition
**Date:** 20 February 2026

Overview
This use case extends UC6 by allowing the caller to explicitly specify the target unit for the addition result.

Instead of defaulting to the unit of the first operand, the result can now be returned in any supported unit (FEET, INCHES, YARDS, CENTIMETERS).

Example:
1 foot + 12 inches in YARDS → ~0.667 yards

---

## Project Structure
```
src  
 ├── main  
 │    └── java/
 │         └── com/
 │              └── apps/
 │                   └── quantitymeasurement
 │  
 └── test  
 │    └── java/
 │         └── com/
 │              └── apps/
 │                   └── quantitymeasurement
 │
 ├── .gitignore
 └── pom.xml
 └── README.md
```

---

## Features
1. Addition with explicit target unit parameter
2. Supports FEET, INCHES, YARDS, CENTIMETERS
3. Result always returned in specified target unit
4. Maintains immutability (returns new object)
5. Uses centralized base unit normalization
6. Reuses UC5 conversion logic
7. Backward compatible with UC6 implicit addition
8. Validates null, NaN, and invalid units

---

## Example Operations
- add(1 FEET, 12 INCHES, FEET) → 2 FEET
- add(1 FEET, 12 INCHES, INCHES) → 24 INCHES
- add(1 FEET, 12 INCHES, YARDS) → ~0.667 YARDS
- add(36 INCHES, 1 YARD, FEET) → 6 FEET
- add(5 FEET, 0 INCHES, YARDS) → ~1.667 YARDS
- add(5 FEET, -2 FEET, INCHES) → 36 INCHES

---

## Test Coverage
1. Explicit target = first operand unit
2. Explicit target = second operand unit
3. Explicit target different from both operands
4. Commutativity with explicit target
5. Zero operand addition
6. Negative value handling
7. Large-to-small scale conversion
8. Small-to-large scale conversion
9. Null target unit validation
10. Precision tolerance using epsilon

---

## Key Concepts
- Method overloading (implicit & explicit add methods)
- Explicit parameter control for result unit
- Private utility method for base addition logic
- Immutability and value object design
- DRY principle preservation
- Base unit normalization reuse
- Floating-point precision handling
- Input validation and exception handling
- API clarity and caller intent specification

---

## Design Strength
1. Addition logic is centralized and reusable.
2. Result unit is always explicitly controlled.
3. No duplication of arithmetic or conversion rules.
4. Scalable and consistent architecture.
5. Maintains mathematical correctness and commutativity.

---

🔗 Code Link
[AdditionWithTargetUnit](https://github.com/Anjali-Kumari27/QuantityMeasurementApp/tree/feature/UC7-TargetUnitAddition)
