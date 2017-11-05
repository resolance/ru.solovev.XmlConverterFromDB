package ru.solovev.LoaderProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoaderProperties {

    private  static LoaderProperties loaderProperties;

    private final String pathToJdbcProperties = "src/main/resources/jdbc.properties";
    private final String driverClassName;
    private final String login;
    private final String password;
    private final String jdbcUrl;

    public static LoaderProperties getInstance() throws Exception{
        if(loaderProperties == null){
            loaderProperties = new LoaderProperties();
        }
        return loaderProperties;
    }

    private LoaderProperties() throws IOException{
        Properties properties = new Properties();
        FileInputStream inputStream = new FileInputStream(pathToJdbcProperties);
        properties.load(inputStream);
        inputStream.close();

        this.driverClassName = properties.getProperty("jdbc.driver");
        this.login = properties.getProperty("jdbc.username");
        this.password = properties.getProperty("jdbc.password");
        this.jdbcUrl = properties.getProperty("jdbc.url");
        /*System.out.println(
                "\nDriver = " + this.driverClassName
                + "\nlogin = " + this.login
                + "\npass = " + this.password
                + "\nUrl = " + this.jdbcUrl
        );*/
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
