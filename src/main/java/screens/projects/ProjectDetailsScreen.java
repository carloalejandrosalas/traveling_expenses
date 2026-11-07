package main.java.screens.projects;

import main.java.AppContext;
import main.java.daos.ProjectDao;
import main.java.dtos.projects.Project;
import main.java.dtos.projects.UpdateProjectRequest;
import main.java.enums.ProjectStatusEnum;
import main.java.forms.projects.UpsertProjectForm;
import main.java.screens.AuthenticatedScreen;
import main.java.screens.HomeScreen;
import main.java.screens.Screen;
import main.java.utils.Mapper;
import main.java.utils.ScreenUtils;

public class ProjectDetailsScreen extends AuthenticatedScreen {
    private final ProjectDao projectDao;
    private final Long projectId;
    private Project project;
    public ProjectDetailsScreen(AppContext context, Long projectId) {
        super(context);
        this.projectDao = new ProjectDao(context.getConnection());
        this.projectId = projectId;
    }

    @Override
    public void render() {
        this.project = projectDao.getProjectById(this.projectId);

        if (this.project == null) {
            ScreenUtils.printError("Project not found. Redirecting to the home screen...");
            new HomeScreen(this.context).render();
            return;
        }

        ScreenUtils.printHeader("PROJECT DETAILS");
        ScreenUtils.printSubtitle("Here are the details of \"%s\" project".
                formatted(project.name()));
        ScreenUtils.printUI("ID: %d".formatted(project.id()));
        ScreenUtils.printUI("Name: %s".formatted(project.name()));
        ScreenUtils.printUI("Description: %s".formatted(project.description()));
        ScreenUtils.printUI("Status: %s".formatted(
                Mapper.mapProjectStatusToDisplayName(project.status())));
        ScreenUtils.printUI("Budget: $%,.2f".formatted(project.budget()));
        ScreenUtils.printUI("Start Date: %s".formatted(
                Mapper.mapLocalDateToLocaleFormat(project.startDate(), "-")));
        ScreenUtils.printUI("End Date: %s".formatted(
                Mapper.mapLocalDateToLocaleFormat(project.endDate(), "-")));
        ScreenUtils.printUI("Owner: %s".formatted(project.ownerName()));
        ScreenUtils.printUI("Created At: %s".formatted(
                Mapper.mapLocalDateTimeToLocaleFormat(project.createdAt(), "-")));
        ScreenUtils.printUI("Updated At: %s".formatted(
                Mapper.mapLocalDateTimeToLocaleFormat(project.updatedAt(), "-")));
        ScreenUtils.printUI("--------------------------------------------------");
        ScreenUtils.printMenuItem(1, "Return to Project Index");
        ScreenUtils.printMenuItem(2, "Return to Home Screen");
        ScreenUtils.printMenuItem(3, "Edit Project");
        ScreenUtils.printMenuItem(4, "Change Project Status");

        var selectedOption = ScreenUtils.promptOption(this.context.getScanner(), 4);

        if (selectedOption == null) {
            render();
            return;
        }

        renderSelectedOption(selectedOption);
    }

    public void renderSelectedOption(Integer option) {
        switch (option) {
            case 1 -> new ProjectIndexScreen(this.context).render();
            case 2 -> new HomeScreen(this.context).render();
            case 3 -> updateProject();
            case 4 -> changeStatus();
            default -> {
                ScreenUtils.printAlert("Invalid option selected. Please try again.");
                render();
            }
        }
    }


    public void changeStatus () {
        ScreenUtils.printHeader("CHANGE PROJECT STATUS");
        ScreenUtils.printSubtitle("Select the new status for the project \"%s\"".
                formatted(projectDao.getProjectById(this.projectId).name()));
        ScreenUtils.printMenuItem(1, "Planned");
        ScreenUtils.printMenuItem(2, "In Progress");
        ScreenUtils.printMenuItem(3, "On Hold");
        ScreenUtils.printMenuItem(4, "Completed");
        ScreenUtils.printMenuItem(5, "Canceled");
        var selectedOption = ScreenUtils.promptOption(this.context.getScanner(), 5);

        if (selectedOption == null) {
            changeStatus();
            return;
        }

        var newStatus = switch (selectedOption) {
            case 1 -> ProjectStatusEnum.PLANNED;
            case 2 -> ProjectStatusEnum.IN_PROGRESS;
            case 3 -> ProjectStatusEnum.ON_HOLD;
            case 4 -> ProjectStatusEnum.COMPLETED;
            case 5 -> ProjectStatusEnum.CANCELED;
            default -> null;
        };

        var success = projectDao.updateProjectStatus(this.projectId, newStatus);
        if (success) {
            ScreenUtils.printSuccess("Project status updated successfully.");
        } else {
            ScreenUtils.printAlert("Failed to update project status.");
        }

        render();
    }

    public void updateProject() {
        var projectForm = new UpsertProjectForm(this.context, this.project);

        ScreenUtils.printHeader("UPDATE PROJECT");
        ScreenUtils.printSubtitle("Update the details of the project \"%s\"".formatted(project.name()));

        projectForm.renderForm();

        var nameField = projectForm.nameField;
        var descriptionField = projectForm.descriptionField;
        var budgetField = projectForm.budgetField;
        var startDateField = projectForm.startDateField;
        var endDateField = projectForm.endDateField;

        var updateOperation = projectDao.updateProject(
            new UpdateProjectRequest(
                    this.projectId,
                    nameField.getValue(),
                    descriptionField.getValue(),
                    budgetField.getValue(),
                    startDateField.getValue(),
                    endDateField.getValue()
            )
        );

        if (!updateOperation) {
            ScreenUtils.printAlert("Failed to update project. Please try again.");
        } else {
            ScreenUtils.printSuccess("Project updated successfully.");
        }

        render();
    }
}
