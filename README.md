# Redis Cache Demo – Spring Boot Application

This project demonstrates how to integrate **Redis caching** into a Spring Boot application using a layered architecture with **DTOs**, **manual and annotation-based caching**, and **PostgreSQL** as the persistent data store.

## Features

- ✅ `@Cacheable`, `@CachePut`, `@CacheEvict` usage
- ✅ Manual cache operations using `CacheManager`
- ✅ REST API for CRUD operations on books
- ✅ Cache warm-up, debug and clear endpoints
- ✅ Redis-based TTL configuration for each cache
- ✅ Clean service-DTO separation with `BookMapper`
- ✅ Docker/Podman-compatible PostgreSQL & Redis setup

## Tech Stack

- Java 21
- Spring Boot 3
- Spring Data JPA (with PostgreSQL)
- Spring Cache + Redis
- Maven

## Architecture Overview

```bash
                     ┌──────────────────────┐
                     │   Client (curl, UI)  │
                     └─────────┬────────────┘
                               │
              ┌────────────────┴────────────────┐
              │                                 │
              ▼                                 ▼
     BookController                      CacheController
              │                                 │
              ▼                                 ▼
     BookService                        CacheUtilityService
        │       │                               │
        ▼       ▼                               ▼
  Repository   Redis  ◄─────@Cacheable──────── CacheManager
        │                                     (manual put/evict/read)
        ▼
   PostgreSQL

```

## API Endpoints

### BookController

| Method | Endpoint               | Description                   |
|--------|------------------------|-------------------------------|
| GET    | `/books`               | Get all books (cached)        |
| GET    | `/books/{id}`          | Get book by ID (cached)       |
| POST   | `/books`               | Add new book (evicts cache)   |
| PUT    | `/books/{id}`          | Update book (put + evict)     |
| DELETE | `/books/clear-cache`   | Clear all caches              |

### CacheController

| Method | Endpoint                   | Description                         |
|--------|----------------------------|-------------------------------------|
| GET    | `/cache/print/{id}`        | Log cached value (manual read)      |
| PUT    | `/cache/put/{id}`          | Manually add book to cache          |
| DELETE | `/cache/evict/{id}`        | Evict specific cache entry          |
| DELETE | `/cache/clear-all`         | Clear all named caches              |

---

## Local Setup with Podman (PostgreSQL + Redis)

```bash
# PostgreSQL container
podman run -d --name pg-redis-demo -e POSTGRES_DB=books -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=1234 -p 5432:5432 docker.io/postgres:15

# Redis container
podman run -d --name redis -p 6379:6379 docker.io/redis:7

# Add a book
curl -X POST http://localhost:8080/books -H "Content-Type: application/json" -d "{\"title\": \"Clean Code\", \"author\": \"Robert C. Martin\"}"

# Get book by ID
curl http://localhost:8080/books/1

# Update book (cache PUT)
curl -X PUT http://localhost:8080/books/1 -H "Content-Type: application/json" -d "{\"title\": \"Updated\", \"author\": \"Martin\"}"

# Print from cache (manual read)
curl http://localhost:8080/cache/print/1

# Put book manually into cache
curl -X PUT http://localhost:8080/cache/put/99 -H "Content-Type: application/json" -d "{\"title\": \"Manual Cache Book\", \"author\": \"Admin\"}"

# Evict from cache
curl -X DELETE http://localhost:8080/cache/evict/1

# Clear all caches
curl -X DELETE http://localhost:8080/cache/clear-all
