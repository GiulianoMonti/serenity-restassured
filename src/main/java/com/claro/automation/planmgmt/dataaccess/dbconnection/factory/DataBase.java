package com.claro.automation.planmgmt.dataaccess.dbconnection.factory;

import org.apache.ibatis.session.SqlSessionFactory;

public interface DataBase {

    SqlSessionFactory getDataBaseSession();
}
