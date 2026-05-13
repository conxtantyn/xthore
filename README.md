# Xthore: Customer Order & Product Catalog Service

This project implements a microservices-based system for managing customer orders and product offerings, built as part of the backend engineering challenge.

## 🚀 How to Run

### Prerequisites
- **Java 21**
- **Maven 3.9+**
- **Docker & Docker Compose**

### Setup
1. **Environment Variables**: The project requires a `.env` file in the root directory.
   ```bash
   cp .env_sample .env
   ```
2. **Execute Automation Script**: Use the provided `xthore.sh` script to build and deploy the entire stack.
   ```bash
   # Run full sequence: Test -> Build -> Deploy
   ./xthore.sh
   
   # Or run individual steps:
   ./xthore.sh --test    # Run unit and integration tests
   ./xthore.sh --build   # Build JARs and Docker images using Jib
   ./xthore.sh --deploy  # Spin up containers (PostgreSQL, Eureka, Services)
   ```

### Reachable Endpoints
- **Order Service**: `http://localhost:8090/order` (Mapped via Gateway/Eureka)
- **Service Registry (Eureka)**: [http://localhost:8761/](http://localhost:8761/)
- **Swagger Documentation**: [http://localhost:8090/swagger-ui/index.html](http://localhost:8090/swagger-ui/index.html)
  - Access specific services using the `q` query parameter, e.g:
    - [Catalog service](http://localhost:8090/swagger-ui/index.html?q=catalog)
    - [Order service](http://localhost:8090/swagger-ui/index.html?q=order)

---

## 🏗️ What Was Built

- **Customer Order Service**: Manages the order lifecycle (Draft -> Preview -> Submitted -> Confirmed) with strict state transition rules.
- **Product Catalog Service**: Provides product offering validation for the Order service.
- **Core Modules**: Shared libraries for REST handling (`core-rest`), Remote communication (`core-remote`), and Common utilities (`core-common`).
- **Persistence Layer**: Reactive persistence using **Spring Data R2DBC** and **PostgreSQL**.
- **Service Discovery**: Integrated **Netflix Eureka** for seamless inter-service communication.

### Scope Decisions & Cuts
- **Authentication**: Purposely omitted to focus on core domain logic and idempotency as per exercise requirements.
- **Event-Driven Updates**: Chose synchronous Feign communication for catalog validation to ensure immediate consistency during order creation/patching, though an async event-bus was considered for state changes.

---

## 🧠 Decisions and Tradeoffs

### State Machine & Validation
- **Placement**: Validation logic is centralized in the `domain` layer using use cases and specific transition validators. This ensures the business rules are independent of the entry point (REST/CLI).
- **Transitions**: Implemented a strict state transition matrix. Once an order reaches `SUBMITTED`, only the state can be patched. Once `CONFIRMED`, it becomes immutable.

### PATCH Semantics
- Used **JSON Merge Patch** behavior. Absent fields in the request body do not overwrite existing data. This is handled by a dedicated `UpdateModel` and internal domain mapping that selectively applies changes.

### Idempotency
- **Mechanism**: Fresh creations are tracked via an `Idempotency-Key` header.
- **Persistence**: The key is stored alongside the session in the database. If a request is replayed with the same key and identical payload, the existing order is returned. Different payloads for the same key result in a `409 Conflict`.

### Inter-Service Communication
- **Feign + Eureka**: Used Feign clients with service discovery.
- **Unavailability**: The Order service uses a custom `RemoteResponse` wrapper to handle the standardized response format of the Catalog service. If the Catalog is down, the system fails-fast with a descriptive error to maintain data integrity.

### Persistence & Migrations
- **R2DBC**: Selected for its non-blocking nature, aligning with the Spring WebFlux stack.
- **Liquibase**: Used for versioned schema migrations, ensuring the PostgreSQL database is correctly structured on startup.

---

## 📝 Assumptions & Limitations
- **Seeding**: The catalog is pre-seeded via Liquibase with offerings `po-1`, `po-2`, and `po-3` (Google Pixel 8, iPhone 15, etc.).
- **Currency**: All prices are handled as decimals in the catalog but treated as simple numeric types in the current iteration for simplicity.
