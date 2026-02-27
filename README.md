# QuantityMeasurementApp
- A progressively evolved Java application built using Test-Driven Development (TDD) to design a scalable, multi-category quantity measurement system.

- The project begins with simple equality checks and incrementally evolves into a generic, extensible, capability-aware architecture supporting multiple measurement categories with selective arithmetic behavior.

## The focus is on:
- Clean object-oriented design
- Incremental refactoring
- SOLID principles
- DRY enforcement
- Backward compatibility preservation
- Architectural scalability


## Overview
A modular Java project modelling quantity measurements across multiple categories:

- Length
- Weight
- Volume
- Temperature

The system evolves across 14 structured Use Cases (UC1–UC14), progressively transforming from basic unit comparison into a generic, centralized, and capability-aware measurement framework.

The architecture ensures:
- Cross-unit conversion
- Category isolation
- Arithmetic support with validation
- Selective operation enforcement
- Immutability and type safety


## Implemented Use Cases


🧩 UC1 – Feet Equality  
Implements value-based equality for feet measurements.  
Establishes the foundational equality contract using overridden equals().


🧩 UC2 – Inches Equality  
Extends equality comparison to inches.  
Reinforces unit-specific validation and comparison semantics.


🧩 UC3 – Generic Length  
Introduces LengthUnit enum.  
Unifies different length types under a common abstraction.  
Enables cross-unit equality using conversion factors.


🧩 UC4 – Extended Length Units  
Adds YARDS and CENTIMETERS.  
Demonstrates scalability of enum-based unit modeling.


🧩 UC5 – Explicit Unit Conversion  
Introduces convertTo() functionality.  
Supports conversion between supported length units.  
Ensures mathematical equivalence.


🧩 UC6 – Length Addition  
Introduces addition between length quantities.  
Automatically normalizes to base unit.  
Returns result in first operand’s unit.


🧩 UC7 – Addition with Target Unit  
Allows explicit specification of result unit.  
Improves API flexibility and user control.


🧩 UC8 – Standalone Unit Refactor  
Extracts LengthUnit into standalone enum.  
Delegates conversion logic entirely to unit enum.  
Improves cohesion and reduces coupling.  
Eliminates circular dependency risk.


🧩 UC9 – Weight Measurement Support  
Introduces WeightUnit (Kilogram, Gram, Pound).  
Supports equality, conversion, addition.  
Maintains strict category isolation from length.


🧩 UC10 – Generic Quantity Architecture  
Introduces Quantity<U extends IMeasurable>.  
Eliminates category-specific duplication.  
Unifies equality, conversion, and arithmetic logic.  
Enables true multi-category scalability.


🧩 UC11 – Volume Measurement Support  
Adds VolumeUnit (Litre, Millilitre, Gallon).  
Validates integration of new categories without modifying core logic.  
Confirms architecture extensibility.


🧩 UC12 – Subtraction and Division  
Introduces subtraction between quantities.  
Introduces division producing dimensionless ratio.  
Supports cross-unit normalization.  
Maintains immutability and validation consistency.


🧩 UC13 – Centralized Arithmetic Logic (DRY Refactor)  
Refactors add(), subtract(), divide() to use centralized helper logic.  
Eliminates duplicated validation and conversion code.  
Introduces ArithmeticOperation enum dispatch.  
Improves maintainability and scalability.  
Preserves full backward compatibility.


🧩 UC14 – Temperature Measurement (Selective Arithmetic Support)  
Introduces TemperatureUnit (Celsius, Fahrenheit, Kelvin).  
Implements non-linear conversion formulas.  
Refactors IMeasurable with default capability validation methods.  
Allows equality and conversion for temperature.  
Blocks unsupported arithmetic operations (add, subtract, divide).  
Demonstrates capability-based design and Interface Segregation.  
Maintains compatibility with all previous use cases.


## Core Capabilities

- Multi-category measurement system  
- Cross-unit conversion  
- Equality comparison across units  
- Addition, subtraction, division (where supported)  
- Dimensionless ratio computation  
- Centralized arithmetic logic (DRY enforced)  
- Selective operation support (Temperature constraints)  
- Generic type safety  
- Immutability  
- Backward compatibility across all use cases  


## Architectural Highlights

- Standalone unit enums encapsulating conversion logic
- Generic Quantity<U> abstraction
- Centralized arithmetic helper (UC13)
- Enum-based operation dispatch
- Capability-based arithmetic validation (UC14)
- Interface evolution using default methods
- Strict cross-category isolation
- Epsilon-based floating-point precision handling


## Tech Stack

Java 17+  
Maven  
JUnit 5  
Test-Driven Development (TDD)  


## Build & Run

Build project:
mvn clean install

Run tests:
mvn test


## Project Structure
```
📦 QuantityMeasurementApp
│
├── 📁 src
│   ├── 📁 main
│   │   └── 📁 java
│   │       └── 📁 com
│   │           └── 📁 apps
│   │               └── 📁 quantitymeasurement
│   │                   ├── IMeasurable.java
│   │                   ├── Quantity.java
│   │                   ├── LengthUnit.java
│   │                   ├── WeightUnit.java
│   │                   ├── VolumeUnit.java
│   │                   ├── TemperatureUnit.java
│   │                   ├── SupportsArithmetic.java
│   │                   └── QuantityMeasurementApp.java
│   │
│   └── 📁 test
│       └── 📁 java
│           └── 📁 com
│               └── 📁 apps
│                   └── 📁 quantitymeasurement
│                       ├── QuantityEqualityTest.java
│                       ├── QuantityConversionTest.java
│                       ├── QuantityArithmeticTest.java
│                       ├── CentralizedArithmeticLogicTest.java
│                       ├── TemperatureQuantityTest.java
│                       ├── BackwardCompatibilityTest.java
│                       └── ArchitecturalTest.java
│
├── pom.xml
├── .gitignore
└── README.md
```

## Development Approach

The project follows strict incremental TDD:

1. Write failing test.
2. Implement minimal logic to pass test.
3. Refactor while preserving behavior.
4. Validate backward compatibility.
5. Repeat for next use case.

Each use case introduces:
- Controlled functionality expansion
- Architectural improvement
- Increased abstraction
- Zero regression tolerance


## Design Goals Achieved

- High Cohesion  
- Low Coupling  
- Full DRY compliance (post UC13)  
- Capability-aware architecture (post UC14)  
- Scalable multi-category support  
- Non-linear conversion support  
- Operation-specific validation  
- Generic and extensible design  
- Strict backward compatibility  


## Future Extensions

The architecture now supports:
- Multiplication or Modulo (via ArithmeticOperation enum extension)
- Temperature difference modeling
- New measurement categories with selective arithmetic rules
- Builder pattern integration
- Capability-based compile-time constraints

---

Designed and implemented using incremental TDD with focus on clean architecture,
refactoring discipline, and scalable object-oriented design.
