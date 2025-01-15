package com.claro.automation.planmgmt.dataaccess.mapper;

import com.claro.automation.planmgmt.dataaccess.model.CellularPlans;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

public interface CellularPlansMapper {

    Optional<CellularPlans> selectCellularPlans(@Param("cellularNumber") String cellularNumber);
}