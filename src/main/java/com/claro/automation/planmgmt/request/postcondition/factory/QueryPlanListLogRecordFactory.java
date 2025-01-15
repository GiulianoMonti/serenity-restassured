package com.claro.automation.planmgmt.request.postcondition.factory;

import com.claro.automation.planmgmt.dto.queryplanlist.RequestPlanMgmtDto;
import io.restassured.response.Response;

public class QueryPlanListLogRecordFactory extends ExpectedLogRecordFactory {

    private final Response response;
    private final RequestPlanMgmtDto queryPlanListDto;

    public QueryPlanListLogRecordFactory(Response response, RequestPlanMgmtDto queryPlanListDto) {
        this.response = response;
        this.queryPlanListDto = queryPlanListDto;
    }

    @Override
    public ExpectedLogRecord createExpectedDocument() {
        return new QueryPlanListExpectedLogRecord(response, queryPlanListDto);
    }
}