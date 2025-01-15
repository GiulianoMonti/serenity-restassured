package com.claro.automation.planmgmt.dto.changeplan;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePlanDto {
    private String msisdn;
    private String planId;
    private String enterpriseId;
    private String country;
    private String channel;
    private String providerId;
    private String correlatorId;
    private String requestSource;
    private String serviceName;
    private List<ExtensionInfo> extensionInfo;

    @Getter
    @Setter
    @Builder
    public static class ExtensionInfo {
    }
}