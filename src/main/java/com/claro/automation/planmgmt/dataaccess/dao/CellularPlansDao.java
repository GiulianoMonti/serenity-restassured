package com.claro.automation.planmgmt.dataaccess.dao;

import com.claro.automation.planmgmt.dataaccess.model.CellularPlans;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

public interface CellularPlansDao {
    Optional<CellularPlans> selectCellularPlans(@Param("cellularNumber") String cellularNumber);

}
