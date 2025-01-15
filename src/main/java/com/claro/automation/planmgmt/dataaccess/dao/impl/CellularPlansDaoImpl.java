package com.claro.automation.planmgmt.dataaccess.dao.impl;

import com.claro.automation.planmgmt.dataaccess.dao.CellularPlansDao;
import com.claro.automation.planmgmt.dataaccess.dbconnection.factory.SessionFactoryTProd;
import com.claro.automation.planmgmt.dataaccess.mapper.CellularPlansMapper;
import com.claro.automation.planmgmt.dataaccess.model.CellularPlans;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Optional;

public class CellularPlansDaoImpl implements CellularPlansDao {

    private final SqlSessionFactory sqlSessionTProd;

    public CellularPlansDaoImpl() {
        sqlSessionTProd = new SessionFactoryTProd().getSqlSession();
    }

    @Override
    public Optional<CellularPlans> selectCellularPlans(String cellularNumber) {
        try (var session = sqlSessionTProd.openSession()) {
            var mapper = session.getMapper(CellularPlansMapper.class);
            return mapper.selectCellularPlans(cellularNumber);
        }
    }
}
