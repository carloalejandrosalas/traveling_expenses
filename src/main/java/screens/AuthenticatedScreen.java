package main.java.screens;

import main.java.AppContext;

public class AuthenticatedScreen extends Screen {
    public AuthenticatedScreen(AppContext context) {
        if (!context.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated.");
        }

        super(context);
    }

    /**
     * Authenticated screens should implement their own render logic, this method is just as a
     * placeholder.
     */
    @Override
    public void render() {}

    @Override
    public void renderSelectedOption(Integer option) {}
}
