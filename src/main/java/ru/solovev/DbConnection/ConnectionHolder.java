package ru.solovev.DbConnection;

import ru.solovev.DbConnection.ExceptionApp.DbException;
import ru.solovev.LoaderProperties.LoaderProperties;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHolder {

    private static ConnectionHolder connectionHolder;

    public static synchronized ConnectionHolder getInstance(){
        if (connectionHolder == null) {
            connectionHolder = new ConnectionHolder();
        }
         return connectionHolder;
    }

    public Connection getConnection() throws Exception {
        try {
            LoaderProperties loadProperties = LoaderProperties.getInstance();
            return DriverManager.getConnection(
                    loadProperties.getJdbcUrl(),
                    loadProperties.getLogin(),
                    loadProperties.getPassword()
            );

        } catch (Exception e) {
            throw new DbException("Can't create connection", e);
        }
    }
}
