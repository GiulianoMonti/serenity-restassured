package com.claro.automation.planmgmt.testsuite.queryplanlist;

import com.claro.automation.planmgmt.apiaction.queryplanlist.QueryPlanListHeadersApiActions;
import com.claro.automation.planmgmt.apiaction.queryplanlist.cross.PlanMgmtProfileApiActions;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
@DisplayName("Validar los headers obligatorios para queryPlanList")
class HeadersValidationTest {

    private QueryPlanListHeadersApiActions headersApiActions;
    private PlanMgmtProfileApiActions profileApiActions;

    @Test
    @Tag("regression")
    @Tag("queryPlanList")
    @DisplayName("PINTEG-52 TC_Consulta de planes - no se envia header password")
    void headerWithoutPassword() {
        headersApiActions.givenHeaderWithoutPassword();
        var requestDto = headersApiActions.whenPostIncludeRequestBody();
        headersApiActions.thenValidateResponseForbidden(requestDto.queryPlanListRequest().getCorrelatorId());
    }

    @Test
    @Tag("queryPlanList")
    @DisplayName("PINTEG-53 TC_Consulta de planes - no se envia header username")
    void headerWithoutUsername() {
        headersApiActions.givenHeaderWithoutUsername();
        var requestDto = headersApiActions.whenPostIncludeRequestBody();
        headersApiActions.thenValidateResponseForbidden(requestDto.queryPlanListRequest().getCorrelatorId());
    }

    @Test
    @Tag("queryPlanList")
    @DisplayName("PINTEG-66 TC_Consulta de planes - no se envian Headers")
    void requestWithoutHeaders() {
        var requestDto = headersApiActions.whenPostIncludeRequestBody();
        headersApiActions.thenValidateResponseForbidden(requestDto.queryPlanListRequest().getCorrelatorId());
    }

    @Test
    @Tag("queryPlanList")
    @DisplayName("PINTEG-68 TC_Consulta de planes - username vacio en headers")
    void headerEmptyUsername() {
        headersApiActions.givenEmptyUsername();
        var requestDto = headersApiActions.whenPostIncludeRequestBody();
        headersApiActions.thenValidateResponseForbidden(requestDto.queryPlanListRequest().getCorrelatorId());
    }

    @Test
    @Tag("queryPlanList")
    @DisplayName("PINTEG-69 TC_Consulta de planes - password vacio en headers")
    void headerEmptyPassword() {
        headersApiActions.givenEmptyPassword();
        var requestDto = headersApiActions.whenPostIncludeRequestBody();
        headersApiActions.thenValidateResponseForbidden(requestDto.queryPlanListRequest().getCorrelatorId());
    }


    @Test
    @Tag("queryPlanList")
    @DisplayName("PINTEG-70 TC_Consulta de planes - headers vacios")
    void emptyHeaders() {
        headersApiActions.givenEmptyUsername();
        var requestDto = headersApiActions.whenPostIncludeRequestBody();
        headersApiActions.thenValidateResponseForbidden(requestDto.queryPlanListRequest().getCorrelatorId());
    }

    @Test
    @Tag("queryPlanList")
    @Tag("regression")
    @DisplayName("PINTEG-75 TC_Consulta de planes - se envia value username incorrecto en los headers")
    void headerIncorrectUsernameValue() {
        headersApiActions.givenIncorrectUsernameValue();
        var requestDto = headersApiActions.whenPostIncludeRequestBody();
        headersApiActions.thenValidateResponseForbidden(requestDto.queryPlanListRequest().getCorrelatorId());
    }

    @Test
    @DisplayName("PINTEG-76 TC_Consulta de planes - se envia key username incorrecto en los headers")
    void headerIncorrectUsernameKey() {
        headersApiActions.givenIncorrectUsernameKey();
        var requestDto = headersApiActions.whenPostIncludeRequestBody();
        headersApiActions.thenValidateResponseForbidden(requestDto.queryPlanListRequest().getCorrelatorId());
    }
}