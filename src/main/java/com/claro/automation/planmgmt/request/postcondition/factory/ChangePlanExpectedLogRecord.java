package com.claro.automation.planmgmt.request.postcondition.factory;

import com.claro.automation.dbloggerelastic.dataaccess.model.IotLog;
import com.claro.automation.planmgmt.dto.changeplan.ResponseChangePlanDto;
import com.claro.automation.planmgmt.dto.elasticsearch.DocumentQueryPlanList;
import com.claro.automation.planmgmt.dto.queryplanlist.RequestPlanMgmtDto;
import com.claro.automation.planmgmt.util.SerenityEnvironmentVariables;
import com.claro.automation.planmgmt.util.Utils;
import com.claro.automation.planmgmt.util.VariableByCountry;
import io.restassured.response.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChangePlanExpectedLogRecord implements ExpectedLogRecord {

    private final Response response;
    private final RequestPlanMgmtDto request;
    private final ResponseChangePlanDto responseChangePlanDto;

    public ChangePlanExpectedLogRecord(Response response, RequestPlanMgmtDto request) {
        this.request = request;
        this.response = response;
        responseChangePlanDto = response.body().as(ResponseChangePlanDto.class);
    }

    @Override
    public IotLog generateExpectedIotLog() {
        var dateTime = VariableByCountry.getLocalTime();
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        var dateNowFormat = LocalDateTime.parse(dateTime.format(formatter), formatter);

        return IotLog.builder()
                .correlator(responseChangePlanDto.changePlanResponse().correlatorId())
                .systemDate(dateNowFormat)
                .serviceName("changePlan")
                .provider(request.providerId())
                .msisdn(request.msisdn())
                .errorCode(request.errorCodeIot())
                .request(request.body())
                .requestDate(dateNowFormat)
                .response(response.getBody().asString())
                .responseDate(dateNowFormat)
                .notifyRequestDate(dateNowFormat)
                .notifyResponseDate(dateNowFormat)
                .notifyRequest(Utils.convertToJson(request.notifyRequest()))
                .notifyResponse(Utils.convertToJson(request.notifyResponse()))
                .build();
    }

    @Override
    public DocumentQueryPlanList generateExpectedDocument() {


        return DocumentQueryPlanList.builder()
                .providerId(request.providerId())
                .msisdn(request.msisdn())
                .type("testing")
                .aspCallbackUrl("")
                .method("changePlan")
                .aspCallbackUrl("")
                .hubIoTError("")
                .imsi("")
                .planId(request.plandId())
                .esIndex("ipp_idx_iot-2024.11.45")
                .responseHub(response.getBody().asString())
                .country(VariableByCountry.getCountryFullName())
                .esPipeline("IPP-PROV-HUBIOT")
                .logLevel("INFO")
                .request(request.body())
                .imei("")
                .transaction("")
                .correlatorId(responseChangePlanDto.changePlanResponse().correlatorId())
                .result(String.valueOf(request.errorCodeIot()))
                .enterprise(request.enterpriseId())
                .server("")
                .fields(DocumentQueryPlanList.Fields.builder()
                        .app("planmgmt")
                        .host("3pp-dai-" + SerenityEnvironmentVariables.getDataBaseCountry())
                        .build())
                .errorDescription("OK")
                .build();
    }
}