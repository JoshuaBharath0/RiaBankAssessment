# Bank Account Management API

## Project Overview
This is a Spring Boot-based RESTful API designed to manage bank account information. It supports core banking operations like creating new accounts, searching by account numbers, and updating existing records. The project is structured using a clean, layered architecture to ensure maintainability and ease of testing.

## Core Features
* Account CRUD Operations: Complete support for creating, reading, and updating account data.
* Search Functionality: Dual search capability via unique Database ID or Account Number.
* Data Integrity: Strict validation rules using Jakarta Validation to prevent negative balances or missing holder information.
* Exception Handling: A centralized error handling system that provides clear feedback.

## Tech Stack
* Java 21
* Spring Boot 4.0.5
* Spring Security (Basic Auth & Form Login)
* Spring Data JPA
* Apache Maven 3.9.14
* H2 In-Memory Database (Chosen for instant setup and portability)
* Lombok
* JUnit 5 & Mockito (Testing framework)
* OpenAPI / Swagger UI (Documentation)

## How to Run
1. Ensure you have Java 21 and Maven 3.9.14 installed.
2. Build the project:
   mvn clean install
3. Run the application:
   mvn spring-boot:run
4. Access the API: Once running, navigate to the interactive documentation at: 
   http://localhost:8080/swagger-ui/index.html
   
   *** LOGIN CREDENTIALS ***
   Username: bank_admin
   Password: bank_admin_01

## API Documentation & Testing
### Manual vs. Automated Testing
* **Manual Testing:** For this assessment, I have intentionally **disabled CSRF protection**. This ensures that manual testing via Swagger UI or Postman is "plug-and-play" and does not return 403 Forbidden errors due to missing browser tokens.
* **Automated Testing:** Comprehensive JUnit 5 tests are included. To run them, navigate to the `src/test/java` folder in your IDE and use the "Play" buttons (Inline Test Runner) next to the class or method names.

## Security & Integrity
* Authentication/Authorization: Implemented Spring Security to ensure only authorized users with the ADMIN role can access banking operations.
* Data Validation: Implemented using Jakarta Bean Validation (@NotBlank, @Positive).
* Handling Sensitive Information: Utilized the DTO (Data Transfer Object) pattern to protect internal database entities.

## Scalability & Architecture
* Clean Architecture: Followed a strict Layered Pattern (Controller, Service, and Repository).
* Design Patterns: Implemented Dependency Injection and Data Mapper patterns.

## Maintainability
* Testing: Full test suite using JUnit 5 and Mockito, covering business logic and API responses using MockMvc.
* Code Quality: Adhered to SOLID principles and standard Java naming conventions.

## API Endpoints

Method | Path | Description | Required Role
-------|------|-------------|--------------
POST | /bank | Create a new bank account | ADMIN
GET | /bank/{id} | Retrieve an account by its Database ID | ADMIN
GET | /bank/search | Search via query parameter (?accountNumber=) | ADMIN
PUT | /bank/{id} | Update details for an existing account | ADMIN

---------------------------------------------------------------------------------
Note: This project was developed as a technical assessment to demonstrate 
proficiency in Spring Boot, API design, and automated testing.