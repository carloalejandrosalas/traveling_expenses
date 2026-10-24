package main.java;

import main.java.screens.HomeScreen;
import main.java.screens.Screen;
import main.java.screens.projects.LoginScreen;
import main.java.utils.ScreenUtils;


public class Main {
    static void main() {
        var appContext = new AppContext();

        // Inject the default admin user into the database if it doesn't exist.
        Bootstrap.seedDefaultAdminUser(appContext);

        if (!appContext.isOk()) {
            System.out.println("Failed to startup the application. Please check DB connection " +
                    "and credentials.");
            System.exit(1);
        }

        showWelcomeMessage();

        new LoginScreen(appContext).render();

        appContext.close();
    }

    static void showWelcomeMessage () {
        ScreenUtils.printUI("*********************************************");
        ScreenUtils.printUI("* Welcome to the Project Management System! *");
        ScreenUtils.printUI("*********************************************");
    }
}
