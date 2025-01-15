package com.claro.automation.planmgmt.dto.queryplanlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public record ResponseQueryPlanListDto(
        QueryPlanListResponse queryPlanListResponse
) {
    @Builder
    public record QueryPlanListResponse(
            String resultCode,
            String resultMessage,
            String country,
            String correlatorId,
            String providerId,
            List<Plan> plans,
            List<ExtensionInfo> extensionInfo // Cambiado a una lista de objetos ExtensionInfo
    ) {}

    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Plan(
            String productType,
            String planId,
            double price,
            String currency,
            String tier1,
            String mbQuantity,
            String smsQuantity,
            String minutesQuantity
    ) {}

    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ExtensionInfo(
            String key,    // Ejemplo de propiedades que podría contener cada objeto en extensionInfo
            String value
    ) {}
}