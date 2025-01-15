package com.claro.automation.planmgmt.request.postcondition;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

import java.util.concurrent.TimeUnit;

public class PlanMgmtResponseSpecification {
    public ResponseSpecification responseSpecBuilder() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(8L), TimeUnit.SECONDS)
                .build();
    }
}
