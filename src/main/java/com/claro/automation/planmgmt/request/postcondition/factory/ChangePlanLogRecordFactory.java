package com.claro.automation.planmgmt.request.postcondition.factory;

import com.claro.automation.planmgmt.dto.queryplanlist.RequestPlanMgmtDto;
import io.restassured.response.Response;

public class ChangePlanLogRecordFactory extends ExpectedLogRecordFactory {

    private final Response response;
    private final RequestPlanMgmtDto queryPlanListDto;

    public ChangePlanLogRecordFactory(Response response, RequestPlanMgmtDto changePlanDto) {
        this.response = response;
        this.queryPlanListDto = changePlanDto;
    }

    @Override
    public ExpectedLogRecord createExpectedDocument() {
        return new ChangePlanExpectedLogRecord(response, queryPlanListDto);
    }
}