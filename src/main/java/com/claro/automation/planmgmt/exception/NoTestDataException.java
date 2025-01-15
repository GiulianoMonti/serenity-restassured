package com.claro.automation.planmgmt.exception;

import net.serenitybdd.model.exceptions.TestCompromisedException;

public class NoTestDataException extends TestCompromisedException {
    public NoTestDataException(String msg) {
        super(msg);
    }
}
