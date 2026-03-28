# Quantity Measurement App  
## UC17 – Spring Boot REST API and JPA Integration for Quantity Measurement  

---

Branch: feature/UC17-SpringBootRESTJPAIntegration  
---

## Overview  

- UC17 transforms the Quantity Measurement Application into a **Spring Boot–based RESTful application**.

- Until UC16, the application used **JDBC for database interaction**, which required manual SQL handling and boilerplate code.

- UC17 replaces JDBC with **Spring Data JPA (ORM)** and exposes functionality through **REST APIs**.

- The application now follows a modern enterprise architecture using:
  - Spring Boot auto-configuration  
  - REST controllers  
  - Spring Data JPA repositories  
  - Dependency Injection  

- This use case eliminates boilerplate code and introduces **clean, scalable, and maintainable architecture**.

- UC17 also introduces:
  - API-based communication (JSON/XML)  
  - Validation for request data  
  - Global exception handling  
  - Embedded server (Tomcat)  

- The application uses **H2 in-memory database** for development and can be easily configured for MySQL/PostgreSQL.

- All functionality from **UC1–UC16 remains intact and fully compatible**.

---

## Project Structure  
```
src
├── main
│ └── java/
│      └── com/
│           └── app/
│               └── quantitymeasurement
│                    ├── controller
│                    │ └── QuantityMeasurementController.java
│                    │
│                    ├── service
│                    │ ├── IQuantityMeasurementService.java
│                    │ └── QuantityMeasurementServiceImpl.java
│                    │
│                    ├── repository
│                    │ └── QuantityMeasurementRepository.java
│                    │
│                    ├── exception
│                    │ └── GlobalExceptionHandler.java
│                    │ └── QuantityMeasurementException.java
│                    │
│                    ├── model
│                    │ ├── QuantityDTO.java
│                    │ ├── QuantityMeasurementDTO.java
│                    │ ├── QuantityInputDTO.java
│                    │ ├── QuantityMeasurementEntity.java
│                    │ └── OperationType.java
│                    │
│                    ├── config
│                    │ └── SecurityConfig.java
│                    │
│                    ├── quantity
│                    │ └── QuantityModel.java
│                    │
│                    ├── unit
│                    │ ├── IMeasurable.java
│                    │ ├── LengthUnit.java
│                    │ ├── TemperatureUnit.java
│                    │ ├── VolumeUnit.java
│                    │ └── WeightUnit.java
│                    │
│                    └── QuantityMeasurementAppApplication.java
│
│                    └── resources
│                    ├── application.properties
│                    ├── application-prod.properties
│                    └── db/schema.sql
│
└── test
│ └── java/
│      └── com/
│           └── app/
│               └── quantitymeasurement
│                    ├── controller
│                    ├── exception
│                    ├── integrationTests
│                    ├── model
│                    ├── repository
│                    ├── service
│                    ├── unit
│                    └── QuantityMeasurementAppApplicationTests.java
│
├── .gitignore
├── pom.xml
└── README.md
```


---

## Features  

- Spring Boot integration for simplified configuration  
- REST API endpoints for quantity operations  
- Spring Data JPA for ORM (no manual SQL)  
- Replacement of JDBC with repository abstraction  
- Embedded Tomcat server (no external server required)  
- H2 database for development and testing  
- DTO-based API communication  
- Validation using annotations (`@NotNull`, `@NotEmpty`, etc.)  
- Global exception handling using `@ControllerAdvice`  
- Dependency Injection using Spring container  
- Automatic JSON serialization/deserialization  
- Swagger/OpenAPI support for API documentation  
- Actuator support for monitoring and health checks  
- Backward compatibility maintained with UC1–UC16  

---

## Example Operations  

REST API Request  

POST /quantity/compare  

Request Body  

{
  "value": 12,
  "unit": "INCHES",
  "measurementType": "LengthUnit"
}

Response  

{
  "operation": "compare",
  "resultString": "true",
  "error": false
}

---

Convert Quantity  

POST /quantity/convert  

Quantity(12.0, INCHES) → Quantity(1.0, FEET)  

---

Arithmetic Operation  

POST /quantity/add  

Quantity(1.0, FEET) + Quantity(12.0, INCHES) → Quantity(2.0, FEET)  

---

## Test Coverage  

- REST controller endpoint testing (MockMvc)  
- Service layer integration testing  
- Repository layer testing using JPA  
- Entity mapping validation  
- DTO validation testing  
- API request/response validation  
- Exception handling validation  
- Validation annotation testing  
- Database interaction testing (H2)  
- End-to-end API testing  
- Backward compatibility validation with UC1–UC16  

---

## Key Concepts  

- Spring Boot  
- RESTful Web Services  
- Spring MVC architecture  
- Spring Data JPA (ORM)  
- Dependency Injection (DI)  
- Hibernate (JPA implementation)  
- DTO pattern  
- Validation annotations  
- Global exception handling  
- Embedded server (Tomcat)  
- API documentation (Swagger/OpenAPI)  
- Application monitoring (Actuator)  

---

## Design Strength  

- Eliminates JDBC boilerplate code using JPA.  
- Clean separation between controller, service, and repository layers.  
- REST APIs enable external system integration.  
- Dependency Injection improves modularity and testability.  
- DTO pattern ensures secure and structured data transfer.  
- Validation ensures robust and error-free input handling.  
- Global exception handling provides consistent error responses.  
- Embedded server simplifies deployment.  
- Easily scalable to microservices architecture.  
- Ready for security integration (JWT in UC18).  
- Maintains all previous functionality without breaking changes.  

---

🔗 Code Link  
[SpringRESTJPAIntegration](https://github.com/Anjali-Kumari27/QuantityMeasurementApp/tree/feature/UC17-SpringBootRESTJPAIntegration)
