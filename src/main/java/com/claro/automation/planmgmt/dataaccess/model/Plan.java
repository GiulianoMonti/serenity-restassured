package com.claro.automation.planmgmt.dataaccess.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
    private String productType;
    private String planId;
    private int price;
    private String currency;
    private String mbQuantity;
    private String smsQuantity;
    private String minutesQuantity;
}
