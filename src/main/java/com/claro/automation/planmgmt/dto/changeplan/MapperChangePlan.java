package com.claro.automation.planmgmt.dto.changeplan;

import com.claro.automation.planmgmt.util.VariableByCountry;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class MapperChangePlan {

    public ChangePlanRequestDto bodyRequest(String msisdn,
                                            String planId,
                                            String enterpriseId,
                                            String providerId,
                                            String correlatorId) {
        ChangePlanDto changePlanRequest = ChangePlanDto.builder()
                .msisdn(msisdn)
                .planId(planId)
                .enterpriseId(enterpriseId)
                .country(VariableByCountry.getCountryShortName())
                .channel("API")
                .providerId(providerId)
                .correlatorId(correlatorId)
                .requestSource("M2M")
                .serviceName("changePlan")
                .extensionInfo(Collections.emptyList())
                .build();
        return ChangePlanRequestDto.builder()
                .changePlanRequest(changePlanRequest)
                .build();
    }

    public ResponseChangePlanDto bodyResponse(int expectedResultCode,
                                              String expectedResultMessage,
                                              String expectedCorrelatorId) {
        return ResponseChangePlanDto.builder()
                .changePlanResponse(
                        ResponseChangePlanDto.ChangePlanResponse.builder()
                                .resultCode(expectedResultCode)
                                .resultMessage(expectedResultMessage)
                                .correlatorId(expectedCorrelatorId)
                                .build()
                )
                .build();
    }

    public RequestNotifyChangePlanResultDto bodyNotifyChangePlanResultRequest(String resultCode,
                                                                              String resultMessage,
                                                                              String correlatorId,
                                                                              String providerId) {
        NotifyChangePlanResultRequest notifyChangePlanResultRequest = NotifyChangePlanResultRequest.builder()
                .resultCode(resultCode)
                .resultMessage(resultMessage)
                .correlatorId(correlatorId)
                .serviceName("changePlan")
                .providerId(providerId)
                .country(VariableByCountry.getCountryShortName())
                .build();

        return RequestNotifyChangePlanResultDto.builder()
                .notifyChangePlanResultRequest(notifyChangePlanResultRequest)
                .build();
    }
    public ResponseNotifyChangePlanResultDto bodyNotifyChangePlanResultResponse(int resultCode,
                                                                               String resultMessage,
                                                                               String correlatorId) {
        NotifyChangePlanResultResponse notifyChangePlanResultResponse = NotifyChangePlanResultResponse.builder()
                .resultCode(resultCode)
                .resultMessage(resultMessage)
                .correlatorId(correlatorId)
                .build();

        return ResponseNotifyChangePlanResultDto.builder()
                .notifyChangePlanResultResponse(notifyChangePlanResultResponse)
                .build();
    }
}
