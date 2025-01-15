package com.claro.automation.planmgmt.dataaccess.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CellularPlans(
        String cellularNumber,
        String stgId,
        String rplId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        LocalDateTime dueDate,
        String tckId,
        String usrId,
        LocalDateTime lastUpdatedDate,
        LocalDateTime addDate,
        String entId,
        String cfeSdsId,
        String downCargId,
        Double downAmount,
        Boolean rplOnline

) {
}
