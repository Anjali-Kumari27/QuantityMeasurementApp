# Quantity Measurement App
## UC10 – Generic Quantity Class with Unit Interface (Multi-Category Support)

---

*Branch:* feature/UC10-GenericQuantity
**Date:** 21 February 2026

---

## Overview
1. UC10 refactors the entire system into a single generic Quantity<U extends IMeasurable> class.

2. This eliminates duplication across QuantityLength and QuantityWeight, consolidates unit behavior under a common interface, and establishes a scalable architecture for unlimited measurement categories.

3. All functionality from UC1–UC9 is preserved while significantly improving maintainability, scalability, and design quality.

4. Architecture Summary
- IMeasurable → Common contract for all unit enums
- LengthUnit → Implements IMeasurable
- WeightUnit → Implements IMeasurable
- Quantity<U> → Generic value object for all categories
- QuantityMeasurementApp → Simplified orchestration layer

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
 │                         └── TargetAdditionTest.java
 │                         └── UnitAdditionTest.java
 │                         └── UnitConversionTest.java
 │                         └── YardEqualityTest.java
 │
 ├── .gitignore
 └── pom.xml

```

---

## Core Features
- Single generic Quantity<U> class for all measurement categories
- IMeasurable interface standardizes unit behavior
- LengthUnit and WeightUnit implement IMeasurable
- Equality, conversion, and addition work across all categories
- Explicit target unit support for arithmetic
- Cross-category comparisons prevented
- Immutability preserved
- Full backward compatibility with UC1–UC9
- Scalable for future categories (Volume, Temperature, Time, etc.)

---

## Example Operations

- Length:
new Quantity<>(1.0, LengthUnit.FEET)
    .equals(new Quantity<>(12.0, LengthUnit.INCHES)) → true

new Quantity<>(1.0, LengthUnit.FEET)
    .convertTo(LengthUnit.INCHES) → Quantity(12.0, INCHES)

- Weight:
new Quantity<>(1.0, WeightUnit.KILOGRAM)
    .equals(new Quantity<>(1000.0, WeightUnit.GRAM)) → true

new Quantity<>(1.0, WeightUnit.KILOGRAM)
    .add(new Quantity<>(1000.0, WeightUnit.GRAM), WeightUnit.KILOGRAM)
    → Quantity(2.0, KILOGRAM)

- Cross-Category Prevention:
new Quantity<>(1.0, LengthUnit.FEET)
    .equals(new Quantity<>(1.0, WeightUnit.KILOGRAM)) → false

- Compiler prevents:
Quantity<LengthUnit> ≠ Quantity<WeightUnit>

---

## Test Coverage
1. IMeasurable interface contract validation
2. Length operations (equality, conversion, addition)
3. Weight operations (equality, conversion, addition)
4. Cross-category prevention
5. Generic constructor validation (null unit, NaN, infinite)
6. Explicit target unit arithmetic
7. Round-trip conversion precision
8. hashCode() consistency with equals()
9. Backward compatibility with UC1–UC9
10. Wildcard and bounded type parameter safety
11. Scalability validation with new unit enums

---

## Key Concepts
- Generics with bounded type parameters (<U extends IMeasurable>)
- Interface-based design
- Single Responsibility Principle (SRP)
- Open-Closed Principle (OCP)
- Don't Repeat Yourself (DRY)
- Polymorphism and delegation
- Composition over inheritance
- Cross-category type safety
- Enum behavior encapsulation
- Type erasure awareness
- Immutability and functional-style operations

---

## Design Strength
1. Logic implemented once in Quantity<U> and reused across categories.
2. Unit enums encapsulate only conversion behavior.
3. New measurement categories require:
- New enum implementing IMeasurable
- No changes to Quantity<U>
- No changes to demonstration logic

---

- Code growth is linear, not exponential.
- Maintenance burden significantly reduced.
- Architecture is enterprise-grade and future-proof.

🔗 Code Link
[GenericQuantity](https://github.com/Anjali-Kumari27/QuantityMeasurementApp/tree/feature/UC10-GenericQuantity)
