package com.claro.automation.planmgmt.apiaction.changeplan;

import com.claro.automation.planmgmt.apiaction.base.LoggerDbKibanaIntegrationApiActions;
import com.claro.automation.planmgmt.dataaccess.model.CellularPlans;
import com.claro.automation.planmgmt.dto.changeplan.ChangePlanRequestDto;
import com.claro.automation.planmgmt.dto.changeplan.MapperChangePlan;
import com.claro.automation.planmgmt.dto.changeplan.RequestNotifyChangePlanResultDto;
import com.claro.automation.planmgmt.dto.changeplan.ResponseNotifyChangePlanResultDto;
import com.claro.automation.planmgmt.exception.NoDataFoundException;
import com.claro.automation.planmgmt.util.SerenityEnvironmentVariables;
import com.claro.automation.planmgmt.util.Utils;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

public class ChangePlanLoggerDbKibanaApiActions extends LoggerDbKibanaIntegrationApiActions {

    public ChangePlanLoggerDbKibanaApiActions() {
        super(SerenityEnvironmentVariables.getPathChangePlan());
    }


    @Given("Se proporciona un body request")
    public String givenChangePlanBodyRequest(String msisdn,
                                             String planId,
                                             String enterpriseId,
                                             String providerId,
                                             String correlatorId) {
        {
            ChangePlanRequestDto requestDto = new MapperChangePlan()
                    .bodyRequest(msisdn, planId, enterpriseId, providerId, correlatorId);
            getRequestSpecification().body(requestDto);
            return Utils.convertToJson(requestDto);
        }
    }

    @Given("se realiza la solicitud al endpoint changePlan")
    public Response whenPostResponseChangePlan() {
        return getRequestSpecification().when().post().then().assertThat().statusCode(HttpStatus.SC_OK).extract().response();

    }

    @Given("se busca un plan con vigencia para despues enviar un plan distinto con msisdn: {0} ")
    public CellularPlans getCellularPlansByMsisdn(String msisdn) {
        return dataBaseApi.selectCellularPlansByMsisdn(msisdn)
                .orElseThrow(() -> new NoDataFoundException("No found msisdn " + msisdn));
    }

    @Given("se crea el notify request para posteriormente chequearlo en DBLOGGER")
    public RequestNotifyChangePlanResultDto givenCreateNotifyRequest(String resultCode,
                                                                     String resultMessage,
                                                                     String correlatorId,
                                                                     String providerId) {
        return new MapperChangePlan().bodyNotifyChangePlanResultRequest(
                resultCode,
                resultMessage,
                correlatorId,
                providerId);
    }

    @Given("se crea el notify response para posteriormente chequearlo en DBLOGGER")
    public ResponseNotifyChangePlanResultDto givencreateNotifyResponse(int resultCode,
                                                                       String resultMessage,
                                                                       String correlatorId) {
        return new MapperChangePlan().bodyNotifyChangePlanResultResponse(
                resultCode,
                resultMessage,
                correlatorId);
    }

    @Given("elimina un registro en Change Status")
    public void deleteFromChangeStatus(String cellularNumber) {
        dataBaseApi.deleteFromChangeStatus(cellularNumber);
    }
}
