package ru.solovev.service;

import org.junit.Test;
import ru.solovev.loaderProperties.PropertiesSystemLoader;
import ru.solovev.xml.parse.ParseXml;

import static org.junit.Assert.assertEquals;

public class AppStarterTest {

     @Test
    public void appStart() throws Exception {
        PropertiesSystemLoader propertiesDBLoader = PropertiesSystemLoader.getInstance();
        String secondXmlName = propertiesDBLoader.getSecondXmlName();

        ParseXml parseXml = new ParseXml(secondXmlName);
        int resultParse = parseXml.getResultParseXml();

        int value = Integer.parseInt(propertiesDBLoader.getNumberRows());
        int checkSumm = 0;

        for (int i = 1; i <= value; i++) {
            checkSumm = checkSumm + i;
        }
        System.out.println(checkSumm);
        assertEquals(checkSumm,resultParse );
    }
}