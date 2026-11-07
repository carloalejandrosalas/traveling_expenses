package main.java.forms.common;

import main.java.utils.ScreenUtils;

import java.util.function.BiPredicate;

public class Rule {
    private final String name;
    private final BiPredicate<String, Form> validationRule;
    private final String errorMessage;

    public Rule(String name, BiPredicate<String, Form> validationRule,
                String errorMessage) {
        this.name = name;
        this.validationRule = validationRule;
        this.errorMessage = errorMessage;
    }

    public boolean validate(String value, Form form) {
       return validationRule.test(value, form);
    }

    public String getErrorMessage(String fieldName) {
        return String.format(errorMessage, fieldName);
    }

    public String getName() {
        return name;
    }
}
