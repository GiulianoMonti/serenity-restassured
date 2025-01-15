package com.claro.automation.planmgmt.request.setup;

import com.claro.automation.planmgmt.util.SerenityEnvironmentVariables;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class QueryPlanListRequestSpecification {

    private final String basePath;

    public QueryPlanListRequestSpecification(String basePath) {
        this.basePath = basePath;
    }

    public RequestSpecification requestSpecAllParameters() {
        return requestSpecBuilder()
                .addHeader("Username", SerenityEnvironmentVariables.getAuthUsername())
                .addHeader("Password", SerenityEnvironmentVariables.getAuthPassword())
                .build();
    }

    public RequestSpecification requestSpecificationBasicParameters() {
        return requestSpecBuilder().build();
    }

    private RequestSpecBuilder requestSpecBuilder() {
        var baseUri = SerenityEnvironmentVariables.getUriQueryPlanList();
        RestAssured.filters(new ResponseTimeFilter());
        return new RequestSpecBuilder()
                .setRelaxedHTTPSValidation()
                .setBaseUri(baseUri)
                .setBasePath(basePath)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON);
    }
}
