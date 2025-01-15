package com.claro.automation.planmgmt.request.postcondition.factory;

import com.claro.automation.dbloggerelastic.dataaccess.model.IotLog;
import com.claro.automation.planmgmt.dto.elasticsearch.DocumentQueryPlanList;

public interface ExpectedLogRecord {
    IotLog generateExpectedIotLog();
    DocumentQueryPlanList generateExpectedDocument();
}