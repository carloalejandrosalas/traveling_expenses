package main.java.forms.common;

import main.java.enums.UserInputTypeEnum;
import main.java.utils.Mapper;
import main.java.utils.ScreenUtils;
import main.java.utils.Validator;

import java.util.Scanner;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class FormField<T> {
    final private String name;
    final private UserInputTypeEnum type;
    final private String label;
    private T value = null;
    private T defaultValue = null;
    private Rule[] rules = new Rule[0];

    public FormField(String name, UserInputTypeEnum type, String label) {
        this.name = name;
        this.type = type;
        this.label = label;
    }

    public FormField(String name, UserInputTypeEnum type, String label,
                     Rule[] rules){
        this.name = name;
        this.type = type;
        this.label = label;
        this.rules = rules;
    }

    public FormField(String name, UserInputTypeEnum type, String label,
                     Rule[] rules, T defaultValue){
        this.name = name;
        this.type = type;
        this.label = label;
        this.rules = rules;
        this.defaultValue = defaultValue;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void prompt(Scanner input, Form form) {
        var isSensitive = this.type == UserInputTypeEnum.PASSWORD;

        var label = this.label;

        // Append the default value to the label if it exists.
        if (defaultValue != null) {
            label += " (default: " + defaultValue + ")";
        }

        while (true) {
            String inputValue = ScreenUtils.promptUserInput(input, label, isSensitive);

            // In case the user just presses enter, we check if there's a default value to use.
            if (inputValue == null || inputValue.isEmpty()) {
                if (defaultValue != null) {
                    setValue(defaultValue);
                    return;
                }
            }

            // If the user provided an input, we validate it and parse it to the appropriate type.
            var isValid = validate(inputValue, form);

            if (!isValid) {
                continue;
            }

            T parsedValue = parseValue(inputValue);

            setValue(parsedValue);
            return;
        }
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
            case STRING, PASSWORD -> inputValue; // Keep original value for STRING and PASSWORD types.
            case CURRENCY -> Mapper.mapFromCurrencyToBigDecimal(inputValue);
            case LONG -> Mapper.mapToLong(inputValue);
        };
    }

    private boolean validate (String value, Form form) {
        for (Rule rule : rules) {
            if (!rule.validate(value, form)) {
                ScreenUtils.printAlert(rule.getErrorMessage(this.label));
                return false;
            }
        }
        return true;
    }

    /**
     * Useful if you need to override the default value of a field after the form has been created.
     * For example, if you want to set a default value for a field based on some other input
     * or condition.
     */
    public void setDefaultValue(T value) {
        this.value = value;
    }

    /**
     * Resets to defaults the field's value. Otherwise, sets the value to null if no default is set.
     */
    public void reset () {
        if (defaultValue != null) {
            this.value = defaultValue;
        } else {
            this.value = null;
        }
    }

    public String getName() {
        return name;
    }
}
