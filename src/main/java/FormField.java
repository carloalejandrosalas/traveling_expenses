package main.java;

import main.java.enums.UserInputTypeEnum;
import main.java.utils.Mapper;
import main.java.utils.ScreenUtils;
import main.java.utils.Validator;

import java.util.Scanner;
import java.util.function.Predicate;

public class FormField<T> {
    final private UserInputTypeEnum type;
    final private String label;
    final private String errorMessage;
    private T value = null;

    public FormField(UserInputTypeEnum type, String label, String errorMessage) {
        this.type = type;
        this.label = label;
        this.errorMessage = errorMessage;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void prompt(Scanner input) {
        String inputValue = ScreenUtils.promptUserInput(input, label, errorMessage);
        var isValid = validate(inputValue);

        if (inputValue == null || !isValid) {
            // Prompt the user again if the input is invalid
            prompt(input);
        }

        // Otherwise, set the value if the input is valid
        setValue(parseValue(inputValue));
    }

    public void prompt(Scanner input, Predicate<T> validationPredicate, String customErrorMessage) {
        String inputValue = ScreenUtils.promptUserInput(input, label, errorMessage);
        var isValid = validate(inputValue);

        if (inputValue == null || !isValid) {
            // Prompt the user again if the input is invalid
            prompt(input, validationPredicate, customErrorMessage);
        }

        // Regular check passes, now check the custom validation predicate.
        if (!validationPredicate.test(parseValue(inputValue))) {
            ScreenUtils.printAlert(customErrorMessage);
            prompt(input, validationPredicate, customErrorMessage);
        }

        // Otherwise, set the value if the input is valid
        setValue(parseValue(inputValue));
    }

    public boolean validate (String value) {
        if (value == null) {
            ScreenUtils.printAlert(errorMessage);
            return false;
        }

        boolean isValid = switch (type) {
            case INTEGER -> Validator.isInteger(value);
            case DOUBLE -> Validator.isDouble(value);
            case BIG_DECIMAL -> Validator.isBigDecimal(value);
            case YES_NO -> Validator.isYesNo(value);
            case DATE -> Validator.isDate(value);
            case STRING -> true; // No validation needed for strings
            case CURRENCY -> Validator.isCurrency(value);
        };

        if (!isValid) {
            ScreenUtils.printAlert(errorMessage);
        }

        return isValid;
    }

    public T parseValue(String inputValue) {
        if (inputValue == null) {
            return null;
        }

        return (T) switch (type) {
            case INTEGER -> Mapper.mapToInteger(inputValue);
            case DOUBLE -> Mapper.mapToDouble(inputValue);
            case BIG_DECIMAL -> Mapper.mapToBigDecimal(inputValue);
            case YES_NO -> Mapper.mapYesNoToBoolean(inputValue);
            case DATE -> Mapper.mapToDate(inputValue);
            case STRING -> inputValue; // Keep original value for STRING type.
            case CURRENCY -> Mapper.mapFromCurrencyToBigDecimal(inputValue);
        };
    }
}
