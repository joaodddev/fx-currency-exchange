# 💱 FX Rate & Currency Exchange API

A production-inspired REST API for real-time currency exchange built with **Kotlin + Spring Boot**, designed for fintech and digital banking use cases. Fetches live exchange rates from an external provider, caches them with Redis, persists conversion history, and auto-refreshes rates on a schedule.

## 🚀 Tech Stack

- **Kotlin** + **Java 21**
- **Spring Boot 3.3**
- **Clean Architecture** + **Domain-Driven Design (DDD)**
- **Spring Security** + **JWT** (jjwt 0.12.5)
- **Spring Data JPA** + **PostgreSQL 16** + **Flyway**
- **Redis 7** (exchange rate cache)
- **Spring Scheduler** (automatic rate refresh)
- **SpringDoc OpenAPI** (Swagger UI)
- **JUnit 5** + **MockK** (29 unit tests)

## 🏗️ Architecture

Clean Architecture with strict layer separation — the domain layer has zero framework dependencies.

## 🔐 Authentication

All endpoints (except auth) require a **JWT Bearer Token**.

### Register
```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "email": "john@email.com",
  "password": "123456"
}
```

### Login
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "john@email.com",
  "password": "123456"
}
```

### Use the token
```http
Authorization: Bearer <token>
```

## 📋 Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/auth/register` | Register a new user |
| POST | `/api/v1/auth/login` | Authenticate and get JWT token |

### Currencies
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/currencies` | List all supported currencies |

### Exchange Rates
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/rates?from=USD&to=BRL` | Get rate between two currencies |
| POST | `/api/v1/rates/refresh` | Force refresh from external API |

### Conversions
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/conversions` | Convert an amount between currencies |
| GET | `/api/v1/conversions/history` | Get all conversion history |

## 💱 Conversion Example

**Request:**
```http
POST /api/v1/conversions
Authorization: Bearer <token>
Content-Type: application/json

{
  "from": "USD",
  "to": "BRL",
  "amount": 100.00
}
```

**Response:**
```json
{
  "from": "USD",
  "to": "BRL",
  "amount": 100.00,
  "convertedAmount": 525.00,
  "rate": 5.25000000,
  "convertedAt": "2026-07-24T09:00:00",
  "source": "CACHE"
}
```

The `source` field indicates whether the rate came from `CACHE` (Redis) or `DATABASE` (PostgreSQL).

## ⚡ Caching Strategy

Exchange rates are cached in **Redis** with a configurable TTL (default: 30 minutes):

- On conversion or rate query → check Redis first
- Cache hit → return instantly, no DB query
- Cache miss → query PostgreSQL, populate cache
- Scheduler → auto-refresh every 30 minutes + daily full refresh at 06:00

## 🗄️ Database

Flyway manages all migrations automatically on startup.

**Tables:** `currencies`, `exchange_rates`, `conversion_history`, `users`

**Seed data:** 8 currencies pre-loaded (USD, BRL, EUR, GBP, JPY, ARS, CLP, MXN)

Coverage includes domain entities, value objects, domain services and use cases — all tested with **JUnit 5 + MockK**.

## 🐳 Infrastructure

```yaml
services:
  postgres:  # PostgreSQL 16 — port 5432
  redis:     # Redis 7 Alpine — port 6379
```

## 👨‍💻 Author

**João Victor**

[![GitHub](https://img.shields.io/badge/GitHub-joaodddev-181717?style=flat&logo=github)](https://github.com/joaodddev)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-joaodddev-0A66C2?style=flat&logo=linkedin)](https://linkedin.com/in/joaodddev)