# Quantity Measurement App  
## UC1 – Feet Measurement Equality

**Branch:** feature/UC1-FeetEquality  
**Date:** 17 February 2026  

---

## Overview
This use case implements equality comparison between two measurements given in **feet**.

The system validates the input and checks whether both values are equal using proper object-oriented principles and safe floating-point comparison.

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
 │                         └── QuantityMeasurementApp.java
 │  
 └── test  
 │    └── java/
 │         └── com/
 │              └── apps/
 │                   └── quantitymeasurement
 │                         └── FeetEqualityTest.java
 │
 ├── .gitignore
 └── pom.xml

```
---


## Features
- Accepts two numerical values in feet  
- Compares them for equality  
- Returns `true` if equal, otherwise `false`  
- Handles null and invalid comparisons safely  

---

## Test Coverage
- Same value comparison  
- Different value comparison  
- Null comparison  
- Same reference comparison  
- Non-numeric input handling  

---

## Key Concepts
- equals() method implementation  
- Safe floating-point comparison  
- Type safety  
- Encapsulation  
- Immutability

---
## 🔗 Code Link
[FeetEquality](https://github.com/Anjali-Kumari27/QuantityMeasurementApp/tree/feature/UC1-FeetEquality)
