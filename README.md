# 🃏 Blackjack Platform – DDD, Hexagonal & Event-Driven Microservices

## Description

This project implements a **Blackjack platform** with the primary goal of **demonstrating professional software design**, rather than building a simple game.

The main motivation is to apply **advanced architectural patterns** to a realistic domain using **Domain-Driven Design (DDD)**, **Hexagonal Architecture (Ports & Adapters)**, **Event-Driven Architecture**, and **microservices**, following a **Test-Driven Development (TDD)** approach.

The project addresses the challenge of **designing scalable, decoupled, and evolvable systems**, where:
- The domain model is framework-independent
- Bounded Contexts communicate exclusively through events
- Infrastructure adapts to the domain, not the other way around

Throughout the development, the following concepts are applied and reinforced:
- Strategic and tactical DDD
- Clean separation of layers
- Asynchronous communication with Kafka
- Multi-level testing strategies
- Code quality measurement with SonarQube and JaCoCo

---

## Table of Contents

- [Architecture](#architecture)
- [Bounded Contexts](#bounded-contexts)
- [Installation](#installation)
- [Usage](#usage)
- [Testing and Quality](#testing-and-quality)
- [Technologies](#technologies)
- [Credits](#credits)
- [License](#license)

---

## Architecture
The system is split into **independent microservices**, each representing a **Bounded Context**.

Communication between services is **asynchronous** and **event-driven**, using **Apache Kafka**.

| Game BC | | Wallet BC |
| :---: | :---: | :---: |
| **Microservice** | $\xrightarrow{\text{Kafka Events}}$ | **Microservice** |
| ⬇️ | | ⬇️ |
| PostgreSQL / MongoDB | | PostgreSQL / MongoDB |



Key architectural principles:
- Hexagonal Architecture (Ports & Adapters)
- No framework dependencies in the domain layer
- Communication between Bounded Contexts via domain events only
- Database-per-service approach

---

## Bounded Contexts

### Game Bounded Context

Responsibilities:
- Start new games
- Manage games in progress
- Deal cards
- Detect Blackjack
- Publish domain events when a game finishes

Persistence rules:
- Only **in-progress games** are stored
- Finished games are removed from the database and emitted as domain events

---

### Ranking Bounded Context

Responsibilities:
- Consume domain events from the Game BC
- Persist winning games
- Expose ranking queries (Top N winners)

This context is **read-only** and fully **decoupled** from the Game BC.

---

## Installation

### Prerequisites

- Java 21
- Maven
- Docker (Kafka, RabbitMQ, MongoDB)
- SonarQube

---

## Usage & API Documentation
The API is documented using Swagger (OpenAPI 3). Once the microservices are running, you can access the interactive interface to test the endpoints:

- **Swagger UI:** http://localhost:8080/swagger-ui.html

### Quick Example: start a new game
### POST /games/start

```json
{
"playerName": "Pepito"
}
```
### Response: 201 Created

---

## Testing and Quality

### Quality Tools
- **JaCoCo:** Code coverage measurement (integrated into Maven).
- **SonarQube:** Static analysis, technical debt detection, and complexity metrics.


## Technologies

- Java 21
- Spring Boot 4
- Spring Cloud Eureka
- Apache Kafka
- Maven
- JUnit 5
- Mockito
- JaCoCo
- SonarQube
- MongoDB

## Credits
Developed by Rot Colton
> Personal project focused on learning and demonstrating advanced software architecture.
