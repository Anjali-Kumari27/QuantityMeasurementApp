# Quantity Measurement App
## UC5 – Unit-to-Unit Conversion (Same Measurement Type)

**Branch:** feature/UC5-UnitConversion
**Date:** 20 February 2026

## Overview
- This use case extends UC4 by introducing explicit unit-to-unit conversion functionality.

- Instead of only checking equality, the QuantityLength API now provides a convert() method that converts a numeric value from a source unit to a target unit using centralized conversion factors.

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
 │    └── java/com/apps/quantitymeasurement  
 │
 ├── .gitignore
 └── pom.xml
```

---

## Features
1. Supports conversion between Feet, Inches, Yards, and Centimeters
2. Static convert(value, sourceUnit, targetUnit) API
3. Instance-based convertTo() method
4. Centralized conversion via base unit normalization
5. Input validation for null, NaN, and infinite values
6. Optional precision handling using epsilon tolerance
7. Maintains backward compatibility with UC1–UC4

---

## Test Coverage
- Feet ↔ Inches conversion
- Yards ↔ Feet conversion
- Centimeters ↔ Inches conversion
- Cross-unit multi-step conversion
- Round-trip conversion accuracy
- Zero value conversion
- Negative value conversion
- Large and small value precision checks
- Same-unit conversion
- Invalid unit handling
- NaN and Infinite value validation
- Precision tolerance using epsilon

---

## Key Concepts
1. Enum with conversion factors
2. Immutability and value object semantics
3. Method overriding (equals(), toString())
4. Method overloading (conversion demonstration methods)
5. Encapsulation using private helper methods
6. Input validation and exception handling
7. Base unit normalization strategy
8. API design with clear static interface
9. Floating-point precision handling

## Design Strength
- Conversion logic is centralized and consistent.
- Adding new units requires only enum modification.
- Conversion and equality share the same base normalization logic.
- Maintains DRY principle and scalable architecture.

🔗 Code Link
[UnitConversion](https://github.com/Anjali-Kumari27/QuantityMeasurementApp/tree/feature/UC5-UnitConversion)
