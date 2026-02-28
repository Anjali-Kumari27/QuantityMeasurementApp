# Quantity Measurement App
## UC2 – Feet and Inches Measurement Equality

**Branch:** feature/UC2-FeetInchesEquality
**Date:** 18 February 2026

---

## Overview
This use case extends UC1 to support equality comparison for both **Feet** and **Inches** measurements.

Feet and Inches are treated as separate entities. The system validates input values and checks equality using proper object-oriented principles and safe floating-point comparison.

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
 │                         └── QuantityMeasurementApp.java
 │  
 └── test  
 │    └── java/
 │         └── com/
 │              └── apps/
 │                   └── quantitymeasurement
 │                         └── FeetEqualityTest.java
 │                         └── InchEqualityTest.java
 │
 ├── .gitignore
 └── pom.xml
```

---

## Features
1. Supports equality comparison for Feet measurements
2. Supports equality comparison for Inches measurements
3. Treats Feet and Inches separately
4. Returns true if equal, otherwise false
5. Handles null and invalid comparisons safely
6. Reduces dependency on main method using separate equality methods

---

## Test Coverage
- Same value comparison
- Different value comparison
- Null comparison
- Same reference comparison
- Non-numeric input handling

---

## Key Concepts
1. equals() method implementation
2. Safe floating-point comparison
3. Type safety
4. Encapsulation
5. Immutability
6. Equality contract compliance

---

## Design Note
- Using separate Feet and Inches classes leads to code duplication.
- Both classes contain similar constructor and equals() logic, which violates the DRY (Don't Repeat Yourself) principle.

- A better design would be to introduce a common Quantity class or use a unit-type parameter to reduce redundancy and improve maintainability.

🔗 Code Link
[FeetInchEquality](https://github.com/Anjali-Kumari27/QuantityMeasurementApp/tree/feature/UC2-InchEquality)
