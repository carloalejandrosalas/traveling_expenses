package main.java.screens;

import main.java.AppContext;
import main.java.screens.projects.NewProjectScreen;
import main.java.screens.projects.ProjectIndexScreen;
import main.java.utils.ScreenUtils;

import java.util.Scanner;

public class HomeScreen extends AuthenticatedScreen {
    public HomeScreen(AppContext context) {
        super(context);
    }

    @Override
    public void render() {
        ScreenUtils.printHeader("TRAVELING EXPENSES - HOME");
        showMenuTitle();
        ScreenUtils.printMenuItem(1, "Projects");
        ScreenUtils.printMenuItem(2, "Users");
        ScreenUtils.printMenuItem(3, "Teams");
        ScreenUtils.printMenuItem(4, "Expenses");
        ScreenUtils.printMenuItem(5, "Categories");
        ScreenUtils.printMenuItem(6, "Exit");

        Integer selectedOption = ScreenUtils.promptOption(this.context.getScanner(), 6);

        if (selectedOption == null) {
            reRenderScreen();
            return;
        }

        renderSelectedOption(selectedOption);
    }

    public void renderSelectedOption(Integer option) {
        switch (option) {
            case 1 ->
                new ProjectIndexScreen(this.context).render();
            case 6 -> {
                ScreenUtils.printUI("\n👋 Exiting the application. Goodbye!");
                System.exit(0);
            }
            default -> {
                ScreenUtils.printAlert("Invalid option selected. Please try again.");
                reRenderScreen();
            }
        }
    }
}
