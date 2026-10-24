package main.java.screens;

import main.java.AppContext;
import main.java.utils.ScreenUtils;


public abstract class Screen {
    public final AppContext context;
    public Screen(AppContext context) {
        this.context = context;
    }

    /**
     * Renders the screen to the user interface. This method should be implemented by
     * subclasses to define the specific rendering logic for each screen.
     */
    public abstract void render ();

    /**
     * Handles the logic for the selected option. This method should be implemented by
     * subclasses to define the specific behavior for each option.
     */
    public abstract void renderSelectedOption (Integer option);


    public boolean isOptionValid(String input, int maxOption) {
        if (input == null) {
            ScreenUtils.printUI("Invalid option. Please try again.");
            return false;
        }

        try {
            var option = Integer.parseInt(input);

            var isValid = option >= 1 && option <= maxOption;
            if (!isValid) {
                ScreenUtils.printUI("Invalid option. Please enter a number between 1 and " + maxOption + ".");
            }

            return isValid;
        } catch (NumberFormatException e) {
            ScreenUtils.printUI("Invalid input. Please enter a valid number.");
            return false;
        } catch (Exception e) {
            ScreenUtils.printUI("An unexpected error occurred: " + e.getMessage());
            return false;
        }
    }

    /**
     * Re-renders the current screen. This method can be called to refresh the screen
     * and display the updated content.
     */
    public void reRenderScreen() {
        render();
    }

    /**
     * Displays the menu title to the screen.
     */
    public void showMenuTitle () {
        showMenuTitle("Please select an option from the menu below:");
    }

    /**
     * Displays the menu title to the screen with a custom title with the provided title.
     * @param title The custom title to be displayed on the screen.
     */
    public void showMenuTitle (String title) {
        ScreenUtils.separator(title.length());
        ScreenUtils.printUI(title);
        ScreenUtils.separator(title.length());
    }
}
