# Quantity Measurement App
## UC11 – Volume Measurement (Equality, Conversion, Addition)

---

**Branch:** feature/UC11-VolumeMeasurement
**Date:** 23 February 2026

---

## Overview
1. UC11 adds a third measurement category: VOLUME.

2. Using the generic Quantity<U extends IMeasurable> design from UC10, volume integrates without changing:
- Quantity<U>
- IMeasurable
- QuantityMeasurementApp (generic demo methods)
- Existing length + weight tests

3. Supported volume units:
- LITRE (base unit)
- MILLILITRE (1 L = 1000 mL)
- GALLON (1 gal ≈ 3.78541 L)

---

## Project Structure
```
src  
 ├── main  
 │    └── java/
 │         └── com/
 │              └── apps/
 │                   └── quantitymeasurement
 │                        ├── IMeasurable.java
 │                        ├── Quantity.java
 │                        ├── LengthUnit.java
 │                        ├── WeightUnit.java
 │                        └── VolumeUnit.java
 │  
 └── test  
 │    └── java/com/apps/quantitymeasurement  
 │
 ├── .gitignore
 └── pom.xml
```

---

## Features
1. Volume equality across units using base-unit normalization (litre)
2. Volume conversion between LITRE, MILLILITRE, and GALLON
3. Volume addition with:
- implicit result unit (first operand unit)
- explicit target unit support
4. Immutability preserved (operations return new Quantity instances)
5. Cross-category comparisons prevented (volume vs length/weight)
6. No changes required to generic Quantity<U> implementation
7. Backward compatible with UC1–UC10

---

## Example Operations
- Equality:
new Quantity<>(1.0, VolumeUnit.LITRE)
    .equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)) → true

new Quantity<>(3.78541, VolumeUnit.LITRE)
    .equals(new Quantity<>(1.0, VolumeUnit.GALLON)) → true (within epsilon)

- Conversion:
new Quantity<>(1.0, VolumeUnit.LITRE)
    .convertTo(VolumeUnit.MILLILITRE) → Quantity(1000.0, MILLILITRE)

new Quantity<>(2.0, VolumeUnit.GALLON)
    .convertTo(VolumeUnit.LITRE) → Quantity(~7.57082, LITRE)

- Addition (Implicit):
new Quantity<>(1.0, LITRE)
    .add(new Quantity<>(1000.0, MILLILITRE)) → Quantity(2.0, LITRE)

new Quantity<>(500.0, MILLILITRE)
    .add(new Quantity<>(0.5, LITRE)) → Quantity(1000.0, MILLILITRE)

- Addition (Explicit Target Unit):
new Quantity<>(1.0, LITRE)
    .add(new Quantity<>(1000.0, MILLILITRE), MILLILITRE) → Quantity(2000.0, MILLILITRE)

new Quantity<>(1.0, GALLON)
    .add(new Quantity<>(3.78541, LITRE), GALLON) → Quantity(~2.0, GALLON)

- Category Incompatibility:
new Quantity<>(1.0, VolumeUnit.LITRE)
    .equals(new Quantity<>(1.0, LengthUnit.FEET)) → false

new Quantity<>(1.0, VolumeUnit.LITRE)
    .equals(new Quantity<>(1.0, WeightUnit.KILOGRAM)) → false

---

## Test Coverage
- Same-unit equality (LITRE, MILLILITRE, GALLON)
- Cross-unit equality (L↔mL, L↔gal, mL↔gal)
- Conversions between all unit pairs
- Round-trip conversion accuracy (within epsilon)
- Addition with same units
- Addition with mixed units
- Addition with explicit target unit
- Commutativity and transitive equality checks
- Zero, negative, large, and small value edge cases
- Null handling (null object/unit)
- Invalid value handling (NaN, ±Infinity)
- Volume vs Length and Volume vs Weight incompatibility
- Backward compatibility: all UC1–UC10 tests still pass

---

## Key Concepts
- Scalability of generic design (UC10 validated again)
- Base unit normalization (litre as base unit)
- Enum implementing IMeasurable with conversion responsibility
- DRY principle maintained across 3 categories
- Type safety through generics + runtime category checks
- Immutability and functional-style operations
- Floating-point precision tolerance (epsilon)

---

## Design Strength
1. UC11 proves that adding a new measurement category requires only:
- a new enum implementing IMeasurable (VolumeUnit)

2. No extra Quantity classes, no duplicated logic, no new demo method explosion.

---

🔗 Code Link
[VolumeMeasurement](https://github.com/Anjali-Kumari27/QuantityMeasurementApp/tree/feature/UC11-VolumeMeasurement)
