package com.claro.automation.planmgmt.testsuite.changeplan;


import com.claro.automation.planmgmt.apiaction.changeplan.ChangePlanLoggerDbKibanaApiActions;
import com.claro.automation.planmgmt.apiaction.queryplanlist.cross.PlanMgmtProfileApiActions;
import com.claro.automation.planmgmt.dto.changeplan.MapperChangePlan;
import com.claro.automation.planmgmt.dto.queryplanlist.RequestPlanMgmtDto;
import com.claro.automation.planmgmt.util.Utils;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
@DisplayName("Validar que la informacion obtenida en el response body sea correcta")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ChangePlanLoggerDbKibanaTest {

    private ChangePlanLoggerDbKibanaApiActions changePlanLoggerDbKibanaApiActions;
    private PlanMgmtProfileApiActions profileApiActions;

    @Test
    @Tag("changePlan")
    @Tag("regression")
    @DisplayName("PINTEG-114 TC_Cambio de plan Ok LoggerDb - Kibana")
    void changePlanOK() throws InterruptedException {

        var profile = profileApiActions.givenGetMsisdnActiveLineChangePlan();
        var checkedPlanId = changePlanLoggerDbKibanaApiActions.getCellularPlansByMsisdn(profile.cellularNumber().substring(3));
        var correlatorId = Utils.createCorrelatorId();

        var notifyRequest = changePlanLoggerDbKibanaApiActions.givenCreateNotifyRequest("0", "Success", correlatorId, profile.providerId());
        var notifyResponse = changePlanLoggerDbKibanaApiActions.givencreateNotifyResponse(10001211, "The parameter value format is incorrect, or the value is invalid.", correlatorId);
        var planIdBodyRequest = Utils.translatePlan(checkedPlanId.rplId());

        var bodyRequest = changePlanLoggerDbKibanaApiActions.givenChangePlanBodyRequest(profile.cellularNumber(),
                planIdBodyRequest,
                profile.enterpriseId(),
                profile.providerId(),
                correlatorId);

        var actualResponse = changePlanLoggerDbKibanaApiActions.whenPostResponseChangePlan();

        var expectedResponse =
                new MapperChangePlan().bodyResponse(0,
                        "Success",
                        correlatorId);

        var iotLog = changePlanLoggerDbKibanaApiActions.whenGetIotLogByProviderIdAndCorrelatorId(profile.providerId(), correlatorId);

        changePlanLoggerDbKibanaApiActions.thenValidateResponseSuccess(actualResponse, expectedResponse);

        var requestDto = RequestPlanMgmtDto.builder()
                .body(bodyRequest)
                .plandId(planIdBodyRequest)
                .enterpriseId(profile.enterpriseId())
                .notifyRequest(notifyRequest)
                .notifyResponse(notifyResponse)
                .msisdn(profile.cellularNumber())
                .providerId(profile.providerId())
                .errorMessageIot(iotLog.errorMessage())
                .errorCodeIot(iotLog.errorCode())
                .build();

        changePlanLoggerDbKibanaApiActions.thenValidateLogInLoggerDb(iotLog, actualResponse, requestDto);
        var documentChangePlan = changePlanLoggerDbKibanaApiActions
                .whenGetDocumentElasticByCorrelator(correlatorId);

        changePlanLoggerDbKibanaApiActions.thenValidateLogInKibana(documentChangePlan, actualResponse, requestDto);
    }

    @Tag("regression")
    @Tag("changePlan")
    @Test
    @DisplayName("PINTEG-118 TC_Cambio de plan vigente -Error - no se encuentra el plan asociado- LoggerDb - Kibana")
    void changePlanNotFound() throws InterruptedException {

        var profile = profileApiActions.givenGetMsisdnActiveLineChangePlan();
        var correlatorId = Utils.createCorrelatorId();

        var notifyRequest = changePlanLoggerDbKibanaApiActions.givenCreateNotifyRequest("10009999", "Change plan failed", correlatorId, profile.providerId());
        var notifyResponse = changePlanLoggerDbKibanaApiActions.givencreateNotifyResponse(10001211, "The parameter value format is incorrect, or the value is invalid.", correlatorId);

        var bodyRequest = changePlanLoggerDbKibanaApiActions.givenChangePlanBodyRequest(profile.cellularNumber(),
                "AH004",
                "JASPER",
                profile.providerId(),
                correlatorId);


        var actualResponse = changePlanLoggerDbKibanaApiActions.whenPostResponseChangePlan();
        var expectedResponse =
                new MapperChangePlan().bodyResponse(0,
                        "Success",
                        correlatorId);


        changePlanLoggerDbKibanaApiActions.thenValidateResponseSuccess(actualResponse, expectedResponse);

        var iotLog = changePlanLoggerDbKibanaApiActions.whenGetIotLogByProviderIdAndCorrelatorId(profile.providerId(), correlatorId);

        var requestDto = RequestPlanMgmtDto.builder()
                .body(bodyRequest)
                .plandId("AH004")
                .enterpriseId("JASPER")
                .notifyRequest(notifyRequest)
                .notifyResponse(notifyResponse)
                .msisdn(profile.cellularNumber())
                .providerId(profile.providerId())
                .errorMessageIot(iotLog.errorMessage())
                .errorCodeIot(iotLog.errorCode())
                .build();

        changePlanLoggerDbKibanaApiActions.thenValidateLogInLoggerDb(iotLog, actualResponse, requestDto);
        var documentChangePlan = changePlanLoggerDbKibanaApiActions
                .whenGetDocumentElasticByCorrelator(correlatorId);

        changePlanLoggerDbKibanaApiActions.thenValidateLogInKibana(documentChangePlan, actualResponse, requestDto);
    }

    @Test
    @Tag("regression")
    @Order(1)
    @Tag("changePlan")
    @EnabledIf("com.claro.automation.planmgmt.util.Utils#isDatabaseCountryAr")
    @DisplayName("PINTEG-1485 TC_Cambio de plan - se agrega una restriccion a la linea")
    void changePlanAddRestriction() throws InterruptedException {
        var profile = profileApiActions.givenGetMsisdnActiveLineRestriction();
        var correlatorId = Utils.createCorrelatorId();
        changePlanLoggerDbKibanaApiActions.deleteFromChangeStatus(profile.cellularNumber().substring(3));
        var notifyRequest = changePlanLoggerDbKibanaApiActions.givenCreateNotifyRequest("0", "Success", correlatorId, profile.providerId());
        var notifyResponse = changePlanLoggerDbKibanaApiActions.givencreateNotifyResponse(10001211, "The parameter value format is incorrect, or the value is invalid.", correlatorId);


        var bodyRequest = changePlanLoggerDbKibanaApiActions.givenChangePlanBodyRequest(profile.cellularNumber(),
                profile.id(),
                profile.enterpriseId(),
                profile.providerId(),
                correlatorId);


        var actualResponse = changePlanLoggerDbKibanaApiActions.whenPostResponseChangePlan();
        var expectedResponse =
                new MapperChangePlan().bodyResponse(0,
                        "Success",
                        correlatorId);


        changePlanLoggerDbKibanaApiActions.thenValidateResponseSuccess(actualResponse, expectedResponse);

        var iotLog = changePlanLoggerDbKibanaApiActions.whenGetIotLogByProviderIdAndCorrelatorId(profile.providerId(), correlatorId);


        var requestDto = RequestPlanMgmtDto.builder()
                .body(bodyRequest)
                .plandId(profile.id())
                .enterpriseId(profile.enterpriseId())
                .notifyRequest(notifyRequest)
                .notifyResponse(notifyResponse)
                .msisdn(profile.cellularNumber())
                .providerId(profile.providerId())
                .errorMessageIot(iotLog.errorMessage())
                .errorCodeIot(iotLog.errorCode())
                .build();

        changePlanLoggerDbKibanaApiActions.thenValidateLogInLoggerDb(iotLog, actualResponse, requestDto);
        var documentChangePlan = changePlanLoggerDbKibanaApiActions
                .whenGetDocumentElasticByCorrelator(correlatorId);

        changePlanLoggerDbKibanaApiActions.thenValidateLogInKibana(documentChangePlan, actualResponse, requestDto);


    }

    @Test
    @Tag("regression")
    @Order(2)
    @Tag("changePlan")
    @EnabledIf("com.claro.automation.planmgmt.util.Utils#isDatabaseCountryAr")
    @DisplayName("PINTEG-1139 TC_Cambio de plan - Error - la linea posee la restriccion que se quiere aplicar")
    void changePlanAlreadyHasRestriction() throws InterruptedException {
        var profile = profileApiActions.givenGetMsisdnActiveLineRestriction();
        var correlatorId = Utils.createCorrelatorId();
        var notifyRequest = changePlanLoggerDbKibanaApiActions.givenCreateNotifyRequest("10009999", "Change plan failed", correlatorId, profile.providerId());
        var notifyResponse = changePlanLoggerDbKibanaApiActions.givencreateNotifyResponse(10001211, "The parameter value format is incorrect, or the value is invalid.", correlatorId);


        var bodyRequest = changePlanLoggerDbKibanaApiActions.givenChangePlanBodyRequest(profile.cellularNumber(),
                profile.id(),
                profile.enterpriseId(),
                profile.providerId(),
                correlatorId);


        var actualResponse = changePlanLoggerDbKibanaApiActions.whenPostResponseChangePlan();
        var expectedResponse =
                new MapperChangePlan().bodyResponse(0,
                        "Success",
                        correlatorId);


        changePlanLoggerDbKibanaApiActions.thenValidateResponseSuccess(actualResponse, expectedResponse);

        var iotLog = changePlanLoggerDbKibanaApiActions.whenGetIotLogByProviderIdAndCorrelatorId(profile.providerId(), correlatorId);


        var requestDto = RequestPlanMgmtDto.builder()
                .body(bodyRequest)
                .plandId(profile.id())
                .enterpriseId(profile.enterpriseId())
                .notifyRequest(notifyRequest)
                .notifyResponse(notifyResponse)
                .msisdn(profile.cellularNumber())
                .providerId(profile.providerId())
                .errorMessageIot(iotLog.errorMessage())
                .errorCodeIot(iotLog.errorCode())
                .build();

        changePlanLoggerDbKibanaApiActions.thenValidateLogInLoggerDb(iotLog, actualResponse, requestDto);
        var documentChangePlan = changePlanLoggerDbKibanaApiActions
                .whenGetDocumentElasticByCorrelator(correlatorId);

        changePlanLoggerDbKibanaApiActions.thenValidateLogInKibana(documentChangePlan, actualResponse, requestDto);

    }
}
