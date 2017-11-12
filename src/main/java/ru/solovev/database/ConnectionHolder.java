package ru.solovev.database;

import ru.solovev.database.exceptionApp.DbException;
import ru.solovev.loaderProperties.PropertiesDBLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionHolder {
    private static final Logger LOG = Logger.getLogger(ConnectionHolder.class.getName());
    private static ConnectionHolder connectionHolder;

    public static synchronized ConnectionHolder getInstance(){
        if (connectionHolder == null) {
            connectionHolder = new ConnectionHolder();
        }
         return connectionHolder;
    }

    public Connection getConnection() throws Exception {
        try {
            PropertiesDBLoader loadProperties = PropertiesDBLoader.getInstance();
            return DriverManager.getConnection(
                    loadProperties.getJdbcUrl(),
                    loadProperties.getLogin(),
                    loadProperties.getPassword()
            );

        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Can't create connection",e);
            throw new DbException("Can't create connection", e);
        }
    }
}
