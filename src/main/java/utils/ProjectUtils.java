package main.java.utils;

import main.java.AppContext;
import main.java.enums.ProjectStatusEnum;
import main.java.screens.projects.ProjectDetailsScreen;

public class ProjectUtils {
    public static Long promptForProjectId(AppContext context) {
        var selectedProjectId = ScreenUtils.promptUserInput(context.getScanner(), "Enter the " +
                "project ID: ");

        if (Validator.isLong(selectedProjectId)) {
          return Mapper.mapToLong(selectedProjectId);
        }

        ScreenUtils.printAlert("Invalid project ID. Please enter a valid numeric value.");
        return promptForProjectId(context);
    }

    public static void redirectToProjectDetailsScreen(AppContext context) {
        var selectedProjectId = ProjectUtils.promptForProjectId(context);
        new ProjectDetailsScreen(context, selectedProjectId).render();
    }

    public static ProjectStatusEnum mapToProjectStatusEnum(String status) {
        if (status == null || status.isEmpty()) {
            return null;
        }

        String formattedStatus = status.trim().replaceAll("[\\s-]+", "_").toUpperCase();

        try {
            return ProjectStatusEnum.valueOf(formattedStatus);
        } catch (IllegalArgumentException e) {
            return null; // Return null if the status does not match any enum value
        }
    }
}
