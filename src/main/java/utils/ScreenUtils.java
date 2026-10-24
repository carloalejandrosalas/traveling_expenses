package main.java.utils;

import main.java.enums.UserInputTypeEnum;

import java.io.Console;
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
        return promptUserInput(scanner, label, errorMessage, false);
    }

    public static String promptUserInput(Scanner scanner, String label, String errorMessage,
                                         boolean sensitive) {
        var input = "";
        if (sensitive) {
            Console console = System.console();

            if (console == null) {
                ScreenUtils.printAlert("No console available for IDE environments. Input will be visible.");
                printUI(label);
                input = scanner.nextLine();
            } else {
                input = new String(console.readPassword(label + " (input will be hidden):"));
            }

        } else {
            printUI(label);
            input = scanner.nextLine();
        }

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
        separator(message.length());
        IO.println("✅ " + message);
        separator(message.length());
    }

    public static void printWarning(String message) {
        separator(message.length());
        IO.println("⚠️ " + message);
        separator(message.length());
    }

    public static void printAlert(String message) {
        separator(message.length());
        IO.println("⚠️ " + message);
        separator(message.length());
    }

    public static void printError(String message) {
        separator(message.length());
        IO.println("❌ " + message);
        separator(message.length());
    }

    public static void separator() {
        separator(50);
    }
    public static void separator(int length) {
        IO.println("-".repeat(length + 4));
    }
}
