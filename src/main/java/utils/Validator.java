package main.java.utils;

import main.java.enums.UserInputTypeEnum;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;

public class Validator {
    public static boolean isBigDecimal(String input) {
        try {
            new BigDecimal(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isYesNo(String input) {
        if (input == null) {
            return false;
        }
        String normalizedInput = input.trim().toLowerCase();
        if (normalizedInput.equals("yes")) {
            return true;
        } else if (normalizedInput.equals("no")) {
            return false;
        } else {
            return false; // Return null if the input is neither "yes" nor "no"
        }
    }

    public static boolean isDate(String input) {
        try {
            LocalDate.parse(input);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    public static boolean isCurrency(String input) {
        // Validate currency format (e.g., $123.45 or 123.45)
        return input.matches("\\$?\\d+(\\.\\d{2})?");
    }

    public static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
