package main.java.screens;

import main.java.AppContext;

public abstract class AuthenticatedScreen extends Screen {
    public AuthenticatedScreen(AppContext context) {
        if (!context.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated.");
        }

        super(context);
    }
}
