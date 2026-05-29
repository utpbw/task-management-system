# Task Management System

A RESTful task management API built with Spring Boot. Users can register, authenticate with a Bearer token, create and assign tasks, update task status, and leave comments.

## Stack

- Java + Spring Boot (Web, Security, Data JPA, Validation)
- H2 (file-based, persisted between restarts)
- Gradle

## Running

```bash
cd "Task Management System/task"
../../gradlew bootRun
```

The server starts on `http://localhost:8080`.

## Authentication

All endpoints except `POST /api/accounts` require authentication.

**Step 1 — register:**
```
POST /api/accounts
{"username": "alice", "password": "secret123"}
```

**Step 2 — get a token (Basic auth):**
```
POST /api/auth/token
Authorization: Basic <base64(username:password)>
```
Returns `{"token": "<value>"}`.

**Step 3 — use the token:**
```
Authorization: Bearer <token>
```

## API

### Accounts
| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/accounts` | Register a new user |

### Auth
| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/auth/token` | Exchange Basic credentials for a Bearer token |

### Tasks
| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/tasks` | Create a task (author = authenticated user) |
| GET | `/api/tasks` | List tasks; filter with `?author=` and/or `?assignee=` |
| PUT | `/api/tasks/{id}/assign` | Assign a task to a user |
| PUT | `/api/tasks/{id}/status` | Update status (`CREATED`, `IN_PROGRESS`, `COMPLETED`) |

### Comments
| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/tasks/{id}/comments` | Add a comment to a task |
| GET | `/api/tasks/{id}/comments` | List comments for a task |

## H2 Console

Available at `http://localhost:8080/h2-console` while the app is running.  
JDBC URL: `jdbc:h2:file:../tms_db` · User: `sa` · Password: `sa`
