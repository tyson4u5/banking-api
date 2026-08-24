# Banking API

A backend banking REST API built with Java and Spring Boot.

The application demonstrates a layered backend architecture with persistent PostgreSQL storage, account management, banking transactions, exception handling, automated testing, and interactive API documentation.

## Features

- Create bank accounts
- Retrieve bank accounts
- Deposit funds
- Withdraw funds
- Transfer money between accounts
- Prevent withdrawals with insufficient funds
- Store account information in PostgreSQL
- Record banking transactions
- Global API exception handling
- Automated service-layer testing
- Interactive Swagger/OpenAPI documentation

## Tech Stack

- Java
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- JUnit 5
- Mockito
- Swagger / OpenAPI
- Git
- GitHub

## Project Architecture

The application follows a layered backend architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL Database
```

### Controller Layer

Receives HTTP requests and exposes the REST API endpoints.

### Service Layer

Contains the application's banking business logic, including deposits, withdrawals, transfers, and balance validation.

### Repository Layer

Uses Spring Data JPA to communicate with PostgreSQL.

### Model Layer

Contains the application's persistent entities, including bank accounts and transactions.

### Exception Handling

Centralized exception handling provides structured HTTP error responses when operations fail.

## API Endpoints

The API provides operations for:

- Account creation
- Account retrieval
- Deposits
- Withdrawals
- Transfers
- Transaction management/history

Interactive endpoint documentation is available through Swagger when the application is running.

## Database

The application uses PostgreSQL.

Example database configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/banking_db
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
```

The PostgreSQL password is intentionally stored outside the source code using an environment variable.

### PowerShell

Before starting the application:

```powershell
$env:DB_PASSWORD="your-postgres-password"
```

Do not commit database passwords or other secrets to GitHub.

## Running the Application

Clone the repository and enter the project directory.

Set the PostgreSQL password environment variable:

```powershell
$env:DB_PASSWORD="your-postgres-password"
```

Start the application:

```powershell
.\mvnw.cmd spring-boot:run
```

The API runs locally at:

```text
http://localhost:8080
```

## Swagger / OpenAPI

With the application running, interactive API documentation is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

Swagger can be used to inspect and test the API endpoints directly from a browser.

## Automated Tests

The project includes automated service-layer tests using JUnit 5 and Mockito.

The tests verify important banking behavior including:

- Deposits increase account balances
- Withdrawals decrease account balances
- Insufficient-funds withdrawals are rejected
- Transfers move funds between accounts correctly

Run the test suite with:

```powershell
.\mvnw.cmd test
```

A successful run should finish with:

```text
BUILD SUCCESS
```

## Security Practices

Sensitive database credentials are not hard-coded into the application's committed configuration.

The application retrieves the PostgreSQL password through the `DB_PASSWORD` environment variable.

Additional production security such as authentication and authorization would be implemented before deploying an application that handles real financial information.

## What This Project Demonstrates

This project demonstrates practical backend software-development skills including:

- Object-oriented Java development
- REST API design
- Spring Boot development
- Business logic implementation
- Relational database integration
- ORM with Hibernate/JPA
- Dependency injection
- Layered application architecture
- Exception handling
- Unit testing and mocking
- API documentation
- Environment-variable secret management
- Maven dependency management
- Git version control

## Future Improvements

Potential future enhancements include:

- Spring Security
- JWT authentication
- User registration and login
- Account ownership
- Role-based authorization
- Docker containerization
- Cloud deployment
- CI/CD pipeline
- Expanded integration testing

## Disclaimer

This project is an educational portfolio application and is not intended to process real financial transactions.