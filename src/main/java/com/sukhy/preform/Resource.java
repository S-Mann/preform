/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sukhy.preform;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author sukhi
 */
public class Resource implements AutoCloseable {

    private final String DB_URL = "dbUrl";
    private final String DB_NAME = "dbName";
    private final String SCHEMA = "schema";
    private final String TABLE_NAME = "tableName";
    private final String USER = "user";
    private final String DRIVER = "driver";
    private final String PASSWORD = "password";
    private Connection connection;
    private Properties properties;

    public Resource() throws Exception {
        InputStream inputStream = new FileInputStream(new File(".\\src\\main\\resources\\config\\settings.properties"));
        properties = new Properties();
        properties.load(inputStream);
        if (properties.getProperty(DRIVER).equalsIgnoreCase("Postgres")) {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://" + properties.getProperty(DB_URL) + "/" + properties.getProperty(DB_NAME),
                    properties.getProperty(USER),
                    properties.getProperty(PASSWORD));
        }
    }

    public List getColumnDataTypes() throws Exception {
        PreparedStatement prepareStatement = connection.prepareStatement("select column_name,column_default,udt_name "
                + "from information_schema.columns "
                + "where table_name= ? and table_schema= ?");
        prepareStatement.setString(1, properties.getProperty(TABLE_NAME));
        prepareStatement.setString(2, properties.getProperty(SCHEMA));
        ResultSet executeQuery = prepareStatement.executeQuery();
        ResultSetMetaData metaData = executeQuery.getMetaData();
        int columnCount = metaData.getColumnCount();
        ArrayList list = new ArrayList();
        while (executeQuery.next()) {
            HashMap row = new HashMap(columnCount);
            for (int i = 1; i <= columnCount; ++i) {
                row.put(metaData.getColumnName(i), executeQuery.getObject(i));
            }
            list.add(row);
        }
        return list;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

}
