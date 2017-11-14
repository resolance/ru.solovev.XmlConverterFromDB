package ru.solovev.loaderProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *  Get connection properties
 *
 * @author res
 */
public class PropertiesDBLoader {

    private static final Logger LOG = Logger.getLogger(PropertiesDBLoader.class.getName());

    private static PropertiesDBLoader propertiesDBLoader;

    private static final String PATH_TO_JDBC_PROPERTIES = "jdbc.properties";
    private static String driverClassName;
    private static String login;
    private final String password;
    private final String jdbcUrl;

    public static PropertiesDBLoader getInstance() throws Exception {
        if (propertiesDBLoader == null) {
            propertiesDBLoader = new PropertiesDBLoader();
        }
        return propertiesDBLoader;
    }

    private PropertiesDBLoader() throws IOException {
        Properties properties = new Properties();

        FileInputStream inputStream = new FileInputStream(PATH_TO_JDBC_PROPERTIES);
        properties.load(inputStream);
        //properties.load(PropertiesDBLoader.class.getResourceAsStream(PATH_TO_JDBC_PROPERTIES));
        inputStream.close();

        this.driverClassName = properties.getProperty("jdbc.driver");
        this.login = properties.getProperty("jdbc.username");
        this.password = properties.getProperty("jdbc.password");
        this.jdbcUrl = properties.getProperty("jdbc.url");

        if (LOG.isLoggable(Level.CONFIG)) {
            LOG.log(
                    Level.CONFIG,
                    "Load JDBC properties:" +
                            "\ndriver: {0}" +
                            "\nlogin: {1}" +
                            "\npass: {2}" +
                            "\nurl: {3}",
                    new Object[]{
                            driverClassName,
                            login,
                            password,
                            jdbcUrl
                    }
            );
        }
    }

    public String getDriverClassName() {
        return this.driverClassName;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public String getJdbcUrl() {
        return this.jdbcUrl;
    }
}
