package com.claro.automation.planmgmt.dataaccess.dao;

import com.claro.automation.planmgmt.dataaccess.model.Plan;

import java.util.List;

public interface PlanListDao {
    List<Plan> selectPlanList(String providerId, String enterpriseId);
}
