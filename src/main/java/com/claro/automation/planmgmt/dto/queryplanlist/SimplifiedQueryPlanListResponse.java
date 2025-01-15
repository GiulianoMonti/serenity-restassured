package com.claro.automation.planmgmt.dto.queryplanlist;

public record SimplifiedQueryPlanListResponse(
        String resultCode,
        String resultMessage,
        String country,
        String correlatorId,
        String providerId
) {}