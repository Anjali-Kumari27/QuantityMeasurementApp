# Quantity Measurement App  
## UC16 – Database Integration with JDBC for Quantity Measurement Persistence  

---

Branch: feature/UC16-DatabaseIntegrationWithJDBC  
Date: 12 March 2026  

---

## Overview  

- UC16 introduces **database persistence** to the Quantity Measurement Application.

- Until UC15, all quantity operations (conversion, equality, arithmetic) were executed **only in memory**.

- This use case integrates **JDBC (Java Database Connectivity)** to persist quantity data in a relational database.

- The application now supports storing and retrieving quantity measurements through a **dedicated persistence layer** following layered architecture principles.

- UC16 introduces repository classes, database configuration, and connection pooling while keeping business logic separate from persistence logic.

- This enhancement maintains backward compatibility with **UC1–UC15** and prepares the system for scalable data management.

---

## Project Structure  
```
src
├── main
│ └── java/
│      └── com/
│       └── app/
│         └── quantitymeasurement
│          ├── controller
│          │ └── QuantityMeasurementController.java
│          │
│          ├── service
│          │ ├── IQuantityMeasurementService.java
│          │ └── QuantityMeasurementServiceImpl.java
│          │
│          ├── repository
│          │ ├── IQuantityRepository.java
│          │ └── QuantityMeasurementDatabaseRepository.java
│          │
│          ├── entity
│          │ ├── QuantityEntity.java
│          │ ├── QuantityModel.java
│          │ └── QuantityDTO.java
│          │
│          └── QuantityMeasurementApp.java
│
└── test
│    └── java/
│         └── com/
│           └── app/
│            └── quantitymeasurement/
│
├── .gitignore
└── pom.xml

```

## Features  

- JDBC-based database integration  
- Quantity measurements persisted in relational database  
- Repository layer introduced for data access  
- Separation of concerns between controller, service, and repository  
- Connection pooling configuration for efficient database access  
- Parameterized SQL queries for secure database operations  
- Database schema design for quantity storage  
- DTO and entity classes introduced for persistence mapping  
- Centralized configuration management for database connection  
- Test support using mock or in-memory databases  
- Backward compatibility maintained with UC1–UC15  

---

## Example Operations  

Save Quantity Measurement  

Quantity(12.0, INCHES) → Stored in database  

Retrieve Quantity Measurement  

Database Record → Quantity(12.0, INCHES)  

Convert Quantity After Retrieval  

Quantity(12.0, INCHES).convertTo(FEET) → Quantity(1.0, FEET)  

Arithmetic Using Persisted Data  

Quantity(1.0, FEET).add(Quantity(12.0, INCHES)) → Quantity(2.0, FEET)  

---

## Test Coverage  

- Database connection validation  
- Repository save operation  
- Repository retrieval operation  
- Entity to DTO mapping validation  
- DTO to entity conversion validation  
- SQL query execution validation  
- Connection pooling configuration validation  
- Service layer integration testing  
- Persistence layer error handling validation  
- Database transaction consistency tests  
- Backward compatibility validation with UC1–UC15  

---

## Key Concepts  

- JDBC (Java Database Connectivity)  
- Repository pattern  
- Layered architecture  
- Data persistence design  
- Connection pooling  
- Parameterized SQL queries  
- DTO and entity separation  
- Transaction management  
- Configuration management  
- Resource management and exception hierarchy  

---

## Design Strength  

- Persistence layer isolated from business logic.  
- Repository pattern simplifies database operations.  
- Controller, service, and repository layers clearly separated.  
- Database configuration centralized for maintainability.  
- Connection pooling improves performance and scalability.  
- Parameterized queries ensure security and prevent SQL injection.  
- DTO and entity separation improves data abstraction.  
- Architecture ready for future integration with ORM frameworks.  
- All previous system functionality preserved without modification.  

---
