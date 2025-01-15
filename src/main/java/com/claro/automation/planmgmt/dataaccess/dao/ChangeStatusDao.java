package com.claro.automation.planmgmt.dataaccess.dao;

import org.apache.ibatis.annotations.Param;

public interface ChangeStatusDao {

    void deleteFromChangeStatus(@Param("cellularNumber") String cellularNumber);

}
