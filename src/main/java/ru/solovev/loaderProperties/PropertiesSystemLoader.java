package ru.solovev.loaderProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class PropertiesSystemLoader {
    private static PropertiesSystemLoader propertiesSystemLoader;

    private final String pathToSystemProperties = "system.properties";
    private final String pathToFirstXML;
    private final String pathToSecondXML;
    private final String pathToXsltTransformer;
    private final String numberRows;


    public static PropertiesSystemLoader getInstance() throws Exception {
        if (propertiesSystemLoader == null) {
            propertiesSystemLoader = new PropertiesSystemLoader();
        }
        return propertiesSystemLoader;
    }

    private PropertiesSystemLoader() throws IOException {
        Properties properties = new Properties();
        FileInputStream inputStream = new FileInputStream(pathToSystemProperties);
        properties.load(inputStream);
        inputStream.close();

        this.pathToFirstXML = properties.getProperty("system.firstXmlName");
        this.pathToSecondXML = properties.getProperty("system.secondXmlName");
        this.pathToXsltTransformer = properties.getProperty("system.xsltTranformer");
        this.numberRows = properties.getProperty("system.valueNum");
    }

    public String getFirstXmlName() {
        return pathToFirstXML;
    }

    public String getSecondXmlName() {
        return pathToSecondXML;
    }

    public String getPathToXsltTransformer() {
        return pathToXsltTransformer;
    }

    public String getNumberRows() {
        return numberRows;
    }

}
