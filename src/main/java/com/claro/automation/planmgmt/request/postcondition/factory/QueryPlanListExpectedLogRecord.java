package com.claro.automation.planmgmt.request.postcondition.factory;

import com.claro.automation.dbloggerelastic.dataaccess.model.IotLog;
import com.claro.automation.planmgmt.dto.elasticsearch.DocumentQueryPlanList;
import com.claro.automation.planmgmt.dto.queryplanlist.RequestPlanMgmtDto;
import com.claro.automation.planmgmt.dto.queryplanlist.ResponseQueryPlanListDto;
import com.claro.automation.planmgmt.util.ErrorCodeHelper;
import com.claro.automation.planmgmt.util.SerenityEnvironmentVariables;
import com.claro.automation.planmgmt.util.VariableByCountry;
import io.restassured.response.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QueryPlanListExpectedLogRecord implements ExpectedLogRecord {

    private final Response response;
    private final RequestPlanMgmtDto request;
    private final ResponseQueryPlanListDto responseQueryPlanListDto;
    private final ErrorCodeHelper errorCodeHelper;

    public QueryPlanListExpectedLogRecord(Response response, RequestPlanMgmtDto request) {
        this.request = request;
        this.response = response;
        responseQueryPlanListDto = response.body().as(ResponseQueryPlanListDto.class);
        errorCodeHelper = new ErrorCodeHelper();
    }

    @Override
    public IotLog generateExpectedIotLog() {
        var dateTime = VariableByCountry.getLocalTime();
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        var dateNowFormat = LocalDateTime.parse(dateTime.format(formatter), formatter);


        return IotLog.builder()
                .correlator(responseQueryPlanListDto.queryPlanListResponse().correlatorId())
                .systemDate(dateNowFormat)
                .serviceName("queryPlanList")
                .provider(responseQueryPlanListDto.queryPlanListResponse().providerId())
                .msisdn(null)
                .status(errorCodeHelper.determineStatus(response.getStatusCode(), responseQueryPlanListDto.queryPlanListResponse().resultCode()))
                .errorCode(errorCodeHelper.determineErrorCode(response.getStatusCode(), responseQueryPlanListDto.queryPlanListResponse().resultCode()))
                .request(request.body())
                .requestDate(dateNowFormat)
                .response(response.getBody().asString())
                .responseDate(dateNowFormat)
                .notifyRequestDate(dateNowFormat)
                .notifyResponseDate(dateNowFormat)
                .notifyRequest(null)
                .notifyResponse(null)
                .build();
    }

    @Override
    public DocumentQueryPlanList generateExpectedDocument() {


        return DocumentQueryPlanList.builder()
                .providerId(responseQueryPlanListDto.queryPlanListResponse().providerId()) // Actualizado según el JSON
                .msisdn("")
                .type("testing")
                .aspCallbackUrl("")
                .method("queryPlanList")
                .aspCallbackUrl("")
                .hubIoTError("")
                .method("queryPlanList")
                .hubIoTError("")
                .imsi("")
                .description(responseQueryPlanListDto.queryPlanListResponse().resultCode().equals("0") ? "Success" : responseQueryPlanListDto.queryPlanListResponse().resultMessage())
                .planId("")
                .esIndex("ipp_idx_iot-2024.11.46")
                .responseHub(response.getBody().asString())
                .country(VariableByCountry.getCountryFullName())
                .esPipeline("IPP-PROV-HUBIOT")
                .logLevel("INFO")
                .request(request.body())
                .imei("")
                .transaction("")
                .correlatorId(responseQueryPlanListDto.queryPlanListResponse().correlatorId())
                .result(response.statusCode() == 200 && responseQueryPlanListDto.queryPlanListResponse().resultCode().equals("0") ? "0" : "9999")
                .enterprise(request.enterpriseId())
                .server("")
                .fields(DocumentQueryPlanList.Fields.builder()
                        .app("planmgmt")
                        .host("3pp-dai-" + SerenityEnvironmentVariables.getDataBaseCountry())
                        .build())
                .errorDescription(responseQueryPlanListDto.queryPlanListResponse().resultCode().equals("0") ? "Success" : responseQueryPlanListDto.queryPlanListResponse().resultMessage())
                .build();
    }
}