# Full Stack Developer Assignment

## Project Overview

Task Tracker is a small internal task-management application. It lists active tasks, supports text search, status filtering, and pagination, and presents clear loading, empty, and error states.

## Tech Stack

- **Frontend:** React 18, Vite 5, JavaScript
- **Backend:** Java 17, Spring Boot 3.2, Spring Data JPA
- **Database:** H2 in-memory database for local development
- **Testing:** JUnit 5, Spring Boot Test, MockMvc
- **Build tools:** npm and Maven Wrapper

## Project Structure

```text
backend/                         Spring Boot API
  src/main/java/                 Controller, entity, repository
  src/main/resources/            H2 configuration and seed data
  src/test/java/                 API integration tests
frontend/                        React/Vite application
  src/components/                Search, status filter, task table
  src/hooks/                     Task data loading hook
db/                              H2 and Oracle query reference artifacts
NOTES.md                         Patch rationale and tradeoffs
```

## Prerequisites

- Node.js 18 or later
- Java 17 or later

## Environment Variables

No secrets are required for local development. Copy the relevant `.env.example` file only when configuring a hosted environment; do not commit the resulting `.env` file.

| Variable | Used by | Purpose |
| --- | --- | --- |
| `VITE_API_BASE_URL` | Frontend | Public API base URL, including `/api`, for separately hosted frontend/API deployments. Defaults to `/api`. |
| `DATABASE_URL` | Backend | JDBC URL. Defaults to local in-memory H2. |
| `DATABASE_USERNAME` | Backend | Database username. |
| `DATABASE_PASSWORD` | Backend | Database password. |
| `CORS_ALLOWED_ORIGINS` | Backend | Allowed deployed frontend origin. Defaults to `http://localhost:5173`. |
| `H2_CONSOLE_ENABLED` | Backend | Enables the H2 web console; keep `false` on hosted environments. |
| `PORT` | Backend | HTTP port supplied by a Java hosting platform. Defaults to `8080`. |

## Local Setup

Start the API in one terminal:

```bash
cd backend
./mvnw spring-boot:run
```

Start the frontend in another terminal:

```bash
cd frontend
npm install
npm run dev
```

Open `http://localhost:5173`. Vite proxies `/api` requests to the local Spring Boot server at port 8080.

## Verification

```bash
cd backend
./mvnw test

cd ../frontend
npm run build
```

## API Information

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/api/tasks` | Lists active tasks with search/filter/pagination support. |

`GET /api/tasks` accepts optional `q`, `status` (`OPEN`, `IN_PROGRESS`, or `DONE`), `page` (minimum 1), and `pageSize` (1–100) query parameters.

## Database Setup

The application initializes an H2 in-memory database from `backend/src/main/resources/schema.sql` and `data.sql`. It requires no local database installation. The H2 console is available locally at `http://localhost:8080/h2-console` while the API is running.

The `db/oracle` file is a query reference artifact only; it is not used by the local application.

## Deployment

No live deployment has been created. The local H2 in-memory database is not suitable for persistent production data. A deployment for this demo can host the Spring Boot service with a persistent disk and set `DATABASE_URL` to an H2 file URL such as `jdbc:h2:file:/var/data/taskdb`; this is appropriate only for a small single-instance demonstration. A production deployment should use:

```text
React static frontend
        ↓ VITE_API_BASE_URL
Spring Boot API (Java-capable host such as Render or Railway)
        ↓ DATABASE_URL / credentials
Persistent database compatible with the configured JDBC driver
```

Set `VITE_API_BASE_URL` to the deployed API URL including `/api`, and set `CORS_ALLOWED_ORIGINS` to the exact HTTPS frontend origin. Store database credentials only in the deployment platform’s encrypted environment-variable settings.

This repository currently ships only the H2 JDBC driver and H2-oriented schema initialization. Do not point it at PostgreSQL, Oracle, or another database without adding the driver and validating a database-specific migration first.

## Features

- Task search with debouncing and cancellation of obsolete requests
- Status filtering and pagination
- Archived-task exclusion and correctly grouped search predicates
- Input validation with useful API errors
- Responsive task table and accessible form controls/error messages
- Backend integration tests for filtering and validation behavior

## Live Demo

Not deployed yet.

## GitHub Repository

To be added after creating the submission repository under `Akeel3040`.

## Author

Akeel3040
