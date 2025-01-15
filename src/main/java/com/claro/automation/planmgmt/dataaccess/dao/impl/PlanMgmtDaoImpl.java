package com.claro.automation.planmgmt.dataaccess.dao.impl;


import com.claro.automation.planmgmt.dataaccess.dao.PlanMgmtDao;
import com.claro.automation.planmgmt.dataaccess.model.CellularProfile;
import com.claro.automation.planmgmt.dataaccess.model.QueryPlanListProfile;
import com.claro.automation.planmgmt.util.SerenityEnvironmentVariables;

import java.util.List;

public class PlanMgmtDaoImpl implements PlanMgmtDao {

    @Override
    public List<QueryPlanListProfile> getQueryPlanListProfile() {
        var country = SerenityEnvironmentVariables.getDataBaseCountry();
        return getQueryPlanListProfile("src/main/resources/data/" + country + "/enterprise_profile.json");
    }

    @Override
    public List<CellularProfile> getCellularProfile() {
        var country = SerenityEnvironmentVariables.getDataBaseCountry();
        return getCellularProfile("src/main/resources/data/" + country + "/enterprise_profile_changeplan.json");
    }
}
