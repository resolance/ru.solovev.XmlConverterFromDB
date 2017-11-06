package ConvertXml;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XmlConvertorXsltTest {
    public static void main(String[] args) {
        try{
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer(new StreamSource("./rebuildXml.xsl"));
            transformer.transform(new StreamSource("./1.xml"), new StreamResult("./2.xml"));
            System.out.println("Transform " + " complete");
        }catch(TransformerException ex){
            System.err.println("Transformation error " + ex);
        }
    }
}
