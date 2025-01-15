package com.claro.automation.planmgmt.dataaccess.dbconnection.factory;

import org.apache.ibatis.session.SqlSessionFactory;

public abstract class SessionFactoryBase {

    public SqlSessionFactory getSqlSession() {
        DataBase dataBase = this::getSqlSessionFactory;
        return dataBase.getDataBaseSession();
    }

    public abstract SqlSessionFactory getSqlSessionFactory();
}
