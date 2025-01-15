package com.claro.automation.planmgmt.testsuite.changeplan;

import com.claro.automation.planmgmt.apiaction.changeplan.ChangePlanHeadersApiActions;
import com.claro.automation.planmgmt.apiaction.queryplanlist.cross.PlanMgmtProfileApiActions;
import com.claro.automation.planmgmt.dto.changeplan.ChangePlanRequestDto;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
@DisplayName("Validar los headers obligatorios para changePlan")
class HeadersValidationTest {

    private ChangePlanHeadersApiActions headersApiActions;
    private PlanMgmtProfileApiActions profileApiActions;

    @Test
    @Tag("changePlan")
    @Tag("regression")
    @DisplayName("PINTEG-265 TC_Cambio de plan - no se envia el header password")
    void requestWithoutPassword() {
        headersApiActions.givenHeaderWithoutPassword();
        ChangePlanRequestDto requestDto = headersApiActions.whenPostIncludeRequestBodyChangePlan();
        headersApiActions.thenValidateResponseForbiddenChangePlan(requestDto.changePlanRequest().getCorrelatorId());
    }

    @Test
    @Tag("changePlan")
    @Tag("regression")
    @DisplayName("PINTEG-266 TC_Cambio de plan - no se envia el header username")
    void requestWithoutUsername() {
        headersApiActions.givenHeaderWithoutUsername();
        ChangePlanRequestDto requestDto = headersApiActions.whenPostIncludeRequestBodyChangePlan();
        headersApiActions.thenValidateResponseForbiddenChangePlan(requestDto.changePlanRequest().getCorrelatorId());
    }

    @Test
    @Tag("changePlan")
    @Tag("regression")
    @DisplayName("PINTEG-267 TC_Cambio de plan - no se envian Headers")
    void requestWithoutHeaders() {
        ChangePlanRequestDto requestDto = headersApiActions.whenPostIncludeRequestBodyChangePlan();
        headersApiActions.thenValidateResponseForbiddenChangePlan(requestDto.changePlanRequest().getCorrelatorId());
    }

    @Test
    @Tag("changePlan")
    @DisplayName("PINTEG-141 TC_Cambio de plan - header Username vacio")
    void emptyUsername() {
        headersApiActions.givenEmptyUsername();
        ChangePlanRequestDto requestDto = headersApiActions.whenPostIncludeRequestBodyChangePlan();
        headersApiActions.thenValidateResponseForbiddenChangePlan(requestDto.changePlanRequest().getCorrelatorId());
    }

    @Test
    @Tag("changePlan")
    @DisplayName("PINTEG-142 TC_Cambio de plan - header Password vacio")
    void emptyPassword() {
        headersApiActions.givenEmptyPassword();
        ChangePlanRequestDto requestDto = headersApiActions.whenPostIncludeRequestBodyChangePlan();
        headersApiActions.thenValidateResponseForbiddenChangePlan(requestDto.changePlanRequest().getCorrelatorId());
    }

    @Test
    @Tag("changePlan")
    @DisplayName("PINTEG-143 TC_Cambio de plan - header Username no valido")
    void requestIncorrectUsernameValue() {
        headersApiActions.givenIncorrectUsernameValue();
        ChangePlanRequestDto requestDto = headersApiActions.whenPostIncludeRequestBodyChangePlan();
        headersApiActions.thenValidateResponseForbiddenChangePlan(requestDto.changePlanRequest().getCorrelatorId());
    }

    @Test
    @Tag("changePlan")
    @DisplayName("PINTEG-144 TC_Cambio de plan - header Password no valido")
    void requestIncorrectPasswordValue() {
        headersApiActions.givenIncorrectUsernameValue();
        ChangePlanRequestDto requestDto = headersApiActions.whenPostIncludeRequestBodyChangePlan();
        headersApiActions.thenValidateResponseForbiddenChangePlan(requestDto.changePlanRequest().getCorrelatorId());
    }


    @Test
    @Tag("changePlan")
    @DisplayName("PINTEG-268 TC_Cambio de plan - headers vacios")
    void emptyHeaders() {
        headersApiActions.givenEmptyUsername();
        ChangePlanRequestDto requestDto = headersApiActions.whenPostIncludeRequestBodyChangePlan();
        headersApiActions.thenValidateResponseForbiddenChangePlan(requestDto.changePlanRequest().getCorrelatorId());
    }

    @Test
    @Tag("changePlan")
    @DisplayName("PINTEG-269 TC_Cambio de plan - key header Username incorrecto")
    void requestIncorrectUsernameKey() {
        headersApiActions.givenIncorrectUsernameKey();
        ChangePlanRequestDto requestDto = headersApiActions.whenPostIncludeRequestBodyChangePlan();
        headersApiActions.thenValidateResponseForbiddenChangePlan(requestDto.changePlanRequest().getCorrelatorId());
    }
}
