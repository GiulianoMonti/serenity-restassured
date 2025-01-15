package com.claro.automation.planmgmt.dto.changeplan;


import lombok.Builder;

@Builder
public record RequestNotifyChangePlanResultDto(
        NotifyChangePlanResultRequest notifyChangePlanResultRequest
) {}
