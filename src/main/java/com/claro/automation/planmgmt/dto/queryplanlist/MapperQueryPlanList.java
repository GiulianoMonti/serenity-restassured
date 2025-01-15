package com.claro.automation.planmgmt.dto.queryplanlist;

import com.claro.automation.planmgmt.dataaccess.model.Plan;
import com.claro.automation.planmgmt.util.VariableByCountry;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class MapperQueryPlanList {

    public QueryPlanListRequestDto bodyRequest(String enterpriseId, String providerId, String correlatorId, List<QueryPlanListDto.ExtensionInfo> extensionInfo) {
        QueryPlanListDto queryPlanListRequest = QueryPlanListDto.builder()
                .enterpriseId(enterpriseId)
                .country(VariableByCountry.getCountryShortName())
                .providerId(providerId)
                .serviceName("queryPlanList")
                .correlatorId(correlatorId)
                .requestSource("M2M")
                .channel("API")
                .extensionInfo(extensionInfo)
                .build();

        return QueryPlanListRequestDto.builder()
                .queryPlanListRequest(queryPlanListRequest)
                .build();
    }

    public List<ResponseQueryPlanListDto.Plan> toPlanList(List<Plan> planList) {
        return planList.stream()
                .map(plan -> ResponseQueryPlanListDto.Plan.builder()
                        .productType(plan.getProductType())
                        .planId(plan.getPlanId())
                        .price(plan.getPrice())
                        .currency(plan.getCurrency())
                        .mbQuantity(plan.getMbQuantity())
                        .minutesQuantity(plan.getMinutesQuantity())
                        .smsQuantity(plan.getSmsQuantity())
                        .build())
                .toList();
    }


    public List<ResponseQueryPlanListDto.Plan> buildExpectedPlans(String planId) {
        return List.of(
                ResponseQueryPlanListDto.Plan.builder()
                        .productType("Conectividad Smartwatch")
                        .planId(planId)
                        .price(1850.0)
                        .currency("$")
                        .tier1("IVA INCLUIDO")
                        .mbQuantity("0")
                        .smsQuantity("0")
                        .minutesQuantity("0")
                        .build()
        );
    }
}
