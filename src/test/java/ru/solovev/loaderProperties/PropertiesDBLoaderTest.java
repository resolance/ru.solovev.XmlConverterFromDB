package ru.solovev.loaderProperties;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PropertiesDBLoaderTest {
    @Test
    public void getDriverClassName() throws Exception {
        PropertiesDBLoader propertiesDBLoader = PropertiesDBLoader.getInstance();
        String value = propertiesDBLoader.getDriverClassName();
        assertEquals("com.mysql.jdbc.Driver", value);
    }

    @Test
    public void getLogin() throws Exception {
        PropertiesDBLoader propertiesDBLoader = PropertiesDBLoader.getInstance();
        String value = propertiesDBLoader.getLogin();
        assertEquals("root", value);
    }

    @Test
    public void getPassword() throws Exception {
        PropertiesDBLoader propertiesDBLoader = PropertiesDBLoader.getInstance();
        String value = propertiesDBLoader.getPassword();
        assertEquals("root", value);
    }

    @Test
    public void getJdbcUrl() throws Exception {
        PropertiesDBLoader propertiesDBLoader = PropertiesDBLoader.getInstance();
        String value = propertiesDBLoader.getJdbcUrl();
        assertEquals("jdbc:mysql://localhost:3306/magnit", value);
    }

}