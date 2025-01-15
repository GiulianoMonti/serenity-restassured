package com.claro.automation.planmgmt.dto.queryplanlist;

import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryPlanListDto {
    private String enterpriseId;
    private String country;
    private String providerId;
    private String serviceName;
    private String correlatorId;
    private String requestSource;
    private String channel;
    private List<ExtensionInfo> extensionInfo;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExtensionInfo {
        private String key;
        private String value;
    }
}
