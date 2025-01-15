package com.claro.automation.planmgmt.util;

import com.claro.automation.planmgmt.request.setup.ResponseTimeFilter;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import net.serenitybdd.core.Serenity;

import java.util.List;

public class SerenityReportRecordData {

    public void measuringResponseTime() {
        List<Filter> filters = RestAssured.filters();
        ResponseTimeFilter responseTimeFilter = filters.stream()
                .filter(ResponseTimeFilter.class::isInstance)
                .map(ResponseTimeFilter.class::cast)
                .findAny().orElseThrow();
        Long responseTime = responseTimeFilter.getResponseTime();
        Serenity.recordReportData().withTitle("Time API").andContents("Response time is: " + responseTime + " ms");
    }
}
