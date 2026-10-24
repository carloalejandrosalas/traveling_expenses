package main.java.utils;

import main.java.enums.UserInputTypeEnum;

import java.util.Scanner;

public class ScreenUtils {
    public static Integer promptOption(Scanner scanner, int maxOption) {
        printUI("Please select an option (1-" + maxOption + "):");
        String input = scanner.nextLine();

        var isValid = Validator.isInteger(input);

        if (!isValid) {
            printAlert("Invalid option. Please enter a number between 1 and " + maxOption + ".");
            return null;
        }

        return Mapper.mapToInteger(input);
    }

    public static String promptUserInput(Scanner scanner, String label, String errorMessage) {
        printUI(label);
        String input = scanner.nextLine();

        if (input == null || input.trim().isEmpty()) {
            printAlert(errorMessage);
            return null;
        }

        return input;
    }

    /**
     * Prints the given content to the user interface.
     * @param content The content to be printed to the user interface.
     */
    public static void printUI(String content) {
        IO.println(content);
    }

    public static void printSuccess(String message) {
        separator();
        IO.println("✅ " + message);
        separator();
    }

    public static void printWarning(String message) {
        separator();
        IO.println("✅ " + message);
        separator();
    }

    public static void printAlert(String message) {
        separator();
        IO.println("⚠️ " + message);
        separator();
    }

    public static void printError(String message) {
        separator();
        IO.println("❌ " + message);
        separator();
    }

    public static void separator() {
        IO.println("--------------------------------------------------");
    }
}
