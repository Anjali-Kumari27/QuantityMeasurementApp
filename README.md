# Quantity Measurement App  
## UC13 – Centralized Arithmetic Logic (DRY Enforcement)  

---

**Branch:** feature/UC13-CentralizedArithmeticLogic 
**Date:** 23 February 2026  

---

## Overview  
- UC13 refactors the internal implementation of arithmetic operations (add, subtract, divide) to eliminate code duplication and fully enforce the DRY principle.

- Public method signatures remain unchanged.  
- All UC12 functionality and behavior remain identical.  
- All existing tests pass without modification.  

---

## What Changed in UC13 (Conceptually)  

1. In UC12, every arithmetic method contained repeated logic:
- Null checks
- Category compatibility checks
- Finiteness validation
- Base-unit conversion
- Target unit validation
- Arithmetic execution

- UC13 centralizes all common logic into:
1. A private validation helper
2. A private base-arithmetic helper
3. An internal ArithmeticOperation enum for dispatching ADD, SUBTRACT, DIVIDE

 Now:
- Validation exists in one place
- Conversion to base unit exists in one place
- Arithmetic dispatch exists in one place
- Public methods are short and readable

---

## Architecture After Refactor  
```
Quantity<U>
 ├── validateArithmeticOperands()      (central validation)
 ├── performBaseArithmetic()           (central conversion + operation)
 ├── ArithmeticOperation enum          (ADD / SUBTRACT / DIVIDE)
 ├── add()
 ├── subtract()
 └── divide()
```

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
 │                         └── UnitAdditionTest.java
 │                         └── UnitConversionTest.java
 │                         └── VolumeTest.java
 │                         └── YardEqualityTest.java
 │
 ├── .gitignore
 └── pom.xml
```

---


Public API remains:
- add(other)
- add(other, targetUnit)
- subtract(other)
- subtract(other, targetUnit)
- divide(other)

---

## Internal Flow Example  

- Calling:  
q1.subtract(q2, FEET)

- Flow:
1. validateArithmeticOperands()
2. performBaseArithmetic(..., SUBTRACT)
3. Convert result from base to FEET
4. Return new Quantity

- Division works similarly but returns a dimensionless double.

- Behavior (Unchanged from UC12)  

- Addition:
1 FEET + 12 INCHES → 2 FEET  

- Subtraction:
10 FEET - 6 INCHES → 9.5 FEET  

- Division:
24 INCHES ÷ 2 FEET → 1.0  

Error Cases (Now Fully Centralized):
- Null operand → IllegalArgumentException
- Cross-category operation → IllegalArgumentException
- Division by zero → ArithmeticException
- Non-finite values → IllegalArgumentException

---

## Design Improvements in UC13  

1. DRY Principle Enforced  
2. Validation and conversion logic written once.  
3. Future operations reuse same infrastructure.

4. Single Source of Truth  
5. Any change in validation rules affects all arithmetic operations uniformly.

6. Cleaner Public Methods  
7. Each public method now expresses intent clearly:
- validate
- perform operation
- convert result

8. Enum-Based Operation Dispatch  
9. Operations are controlled via type-safe enum constants.  
10. No if-else chains or switch blocks.

11. Scalability  
To add multiplication:
- Add MULTIPLY in ArithmeticOperation enum
- Add public multiply() method
No validation duplication required.

12. Consistency  
All arithmetic operations:
- Use identical validation strategy
- Use identical conversion strategy
- Throw identical exceptions for identical errors

13. Backward Compatibility  

All UC12 tests pass unchanged.  
Public behavior is identical.  
No API signatures changed.  

14. Maintenance Benefits  

- Reduced bug risk  
- Reduced future duplication  
- Easier onboarding for new developers  
- Shorter and more readable arithmetic methods  
- Centralized error handling  

15. Scalability Validated  

16. Works for:
- Length
- Weight
- Volume
- Any future measurement category

No structural modification required for future arithmetic operations.

---

## Final Result  

- UC13 does not add new functionality.  
- It improves internal architecture.

- Cleaner  
- Safer  
- More maintainable  
- Fully DRY-compliant  
- Backward compatible  
- Future-ready  
