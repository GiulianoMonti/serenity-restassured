package com.claro.automation.planmgmt.dataaccess.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public record QueryPlanListProfile(
        String enterpriseId,
        String status,
        String providerId,
        String plan,
        String key,
        String value
) {}
