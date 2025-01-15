package com.claro.automation.planmgmt.dataaccess.dao;

import com.claro.automation.planmgmt.dataaccess.model.CellularProfile;
import com.claro.automation.planmgmt.dataaccess.model.QueryPlanListProfile;
import com.claro.automation.planmgmt.exception.NoTestDataException;
import com.claro.automation.planmgmt.exception.ObjectMapperException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public interface PlanMgmtDao {

    ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    List<QueryPlanListProfile> getQueryPlanListProfile();

    List<CellularProfile> getCellularProfile();

    default List<QueryPlanListProfile> getQueryPlanListProfile(String filePath) {
        QueryPlanListProfile[] planListAvailable;
        try {
            planListAvailable = OBJECT_MAPPER.readValue(new File(filePath), QueryPlanListProfile[].class);
        } catch (IOException e) {
            throw new ObjectMapperException(e.getMessage());
        }
        if (planListAvailable.length == 0) {
            throw new NoTestDataException("No input data available");
        }

        return Arrays.asList(planListAvailable);
    }

    default List<CellularProfile> getCellularProfile(String filePath) {
        CellularProfile[] planListAvailable;
        try {
            planListAvailable = OBJECT_MAPPER.readValue(new File(filePath), CellularProfile[].class);
        } catch (IOException e) {
            throw new ObjectMapperException(e.getMessage());
        }
        if (planListAvailable.length == 0) {
            throw new NoTestDataException("No input data available");
        }

        return Arrays.asList(planListAvailable);
    }
}