# 🚀 SnapLink

<p align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen?style=for-the-badge)
![React](https://img.shields.io/badge/React-19-61DAFB?style=for-the-badge)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge)
![Redis](https://img.shields.io/badge/Redis-7-red?style=for-the-badge)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-success?style=for-the-badge)

</p>

---

## 📖 About

SnapLink is a **production-ready URL Shortener** built using **Spring Boot**, **React**, **PostgreSQL**, and **Redis**.

The project focuses on building a scalable backend rather than just shortening URLs. It implements authentication, analytics, caching, asynchronous processing, scheduled synchronization, Dockerized deployment, and CI/CD practices similar to modern production systems.

---

# ✨ Features

## 🔐 Authentication & Security

- JWT Authentication
- BCrypt Password Encryption
- Spring Security
- Protected REST APIs
- Ownership Validation
- Role-based Authorization

---

## 🔗 URL Management

- Create Short URLs
- Custom Aliases
- Reserved Alias Validation
- URL Expiration
- Update URL
- Delete URL
- User Dashboard

---

## 📊 Analytics

- Total Click Tracking
- Browser Analytics
- Device Analytics
- Country Analytics
- Last Click Timestamp
- Live Analytics Dashboard

---

## ⚡ Performance

- Redis URL Cache
- Redis Click Counter
- Asynchronous Click Tracking
- Scheduled Click Synchronization
- Optimized Database Queries

---

## 🚀 DevOps

- Docker
- Docker Compose
- Flyway
- GitHub Actions
- Render Deployment
- Vercel Deployment

---

# 🏗 Tech Stack

## Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT
- PostgreSQL
- Redis
- Flyway
- Maven

---

## Frontend

- React
- Vite
- Tailwind CSS
- Axios
- React Router

---

## DevOps

- Docker
- Docker Compose
- GitHub Actions
- Render
- Vercel

---

# 📐 High Level Architecture

```text
                    User
                      │
                      ▼
          React Frontend (Vercel)
                      │
               HTTPS REST API
                      │
                      ▼
             Spring Boot Backend
                      │
      ┌───────────────┴───────────────┐
      │                               │
      ▼                               ▼
 PostgreSQL                     Redis Cache
      │                               │
      └───────────────┬───────────────┘
                      ▼
           Click Synchronization
              Scheduled Job
```

---

# 🔄 URL Creation Flow

```text
Client

   │

POST /api/url/shorten

   │

Controller

   │

Service

   │

Generate Short Code

   │

Persist URL

   │

PostgreSQL
```

---

# ⚡ Redirect Flow

```text
Client

   │

GET /{shortCode}

   │

Redis Cache

   │

Cache Hit?

┌────────────┴────────────┐
│                         │
▼                         ▼

YES                      NO

│                         │

Redirect            PostgreSQL

                         │

                 Store in Redis

                         │

                   Redirect User
```

---

# 📈 Analytics Flow

```text
User Click

    │

Redirect Request

    │

Async Click Tracker

    │

Redis Counter

    │

Every 60 Seconds

    │

Scheduler

    │

PostgreSQL
```

---

# 📂 Project Structure

```text
SnapLink

backend
│
├── controller
├── service
├── repository
├── security
├── config
├── dto
├── entity
├── scheduler
└── util

frontend
│
├── api
├── components
├── context
├── layouts
├── pages
├── routes
└── hooks
```

---

# 🔌 REST APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/auth/register` | Register User |
| POST | `/api/auth/login` | Login |
| POST | `/api/url/shorten` | Create Short URL |
| GET | `/api/url/myurls` | User URLs |
| GET | `/api/url/analytics/{shortCode}` | URL Analytics |
| PUT | `/api/url/{id}` | Update URL |
| DELETE | `/api/url/{id}` | Delete URL |

---

# 🌍 Deployment

| Component | Platform |
|------------|----------|
| Frontend | Vercel |
| Backend | Render |
| Database | PostgreSQL |
| Cache | Redis |
| CI | GitHub Actions |

---

# 📌 Future Improvements

- Apache Kafka Event Streaming
- QR Code Generation
- Password Protected Links
- Custom Domains
- Team Workspaces
- Webhooks
- Real-time Analytics
- Monitoring & Metrics
- OpenTelemetry
- Prometheus + Grafana

---

# 📜 License

Licensed under the MIT License.

---

# 👨‍💻 Author

### Anand Choudhary

- GitHub: https://github.com/Aanand251
- LinkedIn: https://www.linkedin.com/in/anand-choudhary251/

---

## ⭐ If you found this project useful, consider giving it a star!