# Project Context for AI Agents

## Purpose

This repository contains a work-in-progress Java command-line application for managing traveling expenses and projects. The working flow is login → home → project management. Treat unfinished menus and the design-only database notes as planned work, not implemented functionality.

## Stack and layout

- Java 26 and Maven; Java release level is configured in `pom.xml`.
- MySQL accessed with JDBC and MySQL Connector/J.
- Password4j is used for password hashing.
- Source files are under `src/main/java` and use packages prefixed with `main.java`.
- Key packages: `screens` (menu flow), `forms` (input forms), `daos` (SQL access), `dtos` (data transfer records), `database` (connection), and `utils` (shared helpers).

## Application flow

`Main` creates `AppContext`, runs startup admin seeding via `Bootstrap`, and opens `screens/auth/LoginScreen`. Successful login creates a session in `AppContext` and opens `screens/HomeScreen`. The home screen currently routes Projects to `screens/projects/ProjectIndexScreen` and Exit to application termination. Users, Teams, Expenses, and Categories are menu placeholders. Project screens provide the most complete implementation: create, list/page, select, view details, edit, and change status.

`AuthenticatedScreen` requires an authenticated context. `ScreenUtils` handles common terminal rendering and option input. `ProjectDao` and `UserDao` own persistence operations; preserve this separation when extending functionality.

## Data and local setup

- The application currently has hard-coded MySQL connection settings in `src/main/java/database/Connector.java`. The Compose environment variables are not yet wired into this class.
- `docker-compose.yml` defines local MySQL and Adminer services; the root `env-example` lists Compose variable names.
- No migration or database initialization SQL is currently checked in. `database_schema.md` is a design reference and may not match current DAO column names or implemented features.
- `Bootstrap` seeds a default admin (`admin` / `admin`) if it is absent. Treat this as development-only; never expose or reuse these credentials in a deployed environment.

## Change guidelines

- Make focused changes and follow existing Java formatting, package naming, and screen/DAO/form patterns.
- Keep SQL parameterized with prepared statements.
- Reuse shared validation and display helpers instead of duplicating menu/input logic.
- Avoid claiming or wiring support for a feature just because it appears in the home menu or schema notes; verify the relevant screens, DAO methods, and database columns.
- Do not add credentials or other secrets to source control.
- Update this context or `README.md` when a change materially alters application setup, structure, or user flow.

## Build and validation

Use JDK 26 and Maven. The targeted project build command is:

```sh
mvn compile
```

There are no checked-in automated tests at this time. For database-dependent behavior, verify assumptions against the actual DAO SQL and a compatible local schema; compilation alone does not validate the database setup.
