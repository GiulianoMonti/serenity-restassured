package com.claro.automation.planmgmt.dto.changeplan;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record ResponseChangePlanDto(
        ChangePlanResponse changePlanResponse
) {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    public record ChangePlanResponse(
            int resultCode,
            String resultMessage,
            String correlatorId,
            String country,
            String providerId
    ) {
    }
}
