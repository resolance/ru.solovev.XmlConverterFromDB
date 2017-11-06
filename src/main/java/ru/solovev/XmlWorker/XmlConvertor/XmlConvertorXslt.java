package ru.solovev.XmlWorker.XmlConvertor;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XmlConvertorXslt {

    private final String inputSource;
    private final String outputSource;
    private final String xsltPath;

    public XmlConvertorXslt(String inputSource, String outputSource, String xsltPath){
        this.inputSource = inputSource;
        this.outputSource = outputSource;
        this.xsltPath = xsltPath;
    }

    public void doNewXml(){
        try{
            //TODO: Сделать логирование
            //TODO: Сделать проверку на сущ-ие каталога, поиск по каталогу файла стиля XSLT
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer(new StreamSource(xsltPath));
            transformer.transform(new StreamSource(inputSource), new StreamResult(outputSource));
            System.out.println("Transform " + " complete. See new XML in: " + outputSource );
        }catch(TransformerException ex){
            System.err.println("Transformation error " + ex);
        }
    }
}
