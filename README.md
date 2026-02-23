# Quantity Measurement App
## UC4 – Yard Equality 

**Branch:** feature/UC4-YardEquality
**Date:** 20 February 2026

---

## Overview
- This use case extends UC3 by adding Yards and Centimeters as additional supported length units.

- The generic QuantityLength design allows new units to be added easily without modifying the core class. Only the LengthUnit enum is updated with appropriate conversion factors.

- All equality comparisons work seamlessly across Feet, Inches, Yards, and Centimeters.

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
1. Supports Feet, Inches, Yards, and Centimeters
1 Yard = 3 Feet = 36 Inches
1 Centimeter = 0.393701 Inches
2. Cross-unit equality comparison supported
3. Conversion handled using common base unit
4. No code duplication
5. Easily extendable for future units

---

## Test Coverage
- Yard to Yard equality
- Yard to Feet conversion
- Yard to Inches conversion
- Centimeter to Centimeter equality
- Centimeter to Inch conversion
- Multi-unit transitive comparisons
- Null comparison handling
- Invalid unit validation
- Same reference comparison

---

## Key Concepts
1. Scalable generic design
2. Enum extensibility
3. Centralized conversion factor management
4. Cross-unit mathematical accuracy
5. DRY Principle validation
6. Backward compatibility with UC1, UC2, and UC3
7. Equality contract compliance
8. Type safety and null safety

---

## Design Strength
- New units are added only in the LengthUnit enum.
- No changes required in the QuantityLength class.
- Demonstrates clean architecture and scalable object-oriented design.

---

🔗 Code Link
[YardEquality](https://github.com/Anjali-Kumari27/QuantityMeasurementApp/tree/feature/UC4-YardEquality)
