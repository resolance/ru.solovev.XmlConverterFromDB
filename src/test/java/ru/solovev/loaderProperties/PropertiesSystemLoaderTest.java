package ru.solovev.loaderProperties;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PropertiesSystemLoaderTest {

    @Test
    public void getFirstXmlName() throws Exception {
        PropertiesSystemLoader propertiesSystemLoader = PropertiesSystemLoader.getInstance();
        String value = propertiesSystemLoader.getFirstXmlName();
        assertEquals("1.xml", value);
    }

    @Test
    public void getSecondXmlName() throws Exception {
        PropertiesSystemLoader propertiesSystemLoader = PropertiesSystemLoader.getInstance();
        String value = propertiesSystemLoader.getSecondXmlName();
        assertEquals("2.xml", value);
    }

    @Test
    public void getPathToXsltTransformer() throws Exception {
        PropertiesSystemLoader propertiesSystemLoader = PropertiesSystemLoader.getInstance();
        String value = propertiesSystemLoader.getPathToXsltTransformer();
        assertEquals("rebuildXml.xsl", value);
    }

}