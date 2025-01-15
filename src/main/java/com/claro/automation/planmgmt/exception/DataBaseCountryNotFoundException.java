package com.claro.automation.planmgmt.exception;

import net.serenitybdd.model.exceptions.TestCompromisedException;

public class DataBaseCountryNotFoundException extends TestCompromisedException {
    public DataBaseCountryNotFoundException(String msg) {
        super(msg);
    }
}
