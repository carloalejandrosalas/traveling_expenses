package main.java.daos;

import main.java.dtos.projects.Project;
import main.java.dtos.projects.CreateProjectRequest;
import main.java.dtos.projects.UpdateProjectRequest;
import main.java.enums.ProjectStatusEnum;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProjectDao {
    private final Connection connection;

    public ProjectDao(Connection connection) {
        this.connection = connection;
    }

    public Long createProject(CreateProjectRequest newProjectRequest) {
        try (var statement = connection.prepareStatement("""
                INSERT INTO projects (owner_id, name, description, start_date, end_date, budget, status)
                VALUES (?, ?, ?, ?, ?, ?, ?)
            """, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, newProjectRequest.ownerId());
            statement.setString(2, newProjectRequest.name());
            statement.setString(3, newProjectRequest.description());
            statement.setDate(4, java.sql.Date.valueOf(newProjectRequest.startDate()));
            statement.setDate(5, java.sql.Date.valueOf(newProjectRequest.endDate()));
            statement.setBigDecimal(6, newProjectRequest.budget());
            statement.setString(7, newProjectRequest.status().name());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (var generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    }
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Project> getProjects() {
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery("""
                SELECT
                    p.id,
                    p.owner_id,
                    CONCAT_WS(' ', u.name, u.surname) AS owner_name,
                    p.name,
                    p.description,
                    p.start_date,
                    p.end_date,
                    p.budget,
                    p.status,
                    p.created_at,
                    p.updated_at
                    FROM projects p
                    JOIN users u ON p.owner_id = u.id
            """);
            ArrayList<Project> projects = new ArrayList<>();
            while (resultSet.next()) {
                projects.add(mapResultSetToProject(resultSet));
            }

            return projects;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getProjectsCount() {
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery("SELECT COUNT(*) AS total FROM projects");
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Project getProjectById(Long projectId) {
        try (var statement = connection.prepareStatement("""
                SELECT
                    p.id,
                    p.owner_id,
                    CONCAT_WS(' ', u.name, u.surname) AS owner_name,
                    p.name,
                    p.description,
                    p.start_date,
                    p.end_date,
                    p.budget,
                    p.status,
                    p.created_at,
                    p.updated_at
                FROM projects p
                JOIN users u ON p.owner_id = u.id
                WHERE p.id = ?
            """)) {
            statement.setLong(1, projectId);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToProject(resultSet);
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateProjectStatus(Long projectId, ProjectStatusEnum newStatus) {
        try (var statement = connection.prepareStatement("""
                UPDATE projects
                SET status = ?
                WHERE id = ?
            """)) {
            statement.setString(1, newStatus.name());
            statement.setLong(2, projectId);

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProject(UpdateProjectRequest updateRequest) {
        try (var statement = connection.prepareStatement("""
                UPDATE projects
                SET name = ?, description = ?, start_date = ?, end_date = ?, budget = ?, updated_at = CURRENT_TIMESTAMP
                WHERE id = ?
            """)) {
            statement.setString(1, updateRequest.name());
            statement.setString(2, updateRequest.description());
            statement.setDate(3, java.sql.Date.valueOf(updateRequest.startDate()));
            statement.setDate(4, java.sql.Date.valueOf(updateRequest.endDate()));
            statement.setBigDecimal(5, updateRequest.budget());
            statement.setLong(6, updateRequest.id());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Project mapResultSetToProject(ResultSet resultSet) throws java.sql.SQLException {
        String rawStatus = resultSet.getString("status");
        var status = (rawStatus != null) ? ProjectStatusEnum.valueOf(rawStatus) : null;
        var createdAt = resultSet.getTimestamp("created_at");
        var updatedAt = resultSet.getTimestamp("updated_at");

        return new Project(
            resultSet.getLong("id"),
            resultSet.getLong("owner_id"),
            resultSet.getString("owner_name"),
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getDate("start_date").toLocalDate(),
            resultSet.getDate("end_date").toLocalDate(),
            resultSet.getBigDecimal("budget"),
            status,
            createdAt != null ? createdAt.toLocalDateTime() : null,
            updatedAt != null ? updatedAt.toLocalDateTime() : null
        );
    }
}
