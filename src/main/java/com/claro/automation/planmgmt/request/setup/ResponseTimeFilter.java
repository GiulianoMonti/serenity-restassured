package com.claro.automation.planmgmt.request.setup;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
public class ResponseTimeFilter implements Filter {

    private Long responseTime;

    @Override
    public Response filter(FilterableRequestSpecification filterableRequestSpecification, FilterableResponseSpecification filterableResponseSpecification, FilterContext filterContext) {
        final Response response = filterContext.next(filterableRequestSpecification, filterableResponseSpecification);
        responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
        return response;
    }

}
