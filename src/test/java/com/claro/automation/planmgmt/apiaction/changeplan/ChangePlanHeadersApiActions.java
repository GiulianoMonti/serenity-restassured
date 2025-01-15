package com.claro.automation.planmgmt.apiaction.changeplan;

import com.claro.automation.planmgmt.apiaction.base.HeadersApiActions;
import com.claro.automation.planmgmt.util.SerenityEnvironmentVariables;
import io.cucumber.java.en.Given;

public class ChangePlanHeadersApiActions extends HeadersApiActions {


    public ChangePlanHeadersApiActions() {
        super(SerenityEnvironmentVariables.getPathChangePlan());

    }

    @Given("no se envia el parametro password")
    public void givenHeaderWithoutPassword() {
        requestSpecification.header("Username", "iothub");
    }

    @Given("no se envia el parametro username")
    public void givenHeaderWithoutUsername() {
        requestSpecification.header("Password", "801cec4bbd86f674c0f3963ccfaf29d5cda78057701d5a96c3311bd7d6d60da3");
    }

    @Given("el parametro Password esta vacio")
    public void givenEmptyPassword() {
        requestSpecification.header("Username", "iothub")
                .header("Password", "");
    }

    @Given("el parametro Username esta vacio")
    public void givenEmptyUsername() {
        requestSpecification.header("Username", "")
                .header("Password", "801cec4bbd86f674c0f3963ccfaf29d5cda78057701d5a96c3311bd7d6d60da3");
    }

    @Given("el valor del parametro Username es incorrecto")
    public void givenIncorrectUsernameValue() {
        requestSpecification.header("Username", "iothub no valido")
                .header("Password", "801cec4bbd86f674c0f3963ccfaf29d5cda78057701d5a96c3311bd7d6d60da3");
    }

    @Given("el key Username es incorrecto")
    public void givenIncorrectUsernameKey() {
        requestSpecification.header("Username777", "iothub")
                .header("Password", "801cec4bbd86f674c0f3963ccfaf29d5cda78057701d5a96c3311bd7d6d60da3");
    }
}
