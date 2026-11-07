package main.java.screens.projects;

import main.java.AppContext;
import main.java.forms.common.Form;
import main.java.forms.common.FormField;
import main.java.daos.ProjectDao;
import main.java.dtos.projects.CreateProjectRequest;
import main.java.enums.ProjectStatusEnum;
import main.java.enums.UserInputTypeEnum;
import main.java.forms.projects.UpsertProjectForm;
import main.java.screens.AuthenticatedScreen;
import main.java.screens.HomeScreen;
import main.java.utils.ProjectUtils;
import main.java.utils.ScreenUtils;
import main.java.utils.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;

public class NewProjectScreen extends AuthenticatedScreen {
    private final ProjectDao projectDao;
    private Long latestProjectId;

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
        ScreenUtils.printMenuItem(1, "Enroll a new project");
        ScreenUtils.printMenuItem(2, "View Project details");
        ScreenUtils.printMenuItem(3, "New team");
        ScreenUtils.printMenuItem(4, "Add team member");
        ScreenUtils.printMenuItem(5, "Return to the home screen");

        var selectedOption = ScreenUtils.promptOption(this.context.getScanner(), 5);

        if (selectedOption == null) {
            renderOptionMenu();
            return;
        }

        renderSelectedOption(selectedOption);
    }

    public void renderForm () {
        try {
            ScreenUtils.printHeader("NEW PROJECT");
            showMenuTitle("Please fill in the following details to create a new project:");

            var projectForm = new UpsertProjectForm(this.context, null);

            projectForm.renderForm();

            var nameField = projectForm.nameField;
            var descriptionField = projectForm.descriptionField;
            var budgetField = projectForm.budgetField;
            var startDateField = projectForm.startDateField;
            var endDateField = projectForm.endDateField;
            var statusField = projectForm.statusField;

            // Create a new project using the collected data
            var newProjectRequest = new CreateProjectRequest(
                    this.context.getCurrentUser().id(),
                    nameField.getValue(),
                    descriptionField.getValue(),
                    budgetField.getValue(),
                    startDateField.getValue(),
                    endDateField.getValue(),
                    ProjectUtils.mapToProjectStatusEnum((String) statusField.getValue())
            );
            Long enrolledProjectId = this.projectDao.createProject(newProjectRequest);

            if (enrolledProjectId == null) {
                ScreenUtils.printError("Failed to create the new project.");
                return;
            }

            this.latestProjectId = enrolledProjectId;

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
            case 2 -> new ProjectDetailsScreen(this.context, this.latestProjectId).render();
            case 5 -> new HomeScreen(this.context).render();
            default -> {
                ScreenUtils.printAlert("Invalid option selected. Please try again.");
                renderOptionMenu();
            }
        }
    }
}
