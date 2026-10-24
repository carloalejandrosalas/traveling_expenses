package main.java.screens;

import main.java.screens.projects.NewProjectScreen;
import main.java.utils.ScreenUtils;

import java.util.Scanner;

public class HomeScreen extends Screen {
    public HomeScreen(Scanner input) {
        super(input);
    }

    @Override
    public void render() {
        ScreenUtils.printUI("Please select an option from the menu below:");
        ScreenUtils.printUI("1. New project");
        ScreenUtils.printUI("2. New employee");
        ScreenUtils.printUI("3. New assignment");
        ScreenUtils.printUI("4. New expense");
        ScreenUtils.printUI("5. Query existing projects");
        ScreenUtils.printUI("6. Query existing employees");
        ScreenUtils.printUI("7. Assign an employee to a project");
        ScreenUtils.printUI("8. Update project status");
        ScreenUtils.printUI("9. Exit");

        Integer selectedOption = ScreenUtils.promptOption(this.input, 9);

        if (selectedOption == null) {
            reRenderScreen();
            return;
        }

        renderSelectedOption(selectedOption);
    }

    public void renderSelectedOption(Integer option) {
        switch (option) {
            case 1 ->
                new NewProjectScreen(this.input).render();
            default -> {
                ScreenUtils.printAlert("Invalid option selected. Please try again.");
                reRenderScreen();
            }
        }
    }
}
