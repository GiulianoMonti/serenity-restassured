package com.claro.automation.planmgmt.dto.queryplanlist;

import com.claro.automation.planmgmt.dto.changeplan.RequestNotifyChangePlanResultDto;
import com.claro.automation.planmgmt.dto.changeplan.ResponseNotifyChangePlanResultDto;
import io.restassured.http.Header;
import lombok.Builder;

import java.util.List;


@Builder
public record RequestPlanMgmtDto(
        List<Header> headers,
        String body,
        String message,
        String enterpriseId,
        String msisdn,
        String providerId,
        String plandId,
        String errorMessageIot,
        Long errorCodeIot,
        ResponseNotifyChangePlanResultDto notifyResponse,
        RequestNotifyChangePlanResultDto notifyRequest
) {
}
