package com.claro.automation.planmgmt.testsuite.changeplan;


import com.claro.automation.planmgmt.apiaction.changeplan.ChangePlanApiActions;
import com.claro.automation.planmgmt.apiaction.queryplanlist.cross.PlanMgmtProfileApiActions;
import com.claro.automation.planmgmt.dto.changeplan.ChangePlanRequestDto;
import com.claro.automation.planmgmt.dto.changeplan.MapperChangePlan;
import com.claro.automation.planmgmt.util.Utils;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
@DisplayName("Validar que la informacion obtenida en el response body sea correcta")
class ChangePlanTest {

    private ChangePlanApiActions changePlanApiActions;
    private PlanMgmtProfileApiActions profileApiActions;


    @Test
    @Tag("regression")
    @Tag("changePlan")
    @DisplayName("PINTEG-136 TC_Cambio de plan - Error - se envia serviceName erroneo")
    void changePlanIncorrectServiceName() {
        var profile = profileApiActions.givenGetMsisdnActiveLineChangePlan();
        var correlatorId = Utils.createCorrelatorId();

        ChangePlanRequestDto requestDto = new MapperChangePlan().bodyRequest(profile.cellularNumber(), "AH002", "GMCLAROAR", profile.providerId(), correlatorId);
        requestDto.changePlanRequest().setServiceName("queryPlanList");
        changePlanApiActions.givenChangePlanBodyRequest(requestDto);
        changePlanApiActions.whenPostResponseChangePlan();
        changePlanApiActions.thenValidateResponseBadRequest(9999, "Parameter Invalid Value", correlatorId);
    }


    @Test
    @Tag("regression")
    @Tag("changePlan")
    @DisplayName("PINTEG-134 TC_Cambio de plan - Error - se envia correlatorId erroneo")
    void changePlanIncorrectCorrelatorId() {
        var profile = profileApiActions.givenGetMsisdnActiveLineChangePlan();
        var correlatorId = (Utils.createCorrelatorId() + 1);
        ChangePlanRequestDto requestDto = new MapperChangePlan().bodyRequest(profile.cellularNumber(), "AH002", "GMCLAROAR", profile.providerId(), correlatorId);
        changePlanApiActions.givenChangePlanBodyRequest(requestDto);
        changePlanApiActions.whenPostResponseChangePlan();
        changePlanApiActions.thenValidateResponseBadRequest(9999, "Parameter Invalid Value", correlatorId);
    }


    @Test
    @Tag("changePlan")
    @DisplayName("PINTEG-123 TC_Cambio de plan - Error - se envia un country erroneo")
    void changePlanIncorrectCountry() {
        var profile = profileApiActions.givenGetMsisdnActiveLineChangePlan();
        var correlatorId = Utils.createCorrelatorId();
        ChangePlanRequestDto requestDto = new MapperChangePlan().bodyRequest(profile.cellularNumber(), "AH002", "GMCLAROAR", profile.providerId(), correlatorId);
        requestDto.changePlanRequest().setCountry("AUP");
        changePlanApiActions.givenChangePlanBodyRequest(requestDto);
        changePlanApiActions.whenPostResponseChangePlan();
        changePlanApiActions.thenValidateResponseBadRequest(9999, "Parameter Invalid Value", correlatorId);
    }


    @Test
    @Tag("changePlan")
    @DisplayName("PINTEG-122 TC_Cambio de plan - Error - se envia un enterpriseId vacio")
    void changePlanEmptyEnterpriseId() {
        var profile = profileApiActions.givenGetMsisdnActiveLineChangePlan();
        var correlatorId = Utils.createCorrelatorId();
        var requestDto = new MapperChangePlan().bodyRequest(profile.cellularNumber(), "AH002", "", profile.providerId(), correlatorId);
        changePlanApiActions.givenChangePlanBodyRequest(requestDto);
        changePlanApiActions.whenPostResponseChangePlan();
        changePlanApiActions.thenValidateResponseBadRequest(9999, "Parameter Invalid Value", correlatorId);
    }

    @Test
    @DisplayName("PINTEG-121 TC_Cambio de plan - Error - se envia un planId vacio")
    void changePlanEmptyplanId() {
        var profile = profileApiActions.givenGetMsisdnActiveLineChangePlan();
        var correlatorId = Utils.createCorrelatorId();
        var requestDto = new MapperChangePlan().bodyRequest(profile.cellularNumber(), "", profile.enterpriseId(), profile.providerId(), correlatorId);
        changePlanApiActions.givenChangePlanBodyRequest(requestDto);
        changePlanApiActions.whenPostResponseChangePlan();
        changePlanApiActions.thenValidateResponseBadRequest(9999, "Parameter Invalid Value", correlatorId);
    }

    @Test
    @Tag("changePlan")
    @DisplayName("PINTEG-120 TC_Cambio de plan - Error - se envia un numero de celular vacio")
    void changePlanEmptyMsisdn() {
        var profile = profileApiActions.givenGetMsisdnActiveLineChangePlan();
        var correlatorId = Utils.createCorrelatorId();
        var requestDto = new MapperChangePlan().bodyRequest("", "AH002", profile.enterpriseId(), profile.providerId(), correlatorId);
        changePlanApiActions.givenChangePlanBodyRequest(requestDto);
        changePlanApiActions.whenPostResponseChangePlan();
        changePlanApiActions.thenValidateResponseBadRequest(9999, "Parameter Invalid Value", correlatorId);
    }

    @Test
    @Tag("changePlan")
    @Tag("regression")
    @DisplayName("PINTEG-119 TC_Cambio de plan - Error - se envia un numero de celular no existente")
    void changePlanInvalidMsisdn() {
        var profile = profileApiActions.givenGetMsisdnActiveLineChangePlan();
        var correlatorId = Utils.createCorrelatorId();
        var requestDto = new MapperChangePlan().bodyRequest("54938562900241", "AH002", profile.enterpriseId(), profile.providerId(), correlatorId);
        changePlanApiActions.givenChangePlanBodyRequest(requestDto);
        changePlanApiActions.whenPostResponseChangePlan();
        changePlanApiActions.thenValidateResponseSuccess(correlatorId);
    }
}
