# Quantity Measurement App
## UC9 – Weight Measurement (Equality, Conversion, Addition)

---

*Branch:* feature/UC9-WeightMeasurement
**Date:** 21 February 2026

---

## Overview
1. This use case extends the application to support a new measurement category: WEIGHT.

2. Weight measurements work independently from length measurements and support:
- Equality comparison
- Unit-to-unit conversion
- Addition (implicit + explicit target unit)

3. Supported weight units:
- KILOGRAM (base unit)
- GRAM (1 kg = 1000 g)
- POUND (1 lb ≈ 0.453592 kg)

4. Length functionality (UC1–UC8) remains unchanged and fully operational.

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
 │                         └── QuantityWeight.java
 │                         └── WeightUnit.java
 │  
 └── test  
 │    └── java/
 │         └── com/
 │              └── apps/
 │                   └── quantitymeasurement
 │                         └── FeetEqualityTest.java
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

## Features
1. Weight equality across units using base-unit normalization (kilogram)
2. Weight conversion between KG, GRAM, and POUND
3. Weight addition with:
- implicit result unit (first operand unit)
- explicit target unit support
4. Immutability preserved (operations return new objects)
5. Standalone WeightUnit enum owns conversion logic (like UC8)
6. Type safety: weight and length are incomparable categories
7. Validation for null unit, NaN, and infinite values
8. Backward compatible with UC1–UC8 (length remains intact)

---

## Example Operations
- Equality:
Quantity(1.0, KILOGRAM).equals(Quantity(1000.0, GRAM)) → true
Quantity(1.0, KILOGRAM).equals(Quantity(~2.20462, POUND)) → true (within epsilon)

- Conversion:
Quantity(1.0, KILOGRAM).convertTo(GRAM) → Quantity(1000.0, GRAM)
Quantity(2.0, POUND).convertTo(KILOGRAM) → Quantity(~0.907184, KILOGRAM)

- Addition (Implicit):
Quantity(1.0, KILOGRAM).add(Quantity(1000.0, GRAM)) → Quantity(2.0, KILOGRAM)
Quantity(500.0, GRAM).add(Quantity(0.5, KILOGRAM)) → Quantity(1000.0, GRAM)

- Addition (Explicit Target Unit):
Quantity(1.0, KILOGRAM).add(Quantity(1000.0, GRAM), GRAM) → Quantity(2000.0, GRAM)
Quantity(1.0, POUND).add(Quantity(453.592, GRAM), POUND) → Quantity(~2.0, POUND)

- Category Incompatibility:
Quantity(1.0, KILOGRAM).equals(Quantity(1.0, FEET)) → false (or exception as defined)

---

## Test Coverage
- Same-unit equality (KG, GRAM, POUND)
- Cross-unit equality (KG↔GRAM, KG↔POUND, GRAM↔POUND)
- Unit conversions between all pairs
- Round-trip conversion accuracy (within epsilon)
- Addition with same units
- Addition with mixed units
- Addition with explicit target unit
- Commutativity and transitive equality checks
- Zero, negative, large, and small value edge cases
- Null handling (null object/unit)
- Invalid value handling (NaN, ±Infinity)
- Weight vs Length incompatibility validation
- hashCode consistency with equals (if implemented)

---

## Key Concepts
1. Multiple measurement categories with type safety
2. Standalone unit enums with conversion responsibility (SRP)
3. Base-unit normalization pattern (KG as base for weight)
4. Immutability and value object operations
5. Enum encapsulation of conversion logic
6. Method overloading for add() variants
7. Floating-point precision handling (epsilon tolerance)
8. Architectural scalability for future categories (volume, temperature, etc.)

---

## Design Strength
- WeightUnit mirrors the UC8 pattern: unit owns conversion logic.
- QuantityWeight mirrors QuantityLength: quantity owns comparison + arithmetic.
- No changes required in length modules, proving scalable architecture.

---

🔗 Code Link
[WeightMeasurement](https://github.com/Anjali-Kumari27/QuantityMeasurementApp/tree/feature/UC9-WeightMeasurement)
