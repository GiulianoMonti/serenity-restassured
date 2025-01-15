package com.claro.automation.planmgmt.apiaction.base;

import com.claro.automation.dbloggerelastic.dataaccess.model.IotLog;
import com.claro.automation.planmgmt.dto.changeplan.ResponseChangePlanDto;
import com.claro.automation.planmgmt.dto.elasticsearch.DocumentQueryPlanList;
import com.claro.automation.planmgmt.dto.queryplanlist.RequestPlanMgmtDto;
import com.claro.automation.planmgmt.facade.DataBaseApi;
import com.claro.automation.planmgmt.facade.KibanaApi;
import com.claro.automation.planmgmt.request.postcondition.PlanMgmtResponseSpecification;
import com.claro.automation.planmgmt.request.postcondition.factory.ChangePlanLogRecordFactory;
import com.claro.automation.planmgmt.request.postcondition.factory.ExpectedLogRecordFactory;
import com.claro.automation.planmgmt.request.postcondition.factory.QueryPlanListLogRecordFactory;
import com.claro.automation.planmgmt.request.setup.QueryPlanListRequestSpecification;
import com.claro.automation.planmgmt.util.Utils;
import com.claro.automation.planmgmt.util.VariableByCountry;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.steps.UIInteractions;
import org.apache.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.claro.automation.planmgmt.util.Utils.removeWhitespace;
import static java.time.temporal.ChronoUnit.MINUTES;
import static net.serenitybdd.rest.SerenityRest.given;
import static org.exparity.hamcrest.date.LocalDateTimeMatchers.within;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;


public class LoggerDbKibanaIntegrationApiActions extends UIInteractions {

    private final PlanMgmtResponseSpecification planMgmtResponseSpecification;
    @Getter
    private final RequestSpecification requestSpecification;
    public final DataBaseApi dataBaseApi;
    private final String path;

    public LoggerDbKibanaIntegrationApiActions(String path) {
        planMgmtResponseSpecification = new PlanMgmtResponseSpecification();
        requestSpecification = given().spec(
                new QueryPlanListRequestSpecification(path)
                        .requestSpecAllParameters());
        this.path = path;
        dataBaseApi = new DataBaseApi();
    }


    @When("se realiza la solicitud al endpoint queryPlanList y se extrae la respuesta")
    public Response whenPostResponseQueryPlanList() {
        return getRequestSpecification()
                .when().post()
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response();
    }

    @When("se realiza la solicitud al endpoint queryPlanList y se extrae la respuesta")
    public Response whenPostResponseQueryPlanListBadRequest() {
        return getRequestSpecification()
                .when().post()
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().response();
    }


    @When("consulta por el log registrado con providerId: {0} y correlatorId: {1}")
    public IotLog whenGetIotLogByProviderIdAndCorrelatorId(String providerId, String correlatorId) throws InterruptedException {
        Serenity.recordReportData().withTitle("databaseapi").andContents("providerId: " + providerId + " correlatorId: " + correlatorId
                + dataBaseApi);
        return dataBaseApi.getLogWithProviderIdAndCorrelator(path, providerId, correlatorId);
    }

    @When("consulta en kibana por el log registrado: {0}")
    public DocumentQueryPlanList whenGetDocumentElasticByCorrelator(String correlatorId) {
        return new KibanaApi().getDocumentByCorrelator(correlatorId);
    }

    @Then("Verifica que la respuesta sea la esperada")
    public void thenValidateResponseSuccess(Response actualResponse, ResponseChangePlanDto expectedResponse) {

        Serenity.recordReportData().withTitle("actualresponse").andContents
                (Utils.convertToJsonPrettyPrinter(actualResponse.getBody().asString()));
        Serenity.recordReportData().withTitle("expected").andContents
                (Utils.convertToJsonPrettyPrinter(expectedResponse));

        assertThat("Responses are not equal", removeWhitespace(actualResponse.getBody().asString()),
                equalTo(removeWhitespace(Utils.convertToJsonPrettyPrinter(expectedResponse))));
    }

    @Then("visualiza que se registro correctamente la informacion en LoggerDB")
    public void thenValidateLogInLoggerDb(IotLog iotLog, Response response, RequestPlanMgmtDto requestDto) {
        Serenity.recordReportData().withTitle("LoggerDB").andContents(Utils.convertToJsonPrettyPrinter(iotLog));

        var factory = createLogFactory(response, requestDto);
        var iotLogExpected = factory.getExpectedIotLog();

        LocalDateTime dateNow = VariableByCountry.getLocalTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateNowFormat = LocalDateTime.parse(dateNow.format(formatter), formatter);

        assertAll(
                () -> assertThat("SystemDate is not within the expected range.",
                        iotLog.systemDate(), within(60, MINUTES, dateNowFormat)),
                () -> assertThat("ServiceName does not match.",
                        iotLog.serviceName(), equalTo(iotLogExpected.serviceName())),
                () -> assertThat("CorrelatorId is not equal.",
                        iotLog.correlator(), startsWith(iotLogExpected.correlator())),
                () -> assertThat("Provider does not match.",
                        iotLog.provider(), equalTo(iotLogExpected.provider())),
                () -> assertThat("Msisdn does not match.",
                        iotLog.msisdn(), equalTo(iotLogExpected.msisdn())),
                () -> assertThat("ErrorCode does not match.",
                        iotLog.errorCode(), equalTo(iotLogExpected.errorCode())),
                () -> assertThat("Request content does not match.",
                        removeWhitespaceFromJson(iotLog.request()), equalToCompressingWhiteSpace(iotLogExpected.request())),
                () -> assertThat("Response content does not match.",
                        removeWhitespaceFromJson(iotLog.response()), equalToCompressingWhiteSpace(iotLogExpected.response()))
        );

        validateNotifyFieldsIfNotNull(iotLog.notifyRequest(), iotLogExpected.notifyRequest(), "NotifyRequest");
        validateNotifyFieldsIfNotNull(iotLog.notifyResponse(), iotLogExpected.notifyResponse(), "NotifyResponse");
    }

    private void validateNotifyFieldsIfNotNull(String actual, String expected, String fieldName) {
        if (actual != null && expected != null) {
            assertThat(fieldName + " does not match.",
                    removeWhitespaceFromJson(actual), equalToCompressingWhiteSpace(removeWhitespaceFromJson(expected)));
        }
    }


    public String removeWhitespaceFromJson(String json) {
        return json.replaceAll("\\s*(\\{|\\}|\\[|\\]|:|,|\\\"[^\"]*\\\")\\s*", "$1");
    }

    @Then("visualiza que se registro correctamente la informacion en Kibana")
    public void thenValidateLogInKibana(DocumentQueryPlanList documentQueryPlanListExpected,
                                        Response response, RequestPlanMgmtDto requestDto) {
        Serenity.recordReportData().withTitle("Kibana").andContents(Utils.convertToJsonPrettyPrinter(documentQueryPlanListExpected));
        var factory = createLogFactory(response, requestDto);
        var documentElasticExpected = factory.getExpectedDocument();

        assertAll(
                () -> assertThat("ProviderId is not equal.", documentQueryPlanListExpected.getProviderId(), equalTo(documentElasticExpected.getProviderId())),
                () -> assertThat("MSISDN is not equal.", documentQueryPlanListExpected.getMsisdn(), equalTo(documentElasticExpected.getMsisdn())),
                () -> assertThat("Type is not equal.", documentQueryPlanListExpected.getType(), equalTo(documentElasticExpected.getType())),
                () -> assertThat("AspCallbackUrl is not equal.", documentQueryPlanListExpected.getAspCallbackUrl(), equalTo(documentElasticExpected.getAspCallbackUrl())),
                () -> assertThat("Method is not equal.", documentQueryPlanListExpected.getMethod(), equalTo(documentElasticExpected.getMethod())),
                () -> assertThat("HubIoTError is not equal.", documentQueryPlanListExpected.getHubIoTError(), equalTo(documentElasticExpected.getHubIoTError())),
                () -> assertThat("IMSI is not equal.", documentQueryPlanListExpected.getImsi(), equalTo(documentElasticExpected.getImsi())),
                () -> assertThat("PlanId is not equal.", documentQueryPlanListExpected.getPlanId(), equalTo(documentElasticExpected.getPlanId())),
                () -> assertThat("ResponseHub is not equal.", removeWhitespaceFromJson(documentQueryPlanListExpected.getResponseHub()), equalTo(documentElasticExpected.getResponseHub())),
                () -> assertThat("Country is not equal.", documentQueryPlanListExpected.getCountry(), equalTo(documentElasticExpected.getCountry())),
                () -> assertThat("EsPipeline is not equal.", documentQueryPlanListExpected.getEsPipeline(), equalTo(documentElasticExpected.getEsPipeline())),
                () -> assertThat("LogLevel is not equal.", documentQueryPlanListExpected.getLogLevel(), equalTo(documentElasticExpected.getLogLevel())),
                () -> assertThat("Request is not equal.", removeWhitespaceFromJson(documentQueryPlanListExpected.getRequest()), equalToCompressingWhiteSpace(documentElasticExpected.getRequest())),
                () -> assertThat("IMEI is not equal.", documentQueryPlanListExpected.getImei(), equalTo(documentElasticExpected.getImei())),
                () -> assertThat("Message is not null.", documentQueryPlanListExpected.getMessage(), notNullValue()),
                () -> assertThat("Transaction is not equal.", documentQueryPlanListExpected.getTransaction(), equalTo(documentElasticExpected.getTransaction())),
                () -> assertThat("CorrelatorId is not equal.", documentQueryPlanListExpected.getCorrelatorId(), equalTo(documentElasticExpected.getCorrelatorId())),
                () -> assertThat("Result is not equal.", documentQueryPlanListExpected.getResult(), equalTo(documentElasticExpected.getResult())),
                () -> assertThat("Elapsed time is not null.", documentQueryPlanListExpected.getElapsed(), notNullValue()),
                () -> assertThat("Enterprise is not equal.", documentQueryPlanListExpected.getEnterprise(), equalTo(documentElasticExpected.getEnterprise())),
                () -> assertThat("Server is not equal.", documentQueryPlanListExpected.getServer(), equalTo(documentElasticExpected.getServer())),
                () -> assertThat("Fields app is not equal.", documentQueryPlanListExpected.getFields().getApp(), equalTo(documentElasticExpected.getFields().getApp())),
                () -> assertThat("Fields host is not equal.", documentQueryPlanListExpected.getFields().getHost(), equalTo(documentElasticExpected.getFields().getHost())),
                () -> assertThat("ErrorDescription is not equal.", documentQueryPlanListExpected.getErrorDescription(), equalTo(documentElasticExpected.getErrorDescription()))
        );
    }

    private ExpectedLogRecordFactory createLogFactory(Response response, RequestPlanMgmtDto requestDto) {
        return switch (path) {
            case "queryPlanList" -> new QueryPlanListLogRecordFactory(response, requestDto);
            case "changePlan" -> new ChangePlanLogRecordFactory(response, requestDto);
            default -> throw new RuntimeException();
        };
    }

}