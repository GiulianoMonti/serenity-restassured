package com.claro.automation.planmgmt.apiaction.changeplan;

import com.claro.automation.planmgmt.dto.changeplan.ChangePlanRequestDto;
import com.claro.automation.planmgmt.request.postcondition.PlanMgmtResponseSpecification;
import com.claro.automation.planmgmt.request.setup.QueryPlanListRequestSpecification;
import com.claro.automation.planmgmt.util.SerenityEnvironmentVariables;
import com.claro.automation.planmgmt.util.SerenityReportRecordData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.core.steps.UIInteractions;
import net.serenitybdd.rest.SerenityRest;
import org.apache.http.HttpStatus;

import static net.serenitybdd.rest.SerenityRest.then;
import static net.serenitybdd.rest.SerenityRest.when;
import static org.hamcrest.Matchers.equalTo;


public class ChangePlanApiActions extends UIInteractions {

    private final PlanMgmtResponseSpecification planMgmtResponseSpecification;
    private final RequestSpecification changePlanRequestSpecification;

    public ChangePlanApiActions() {
        planMgmtResponseSpecification = new PlanMgmtResponseSpecification();
        changePlanRequestSpecification = SerenityRest.given().spec(
                new QueryPlanListRequestSpecification(SerenityEnvironmentVariables.getPathChangePlan())
                        .requestSpecAllParameters());
    }

    @Given("Se proporciona un body request")
    public void givenChangePlanBodyRequest(ChangePlanRequestDto requestDto) {
        changePlanRequestSpecification.body(requestDto);
    }

    @When("se realiza la solicitud al endpoint changePlan")
    public void whenPostResponseChangePlan() {
        when().post();
        new SerenityReportRecordData().measuringResponseTime();
    }

    @Then("Verifica que el mensaje de la respuesta sea resultCode: {0} y resultMessage: {1}")
    public void thenValidateResponseBadRequest(int expectedResultCode, String expectedResultMessage, String expectedCorrelatorId) {
        then().spec(planMgmtResponseSpecification.responseSpecBuilder())
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .assertThat().body("changePlanResponse.resultCode", equalTo(expectedResultCode),
                        "changePlanResponse.resultMessage", equalTo(expectedResultMessage),
                        "changePlanResponse.correlatorId", equalTo(expectedCorrelatorId));
    }

    @Then("Verifica que el mensaje de la respuesta sea resultCode: {0} y resultMessage: {1}")
    public void thenValidateResponseSuccess(String expectedCorrelatorId) {
        then().spec(planMgmtResponseSpecification.responseSpecBuilder())
                .assertThat().statusCode(HttpStatus.SC_OK)
                .assertThat().body("changePlanResponse.resultCode", equalTo(0),
                        "changePlanResponse.resultMessage", equalTo("Success"),
                        "changePlanResponse.correlatorId", equalTo(expectedCorrelatorId));
    }

}
