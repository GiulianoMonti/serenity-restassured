package com.claro.automation.planmgmt.facade;


import com.claro.automation.planmgmt.dataaccess.dao.impl.PlanMgmtDaoImpl;
import com.claro.automation.planmgmt.dataaccess.model.CellularProfile;
import com.claro.automation.planmgmt.exception.NoTestDataException;

import java.util.List;

public class GetCellularProfile {

    private final List<CellularProfile> cellularProfile;

    public GetCellularProfile() {
        this.cellularProfile = new PlanMgmtDaoImpl().getCellularProfile();
    }

    public CellularProfile activeProfile(String plan) {
        return cellularProfile.stream()
                .filter(c -> c.plan().equals(plan))
                .findAny().orElseThrow(() -> new NoTestDataException("No getLogWithProviderIdAndCorrelator data for ActiveLine scenario"));
    }

}
