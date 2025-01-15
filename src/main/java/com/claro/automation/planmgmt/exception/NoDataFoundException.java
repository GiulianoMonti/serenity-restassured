package com.claro.automation.planmgmt.exception;

public class NoDataFoundException extends AssertionError {
    public NoDataFoundException(String msg) {
        super(msg);
    }
}