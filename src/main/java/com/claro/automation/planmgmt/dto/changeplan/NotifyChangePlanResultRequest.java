package com.claro.automation.planmgmt.dto.changeplan;


import lombok.Builder;

@Builder
public record NotifyChangePlanResultRequest(
        String resultCode,
        String resultMessage,
        String correlatorId,
        String serviceName,
        String providerId,
        String country
) {
}

