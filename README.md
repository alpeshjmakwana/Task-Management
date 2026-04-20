# Task Management API

A RESTful Task Management System built with **Java 17**, **Spring Boot 3.3.5**, **MySQL**, and **JWT Authentication**.

---

## Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3.3.5
- **Database:** MySQL 8.0
- **ORM:** Spring Data JPA (Hibernate)
- **Security:** JWT (JSON Web Tokens) — 24hr expiry
- **Docs:** Springdoc OpenAPI (Swagger UI)
- **Build Tool:** Maven

---

## Prerequisites

- [Docker](https://www.docker.com/) & Docker Compose

That's it! No need to install Java or MySQL separately.

---

## Setup & Run

### 1. Clone the repository

```bash
git clone <your-repo-url>
cd task-management-api
```

### 2. Create `.env` file

```bash
cp .env.example .env
```

Edit `.env` and set your values:

```env
DB_USERNAME=taskuser
DB_PASSWORD=taskpass
DB_ROOT_PASSWORD=rootpass
JWT_SECRET=<base64-encoded-secret>
```

To generate a JWT secret:

**Windows (PowerShell):**
```powershell
[Convert]::ToBase64String([System.Text.Encoding]::UTF8.GetBytes('my-super-secret-key-minimum-32-chars!!'))
```

**Mac/Linux:**
```bash
openssl rand -base64 32
```

### 3. Run the project (App + MySQL both)

```bash
docker-compose up --build
```

Wait for this message:
```
task_management_app  | Started TaskmanagementApplication
```

### 4. Open Swagger UI

```
http://localhost:8081/swagger-ui.html
```

---

## API Endpoints

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/signup` | Register new user |
| POST | `/api/auth/login` | Login and get JWT |

### Projects *(requires Bearer token)*
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/projects` | Create a project |
| GET | `/api/projects` | Get all your projects |

### Tasks *(requires Bearer token)*
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/projects/{projectId}/tasks` | Create a task |
| GET | `/api/projects/{projectId}/tasks` | List tasks (paginated, filterable) |
| PATCH | `/api/tasks/{taskId}/status` | Update task status |
| DELETE | `/api/tasks/{taskId}` | Delete a task |

#### List Tasks Query Params
| Param | Default | Description |
|-------|---------|-------------|
| `page` | `0` | Page number |
| `size` | `10` | Page size |
| `status` | *(optional)* | Filter: `TODO`, `IN_PROGRESS`, `DONE` |

---

## Testing with Swagger

1. Call `POST /api/auth/signup` to register
2. Call `POST /api/auth/login` to get JWT token
3. Click **Authorize** button (top right)
4. Enter: `Bearer <your-token>`
5. Now all protected endpoints are unlocked!

---

## Stop the project

```bash
docker-compose down
```

To also delete the database volume:
```bash
docker-compose down -v
```

---

## Project Structure

```
task-management-api/
├── docker-compose.yml
├── .env.example
├── README.md
└── api/
    ├── Dockerfile
    ├── pom.xml
    └── src/main/java/com/taskmanagement/taskmanagement/
        ├── config/          # SecurityConfig, OpenApiConfig
        ├── controller/      # AuthController, ProjectController, TaskController
        ├── dto/
        │   ├── request/     # SignupRequest, LoginRequest, ProjectRequest, TaskRequest
        │   └── response/    # SignupResponse, LoginResponse, ProjectResponse, TaskResponse
        ├── entity/          # User, Project, Task, TaskStatus
        ├── exception/       # GlobalExceptionHandler, custom exceptions
        ├── repository/      # UserRepository, ProjectRepository, TaskRepository
        ├── security/        # JwtUtil, JwtAuthFilter, UserDetailsServiceImpl
        └── service/
            ├── interfaces   # AuthService, ProjectService, TaskService
            └── impl/        # AuthServiceImpl, ProjectServiceImpl, TaskServiceImpl
```

---

## Development Assumptions

- Passwords hashed using **BCrypt**
- JWT tokens include `userId` and `email` claims, valid for **24 hours**
- Only the **project owner** can create, update, or delete tasks
- Database schema auto-managed by Hibernate (`ddl-auto: update`)
- All responses use `application/json`
