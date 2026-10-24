package main.java.utils;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public class Mapper {
    static public Integer mapToInteger(String input) {
        return Validator.isInteger(input) ? Integer.parseInt(input) : null;
    }

    static public Double mapToDouble(String input) {
        return Validator.isDouble(input) ? Double.parseDouble(input) : null;
    }

    static public BigDecimal mapToBigDecimal(String input) {
        return Validator.isBigDecimal(input) ? new BigDecimal(input) : null;
    }

    static public Boolean mapYesNoToBoolean(String input) {
        return Validator.isYesNo(input) ? (input.trim().equalsIgnoreCase("yes")) : null;
    }

    static public LocalDate mapToDate(String input) {
        return Validator.isDate(input) ? LocalDate.parse(input) : null;
    }

    static public BigDecimal mapFromCurrencyToBigDecimal(String input) {
        var isValid = Validator.isCurrency(input);

        if (!isValid) {
            return null;
        }

        var cleanedInput = input.replaceAll("[$,]", ""); // Remove $ and commas
        return Validator.isBigDecimal(cleanedInput) ? new BigDecimal(cleanedInput): null;
    }
}
