package ru.solovev.xml.converter;

import org.junit.Test;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XmlConverterXsltTest {
    @Test
    public void doNewXml() throws Exception {


        String pathToFirstXML = "1.xml";
        String pathToSecondXML = "2.xml";
        String pathToXslt = "rebuildXml.xsl";

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer(new StreamSource(pathToXslt));
        transformer.transform(new StreamSource(pathToFirstXML), new StreamResult(pathToSecondXML));

    }

}