# 🚀 SnapLink - Smart URL Shortener with Analytics

<p align="center">
  <b>A production-ready URL shortening platform built with Spring Boot and PostgreSQL.</b>
</p>

<p align="center">
  URL Shortening • Analytics • Validation • Swagger • Exception Handling • JPA Relationships
</p>

---

## 📖 Overview

SnapLink is a backend-focused URL shortening platform that allows users to generate short URLs, redirect to original URLs, and track click analytics.

The project was built to demonstrate real-world backend development concepts such as layered architecture, database relationships, validation, exception handling, API documentation, and analytics tracking.

---

## ✨ Features

| Feature                   | Status     |
| ------------------------- | ---------- |
| URL Shortening            | ✅          |
| URL Redirection           | ✅          |
| Click Tracking            | ✅          |
| Analytics API             | ✅          |
| DTO Validation            | ✅          |
| Global Exception Handling | ✅          |
| Swagger Documentation     | ✅          |
| PostgreSQL Integration    | ✅          |
| JPA Relationships         | ✅          |
| Logging                   | ✅          |
| Redis Caching             | 🚧 Planned |
| Rate Limiting             | 🚧 Planned |
| Docker Support            | 🚧 Planned |

---

## 🏗️ Architecture

```text
Client
   │
   ▼
Controller Layer
   │
   ▼
Service Layer
   │
   ▼
Repository Layer
   │
   ▼
PostgreSQL Database
```

---

## 📂 Project Structure

```text
src/main/java/com/anand/url_shortner

├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── service
└── UrlShortnerApplication
```

---

## 🛠️ Tech Stack

| Category      | Technology        |
| ------------- | ----------------- |
| Language      | Java 21           |
| Framework     | Spring Boot       |
| Database      | PostgreSQL        |
| ORM           | Spring Data JPA   |
| Documentation | Swagger / OpenAPI |
| Build Tool    | Maven             |
| Utilities     | Lombok            |
| API Testing   | Postman           |

---

## 📊 Database Design

### URL Mapping

| Field       | Type   |
| ----------- | ------ |
| id          | Long   |
| originalUrl | String |
| shortCode   | String |
| createdAt   | Date   |

### Click Event

| Field     | Type   |
| --------- | ------ |
| id        | Long   |
| clickedAt | Date   |
| device    | String |
| browser   | String |
| country   | String |

### Relationship

```text
UrlMapping (1)
      │
      ▼
ClickEvent (Many)
```

Implemented using:

Implemented using:

Implemented using:

- @OneToMany
- @ManyToOne
- @JoinColumn

---

## 🔌 API Endpoints

### Create Short URL

```http
POST /api/url/shorten
```

Request:

```json
{
  "originalUrl": "https://youtube.com"
}
```

---

### Get All URLs

```http
GET /api/url
```

---

### Get Analytics

```http
GET /api/url/analytics/{shortCode}
```

---

### Redirect URL

```http
GET /{shortCode}
```

---

## 📚 Swagger Documentation

Run the application and open:

```text
http://localhost:8080/swagger-ui/index.html
```

Swagger provides interactive API testing and documentation.

---

## ⚙️ Local Setup

### Clone Repository

```bash
git clone https://github.com/Aanand251/snaplink-url-shortener.git
```

### Navigate

```bash
cd snaplink-url-shortener
```

### Configure PostgreSQL

Update:

```properties
application.properties
```

with your database credentials.

### Run Application

```bash
mvn spring-boot:run
```

---

## 🚀 Future Enhancements

* Redis Caching
* Rate Limiting
* Docker Containerization
* Custom Alias URLs
* QR Code Generation
* Expiring Links
* User Authentication (JWT)

---

## 👨‍💻 Author

**Aanand Kumar**

* GitHub: https://github.com/Aanand251
* Backend Development | Spring Boot | Java

---

## ⭐ Project Goal

This project is being developed as a production-oriented backend application to strengthen expertise in:

* Spring Boot
* REST APIs
* PostgreSQL
* Database Design
* System Design Fundamentals
* Scalable Backend Development
