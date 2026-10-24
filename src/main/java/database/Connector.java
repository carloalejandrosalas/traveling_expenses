package main.java.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connector {
    private Connection connection = null;
    private final String URL = "jdbc:mysql://localhost:3306/traveling_expenses_db"
            + "?sslMode=REQUIRED&serverTimezone=UTC&characterEncoding=utf8";
    private final String USERNAME = "db_user";
    private final String PASSWORD = "db_password";
    private boolean doesConnectionFail;

    public Connector () {
        try {
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            this.doesConnectionFail = true;
            e.printStackTrace();
        }
    }

    public Connection getConnection () {
        return this.connection;
    }

    public void closeConnection () {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasConnectionFailed() {
        return this.doesConnectionFail;
    }
}
