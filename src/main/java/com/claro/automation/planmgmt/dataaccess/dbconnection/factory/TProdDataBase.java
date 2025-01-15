package com.claro.automation.planmgmt.dataaccess.dbconnection.factory;


import com.claro.automation.planmgmt.exception.DataBaseCountryNotFoundException;
import com.claro.automation.planmgmt.exception.MyBatisFileException;
import com.claro.automation.planmgmt.util.SerenityEnvironmentVariables;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TProdDataBase implements DataBase {

    private enum SCHEMA {
        ARTPROD, PYTPROD, UYTPROD
    }

    @Override
    public SqlSessionFactory getDataBaseSession() {
        String country = SerenityEnvironmentVariables.getDataBaseCountry();
        var path = Paths.get("src/main/resources/mybatis-config.xml");
        try (InputStream dataSource = Files.newInputStream(path)) {
            return switch (country) {
                case "ar" -> new SqlSessionFactoryBuilder().build(dataSource, SCHEMA.ARTPROD.name());
                case "py" -> new SqlSessionFactoryBuilder().build(dataSource, SCHEMA.PYTPROD.name());
                case "uy" -> new SqlSessionFactoryBuilder().build(dataSource, SCHEMA.UYTPROD.name());
                default -> throw new DataBaseCountryNotFoundException("the country: " + country + " not found");
            };
        } catch (IOException e) {
            throw new MyBatisFileException(e.toString());
        }
    }

}
