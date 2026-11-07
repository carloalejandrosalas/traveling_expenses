package main.java.utils;

import main.java.enums.UserInputTypeEnum;

import java.io.Console;
import java.util.Scanner;

public class ScreenUtils {
    public static Integer promptOption(Scanner scanner, int maxOption) {
        IO.print("\n➤ Select an option (1-" + maxOption + "): ");
        String input = scanner.nextLine();

        var option = Mapper.mapToInteger(input);

        if (option == null || option < 1 || option > maxOption) {
            printAlert("Invalid option. Please enter a number between 1 and " + maxOption + ".");
            return null;
        }

        return option;
    }

    public static String promptUserInput(Scanner scanner, String label) {
        return promptUserInput(scanner, label, false);
    }

    public static String promptUserInput(Scanner scanner, String label,
                                         boolean sensitive) {
        var input = "";
        if (sensitive) {
            Console console = System.console();

            if (console == null) {
                ScreenUtils.printAlert("No console available for IDE environments. Input will be visible.");
                IO.print("➤ " + label.trim() + " ");
                input = scanner.nextLine();
            } else {
                input = new String(console.readPassword("➤ " + label.trim() + " (hidden): "));
            }

        } else {
            IO.print("➤ " + label.trim() + " ");
            input = scanner.nextLine();
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

    public static void printHeader(String title) {
        var line = "═".repeat(title.length() + 4);
        IO.println("\n╔" + line + "╗");
        IO.println("║  " + title + "  ║");
        IO.println("╚" + line + "╝");
    }

    public static void printSubtitle(String subtitle) {
        IO.println("\n" + subtitle);
        IO.println("─".repeat(subtitle.length()));
    }

    public static void printMenuItem(int number, String label) {
        IO.println("  [" + number + "] " + label);
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
        IO.println("─".repeat(length + 4));
    }

    protected static void cleanScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
