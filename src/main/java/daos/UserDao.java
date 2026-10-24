package main.java.daos;

import main.java.dtos.users.CreateNewUser;
import main.java.dtos.users.User;
import main.java.enums.UsertTypeEnum;
import main.java.utils.Password;

import java.sql.Connection;
import java.sql.ResultSet;

public class UserDao {
    final Connection connection;

    public UserDao (Connection connection) {
        this.connection = connection;
    }

    public boolean createUser(CreateNewUser newUser) {
        try (var statement = connection.prepareStatement("""
                INSERT INTO users (identification_number, name, surname, phone_number, address, password_hash, type)
                VALUES (?, ?, ?, ?, ?, ?, ?)
            """)) {
            var passwordHash = Password.hashPassword(newUser.password());

            statement.setString(1, newUser.identificationNumber());
            statement.setString(2, newUser.name());
            statement.setString(3, newUser.surname());
            statement.setString(4, newUser.phoneNumber());
            statement.setString(5, newUser.address());
            statement.setString(6, passwordHash);
            statement.setString(7, newUser.type().name());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User findUserByIdentificationNumber(String identificationNumber) {
        try (var statement = connection.prepareStatement("""
                SELECT
                    id,
                    identification_number,
                    name,
                    surname,
                    phone_number,
                    address,
                    type,
                    password_hash,
                    created_at,
                    updated_at,
                    last_login_at
                FROM users
                WHERE identification_number = ?
            """)) {
            statement.setString(1, identificationNumber);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private User mapResultSetToUser (ResultSet resultSet) throws java.sql.SQLException {
        return new User(
            resultSet.getLong("id"),
            resultSet.getString("identification_number"),
            resultSet.getString("name"),
            resultSet.getString("surname"),
            resultSet.getString("phone_number"),
            resultSet.getString("address"),
            resultSet.getString("password_hash"),
            UsertTypeEnum.valueOf(resultSet.getString("type")),
            resultSet.getTimestamp("created_at").toLocalDateTime(),
            resultSet.getString("updated_at") != null ? resultSet.getTimestamp("updated_at").toLocalDateTime() : null,
            resultSet.getString("last_login_at") != null ? resultSet.getTimestamp("last_login_at").toLocalDateTime() : null
        );
    }
}
