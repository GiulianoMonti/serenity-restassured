package com.claro.automation.planmgmt.util;


import com.claro.automation.planmgmt.exception.DataBaseCountryNotFoundException;

import java.time.LocalDateTime;

public class VariableByCountry {

    private static final String COUNTRY = SerenityEnvironmentVariables.getDataBaseCountry();

    private VariableByCountry() {
        throw new IllegalStateException("Utility class");
    }

    public static String getCountryShortName() {
        return switch (COUNTRY) {
            case "ar" -> "ARG";
            case "uy" -> "URY";
            case "py" -> "PRY";
            default -> throw new DataBaseCountryNotFoundException("No short name found for the country: " + COUNTRY);
        };
    }

    public static String getServiceProviderGm() {
        return switch (COUNTRY) {
            case "ar" -> "PA00003298";
            case "py" -> "PA00002972";
            case "uy" -> "PA00003010";
            default ->
                    throw new DataBaseCountryNotFoundException("There are no options available for the country: " + COUNTRY);
        };
    }

    public static String getCountryEnterpriseName() {
        return switch (COUNTRY) {
            case "ar" -> "GMCLAROAR";
            case "uy" -> "GMCLAROUY";
            case "py" -> "GMTESTPY";
            default ->
                    throw new DataBaseCountryNotFoundException("No enterpriseId name found for the country: " + COUNTRY);
        };
    }

    public static LocalDateTime getLocalTime() {
        return switch (COUNTRY) {
            case "ar", "uy" -> LocalDateTime.now();
            case "py" -> LocalDateTime.now().minusHours(1);
            default ->
                    throw new DataBaseCountryNotFoundException("No local date time found for the country: " + COUNTRY);
        };
    }

    public static String getCountryFullName() {
        return switch (COUNTRY) {
            case "ar" -> "ARGENTINA";
            case "uy" -> "URUGUAY";
            case "py" -> "PARAGUAY";
            default -> throw new DataBaseCountryNotFoundException("No full name found for the country: " + COUNTRY);
        };
    }
}
