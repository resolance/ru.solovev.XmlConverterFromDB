package ru.solovev.DbConnection;

import ru.solovev.XmlWorker.ParseXml.ParseXml;
import ru.solovev.XmlWorker.XmlBuilder.MarshallerXmlFromDb;
import ru.solovev.XmlWorker.XmlConvertor.XmlConvertorXslt;

import java.util.concurrent.TimeUnit;


public class AppTest {
    public static void main(String[] args) {

        String ar = "100";
        String pathToFirstXML = "./1.xml";
        String outputSource = "./2.xml";
        String pathToXslt = "./rebuildXml.xsl";
        int result = 0;
        long start = System.nanoTime();

        int numberOfInputRow = 100;
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

            XmlConvertorXslt xmlConvertorXslt = new XmlConvertorXslt(pathToFirstXML, outputSource, pathToXslt);
            xmlConvertorXslt.doNewXml();

            ParseXml parseXml = new ParseXml(outputSource);
            System.out.println("\nResult of summing field attributes = " + parseXml.getResultParseXml());


            for (int i = 1; i <= numberOfInputRow; i++) {
                result = result + i;
            }

            if (result == parseXml.getResultParseXml()) {
                System.out.println("\nParsed value and test result are equal");
            }else {
                System.out.println("\nWARNING!!! \nParsed value and test result are not equal");
            }
            long end = System.nanoTime();
            System.out.println("App running : " + TimeUnit.SECONDS.convert((end-start), TimeUnit.NANOSECONDS) + "ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
