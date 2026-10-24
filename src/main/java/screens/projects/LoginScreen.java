package main.java.screens.projects;

import main.java.FormField;
import main.java.daos.UserDao;
import main.java.dtos.users.AuthUser;
import main.java.enums.UserInputTypeEnum;
import main.java.screens.HomeScreen;
import main.java.screens.Screen;
import main.java.utils.Password;
import main.java.utils.ScreenUtils;


public class LoginScreen extends Screen {
    private final UserDao userDao;

    public LoginScreen(main.java.AppContext context) {
        super(context);
        this.userDao = new UserDao(context.getConnection());
    }

    @Override
    public void render() {
        var scanner = this.context.getScanner();

        // Implementation for rendering the login screen
        ScreenUtils.printUI("Please log in to your account");

        var userIdentificationField = new FormField<String>(UserInputTypeEnum.STRING, "Identification " +
                "Number:",
                "Please enter a valid identification number.");
        var passwordField = new FormField<String>(UserInputTypeEnum.PASSWORD, "Password:",
                "Please enter a valid password.");

        userIdentificationField.prompt(scanner);
        passwordField.prompt(scanner);

        var user = userDao.findUserByIdentificationNumber(userIdentificationField.getValue());
        var UNAUTHORIZED_MESSAGE = "Identification number or password is incorrect. " +
                "Please try again.";

        // Validate if the user exists and if the password is correct.
        if (user == null) {
            ScreenUtils.printError(UNAUTHORIZED_MESSAGE);
            reRenderScreen();
            return;
        }

        var isPasswordCorrect = Password.check(passwordField.getValue(), user.passwordHash());

        if (!isPasswordCorrect) {
            ScreenUtils.printError(UNAUTHORIZED_MESSAGE);
            reRenderScreen();
            return;
        }

        // If the user is authenticated, we create a new session and redirect to the home screen.
        this.context.newSession(
                new AuthUser(
                        user.id(),
                        user.identificationNumber(),
                        user.name(),
                        user.surname(),
                        user.phoneNumber(),
                        user.address(),
                        user.type()
                )
        );

        ScreenUtils.printSuccess("Logged in successfully! Welcome %s".formatted(user.fullName()));

        new HomeScreen(context).render();
    }

    @Override
    public void renderSelectedOption(Integer option) {
        // Handle selected option for the login screen if needed
    }
}
