package main.java.daos;

import main.java.dtos.Project;
import main.java.dtos.UpsertProjectRequest;
import main.java.enums.ProjectStatusEnum;

import java.sql.Connection;
import java.sql.ResultSet;

public class ProjectDao {
    private final Connection connection;

    public ProjectDao(Connection connection) {
        this.connection = connection;
    }

    public boolean createProject (UpsertProjectRequest newProjectRequest) {
        try (var statement = connection.prepareStatement("""
                INSERT INTO projects (owner_id, name, description, start_date, end_date, budget, status)
                VALUES (?, ?, ?, ?, ?, ?, ?)
            """)) {
            statement.setLong(1, newProjectRequest.ownerId());
            statement.setString(2, newProjectRequest.name());
            statement.setString(3, newProjectRequest.description());
            statement.setDate(4, java.sql.Date.valueOf(newProjectRequest.startDate()));
            statement.setDate(5, java.sql.Date.valueOf(newProjectRequest.endDate()));
            statement.setBigDecimal(6, newProjectRequest.budget());
            statement.setString(7, newProjectRequest.status().name());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Project getProjects() {
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery("""
                SELECT
                    id,
                    owner_id,
                    owner_name,
                    name,
                    description,
                    start_date,
                    end_date,
                    budget,
                    status,
                    created_at,
                    updated_at
                    FROM projects
                    JOIN users ON projects.owner_id = users.id
            """);
            if (resultSet.next()) {
                return mapResultSetToProject(resultSet);
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private Project mapResultSetToProject(ResultSet resultSet) throws java.sql.SQLException {
        String rawStatus = resultSet.getString("status");
        var status = (rawStatus != null) ? ProjectStatusEnum.valueOf(rawStatus) : null;

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
            resultSet.getTimestamp("created_at").toLocalDateTime(),
            resultSet.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
