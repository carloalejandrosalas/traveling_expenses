# Traveling Expenses

A Java command-line application for managing projects and, as the application grows, related teams, expenses, and categories. The current user experience is menu-driven and starts with login; project management is the most developed area.

## What is implemented

- Login using a user's identification number and password.
- A home menu with entries for Projects, Users, Teams, Expenses, Categories, and Exit. At present, only Projects and Exit are connected to working screens; the other menu entries are placeholders.
- Project creation, listing with pagination, detail lookup, project editing, and status changes.
- Initial admin-user seeding at startup when an `admin` user is not found.

The project is still in progress. In particular, the presence of a menu item or a planned entity in `database_schema.md` does not mean that feature or its database schema is implemented.

## Technology

- Java 26 (configured in `pom.xml`)
- Maven
- MySQL with MySQL Connector/J
- Password4j for password hashing

## Getting started

### Requirements

- JDK 26
- Maven
- Docker Compose (if using the included MySQL service)

### Database

The included `docker-compose.yml` starts MySQL and Adminer. It expects `MYSQL_ROOT_PASSWORD`, `MYSQL_DATABASE`, `MYSQL_USER`, and `MYSQL_PASSWORD` to be set in the environment or a local `.env` file. `env-example` lists these variable names; replace them with appropriate local values before use.

The application currently connects using hard-coded settings in `src/main/java/database/Connector.java` (`traveling_expenses_db`, `db_user`, and `db_password`). The Compose variables are not currently read by the application, so configure the connector to match your local database before running it. Do not commit credentials.

The repository does not currently include database migration or initialization SQL. `database_schema.md` is a design reference, not a guaranteed runnable schema, and may not match the columns expected by the current DAOs. Create and verify the tables required by the code before starting the application.

### Build and run

Compile the project from the repository root:

```sh
mvn compile
```

Run `main.java.Main` from an IDE configured with JDK 26 and the Maven dependencies. On startup, the application checks the database, attempts to seed the default admin user, and then displays the login screen. The seeded admin account currently uses the ID `admin` and password `admin`; change this bootstrap behavior and credentials before using the application outside a local development environment.

## Navigation

The main flow is:

```text
Main
└── LoginScreen
    └── HomeScreen
        └── ProjectIndexScreen
            ├── NewProjectScreen
            ├── ProjectsTableScreen
            └── ProjectDetailsScreen
```

`HomeScreen` is the authenticated landing page. It sends the Projects selection to `ProjectIndexScreen`; the other domain menu entries are not yet wired to screens. From the project index, users can create a project, view the project table, or select a project by ID. The table supports paging and project selection. Project details provide navigation back to the index or home screen, project editing, and status changes.

## Repository structure

```text
.
├── src/main/java/
│   ├── Main.java, AppContext.java, Bootstrap.java
│   ├── daos/                 # JDBC access to users and projects
│   ├── database/             # MySQL connection setup
│   ├── dtos/                 # User and project data records
│   ├── enums/                # Project statuses, user types, and input types
│   ├── forms/                # Login and project forms, field validation
│   ├── screens/              # Screen base classes and command-line UI
│   │   ├── auth/
│   │   └── projects/
│   └── utils/                # Input, mapping, session, pagination, and display helpers
├── src/main/resources/       # Application resources
├── database_schema.md        # Database design notes
├── docker-compose.yml        # Local MySQL and Adminer services
├── env-example               # Names of expected Compose environment variables
└── pom.xml                   # Maven configuration and dependencies
```

Java packages use the `main.java` prefix, matching the current source layout. `AppContext` shares the scanner, database connection, and authenticated session between screens. Screens render menus and route selections; DAOs perform SQL operations; DTOs carry data between those layers.

## Development notes

- Keep database access in the DAO layer and use prepared statements for SQL values.
- Keep screen routing consistent with the existing `Screen` and `AuthenticatedScreen` patterns.
- Validate menu input through the shared helpers in `utils/ScreenUtils.java`.
- There are currently no checked-in automated tests. Compile with `mvn compile` after code changes.
