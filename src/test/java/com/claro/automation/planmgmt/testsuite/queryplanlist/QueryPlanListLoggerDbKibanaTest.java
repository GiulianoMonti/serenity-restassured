package com.claro.automation.planmgmt.testsuite.queryplanlist;


import com.claro.automation.planmgmt.apiaction.queryplanlist.QueryPlanListLoggerDbKibanaApiActions;
import com.claro.automation.planmgmt.apiaction.queryplanlist.cross.PlanMgmtProfileApiActions;
import com.claro.automation.planmgmt.dataaccess.model.QueryPlanListProfile;
import com.claro.automation.planmgmt.dto.queryplanlist.QueryPlanListDto;
import com.claro.automation.planmgmt.dto.queryplanlist.RequestPlanMgmtDto;
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
@DisplayName("Validar que la informacion enviada al LoggerDB y Kibana es correcta. QueryPlanList")
class QueryPlanListLoggerDbKibanaTest {

    private PlanMgmtProfileApiActions profileApiActions;
    private QueryPlanListLoggerDbKibanaApiActions queryPlanListApiActions;

    @Test
    @Tag("regression")
    @Tag("queryPlanList")
    @DisplayName("PINTEG-46 TC_Consulta de planes asociados a la combinación enterpriseId con providerId correcto - LoggerDB y Kibana")
    void checkPlansOKLoggerDbKibana() throws InterruptedException {
        QueryPlanListProfile profile = profileApiActions.givenGetMsisdnActiveLine();

        var correlatorId = Utils.createCorrelatorId();
        var bodyRequest = queryPlanListApiActions.givenQueryPlanListBodyRequest(profile.enterpriseId(), profile.providerId(), correlatorId, Collections.emptyList());

        var requestDto = RequestPlanMgmtDto
                .builder()
                .body(bodyRequest)
                .enterpriseId(profile.enterpriseId())
                .build();

        var response = queryPlanListApiActions.whenPostResponseQueryPlanList();

        var iotLog = queryPlanListApiActions.whenGetIotLogByProviderIdAndCorrelatorId
                (profile.providerId(), correlatorId);

        queryPlanListApiActions.thenValidateLogInLoggerDb(iotLog, response, requestDto);

        var documentQueryPlanList =
                queryPlanListApiActions.whenGetDocumentElasticByCorrelator(correlatorId);

        queryPlanListApiActions.thenValidateLogInKibana(documentQueryPlanList, response, requestDto);
    }

    @Test
    @Tag("regression")
    @Tag("queryPlanList")
    @DisplayName("PINTEG-47 TC_Consulta de planes - country invalido - LoggerDB y Kibana")
    void checkPlansErrorInvalidCountry() throws InterruptedException {
        var profile = profileApiActions.givenGetMsisdnActiveLine();
        var correlatorId = Utils.createCorrelatorId();
        var bodyRequest = queryPlanListApiActions.givenQueryPlanListBodyRequestCountryError(profile.enterpriseId(), profile.providerId(), correlatorId);

        var requestDto = RequestPlanMgmtDto
                .builder()
                .message("Unexpected value for parameter country.")
                .enterpriseId(profile.enterpriseId())
                .body(bodyRequest)
                .build();

        var response = queryPlanListApiActions.whenPostResponseQueryPlanListBadRequest();

        var iotLog = queryPlanListApiActions.whenGetIotLogByProviderIdAndCorrelatorId
                (profile.providerId(), correlatorId);

        queryPlanListApiActions.thenValidateLogInLoggerDb(iotLog, response, requestDto);
        var documentQueryPlanList =
                queryPlanListApiActions.whenGetDocumentElasticByCorrelator(correlatorId);

        queryPlanListApiActions.thenValidateLogInKibana(documentQueryPlanList, response, requestDto);

    }

    @Test
    @Tag("regression")
    @Tag("queryPlanList")
    @DisplayName("PINTEG-48 TC_Consulta de planes - providerId invalido - LoggerDB - Kibana")
    void checkPlansErrorInvalidProviderId() throws InterruptedException {
        var profile = profileApiActions.givenGetMsisdnActiveLine();
        var correlatorId = Utils.createCorrelatorId();
        var bodyRequest = queryPlanListApiActions.givenQueryPlanListBodyRequestProviderIdError(profile.enterpriseId(), profile.providerId(), correlatorId);

        var requestDto = RequestPlanMgmtDto
                .builder()
                .message("provider id erroneo")
                .enterpriseId(profile.enterpriseId())
                .body(bodyRequest)
                .build();

        var response = queryPlanListApiActions.whenPostResponseQueryPlanList();

        var iotLog = queryPlanListApiActions.whenGetIotLogByProviderIdAndCorrelatorId
                ("providerIdErroneo", correlatorId);

        queryPlanListApiActions.thenValidateLogInLoggerDb(iotLog, response, requestDto);
        var documentQueryPlanList =
                queryPlanListApiActions.whenGetDocumentElasticByCorrelator(correlatorId);

        queryPlanListApiActions.thenValidateLogInKibana(documentQueryPlanList, response, requestDto);

    }

    @Test
    @Tag("queryPlanList")
    @Tag("regression")
    @EnabledIf("com.claro.automation.planmgmt.util.Utils#isDatabaseCountryAr")
    @DisplayName("PINTEG-1706 TC_Consulta de planes Apple -LoggerDb Kibana")
    void checkPlansOKApple() throws InterruptedException {
        var profile = profileApiActions.givenGetMsisdnActiveLineApple();
        var correlatorId = Utils.createCorrelatorId();

        List<QueryPlanListDto.ExtensionInfo> extensionInfoList = List.of(
                new QueryPlanListDto.ExtensionInfo(profile.key(), profile.value()));
        var bodyRequest = queryPlanListApiActions.givenQueryPlanListBodyRequest(profile.enterpriseId(), profile.providerId(), correlatorId, extensionInfoList);

        var requestDto = RequestPlanMgmtDto
                .builder()
                .body(bodyRequest)
                .enterpriseId(profile.enterpriseId())
                .build();

        var response = queryPlanListApiActions.whenPostResponseQueryPlanList();

        var iotLog = queryPlanListApiActions.whenGetIotLogByProviderIdAndCorrelatorId(profile.providerId(), correlatorId);

        queryPlanListApiActions.thenValidateLogInLoggerDb(iotLog, response, requestDto);

        var documentQueryPlanList =
                queryPlanListApiActions.whenGetDocumentElasticByCorrelator(correlatorId);

        queryPlanListApiActions.thenValidateLogInKibana(documentQueryPlanList, response, requestDto);

    }

}
