package com.claro.automation.planmgmt.dto.queryplanlist;

import lombok.Builder;
import lombok.Getter;


@Builder
public record QueryPlanListRequestDto(
        QueryPlanListDto queryPlanListRequest
) {
}
