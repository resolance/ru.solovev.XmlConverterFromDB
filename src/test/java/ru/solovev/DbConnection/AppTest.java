package ru.solovev.DbConnection;

import ru.solovev.XmlWorker.XmlBuilder.MarshallerXmlFromDb;
import ru.solovev.XmlWorker.XmlConvertor.XmlConvertorXslt;

public class AppTest {
    public static void main(String[] args) {

        String ar = "10";
        String pathToFirstXML = "./1.xml";
        String outputSource = "./2.xml";
        String pathToXslt = "./rebuildXml.xsl";


        int numberOfInputRow = 10;
        try {
            numberOfInputRow = Integer.parseInt(ar);
        } catch (ClassFormatError e) {
            System.err.println("argument not a number");
        }

        try {
            TableBuilder tableBuilder = new TableBuilder(numberOfInputRow);
            tableBuilder.fillTable();

            MarshallerXmlFromDb marshallerXmlFromDb = new MarshallerXmlFromDb(numberOfInputRow, pathToFirstXML);
            marshallerXmlFromDb.getXmlFromDb();

            XmlConvertorXslt xmlConvertorXslt = new XmlConvertorXslt(pathToFirstXML,outputSource,pathToXslt);
            xmlConvertorXslt.doNewXml();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
