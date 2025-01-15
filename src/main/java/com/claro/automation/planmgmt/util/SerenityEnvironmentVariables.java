package com.claro.automation.planmgmt.util;

import net.serenitybdd.core.di.SerenityInfrastructure;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.util.EnvironmentVariables;

public class SerenityEnvironmentVariables {

    static final EnvironmentVariables ENVIRONMENT_VARIABLES = SerenityInfrastructure.getEnvironmentVariables();

    private SerenityEnvironmentVariables() {
        throw new IllegalStateException("Utility class");
    }

    public static String getUriQueryPlanList() {
        return EnvironmentSpecificConfiguration.from(ENVIRONMENT_VARIABLES).getProperty("queryplanlist.service.uri");
    }

    public static String getDataBaseCountry() {
        return EnvironmentSpecificConfiguration.from(ENVIRONMENT_VARIABLES).getProperty("queryplanlist.database.country");
    }

    public static String getAuthUsername() {
        return EnvironmentSpecificConfiguration.from(ENVIRONMENT_VARIABLES).getProperty("queryplanlist.service.auth.username");
    }

    public static String getAuthPassword() {
        return EnvironmentSpecificConfiguration.from(ENVIRONMENT_VARIABLES).getProperty("queryplanlist.service.auth.password");
    }

    public static String getPathQueryPlanList() {
        return EnvironmentSpecificConfiguration.from(ENVIRONMENT_VARIABLES).getProperty("planmgmt.service.queryplanlist.path");
    }
    public static String getPathChangePlan() {
        return EnvironmentSpecificConfiguration.from(ENVIRONMENT_VARIABLES).getProperty("planmgmt.service.changeplan.path");
    }

    public static Integer getElasticsearchAttempts() {
        return Integer.valueOf(EnvironmentSpecificConfiguration.from(ENVIRONMENT_VARIABLES).getProperty("elasticsearch.attempts"));
    }

    public static Long getElasticsearchWaitInMilliseconds() {
        return Long.valueOf(EnvironmentSpecificConfiguration.from(ENVIRONMENT_VARIABLES).getProperty("elasticsearch.waitmilliseconds"));
    }
}
