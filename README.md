# Quantity Measurement App  
## UC15 – N-Tier Architecture Refactoring for Quantity Measurement Application  

---

Branch: feature/UC15-NTierArchitectureRefactoring  
Date: 11 March 2026  

---

## Overview  

- UC15 refactors the Quantity Measurement Application into a **clean N-Tier (Layered) Architecture**.

- Earlier use cases contained most of the logic within a single structure. UC15 restructures the system into **separate layers for controller, service, model, and data transfer objects**.

- This architectural refactoring improves **separation of concerns, maintainability, scalability, and testability**.

- The application now follows a standard enterprise structure where:
  - Controller handles client interaction
  - Service handles business logic
  - Model represents the domain objects
  - DTO acts as a data transfer layer between components

- UC15 prepares the system for **database integration and persistence**, which will be implemented in UC16.

- All functionality from **UC1–UC14 remains unchanged** and continues to work within the new layered structure.

---

## Project Structure  
  
```
src  
 ├── main  
 │    └── java/
 │         └── com/
 │              └── apps/
 │                   └── quantitymeasurement
 │                         └── controller/
 │                         └── dto/
 │                         └── exception/
 │                         └── interfaces/
 │                         └── model/
 │                         └── repository/
 │                         └── service/
 │                         └── units/
 │                         └── QuantityMeasurementApp.jav
 │  
 └── test  
 │    └── java/
 │         └── com/
 │              └── apps/
 │                   └── quantitymeasurement
 │                         └── controller/
 │                         └── integration/
 │                         └── model/
 │                         └── repository/
 │                         └── service/
 │                         └── units/
 │
 ├── .gitignore
 └── pom.xml

```

---

## Features  

- Application refactored into **N-Tier architecture**  
- Introduction of **controller layer** for client interaction  
- Introduction of **service layer** for business logic  
- DTO introduced for **safe data transfer between layers**  
- Domain models separated from service logic  
- Improved separation of concerns  
- Improved maintainability and scalability  
- Preparation for database persistence in UC16  
- Existing functionality preserved without modification  
- Clean architecture ready for enterprise-grade development  

---

## Example Operations  

Controller receives request  

QuantityMeasurementController → calls service layer  

Service performs business logic  

QuantityMeasurementService → processes conversion / equality / arithmetic  

Service returns result  

Controller returns final response to client  

Examples  

Quantity(1.0, FEET).add(Quantity(12.0, INCHES)) → Quantity(2.0, FEET)  

Quantity(10.0, KILOGRAM).subtract(Quantity(5000.0, GRAM)) → Quantity(5.0, KILOGRAM)  

Quantity(24.0, INCHES).divide(Quantity(2.0, FEET)) → 1.0  

---

## Test Coverage  

- Controller layer interaction testing  
- Service layer business logic validation  
- DTO mapping validation  
- Cross-unit arithmetic validation  
- Conversion accuracy validation  
- Equality comparison validation  
- Error handling validation  
- Layer interaction testing  
- Backward compatibility validation (UC1–UC14 regression tests)  

---

## Key Concepts  

- N-Tier Architecture  
- Layered application design  
- Separation of concerns  
- DTO (Data Transfer Object) pattern  
- Controller-Service interaction  
- Clean architecture principles  
- Scalable enterprise design  
- Maintainable code structure  
- Dependency management between layers  
- Preparation for persistence layer  

---

## Design Strength  

- Controller layer handles client interaction.  
- Service layer encapsulates business logic.  
- Model layer represents measurement domain objects.  
- DTO ensures safe data transfer across layers.  
- Business logic isolated from UI and persistence concerns.  
- Architecture becomes easier to test and maintain.  
- Clean structure prepares the application for database integration.  
- All previous measurement features remain unchanged.  
- System now follows enterprise-level architectural practices.  

---
