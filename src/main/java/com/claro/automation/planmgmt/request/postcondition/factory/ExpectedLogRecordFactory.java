package com.claro.automation.planmgmt.request.postcondition.factory;

import com.claro.automation.dbloggerelastic.dataaccess.model.IotLog;
import com.claro.automation.planmgmt.dto.elasticsearch.DocumentQueryPlanList;

public abstract class ExpectedLogRecordFactory {

    public IotLog getExpectedIotLog() {
        ExpectedLogRecord document = createExpectedDocument();
        return document.generateExpectedIotLog();
    }
    public DocumentQueryPlanList getExpectedDocument() {
        ExpectedLogRecord document = createExpectedDocument();
        return document.generateExpectedDocument();
    }
    public abstract ExpectedLogRecord createExpectedDocument();
}