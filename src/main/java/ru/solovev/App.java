package ru.solovev;

import ru.solovev.database.ConnectionHolder;
import ru.solovev.database.UserDaoJdbcImpl;
import ru.solovev.loaderProperties.PropertiesSystemLoader;
import ru.solovev.service.TableBuilder;
import ru.solovev.xml.build.MarshallerXmlFromDb;
import ru.solovev.xml.converter.XmlConverterXslt;
import ru.solovev.xml.parse.ParseXml;

public class App {
    public static void main(String[] args) {

        int numberOfInputRow = 0;
        String pathToFirstXML = "";
        String pathToSecondXML = "";
        String pathToXslt = "";

        try {
            PropertiesSystemLoader propertiesSystemLoader = PropertiesSystemLoader.getInstance();

            String defaultPath = System.getProperty("user.home");

            pathToFirstXML = defaultPath + "/" + propertiesSystemLoader.getFirstXmlName();
            pathToSecondXML = defaultPath + "/" + propertiesSystemLoader.getSecondXmlName();
            pathToXslt = propertiesSystemLoader.getPathToXsltTransformer();
            numberOfInputRow = Integer.parseInt(propertiesSystemLoader.getNumberRows());
            System.out.println(pathToFirstXML);
            System.out.println(pathToSecondXML);
            System.out.println(pathToXslt);
            System.out.println(numberOfInputRow);

            TableBuilder tableBuilder = new TableBuilder(
                    numberOfInputRow,
                    new UserDaoJdbcImpl(
                            ConnectionHolder.getInstance().getConnection()
                    )
            );

            tableBuilder.fillTable();

            MarshallerXmlFromDb marshallerXmlFromDb = new MarshallerXmlFromDb(numberOfInputRow, pathToFirstXML);
            marshallerXmlFromDb.getXmlFromDb();

            XmlConverterXslt xmlConverterXslt = new XmlConverterXslt(pathToFirstXML, pathToSecondXML, pathToXslt);
            xmlConverterXslt.doNewXml();

            ParseXml parseXml = new ParseXml(pathToSecondXML);
            System.out.println("\nResult of summing field attributes = " + parseXml.getResultParseXml());


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("");
    }
}
