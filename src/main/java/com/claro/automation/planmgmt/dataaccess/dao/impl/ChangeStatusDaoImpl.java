package com.claro.automation.planmgmt.dataaccess.dao.impl;

import com.claro.automation.planmgmt.dataaccess.dao.ChangeStatusDao;
import com.claro.automation.planmgmt.dataaccess.dbconnection.factory.SessionFactoryTProd;
import com.claro.automation.planmgmt.dataaccess.mapper.ChangeStatusMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class ChangeStatusDaoImpl implements ChangeStatusDao {

    private final SqlSessionFactory sqlSessionTProd;

    public ChangeStatusDaoImpl() {
        this.sqlSessionTProd = new SessionFactoryTProd().getSqlSession();
    }

    @Override
    public void deleteFromChangeStatus(String cellularNumber) {
        try (SqlSession session = sqlSessionTProd.openSession()) {
            ChangeStatusMapper mapper = session.getMapper(ChangeStatusMapper.class);
            mapper.deleteFromChangeStatus(cellularNumber);
            session.commit();
        }
    }
}
