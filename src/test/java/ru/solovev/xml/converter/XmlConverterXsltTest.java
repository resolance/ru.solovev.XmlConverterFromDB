package ru.solovev.xml.converter;

import org.junit.Test;
import ru.solovev.loaderProperties.PropertiesSystemLoader;

import java.io.File;

import static org.junit.Assert.assertNotSame;

public class XmlConverterXsltTest {
    @Test
    public void doNewXml() throws Exception {
        PropertiesSystemLoader propertiesSystemLoader = PropertiesSystemLoader.getInstance();

        File fileOne = new File(propertiesSystemLoader.getFirstXmlName());
        File fileTwo = new File(propertiesSystemLoader.getSecondXmlName());
        assertNotSame(fileOne,fileTwo);
    }

}