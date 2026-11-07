package main.java.screens.projects;

import main.java.screens.AuthenticatedScreen;
import main.java.screens.HomeScreen;
import main.java.utils.ProjectUtils;
import main.java.utils.ScreenUtils;

public class ProjectIndexScreen extends AuthenticatedScreen {
    public ProjectIndexScreen(main.java.AppContext context) {
        super(context);
    }

    @Override
    public void render() {
        // Implementation for rendering the project index screen
        showMenuTitle();
        ScreenUtils.printHeader("Project Management System - Project Index");
        ScreenUtils.printSubtitle("Please select an option from the menu below:");
        ScreenUtils.printMenuItem(1, "Create a new project");
        ScreenUtils.printMenuItem(2, "View existing projects");
        ScreenUtils.printMenuItem(3, "Select Project by ID");
        ScreenUtils.printMenuItem(4, "Update project details");
        ScreenUtils.printMenuItem(5, "Delete a project");
        ScreenUtils.printMenuItem(6, "Return to main menu");
        var option = ScreenUtils.promptOption(this.context.getScanner(), 6);

        if (option == null) {
            reRenderScreen();
            return;
        }

        renderSelectedOption(option);
    }

    @Override
    public void renderSelectedOption(Integer option) {
        switch (option) {
            case 1 -> new NewProjectScreen(this.context).render();
            case 2 -> new ProjectsTableScreen(this.context).render();
            case 3 -> ProjectUtils.redirectToProjectDetailsScreen(this.context);
            case 6 -> new HomeScreen(this.context).render();
            default -> {
                ScreenUtils.printWarning("Invalid option selected.");
                reRenderScreen();
            }
        }
    }
}
