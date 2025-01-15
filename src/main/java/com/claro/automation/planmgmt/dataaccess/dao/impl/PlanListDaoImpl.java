package com.claro.automation.planmgmt.dataaccess.dao.impl;

import com.claro.automation.planmgmt.dataaccess.dao.PlanListDao;
import com.claro.automation.planmgmt.dataaccess.dbconnection.factory.SessionFactoryTProd;
import com.claro.automation.planmgmt.dataaccess.mapper.PlanListMapper;
import com.claro.automation.planmgmt.dataaccess.model.Plan;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class PlanListDaoImpl implements PlanListDao {

    private final SqlSessionFactory sqlSessionTProd;

    public PlanListDaoImpl() {
        sqlSessionTProd = new SessionFactoryTProd().getSqlSession();
    }

    @Override
    public List<Plan> selectPlanList(String providerId, String enterpriseId) {
        try (var session = sqlSessionTProd.openSession()) {
            var mapper = session.getMapper(PlanListMapper.class);
            return mapper.selectPlanList(providerId, enterpriseId);
        }
    }

}
