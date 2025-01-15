package com.claro.automation.planmgmt.util;


import com.claro.automation.planmgmt.exception.ObjectMapperException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@UtilityClass
public class Utils {

    private static final Random RANDOM = new Random();

    public static String convertToJsonPrettyPrinter(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            throw new ObjectMapperException(e.getMessage());
        }
    }

    public static String createCorrelatorId() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(formatter);
        int randomNumber = RANDOM.nextInt(90000) + 10000;
        return VariableByCountry.getServiceProviderGm() + formattedDateTime + randomNumber;
    }

    public static String convertToJson(Object obj) {
        var objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String translatePlan(String planId) {
        return switch (planId) {
            case "J002H", "JH002" -> "AH001";
            case "J000H", "JH001", "J001H", "JH003" -> "AH002";
            default -> throw new IllegalArgumentException("Plan ID no reconocido: " + planId);
        };
    }

    public static String removeWhitespace(String json) {
        if (json == null) {
            return null;
        }
        return json.replaceAll("\\s+", "");
    }

    public static boolean isDatabaseCountryAr() {
        return SerenityEnvironmentVariables.getDataBaseCountry().equals("ar");
    }
}
