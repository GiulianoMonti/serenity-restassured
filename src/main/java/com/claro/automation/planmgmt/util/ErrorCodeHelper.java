package com.claro.automation.planmgmt.util;


public class ErrorCodeHelper {


    public String determineStatus(int statusCode, String resultCode) {
        if (statusCode == 200 && "9999" .equals(resultCode)) {
            return "E";
        }
        return statusCode == 200 ? "O" : "E";
    }

    public long determineErrorCode(int statusCode, String resultCode) {
        if (statusCode == 200 && "9999" .equals(resultCode)) {
            return 9999;
        }
        return switch (statusCode) {
            case 200 -> 0;
            case 404 -> 1;
            case 400 -> 4;
            default -> 500;
        };
    }

}
