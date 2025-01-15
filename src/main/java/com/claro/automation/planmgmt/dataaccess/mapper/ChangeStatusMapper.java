package com.claro.automation.planmgmt.dataaccess.mapper;

import org.apache.ibatis.annotations.Param;

public interface ChangeStatusMapper {

    void deleteFromChangeStatus(@Param("cellularNumber") String cellularNumber);
}