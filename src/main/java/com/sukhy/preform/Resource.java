/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sukhy.preform;

import static com.sukhy.preform.Constants.DB_NAME;
import static com.sukhy.preform.Constants.DB_URL;
import static com.sukhy.preform.Constants.DRIVER;
import static com.sukhy.preform.Constants.PASSWORD;
import static com.sukhy.preform.Constants.SCHEMA;
import static com.sukhy.preform.Constants.TABLE_NAME;
import static com.sukhy.preform.Constants.USER;
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
 * @author Sukhy <https://github.com/S-Mann>
 */
public class Resource implements AutoCloseable {

    private Connection connection;
    private Properties properties;
    private String columns,values,condition;

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

    public List<HashMap> getColumnDetails() throws Exception {
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

    public boolean createResource() throws Exception {
        try {
            List<HashMap> hashMaps = getColumnDetails();
            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();
            StringBuilder condition = new StringBuilder();
            for (HashMap hashMap : hashMaps) {
                columns.append(hashMap.get(Constants.PG_COLUMN_NAME) + " ");
                values.append("$" + hashMap.get(Constants.PG_COLUMN_NAME) + "::" + hashMap.get(Constants.PG_DATA_TYPE) + " ");
                if (hashMap.get(Constants.PG_COLUMN_NAME).equals(Constants.TABLE_ID)) {
                    condition.append(hashMap.get(Constants.PG_COLUMN_NAME) + "=$" + hashMap.get(Constants.PG_COLUMN_NAME) + "::"
                            + hashMap.get(Constants.PG_DATA_TYPE) + " ");
                }
            }
            this.columns = columns.toString().trim().replaceAll(" ", ",");
            this.values = columns.toString().trim().replaceAll(" ", ",");
            this.condition = condition.toString().trim();
            return new Query().queryBuilder(this.values, this.columns, properties.getProperty(Constants.TABLE_NAME), this.condition);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

}
