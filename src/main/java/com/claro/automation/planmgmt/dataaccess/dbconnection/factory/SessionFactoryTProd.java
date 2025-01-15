package com.claro.automation.planmgmt.dataaccess.dbconnection.factory;

import org.apache.ibatis.session.SqlSessionFactory;

public class SessionFactoryTProd extends SessionFactoryBase {
    @Override
    public SqlSessionFactory getSqlSessionFactory() {
        return new TProdDataBase().getDataBaseSession();
    }
}
