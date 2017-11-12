package ru.solovev.ConvertXml;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ParseXmlTest {

    private final String filePath;
    private File file;
    private int summFieldValue = 0;

    public ParseXmlTest(String filePath) {
        this.filePath = filePath;
    }

    private File getXmlFile() {
        this.file = new File(filePath);
        //TODO: сделать обрабочик ошибки открытия или наличия файла
        if (file.canRead()) {
            return this.file;
        } else {
            System.out.println("Can't read file ");
        }
        return null;
    }

    public int getResultParseXml() {

        FileInputStream fileInputStream = null;
        XMLStreamReader reader = null;
        try {
            fileInputStream = new FileInputStream(getXmlFile());
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            reader = inputFactory.createXMLStreamReader(fileInputStream);

            reader.next();

            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {

                    /*System.out.println("type: " + type);
                    System.out.println("getLocalName: " + reader.getLocalName());
                    System.out.println("getAttributeName: " + reader.getAttributeName(0));
                    System.out.println("getAttributeValue(0): " + reader.getAttributeValue(0));
                    System.out.println("getAttributeValue(0): " + reader.getAttributeValue(1));*/


                    summFieldValue = summFieldValue +
                            Integer.parseInt(reader.getAttributeValue(0));
                    //System.out.println(reader.getAttributeName(0).equals("field"));

                    //TODO: Разобраться почему не выполняется условие.
                    /*if(reader.getAttributeName(0).equals("field")) {
                    }*/
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
            return summFieldValue;
    }

    public static void main(String[] args) throws Exception {
        ParseXmlTest parseXmlTest = new ParseXmlTest("./2.xml");
        int result  = parseXmlTest.getResultParseXml();
        System.out.println(result);
    }
}
