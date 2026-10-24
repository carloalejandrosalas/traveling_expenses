package main.java.screens.projects;

import main.java.FormField;
import main.java.enums.UserInputTypeEnum;
import main.java.screens.HomeScreen;
import main.java.screens.Screen;
import main.java.utils.ScreenUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Scanner;

public class NewProjectScreen extends Screen {
    public NewProjectScreen(Scanner input) {
        super(input);
    }

    @Override
    public void render() {
        ScreenUtils.printUI("Please enter the details for the new project:");
        var nameField = new FormField<String>(UserInputTypeEnum.STRING, "Project Name: ",
                "Please enter a valid project name.");
        var descriptionField = new FormField<String>(UserInputTypeEnum.STRING, "Project " +
                "Description: ",
                "Please enter a valid project description.");
        var budgetField = new FormField<BigDecimal>(UserInputTypeEnum.CURRENCY, "Project Budget: ",
                "Please enter a valid budget.");
        var startDateField = new FormField<LocalDate>(UserInputTypeEnum.DATE,
                "Project Start Date (YYYY-MM-DD): ",
                "Please enter a valid start date.");
        var endDateField = new FormField<LocalDate>(UserInputTypeEnum.DATE,
                "Project End Date (YYYY-MM-DD): ",
                "Please enter a valid end date.");
        var statusField = new FormField<String>(UserInputTypeEnum.STRING,
                "Project Status (e.g., Planned, In Progress, Completed): ",
                "Please enter a valid project status.");

        // Prompt the user for each field and validate the input
        nameField.prompt(this.input);
        descriptionField.prompt(this.input);
        budgetField.prompt(this.input);

        startDateField.prompt(this.input);
        endDateField.prompt(this.input, (endDate) -> endDate.isAfter(startDateField.getValue()),
                "End date must be after the start date.");
        statusField.prompt(this.input);

        ScreenUtils.printSuccess("New project created successfully!");

        new HomeScreen(this.input).render();
    }

    @Override
    public void renderSelectedOption(Integer option) {
        // Nothing to do here.
    }
}
