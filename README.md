# Quantity Measurement App  
## UC18 – Spring Security and JWT Authentication Integration  

---

Branch: feature/UC18-SpringSecurityJWTIntegration  

---

## Overview  

- UC18 enhances the Spring Boot REST application (UC17) by introducing **Spring Security with JWT (JSON Web Token)**.

- Until UC17, all REST APIs were **publicly accessible without authentication**, which is not suitable for real-world applications.

- UC18 secures the application by implementing:
  - Authentication (Login/Register)
  - Authorization (Role-based access)
  - Stateless session management using JWT  

- The system now generates a **JWT token after successful login**, which must be sent in every request header for accessing secured endpoints.

- Spring Security filters intercept incoming requests, validate the token, and allow access only if the token is valid.

- This use case transforms the application into a **secure, production-ready backend system**.

- All functionality from **UC1–UC17 remains intact and protected behind authentication**.

---

## Project Structure  

```
src
├── main
│ └── java/
│      └── com/
│           └── app/
│               └── quantitymeasurement
│                    ├── auth
│                    │ └── AuthRequest.java
│                    │ └── AuthResponse.java
│                    │ └── RegisterRequest.java
│                    │
│                    ├── config
│                    │ └── SecurityConfig.java
│                    │
│                    ├── controller
│                    │ └── AuthController.java
│                    │ └── QuantityMeasurementServiceImpl.java
│                    │
│                    ├── exception
│                    │ └── GlobalExceptionHandler.java
│                    │ └── QuantityMeasurementException.java
│                    │
│                    ├── filter
│                    │ └── JwtAuthenticationFilter.java
│                    │
│                    ├── model
│                    │ ├── QuantityDTO.java
│                    │ ├── QuantityMeasurementDTO.java
│                    │ ├── QuantityInputDTO.java
│                    │ ├── QuantityMeasurementEntity.java
│                    │ ├── User.java
│                    │ └── OperationType.java
│                    │
│                    ├── quantity
│                    │ └── QuantityModel.java
│                    │
│                    ├── repository
│                    │ ├── UserRepoistory.java
│                    │ └── QuantityMeasurementRepository.java
│                    │
│                    ├── service
│                    │ ├── AuthService.java
│                    │ ├── CustomerUserDetailsService.java
│                    │ ├── IQuantityMeasurementService.java
│                    │ ├── JwtService.java
│                    │ └── QuantityMeasurementServiceImpl.java
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

- Spring Security integration  
- JWT-based authentication (stateless)  
- Login and Register API implemented  
- Token generation after successful login  
- Token validation for every request  
- Authorization using roles (USER, ADMIN)  
- Secure REST endpoints using filters  
- Custom UserDetailsService implementation  
- Password encryption using BCrypt  
- Security configuration using filter chain  
- AuthenticationManager integration  
- Exception handling for unauthorized access  
- Backward compatibility maintained with UC1–UC17  

---

## Example Operations  

### Register User  

POST /auth/register  

Request  
{
  "email": "anjali@gmail.com",
  "password": "1234"
}

Response  
{
  "message": "User registered successfully"
}

---

### Login User  

POST /auth/login  

Request  
{
  "email": "anjali@gmail.com",
  "password": "1234"
}

Response  
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}

---

### Access Secured API  

GET /quantity/compare  

Header  
Authorization: Bearer <JWT_TOKEN>  

Response  
{
  "operation": "compare",
  "resultString": "true",
  "error": false
}

---

## Test Coverage  

- Authentication API testing (login/register)  
- JWT token generation validation  
- JWT token parsing and validation  
- Secured endpoint access testing  
- Unauthorized access testing (401/403)  
- Role-based authorization testing  
- Password encryption validation  
- Security filter testing  
- Service and repository integration testing  
- Backward compatibility validation with UC1–UC17  

---

## Key Concepts  

- Spring Security  
- JWT (JSON Web Token)  
- Authentication vs Authorization  
- Stateless session management  
- Security Filter Chain  
- UserDetails & UserDetailsService  
- Password encoding (BCrypt)  
- AuthenticationManager  
- HTTP Security configuration  
- Role-based access control (RBAC)  
- Token-based authentication  
- Secure REST API design  

---

## Design Strength  

- Stateless authentication using JWT improves scalability.  
- No server-side session storage required.  
- Secure endpoints prevent unauthorized access.  
- Clean separation of authentication and business logic.  
- Passwords stored securely using encryption.  
- Flexible role-based authorization implemented.  
- Easy integration with frontend (React, Angular, etc.).  
- Security layer fully modular and extendable.  
- Ready for production-grade deployment.  
- All previous functionality preserved securely.  

---

🔗 Code Link  
[SpringSecurityJWTGoogleOAuth2](https://github.com/Anjali-Kumari27/QuantityMeasurementApp/tree/feature/UC18-SpringSecurityJWTGoogleOAuth2/src/main/java/com/app/quantitymeasurement)
