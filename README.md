# Smart Expense Tracker

Smart Expense Tracker is a backend REST API for recording expenses, managing budgets, and viewing spending analytics. It uses a clean layered architecture with controllers, services, repositories, entities, and DTOs.

## Features

- Create, read, update, and delete expenses
- Create, read, update, and delete budgets
- View monthly spending summaries
- Group spending by category for a date range
- Compare spending between two consecutive months
- Validate requests and return error responses
- Run unit, integration, and REST Assured API tests
- Execute automated checks through Jenkins

## Technology stack

- Java 17+
- Spring Boot and Spring Web MVC
- Spring Data JPA and Hibernate
- MySQL
- Maven
- Lombok
- JUnit 5 and Mockito
- REST Assured
- H2 in-memory database for tests
- Jenkins Pipeline

## Project structure

```text
src
├── main/java/com/expensetracker
│   ├── controller       # REST endpoints
│   ├── service          # Service contracts
│   ├── service/impl     # Service implementations
│   ├── repository       # Spring Data repositories
│   ├── entity           # JPA entities
│   ├── dto              # Request and response DTOs
│   ├── exception        # Exceptions and global handlers
│   ├── config           # Application configuration
│   └── util             # Shared utilities
└── test/java/com/expensetracker
    ├── service          # Unit tests
    ├── controller       # Controller tests
    └── integration      # Integration and API tests
```

## Requirements

- JDK 17 or later
- MySQL 8 or later
- Git

The Maven Wrapper is included, so a separate Maven installation is not required.

## Database setup

Create the MySQL database:

```sql
CREATE DATABASE smart_expense_tracker;
```

Configure credentials with environment variables so passwords are not committed:

Windows PowerShell:

```powershell
$env:DB_URL = "jdbc:mysql://localhost:3306/smart_expense_tracker"
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "your_mysql_password"
```

## Running the application

Windows:

```powershell
./mvnw.cmd spring-boot:run
```

Linux or macOS:

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

The API starts at `http://localhost:8080`.

## API endpoints

### Expenses

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/expenses` | Create an expense |
| GET | `/api/expenses` | List all expenses |
| GET | `/api/expenses/{id}` | Find an expense |
| PUT | `/api/expenses/{id}` | Update an expense |
| DELETE | `/api/expenses/{id}` | Delete an expense |

Example request:

```json
{
  "description": "Lunch",
  "amount": 12.50,
  "category": "Food",
  "expenseDate": "2026-11-10",
  "notes": "Business lunch"
}
```

### Budgets

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/budgets` | Create a budget |
| GET | `/api/budgets` | List all budgets |
| GET | `/api/budgets/{id}` | Find a budget |
| PUT | `/api/budgets/{id}` | Update a budget |
| DELETE | `/api/budgets/{id}` | Delete a budget |

Example request:

```json
{
  "category": "Food",
  "amount": 500.00,
  "startDate": "2026-11-01",
  "endDate": "2026-11-30"
}
```

### Analytics

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/analytics/monthly?month=2026-11` | Monthly spending summary |
| GET | `/api/analytics/categories?startDate=2026-11-01&endDate=2026-11-30` | Spending by category |
| GET | `/api/analytics/comparison?month=2026-11` | Current versus previous month |

Dates use `YYYY-MM-DD`; month values use `YYYY-MM`.

## Testing

The test profile uses an H2 in-memory database, so tests do not require MySQL.

Run the complete suite:

```powershell
./mvnw.cmd clean test
```

Run unit tests:

```powershell
./mvnw.cmd -Dtest=*ServiceTest test
```

Run integration tests:

```powershell
./mvnw.cmd -Dtest=*IntegrationTest test
```

Run REST Assured API tests:

```powershell
./mvnw.cmd -Dtest=*ApiTest test
```

## Jenkins

The `Jenkinsfile` runs these stages:

1. Build the application
2. Run unit tests
3. Run integration tests
4. Run REST Assured tests
5. Publish JUnit test reports

Jenkins checks out the repository and runs the same Maven Wrapper commands used locally.

## GitHub workflow

```powershell
git add .
git commit -m "Describe your change"
git push origin main
```

Do not commit `application.properties` or database passwords. Use environment variables for local credentials.

## License

This project is currently intended for learning and portfolio purposes.
