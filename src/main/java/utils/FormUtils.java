package main.java.utils;

import main.java.forms.common.Rule;

public class FormUtils {
    public static Rule requiredRule = new Rule("required", (value, _) -> {
        if (value == null) {
            return false;
        }

        return !value.trim().isEmpty();
    }, "The field %s is required.");

    public static Rule isCurrencyRule = new Rule("isCurrency", (value, _) -> {
        return Validator.isCurrency(value);
    }, "The field %s must be a valid currency format (e.g., $123.45 or 123.45).");

    public static Rule isYesNoRule = new Rule("isYesNo", (value, _) -> {
        return Validator.isYesNo(value);
    }, "The field %s must be either 'yes' or 'no'.");

    public static Rule isNumericRule = new Rule("isNumeric", (value, _) -> {
        return Validator.isNumeric(value);
    }, "The field %s must be a valid number.");

    public static Rule isIntegerRule = new Rule("isInteger", (value, _) -> {
        return Validator.isInteger(value);
    }, "The field %s must be a valid integer.");

    public static Rule isIdRule = new Rule("isId", (value, _) -> {
        return Validator.isLong(value);
    }, "The field %s must be a valid positive integer.");
}
