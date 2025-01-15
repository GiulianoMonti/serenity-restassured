package com.claro.automation.planmgmt.testsuite.queryplanlist;


import com.claro.automation.planmgmt.apiaction.queryplanlist.QueryPlanListApiActions;
import com.claro.automation.planmgmt.apiaction.queryplanlist.cross.PlanMgmtProfileApiActions;
import com.claro.automation.planmgmt.dto.queryplanlist.MapperQueryPlanList;
import com.claro.automation.planmgmt.dto.queryplanlist.QueryPlanListDto;
import com.claro.automation.planmgmt.util.Utils;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;
import java.util.List;

@ExtendWith(SerenityJUnit5Extension.class)
@DisplayName("Validar que la informacion obtenida en el response body sea correcta")
class QueryPlanTest {

    private QueryPlanListApiActions queryPlanListApiActions;
    private PlanMgmtProfileApiActions profileApiActions;


    @Test
    @Tag("queryPlanList")
    @EnabledIf("com.claro.automation.planmgmt.util.Utils#isDatabaseCountryAr")
    @Tag("regression")
    @DisplayName("PINTEG-112 TC_Consulta de planes Apple asociados a la combinación enterpriseId con providerId correcto")
    void checkPlansOKApple() {
        var profile = profileApiActions.givenGetMsisdnActiveLineApple();
        List<QueryPlanListDto.ExtensionInfo> extensionInfoList = List.of(
                new QueryPlanListDto.ExtensionInfo(profile.key(), profile.value()));
        var requestDto = new MapperQueryPlanList().bodyRequest(profile.enterpriseId(), profile.providerId(), Utils.createCorrelatorId(), extensionInfoList);
        queryPlanListApiActions.givenQueryPlanListBodyRequest(requestDto);
        queryPlanListApiActions.whenPostResponseQueryPlanList();
        queryPlanListApiActions.thenValidateResponseSuccessSamsungApple(requestDto, "RIO1", "09.20");

    }

    @Test
    @Tag("regression")
    @EnabledIf("com.claro.automation.planmgmt.util.Utils#isDatabaseCountryAr")
    @Tag("queryPlanList")
    @DisplayName("PINTEG-67 TC_Consulta de planes Samsung asociados a la combinación enterpriseId con providerId correcto")
    void checkPlansOKSamsung() {
        var profile = profileApiActions.givenGetMsisdnActiveLineSamsung();
        List<QueryPlanListDto.ExtensionInfo> extensionInfoList = List.of(
                new QueryPlanListDto.ExtensionInfo(profile.key(), profile.value()));
        var requestDto = new MapperQueryPlanList().bodyRequest
                (profile.enterpriseId(), profile.providerId(), Utils.createCorrelatorId(), extensionInfoList);
        queryPlanListApiActions.givenQueryPlanListBodyRequest(requestDto);
        queryPlanListApiActions.whenPostResponseQueryPlanList();
        queryPlanListApiActions.thenValidateResponseSuccessSamsungApple(requestDto, "SAM2", "09.11");
    }

    @Test
    @Tag("QueryPlanList")
    @Tag("regression")
    @DisplayName("PINTEG-71 TC_Consulta de planes asociados a la combinación enterpriseId con providerId correcto")
    void checkPlansOK() {
        var profile = profileApiActions.givenGetMsisdnActiveLine();
        var requestDto = new MapperQueryPlanList().bodyRequest(profile.enterpriseId(), profile.providerId(), Utils.createCorrelatorId(), Collections.emptyList());

        queryPlanListApiActions.givenQueryPlanListBodyRequest(requestDto);
        var actualPlansDatabase = queryPlanListApiActions.whenGetPlanList(profile.providerId(), profile.enterpriseId());
        queryPlanListApiActions.whenPostResponseQueryPlanList();
        queryPlanListApiActions.thenValidateResponseSuccessGm(requestDto, actualPlansDatabase);
    }

    @Test
    @Tag("queryPlanList")
    @DisplayName("PINTEG-49 TC_Consulta de planes - serviceName invalido")
    void checkPlansOKInvalidServiceName() {
        var profile = profileApiActions.givenGetMsisdnActiveLine();
        var requestDto = new MapperQueryPlanList().bodyRequest(profile.enterpriseId(), profile.providerId(), Utils.createCorrelatorId(), Collections.emptyList());
        requestDto.queryPlanListRequest().setServiceName("serviceName invalido");
        queryPlanListApiActions.givenQueryPlanListBodyRequest(requestDto);
        queryPlanListApiActions.whenPostResponseQueryPlanList();
        queryPlanListApiActions.thenValidateResponseBadRequest("9999", "Parameter Invalid Value", requestDto.queryPlanListRequest().getCorrelatorId(), requestDto.queryPlanListRequest().getProviderId());
    }

    @Test
    @Tag("QueryPlanList")
    @DisplayName("PINTEG-50 TC_Consulta de planes - correlatorId invalido")
    void checkPlansOKInvalidCorrelatorId() {
        var profile = profileApiActions.givenGetMsisdnActiveLine();

        var requestDto = new MapperQueryPlanList()
                .bodyRequest(profile.enterpriseId(), profile.providerId(), Utils.createCorrelatorId(), Collections.emptyList());

        requestDto.queryPlanListRequest().setCorrelatorId("correlatorId invalido");
        queryPlanListApiActions.givenQueryPlanListBodyRequest(requestDto);
        queryPlanListApiActions.whenPostResponseQueryPlanList();
        queryPlanListApiActions.thenValidateResponseBadRequest("9999",
                "Parameter Invalid Value",
                requestDto.queryPlanListRequest().getCorrelatorId(),
                requestDto.queryPlanListRequest().getProviderId());
    }

    @Test
    @Tag("queryPlanList")
    @DisplayName("PINTEG-51 TC_Consulta de planes - requestSource vacio")
    void checkPlansOKEmptyRequestSource() {
        var profile = profileApiActions.givenGetMsisdnActiveLine();
        var requestDto = new MapperQueryPlanList().bodyRequest(profile.enterpriseId(), profile.providerId(), Utils.createCorrelatorId(), Collections.emptyList());
        requestDto.queryPlanListRequest().setRequestSource("");
        queryPlanListApiActions.givenQueryPlanListBodyRequest(requestDto);
        queryPlanListApiActions.whenPostResponseQueryPlanList();
        queryPlanListApiActions.thenValidateResponseBadRequest("9999", "Parameter Invalid Value", requestDto.queryPlanListRequest().getCorrelatorId(), requestDto.queryPlanListRequest().getProviderId());
    }
}


