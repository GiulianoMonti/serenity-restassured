package com.claro.automation.planmgmt.dataaccess.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChangeStatus(
        String cellularNumber,
        String status,
        LocalDateTime startDate,
        LocalDateTime endDate,
        LocalDateTime lastUpdateDate,
        String userId,
        String userIdLastUpdate,
        String reasonCodeCm,
        String reasonCodeStl
) {
}
