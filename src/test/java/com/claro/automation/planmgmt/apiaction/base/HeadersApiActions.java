package com.claro.automation.planmgmt.apiaction.base;

import com.claro.automation.planmgmt.dto.changeplan.ChangePlanRequestDto;
import com.claro.automation.planmgmt.dto.changeplan.MapperChangePlan;
import com.claro.automation.planmgmt.dto.queryplanlist.MapperQueryPlanList;
import com.claro.automation.planmgmt.dto.queryplanlist.QueryPlanListRequestDto;
import com.claro.automation.planmgmt.request.postcondition.PlanMgmtResponseSpecification;
import com.claro.automation.planmgmt.request.setup.QueryPlanListRequestSpecification;
import com.claro.automation.planmgmt.util.Utils;
import com.claro.automation.planmgmt.util.VariableByCountry;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import net.serenitybdd.core.steps.UIInteractions;
import net.serenitybdd.rest.SerenityRest;
import org.apache.http.HttpStatus;

import java.util.Collections;

import static net.serenitybdd.rest.SerenityRest.then;
import static org.hamcrest.Matchers.equalTo;


public class HeadersApiActions extends UIInteractions {

    @Getter
    public final RequestSpecification requestSpecification;
    private final PlanMgmtResponseSpecification responseSpecification;
    private final String path;

    public HeadersApiActions(String path) {
        this.path = path;
        requestSpecification = SerenityRest.given().spec(
                new QueryPlanListRequestSpecification(path)
                        .requestSpecificationBasicParameters());
        responseSpecification = new PlanMgmtResponseSpecification();
    }


    @When("se realiza la solicitud al endpoint queryPlanList")
    public QueryPlanListRequestDto whenPostIncludeRequestBody() {
        QueryPlanListRequestDto requestDto =
                new MapperQueryPlanList().bodyRequest(VariableByCountry.getCountryEnterpriseName(), VariableByCountry.getServiceProviderGm(), Utils.createCorrelatorId(), Collections.emptyList());
        requestSpecification.body(requestDto).post();
        return requestDto;
    }

    @When("se realiza la solicitud al endpoint changePlan")
    public ChangePlanRequestDto whenPostIncludeRequestBodyChangePlan() {
        ChangePlanRequestDto requestDto =
                new MapperChangePlan()
                        .bodyRequest("5493856290024",
                                "AH005",
                                VariableByCountry.getCountryEnterpriseName(),
                                VariableByCountry.getServiceProviderGm(),
                                Utils.createCorrelatorId());
        requestSpecification.body(requestDto).post();
        return requestDto;
    }

    @Then("Verifica que el mensaje de error sea el esperado")
    public void thenValidateResponseForbiddenChangePlan(String expectedCorrelatorId) {
        then().spec(responseSpecification.responseSpecBuilder())
                .assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .assertThat().body("changePlanResponse.resultCode", equalTo(9999),
                        "changePlanResponse.resultMessage", equalTo("Authentication Failed"),
                        "changePlanResponse.correlatorId", equalTo(expectedCorrelatorId));
    }

    @Then("Verifica que el mensaje de error sea el esperado")
    public void thenValidateResponseForbidden(String expectedCorrelatorId) {
        then().spec(responseSpecification.responseSpecBuilder())
                .assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .assertThat().body("queryPlanListResponse.resultCode", equalTo("9999"),
                        "queryPlanListResponse.resultMessage", equalTo("Authentication Failed"),
                        "queryPlanListResponse.correlatorId", equalTo(expectedCorrelatorId));
    }
}