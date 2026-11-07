package main.java.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import main.java.enums.ProjectStatusEnum;

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

    static public Long mapToLong(String input) {
        return Validator.isLong(input) ? Long.parseLong(input) : null;
    }

    static public BigDecimal mapFromCurrencyToBigDecimal(String input) {
        var isValid = Validator.isCurrency(input);

        if (!isValid) {
            return null;
        }

        var cleanedInput = input.replaceAll("[$,]", ""); // Remove $ and commas
        return Validator.isBigDecimal(cleanedInput) ? new BigDecimal(cleanedInput): null;
    }

    static public LocalDateTime mapToLocalDateTime(String input) {
        try {
            return LocalDateTime.parse(input);
        } catch (Exception e) {
            return null;
        }
    }

    static public String mapLocalDateTimeToLocaleFormat(LocalDateTime input, String defaultValue) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

       return input != null ? input.format(formatter) : defaultValue;
    }

    static public String mapLocalDateToLocaleFormat(LocalDate input, String defaultValue) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

       return input != null ? input.format(formatter) : defaultValue;
    }

    static public String mapProjectStatusToDisplayName(ProjectStatusEnum status) {
        if (status == null) {
            return "-";
        }

        var displayName = status.getStatus().replace('_', ' ').toLowerCase();
        return Character.toUpperCase(displayName.charAt(0)) + displayName.substring(1);
    }
}
