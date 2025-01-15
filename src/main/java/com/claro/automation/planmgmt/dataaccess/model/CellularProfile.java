package com.claro.automation.planmgmt.dataaccess.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record CellularProfile(
        String enterpriseId,
        String providerId,
        String id,
        String cellularNumber,
        String plan
) {
}
