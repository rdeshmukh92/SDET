# Spring Boot API Test Automation Framework

This repository is a portfolio project for SDET / QA Automation roles. It demonstrates automated testing for a Spring Boot REST API using REST Assured, JUnit 5, database validation, service-layer unit tests, JSON schema validation, and CI/CD execution with GitHub Actions.

## Project Overview

The sample application is an Employee Management REST API with CRUD operations. The test automation suite validates API behavior, backend data integrity, negative scenarios, and service-layer business logic.

## Tech Stack

- Java 17
- Spring Boot
- JUnit 5
- REST Assured
- Maven
- H2 In-Memory Database
- Spring Data JPA
- Mockito
- JSON Schema Validator
- GitHub Actions

## What This Project Demonstrates

- REST API automation testing
- Positive and negative API validation
- JSON request and response validation
- JSON schema validation
- Database validation using repository checks
- Service-layer unit testing with Mockito
- Spring Boot integration testing
- Test data setup and cleanup
- CI/CD test execution using GitHub Actions
- Test reports using Maven Surefire

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/employees` | Get all employees |
| GET | `/api/employees/{id}` | Get employee by ID |
| POST | `/api/employees` | Create employee |
| PUT | `/api/employees/{id}` | Update employee |
| DELETE | `/api/employees/{id}` | Delete employee |

## Sample Test Scenarios

- Create an employee successfully
- Validate duplicate email error handling
- Validate required field errors
- Get employee by ID
- Return 404 for missing employee
- Validate employee data persisted in database
- Unit test service-layer business logic

## Project Structure

```text
src/
  main/
    java/com/example/sdetapi/
      controller/      # REST controllers
      service/         # Business logic
      repository/      # JPA repositories
      model/           # Entity classes
      exception/       # Custom exceptions and handler
  test/
    java/com/example/sdetapi/
      api/             # REST Assured API tests
      integration/     # Database/integration tests
      service/         # Unit tests using Mockito
    resources/
      schemas/         # JSON schema files
.github/
  workflows/           # GitHub Actions pipeline
```

## How to Run the Application

```bash
mvn spring-boot:run
```

The API will start at:

```text
http://localhost:8080/api/employees
```

## How to Run Tests

```bash
mvn clean test
```

## Example API Request

```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Ritika Deshmukh",
    "email": "ritika@example.com",
    "department": "QA Automation",
    "salary": 95000
  }'
```

## CI/CD

GitHub Actions runs the automated test suite on every push and pull request to the `main` branch.

Workflow file:

```text
.github/workflows/maven-tests.yml
```

## Why I Built This

I built this project to demonstrate practical SDET skills in API automation, backend validation, Spring Boot integration testing, unit testing, CI/CD execution, and maintainable test framework design.

## About Me

I am a Senior QA Automation Engineer / SDET with experience in Selenium, Playwright, REST Assured, Java, Python, SQL, Jenkins, Azure DevOps, and Agile testing practices.

LinkedIn: Add your LinkedIn URL here
