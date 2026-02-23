# Quantity Measurement App
## UC6 – Addition of Two Length Units (Same Category)

**Branch:** feature/UC6-LengthAddition
**Date:** 20 February 2026

---

## Overview
- This use case extends UC5 by introducing addition operations between length measurements.

- The QuantityLength API can now add two length values (possibly in different units) and return the result in the unit of the first operand (or specified target unit).

- Example:
1 foot + 12 inches = 2 feet

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
1. Addition of same-unit measurements
2. Cross-unit addition with automatic conversion
3. Result returned in unit of first operand (or specified target unit)
4. Uses base unit normalization for accurate arithmetic
5. Maintains immutability (returns new object)
6. Validates null, NaN, and infinite inputs
7. Maintains backward compatibility with UC1–UC5

---

## Test Coverage
- Same-unit addition
- Cross-unit addition
- Yard and centimeter addition support
- Commutativity validation
- Identity element (adding zero)
- Negative value handling
- Large value addition
- Small value precision check
- Null operand validation
- Target unit consistency

---

## Key Concepts
1. Arithmetic operations on value objects
2. Immutability and functional design
3. Base unit normalization for arithmetic
4. Unit conversion reuse from UC5
5. Commutativity and identity properties
6. Floating-point precision handling (epsilon)
7. Method overloading for flexible API
8. Input validation and exception handling
9. Factory-style object creation in add()

---

## Design Strength
- Addition reuses centralized conversion logic.
- No duplication of conversion rules.
- Result is always consistent and unit-safe.
- Preserves DRY principle and scalable architecture.

🔗 Code Link
[UnitAddition](https://github.com/Anjali-Kumari27/QuantityMeasurementApp/tree/feature/UC6-UnitAddition)
