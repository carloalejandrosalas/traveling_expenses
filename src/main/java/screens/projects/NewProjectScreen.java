package main.java.screens.projects;

import main.java.AppContext;
import main.java.FormField;
import main.java.daos.ProjectDao;
import main.java.dtos.UpsertProjectRequest;
import main.java.enums.ProjectStatusEnum;
import main.java.enums.UserInputTypeEnum;
import main.java.screens.HomeScreen;
import main.java.screens.Screen;
import main.java.utils.ScreenUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class NewProjectScreen extends Screen {
    private final ProjectDao projectDao;

    public NewProjectScreen(AppContext context) {
        super(context);
        this.projectDao = new ProjectDao(this.context.getConnection());
    }

    @Override
    public void render() {
        // First, let's render the form to collect project details from the user.
        renderForm();

        // After the form is submitted, we ask the user if they want to enroll a new project
        // or return to the home screen.
        renderOptionMenu();
    }

    public void renderOptionMenu () {
        showMenuTitle("Would you like to:");
        ScreenUtils.printUI("1. Enroll a new project");
        ScreenUtils.printUI("2. Return to the home screen");

        var selectedOption = ScreenUtils.promptOption(this.context.getScanner(), 2);

        if (selectedOption == null) {
            renderOptionMenu();
            return;
        }

        renderSelectedOption(selectedOption);
    }

    public void renderForm () {
        try {
            showMenuTitle("Please fill in the following details to create a new project:");

            var scanner = this.context.getScanner();
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
            nameField.prompt(scanner);
            descriptionField.prompt(scanner);
            budgetField.prompt(scanner);

            startDateField.prompt(scanner);
            endDateField.prompt(scanner, (endDate) -> endDate.isAfter(startDateField.getValue()),
                    "End date must be after the start date.");
            statusField.prompt(scanner);

            // Create a new project using the collected data
            var newProjectRequest = new UpsertProjectRequest(
                    this.context.getCurrentUser().id(),
                    nameField.getValue(),
                    descriptionField.getValue(),
                    budgetField.getValue(),
                    startDateField.getValue(),
                    endDateField.getValue(),
                    ProjectStatusEnum.valueOf(statusField.getValue())
            );
            var enrolled = this.projectDao.createProject(newProjectRequest);

            if (!enrolled) {
                ScreenUtils.printError("Failed to create the new project.");
                return;
            }

            ScreenUtils.printSuccess("New project \"%s\" created successfully!"
                    .formatted(nameField.getValue()));
        } catch (Exception e) {
            ScreenUtils.printError("An error occurred while creating the project: " + e.getMessage());
        }
    }

    @Override
    public void renderSelectedOption(Integer option) {
        switch (option) {
            case 1 -> render();
            case 2 -> new HomeScreen(this.context).render();
            default -> {
                ScreenUtils.printAlert("Invalid option selected. Please try again.");
                renderOptionMenu();
            }
        }
    }
}
