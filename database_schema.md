# Database Schema
## Entities
## Users
- id (Primary Key)
- identification_number (Unique)
- name
- surname
- email (Unique)
- phone_number (Unique)
- address
- password_hash
- created_at
- updated_at
- last_login_at
- type_user (Enum: 'admin', 'employee')

## Project
- id (Primary Key)
- name
- description
- start_date
- end_date
- budget
- status (Enum: 'active', 'completed', 'on_hold')
- created_at
- updated_at
- owner_id (Foreign Key: Users.id)
- team_members (Many-to-Many relationship with Users)

## Team
- id (Primary Key)
- name
- description
- created_at
- updated_at
- project_id (Foreign Key: Project.id) unique

## Team Members
- id (Primary Key)
- user_id (Foreign Key: Users.id)
- team_id (Foreign Key: Team.id)
- role (Enum: 'technician', 'designer', 'manager', 'tester')
- created_at
- updated_at
- deactivated_at (nullable)
- user_id and team_id should be unique together to prevent duplicate entries for the same user in a team.

## Expenses
- id (Primary Key)
- amount
- description
- date_incurred
- created_at
- updated_at
- team_id (Foreign Key: Team.id)
- category_id (Foreign Key: Category.id)

## Category
- id (Primary Key)
- name (Unique)
- description
- created_at
- updated_at