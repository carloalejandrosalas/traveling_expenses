package main.java.screens.projects;

import main.java.screens.Screen;
import main.java.utils.ScreenUtils;

public class ProjectIndexScreen extends Screen {
    public ProjectIndexScreen(main.java.AppContext context) {
        super(context);
    }

    @Override
    public void render() {
        // Implementation for rendering the project index screen
        showMenuTitle();
        ScreenUtils.printUI("1. Create a new project");
        ScreenUtils.printUI("2. View existing projects");
        ScreenUtils.printUI("3. Update project details");
        ScreenUtils.printUI("4. Delete a project");
        ScreenUtils.printUI("5. Return to main menu");

        var option = ScreenUtils.promptOption(this.context.getScanner(), 5);

        if (option == null) {
            reRenderScreen();
            return;
        }

        renderSelectedOption(option);
    }

    @Override
    public void renderSelectedOption(Integer option) {
        switch (option) {
            case 1 ->
                new NewProjectScreen(this.context).render();

            default -> {
                ScreenUtils.printWarning("Invalid option selected.");
                reRenderScreen();
            }
        }
    }
}
