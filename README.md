
# Inditex Price Management Service

## Overview

This project implements a Spring Boot application that provides an endpoint to retrieve the applicable price for a product based on specific criteria. The application is designed using **hexagonal architecture principles**, ensuring maintainability, scalability, and separation of concerns.

## Features

- Retrieve the price of a product for a given brand, product ID, and application date.
- Prioritize prices based on their `priority` field when multiple prices overlap for the given criteria.
- Uses an in-memory H2 database for data storage and initialization.
- Comprehensive test coverage for the endpoint and its functionality.

## Architecture

The application follows **hexagonal architecture** (also known as ports and adapters), emphasizing low coupling and high cohesion. The structure is organized as follows:

### Layers

1. **Domain Layer**
    - Contains the core business logic and domain models (e.g., `Price`, `Brand`, `Product`).
    - Interfaces like `PriceDomainRepository` define the contracts for interacting with external systems.

2. **Application Layer**
    - Acts as a mediator between the domain and the external world.
    - Includes use cases (e.g., `SearchPriceUseCase`) that orchestrate the application's functionality.

3. **Adapters Layer**
    - Handles communication with external systems such as APIs, databases, and controllers.
    - Split into `in` (e.g., REST controllers) and `out` (e.g., JPA repositories, external clients).

### Key Components

- **Controller (`adapter.in.controller`)**:
    - Exposes REST endpoints for querying prices.
    - Handles input validation and delegates processing to the application layer.
    - Includes `GlobalExceptionHandler` for centralized error handling.

- **Persistence (`adapter.out.persistence`)**:
    - Manages data access using JPA repositories.
    - Includes entities (`PriceEntity`, `BrandEntity`, `ProductEntity`) mapped to the H2 database.

- **Specifications (`adapter.out.persistence.specification`)**:
    - Encapsulates dynamic query logic using the `Specification` interface.
    - Implements complex filtering logic, including subqueries for determining maximum priority.

- **Application Layer (`application`)**:
    - Contains use cases (`SearchPriceUseCase`) that define the application's main functionality.
    - Uses mappers to convert between domain models and persistence/API models.

## Hexagonal Architecture Alignment

- **Decoupled Design**: The domain layer is independent of external frameworks like JPA or Spring, ensuring low coupling.
- **Interfaces for Port Communication**: Contracts (e.g., `PriceDomainRepository`) abstract interactions between layers.
- **Adapters for Implementation**: Input (controllers) and output (repositories, mappers) implementations are well-separated.

## Testing

- Comprehensive tests validate the functionality of the application using both unit and integration tests.
- Test cases ensure compliance with the following scenarios:
    - Query at `10:00` on `2020-06-14` for product `35455` and brand `1`.
    - Query at `16:00` on `2020-06-14` for product `35455` and brand `1`.
    - Additional tests cover edge cases like overlapping price ranges and missing effective dates.

## Database

- The application uses an in-memory H2 database for persistence.
- The schema is initialized using `schema.sql` and `data.sql` files.
- Example `PRICES` table structure:
  ```sql
  CREATE TABLE prices (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      brand_id BIGINT NOT NULL,
      start_date TIMESTAMP NOT NULL,
      end_date TIMESTAMP NOT NULL,
      price_list INT NOT NULL,
      product_id BIGINT NOT NULL,
      priority INT NOT NULL,
      price DOUBLE NOT NULL,
      currency VARCHAR(3) NOT NULL
  );
  ```

## How to Run

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd inditex-price-service
   ```

2. **Build and Run**:
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Test the application**:
    - Use `Postman` or `cURL` to test the REST endpoint.
    - Example request:
      ```bash
      curl -X GET "http://localhost:8080/api/prices?effectiveDate=2020-06-14T10:00:00&productId=35455&brandId=1"
      ```

## Requirements Compliance

1. **Hexagonal Architecture**:
    - The design strictly adheres to the principles of hexagonal architecture with clear separation of ports and adapters.

2. **SOLID Principles**:
    - Single Responsibility Principle: Each class and method has a single responsibility.
    - Open/Closed Principle: The system is extensible through interfaces and specifications without modifying existing code.

3. **Code Quality**:
    - Well-defined methods and classes with descriptive naming.
    - Consistent use of builders and constructors (avoiding setters).

4. **Low Coupling**:
    - Interfaces and mappers decouple the domain layer from persistence and API layers.

## Future Enhancements

- Add support for additional query parameters (e.g., filtering by currency).
- Implement authentication and authorization for the REST API.
- Extend the database schema to handle multi-currency pricing.