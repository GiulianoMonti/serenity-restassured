package com.claro.automation.planmgmt.dto.changeplan;


import lombok.Builder;

@Builder
public record NotifyChangePlanResultResponse(
        int resultCode,
        String resultMessage,
        String correlatorId
) {
}

