# Changelog

## Stage 5 — Comments and total_comments on tasks
- `POST /api/tasks/{id}/comments` — add a comment to a task
- `GET /api/tasks/{id}/comments` — list all comments for a task
- Task responses include a `total_comments` count

## Stage 4 — Task assignment and status management
- `PUT /api/tasks/{id}/assign` — assign a task to a registered user
- `PUT /api/tasks/{id}/status` — update status (`CREATED` → `IN_PROGRESS` → `COMPLETED`)

## Stage 3 — Bearer token authentication
- `POST /api/auth/token` — exchange Basic credentials for a Bearer token
- All task endpoints now require `Authorization: Bearer <token>`

## Stage 2 — Task creation and retrieval
- `POST /api/tasks` — create a task; author set to the authenticated user
- `GET /api/tasks` — list tasks with optional `?author=` / `?assignee=` filters

## Stage 1 — User registration and basic auth
- `POST /api/accounts` — register a new user (username + password)
- Basic HTTP authentication wired up via Spring Security
