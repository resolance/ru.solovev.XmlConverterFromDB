package ru.solovev.xml.converter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XmlConverterXslt {
    private static final Logger LOG = Logger.getLogger(XmlConverterXslt.class.getName());
    private final String inputSource;
    private final String outputSource;
    private final String xsltPath;

    public XmlConverterXslt(String inputSource, String outputSource, String xsltPath) {
        this.inputSource = inputSource;
        this.outputSource = outputSource;
        this.xsltPath = xsltPath;
    }

    public String doNewXml() {
        try {
            //TODO: Сделать логирование
            //TODO: Сделать проверку на сущ-ие каталога, поиск по каталогу файла стиля XSLT

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer(new StreamSource(xsltPath));
            transformer.transform(new StreamSource(inputSource), new StreamResult(outputSource));
            LOG.log(Level.INFO, "Transform complete. See new XML in: {0} ", new Object[]{outputSource});
            return outputSource;

        } catch (TransformerException ex) {
            LOG.log(Level.INFO, "Transformation error. {0} ", new Object[]{ex.toString()});
            return null;
        }

    }
}
