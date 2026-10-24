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
        showMenuTitle();
        ScreenUtils.printUI("1. Projects");
        ScreenUtils.printUI("2. Users");
        ScreenUtils.printUI("3. Teams");
        ScreenUtils.printUI("4. Expenses");
        ScreenUtils.printUI("5. Categories");
        ScreenUtils.printUI("6. Exit");

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
            default -> {
                ScreenUtils.printAlert("Invalid option selected. Please try again.");
                reRenderScreen();
            }
        }
    }
}
