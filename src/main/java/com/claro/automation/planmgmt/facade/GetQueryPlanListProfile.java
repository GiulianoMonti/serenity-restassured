package com.claro.automation.planmgmt.facade;


import com.claro.automation.planmgmt.dataaccess.dao.impl.PlanMgmtDaoImpl;
import com.claro.automation.planmgmt.dataaccess.model.QueryPlanListProfile;
import com.claro.automation.planmgmt.exception.NoTestDataException;

import java.util.List;

public class GetQueryPlanListProfile {

    private final List<QueryPlanListProfile> queryPlanListProfile;

    public GetQueryPlanListProfile() {
        this.queryPlanListProfile = new PlanMgmtDaoImpl().getQueryPlanListProfile();
    }

    public QueryPlanListProfile activeProfile(String plan) {
        return queryPlanListProfile.stream()
                .filter(c -> c.plan().equals(plan))
                .findAny().orElseThrow(() -> new NoTestDataException("No getLogWithProviderIdAndCorrelator data for ActiveLine scenario"));
    }

}
