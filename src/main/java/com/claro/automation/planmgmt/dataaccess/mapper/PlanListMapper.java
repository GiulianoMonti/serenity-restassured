package com.claro.automation.planmgmt.dataaccess.mapper;

import com.claro.automation.planmgmt.dataaccess.model.Plan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlanListMapper {

    List<Plan> selectPlanList(@Param("providerId") String providerId,
                              @Param("enterpriseId") String enterpriseId);
}