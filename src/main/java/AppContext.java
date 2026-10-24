package main.java;

import main.java.database.Connector;
import main.java.dtos.users.AuthUser;
import main.java.utils.Session;

import java.sql.Connection;
import java.util.Scanner;

public class AppContext {
    private final Scanner scanner;
    private final Connector connector;
    private Session session;

    public AppContext() {
        this.scanner = new Scanner(System.in);
        this.connector = new Connector();
    }

    public Scanner getScanner() {
        return scanner;
    }

    public Connection getConnection() {
        return connector.getConnection();
    }

    public boolean isOk() {
        return !connector.hasConnectionFailed();
    }

    public void close() {
        scanner.close();
        connector.closeConnection();
    }

    public Session getSession() {
        return this.session;
    }

    public void newSession(AuthUser authUser) {
        this.session = new Session(authUser);
    }

    public boolean isAuthenticated() {
        return this.session != null;
    }

    public void endSession() {
        this.session = null;
    }

    public AuthUser getCurrentUser() {
        if (this.session == null) {
            return null;
        }
        return this.session.getAuthUser();
    }
}
