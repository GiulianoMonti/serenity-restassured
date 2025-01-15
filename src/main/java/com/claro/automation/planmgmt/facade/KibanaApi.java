package com.claro.automation.planmgmt.facade;

import com.claro.automation.dbloggerelastic.elasticsearch.api.ElasticsearchApi;
import com.claro.automation.planmgmt.dto.elasticsearch.DocumentQueryPlanList;
import com.claro.automation.planmgmt.exception.assertion.NoDataFoundAssertionError;
import com.claro.automation.planmgmt.exception.assertion.RepeatedElasticDocumentAssertionError;
import com.claro.automation.planmgmt.util.SerenityEnvironmentVariables;
import com.claro.automation.planmgmt.util.VariableByCountry;
import net.serenitybdd.core.Serenity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class KibanaApi {

    private final ElasticsearchApi<DocumentQueryPlanList> elasticsearchApi;
    private final Integer elasticSearchAttempts;
    private final Long elasticSearchWait;

    public KibanaApi( ) {
        this.elasticsearchApi = new ElasticsearchApi<>(
                DocumentQueryPlanList.class
        );
        this.elasticSearchAttempts = SerenityEnvironmentVariables.getElasticsearchAttempts();
        this.elasticSearchWait = SerenityEnvironmentVariables.getElasticsearchWaitInMilliseconds();
    }

    public DocumentQueryPlanList getDocumentByCorrelator(String correlatorId) {
        var correlator = Map.entry("CorrelatorId", correlatorId);
        var date = Map.entry("Fecha", VariableByCountry.getLocalTime().minusYears(10));

        var documentCommunicationList = elasticsearchApi.searchDocByOneFieldAndDateGreater(correlator, date);
        var attemptsToFind = elasticsearchApi.getAttemptsToFind();

        if (documentCommunicationList.isEmpty()) {
            throw new NoDataFoundAssertionError("No data found CorrelatorId: " + correlatorId + " in " + elasticSearchAttempts + " attempts.");
        }
        if (documentCommunicationList.size() > 1) {
            throw new RepeatedElasticDocumentAssertionError("The search returned more than one result with the CorrelatorId: " + correlatorId);
        }
        Serenity.recordReportData().withTitle("Kibana API OK").andContents(
                "The document found in " + attemptsToFind + " attempts and " + elasticSearchWait * attemptsToFind + " milliseconds");

        return documentCommunicationList.getFirst();
    }
}
