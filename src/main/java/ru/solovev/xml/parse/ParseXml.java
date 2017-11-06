package ru.solovev.xml.parse;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ParseXml {

    private final String filePath;
    private File file;
    private int summFieldValue;

    public ParseXml(String filePath) {
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
        summFieldValue = 0;

        FileInputStream fileInputStream = null;
        XMLStreamReader reader;
        try {
            fileInputStream = new FileInputStream(getXmlFile());
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            reader = inputFactory.createXMLStreamReader(fileInputStream);

            reader.next();

            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    summFieldValue = summFieldValue
                            + Integer.parseInt(reader.getAttributeValue(0));
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
}
