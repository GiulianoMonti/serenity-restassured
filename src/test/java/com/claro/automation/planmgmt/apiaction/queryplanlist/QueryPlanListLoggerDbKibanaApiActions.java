package com.claro.automation.planmgmt.apiaction.queryplanlist;

import com.claro.automation.planmgmt.apiaction.base.LoggerDbKibanaIntegrationApiActions;
import com.claro.automation.planmgmt.dto.queryplanlist.MapperQueryPlanList;
import com.claro.automation.planmgmt.dto.queryplanlist.QueryPlanListDto;
import com.claro.automation.planmgmt.dto.queryplanlist.QueryPlanListRequestDto;
import com.claro.automation.planmgmt.util.SerenityEnvironmentVariables;
import com.claro.automation.planmgmt.util.Utils;
import io.cucumber.java.en.Given;

import java.util.Collections;
import java.util.List;

public class QueryPlanListLoggerDbKibanaApiActions extends LoggerDbKibanaIntegrationApiActions {

    public QueryPlanListLoggerDbKibanaApiActions() {
        super(SerenityEnvironmentVariables.getPathQueryPlanList());
    }
    @Given("Se proporciona un body request")
    public String givenQueryPlanListBodyRequest(String enterpriseId, String providerId, String correlatorId, List<QueryPlanListDto.ExtensionInfo> extensionInfo) {
        QueryPlanListRequestDto requestDto = new MapperQueryPlanList().bodyRequest(enterpriseId, providerId, correlatorId, extensionInfo);
        getRequestSpecification().body(requestDto);
        return Utils.convertToJson(requestDto);
    }

    @Given("Se proporciona un body request con country invalido")
    public String givenQueryPlanListBodyRequestCountryError(String enterpriseId, String providerId, String correlatorId) {
        QueryPlanListRequestDto requestDto = new MapperQueryPlanList().bodyRequest(enterpriseId, providerId, correlatorId,Collections.emptyList());
        requestDto.queryPlanListRequest().setCountry("prueba");
        getRequestSpecification().body(requestDto);
        return Utils.convertToJson(requestDto);
    }

    @Given("Se proporciona un body request con providerId invalido")
    public String givenQueryPlanListBodyRequestProviderIdError(String enterpriseId, String providerId, String correlatorId) {
        QueryPlanListRequestDto requestDto = new MapperQueryPlanList().bodyRequest(enterpriseId, providerId, correlatorId,Collections.emptyList());
        requestDto.queryPlanListRequest().setProviderId("providerIdErroneo");
        getRequestSpecification().body(requestDto);
        return Utils.convertToJson(requestDto);
    }
}
