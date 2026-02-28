# Quantity Measurement App  
## UC14 – Temperature Measurement with Selective Arithmetic Support  

---

**Branch:** feature/UC14-TemperatureMeasurement 
**Date:** 23 February 2026  

---

## Overview  
- This use case extends the system to support Temperature measurements alongside Length, Weight, and Volume.

- Unlike other measurement categories, Temperature has non-linear conversion rules and does not support all arithmetic operations in a meaningful way.

- UC14 introduces selective arithmetic capability by refactoring the IMeasurable interface using default methods. TemperatureUnit supports equality comparison and conversion but restricts unsupported arithmetic operations.

- This enhancement preserves backward compatibility with UC1–UC13 while making the architecture capable of handling categories with unique operational constraints.

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
 │                         └── TemperatureUnit.java
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
 │                         └── SubtractionDivisionnTest.java
 │                         └── TargetAdditionTest.java
 │                         └── TemperatureUnitTest.java
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
- TemperatureUnit enum introduced  
- Supports CELSIUS, FAHRENHEIT (optional KELVIN)  
- Non-linear conversion formulas implemented  
- I̲M̲e̲a̲s̲u̲r̲a̲b̲l̲e̲ refactored with default operation validation methods  
- Selective arithmetic capability enforcement  
- Temperature supports equality and conversion  
- Temperature blocks addition, subtraction, division  
- Unsupported operations throw UnsupportedOperationException  
- Cross-category comparison remains prohibited  
- Backward compatibility maintained with UC1–UC13  
- Architecture supports future constrained categories  

---

## Example Operations  
- Quantity(0.0, CELSIUS).equals(Quantity(32.0, FAHRENHEIT)) → true  
- Quantity(100.0, CELSIUS).equals(Quantity(212.0, FAHRENHEIT)) → true  
- Quantity(-40.0, CELSIUS).equals(Quantity(-40.0, FAHRENHEIT)) → true  
- Quantity(100.0, CELSIUS).convertTo(FAHRENHEIT) → Quantity(212.0, FAHRENHEIT)  
- Quantity(32.0, FAHRENHEIT).convertTo(CELSIUS) → Quantity(0.0, CELSIUS)  
- Quantity(0.0, CELSIUS).convertTo(KELVIN) → Quantity(273.15, KELVIN)  

Unsupported Operations  
- Quantity(100.0, CELSIUS).add(Quantity(50.0, CELSIUS)) → UnsupportedOperationException  
- Quantity(100.0, CELSIUS).subtract(Quantity(50.0, CELSIUS)) → UnsupportedOperationException  
- Quantity(100.0, CELSIUS).divide(Quantity(50.0, CELSIUS)) → UnsupportedOperationException  

---

## Test Coverage  
- Celsius-to-Celsius equality  
- Fahrenheit-to-Fahrenheit equality  
- Celsius-to-Fahrenheit cross equality  
- Celsius-to-Kelvin equality  
- Symmetric and transitive equality validation  
- Temperature conversion accuracy (C ↔ F, C ↔ K)  
- Round-trip conversion precision  
- Unsupported addition validation  
- Unsupported subtraction validation  
- Unsupported division validation  
- Clear error message verification  
- Cross-category comparison prevention  
- Backward compatibility validation (UC1–UC13 regression tests)  
- Default method inheritance validation for other units  
- Epsilon-based precision testing  

---

## Key Concepts  
- Selective arithmetic capability  
- Default methods in interfaces  
- Interface Segregation Principle (ISP) improvement  
- Non-linear conversion handling  
- Capability-based design  
- Exception semantics (UnsupportedOperationException)  
- Backward-compatible interface evolution  
- Generic type safety  
- Scalable architecture for constrained categories  
- Polymorphic behavior via enum  

---

## Design Strength  
- TemperatureUnit encapsulates non-linear conversion formulas.  
- IMeasurable evolves without breaking existing unit implementations.  
- Quantity validates operation support before executing arithmetic.  
- Length, Weight, and Volume remain unchanged.  
- Selective arithmetic constraints enforced cleanly.  
- Architecture remains DRY and centralized (UC13 preserved).  
- Future categories with unique constraints can integrate seamlessly.  
- All previous functionality preserved without modification.  

---

🔗 *Code Link* 
 [TemperatureMeasurement](https://github.com/Anjali-Kumari27/QuantityMeasurementApp/tree/feature/UC14-TemperatureMeasurement)
