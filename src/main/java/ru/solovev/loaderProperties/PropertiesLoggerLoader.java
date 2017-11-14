package ru.solovev.loaderProperties;

import ru.solovev.service.AppStarter;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *  Get logger properties
 *
 * @author res
 */
public class PropertiesLoggerLoader {
    private static final Logger LOG = Logger.getLogger(PropertiesLoggerLoader.class.getName());

    private static final String PATH_TO_LOGGER_PROPERTIES = "/log.properties";

    public static PropertiesLoggerLoader propertiesLoggerLoader;

    public static PropertiesLoggerLoader getInstance() throws Exception {
        if (null == propertiesLoggerLoader) {
            propertiesLoggerLoader = new PropertiesLoggerLoader();
        }
        return propertiesLoggerLoader;
    }

    private PropertiesLoggerLoader() {
        try {
            LogManager.getLogManager().readConfiguration(
                    AppStarter.class.getResourceAsStream(PATH_TO_LOGGER_PROPERTIES));
        } catch (Exception ex) {
            LOG.log(Level.ALL, "Can't read file {0} ", new Object[]{PATH_TO_LOGGER_PROPERTIES, ex});
        }
    }

}


