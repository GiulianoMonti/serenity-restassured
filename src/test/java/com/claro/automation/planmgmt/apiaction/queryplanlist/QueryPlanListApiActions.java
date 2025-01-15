package com.claro.automation.planmgmt.apiaction.queryplanlist;

import com.claro.automation.planmgmt.dataaccess.model.Plan;
import com.claro.automation.planmgmt.dto.queryplanlist.MapperQueryPlanList;
import com.claro.automation.planmgmt.dto.queryplanlist.QueryPlanListRequestDto;
import com.claro.automation.planmgmt.dto.queryplanlist.ResponseQueryPlanListDto;
import com.claro.automation.planmgmt.facade.DataBaseApi;
import com.claro.automation.planmgmt.request.postcondition.PlanMgmtResponseSpecification;
import com.claro.automation.planmgmt.request.setup.QueryPlanListRequestSpecification;
import com.claro.automation.planmgmt.util.SerenityEnvironmentVariables;
import com.claro.automation.planmgmt.util.SerenityReportRecordData;
import com.claro.automation.planmgmt.util.Utils;
import com.claro.automation.planmgmt.util.VariableByCountry;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.steps.UIInteractions;
import net.serenitybdd.rest.SerenityRest;
import org.apache.http.HttpStatus;

import java.util.List;

import static net.serenitybdd.rest.SerenityRest.then;
import static net.serenitybdd.rest.SerenityRest.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class QueryPlanListApiActions extends UIInteractions {

    private final PlanMgmtResponseSpecification planMgmtResponseSpecification;
    private final RequestSpecification queryPlanListRequestSpecification;
    private final DataBaseApi dataBaseApi;

    public QueryPlanListApiActions() {
        planMgmtResponseSpecification = new PlanMgmtResponseSpecification();
        queryPlanListRequestSpecification = SerenityRest.given().spec(
                new QueryPlanListRequestSpecification(SerenityEnvironmentVariables.getPathQueryPlanList())
                        .requestSpecAllParameters());
        dataBaseApi = new DataBaseApi();

    }

    @Given("Se proporciona un body request")
    public void givenQueryPlanListBodyRequest(QueryPlanListRequestDto requestDto) {
        queryPlanListRequestSpecification.body(requestDto);
    }

    @When("se realiza la solicitud al endpoint queryPlanList")
    public void whenPostResponseQueryPlanList() {
        when().post();
        new SerenityReportRecordData().measuringResponseTime();
    }

    @When("consulta en base de datos por los planes registrados con providerId: {0} y el enterpriseId: {1}")
    public List<Plan> whenGetPlanList(String providerId, String enterpriseId) {
        return dataBaseApi.selectPlanList(providerId, enterpriseId);
    }

    @Then("Verifica que el mensaje de la respuesta sea resultCode: {0} y resultMessage: {1}")
    public void thenValidateResponseBadRequest(String expectedResultCode, String expectedResultMessage, String expectedCorrelatorId, String expectedProviderId) {


        then().spec(planMgmtResponseSpecification.responseSpecBuilder())
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .assertThat().body("queryPlanListResponse.resultCode", equalTo(expectedResultCode),
                        "queryPlanListResponse.resultMessage", equalTo(expectedResultMessage),
                        "queryPlanListResponse.country", equalTo(VariableByCountry.getCountryShortName()),
                        "queryPlanListResponse.correlatorId", equalTo(expectedCorrelatorId),
                        "queryPlanListResponse.providerId", equalTo(expectedProviderId));
    }

    @Then("Verifica que el mensaje de la respuesta planes apple y samsung")
    public void thenValidateResponseSuccessSamsungApple(QueryPlanListRequestDto requestDto, String planId, String profileType) {

        var actualPlansList = then().spec(planMgmtResponseSpecification.responseSpecBuilder()).assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ResponseQueryPlanListDto.class)
                .queryPlanListResponse()
                .plans();

        then().spec(planMgmtResponseSpecification.responseSpecBuilder())
                .assertThat().statusCode(HttpStatus.SC_OK)
                .assertThat().body("queryPlanListResponse.resultCode", equalTo("0"),
                        "queryPlanListResponse.resultMessage", equalTo("Success"),
                        "queryPlanListResponse.country", equalTo(VariableByCountry.getCountryShortName()),
                        "queryPlanListResponse.correlatorId", equalTo(requestDto.queryPlanListRequest().getCorrelatorId()),
                        "queryPlanListResponse.providerId", equalTo(requestDto.queryPlanListRequest().getProviderId()));

        assertThat(actualPlansList, not(empty()));
    }

    @Then("Verifica que el mensaje de la respuesta planes GM")
    public void thenValidateResponseSuccessGm(QueryPlanListRequestDto requestDto, List<Plan> plansDb) {

        var expectedPlans = then().spec(planMgmtResponseSpecification.responseSpecBuilder()).assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ResponseQueryPlanListDto.class)
                .queryPlanListResponse()
                .plans();

        Serenity.recordReportData().withTitle("plans from json")
                .andContents(Utils.convertToJsonPrettyPrinter(expectedPlans));

        Serenity.recordReportData().withTitle("plans from db")
                .andContents(Utils.convertToJsonPrettyPrinter(plansDb));

        var actualPlansList = new MapperQueryPlanList().toPlanList(plansDb);

        assertThat(actualPlansList, equalTo(expectedPlans));
        then().spec(planMgmtResponseSpecification.responseSpecBuilder())
                .assertThat().statusCode(HttpStatus.SC_OK)
                .assertThat().body("queryPlanListResponse.resultCode", equalTo("0"),
                        "queryPlanListResponse.resultMessage", equalTo("Success"),
                        "queryPlanListResponse.country", equalTo(VariableByCountry.getCountryShortName()),
                        "queryPlanListResponse.correlatorId", equalTo(requestDto.queryPlanListRequest().getCorrelatorId()),
                        "queryPlanListResponse.providerId", equalTo(requestDto.queryPlanListRequest().getProviderId()));
    }


}
