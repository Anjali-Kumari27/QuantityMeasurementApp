# Quantity Measurement App
## UC3 – Generic Quantity Class (DRY Principle)

**Branch:** feature/UC3-GenericQuantity
**Date:** 19 February 2026

---

## Overview
- This use case refactors the separate Feet and Inches classes into a single generic QuantityLength class.

- It eliminates code duplication and applies the DRY (Don't Repeat Yourself) principle while preserving all functionality from UC1 and UC2.

- The system supports cross-unit equality comparison by converting values to a common base unit before comparison.
  
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
 │
 ├── .gitignore
 └── pom.xml
```

---

## Features
1. Single generic class to handle multiple length units
2. Supports Feet and Inches comparison
3. Cross-unit equality (1 foot = 12 inches)
4. Conversion to common base unit before comparison
5. Eliminates duplicate code from UC1 and UC2
6. Easy to extend for future units

---

## Test Coverage
- Feet to Feet equality
- Inch to Inch equality
- Feet to Inch equivalent comparison
- Different value comparison
- Null comparison
- Invalid unit handling
- Same reference comparison

---

## Key Concepts
1. DRY Principle (Don't Repeat Yourself)
2. Enum usage for unit type safety
3. Encapsulation
4. Abstraction of conversion logic
5. Value-based equality
6. Equality contract compliance
7. Scalability and maintainability

---

## Design Improvement
- UC3 removes duplicated constructor and equals() logic from separate Feet and Inches classes.
- A single QuantityLength class with LengthUnit enum improves maintainability and scalability.
- New units can be added without rewriting equality logic.

🔗 Code Link
[GenericLength](https://github.com/Anjali-Kumari27/QuantityMeasurementApp/tree/feature/UC3-GenericLength)
