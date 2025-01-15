package com.claro.automation.planmgmt.dto.changeplan;

import io.restassured.http.Header;
import lombok.Builder;

import java.util.List;


@Builder
public record RequestChangePlanDto(
        List<Header> headers,
        String body,
        String msisdn,
        String enterpriseId,
        String providerId,
        Long errorCodeIot,
        ResponseNotifyChangePlanResultDto notifyResponse,
        RequestNotifyChangePlanResultDto notifyRequest
) {
}
