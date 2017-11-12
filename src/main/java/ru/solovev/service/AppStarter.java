package ru.solovev.service;


import ru.solovev.database.ConnectionHolder;
import ru.solovev.database.UserDaoJdbcImpl;
import ru.solovev.loaderProperties.PropertiesLoggerLoader;
import ru.solovev.loaderProperties.PropertiesSystemLoader;
import ru.solovev.xml.build.MarshallerXmlFromDb;
import ru.solovev.xml.converter.XmlConverterXslt;
import ru.solovev.xml.parse.ParseXml;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AppStarter {
    private static final Logger LOG = Logger.getLogger(AppStarter.class.getName());

    public void appStart() {


        int numberOfInputRow = 0;
        String pathToFirstXML = "";
        String pathToSecondXML = "";
        String pathToXslt = "";

        try {
            /*
            * Loading properties
            **/
            PropertiesSystemLoader propertiesSystemLoader = PropertiesSystemLoader.getInstance();
            PropertiesLoggerLoader propertiesLoggerLoader = PropertiesLoggerLoader.getInstance();

            /*
             * Set base path to saving files.
             *
             */
            String defaultPath = System.getProperty("user.home");

            pathToFirstXML = defaultPath + "/" + propertiesSystemLoader.getFirstXmlName();
            pathToSecondXML = defaultPath + "/" + propertiesSystemLoader.getSecondXmlName();
            pathToXslt = propertiesSystemLoader.getPathToXsltTransformer();
            numberOfInputRow = Integer.parseInt(propertiesSystemLoader.getNumberRows());

            /*
             * Insert data in mySql or other DataBase
             *
             */
            LOG.log(Level.INFO, "Staring App");
            TableBuilder tableBuilder = new TableBuilder(
                    numberOfInputRow,
                    new UserDaoJdbcImpl(
                            ConnectionHolder.getInstance().getConnection()
                    )
            );
            tableBuilder.fillTable();

            /*
            * Marshaller data to XML file.
            * */
            MarshallerXmlFromDb marshallerXmlFromDb =
                    new MarshallerXmlFromDb(numberOfInputRow, pathToFirstXML);
            marshallerXmlFromDb.getXmlFromDb();

            /*
             * Transform XML file whith XML StyleSheets language
             *
             */
            XmlConverterXslt xmlConverterXslt = new XmlConverterXslt(pathToFirstXML, pathToSecondXML, pathToXslt);
            pathToSecondXML = xmlConverterXslt.doNewXml();


            /*
            * Check that created file done and parse new XML for math.
            * */
            if (null == pathToSecondXML) {
                LOG.log(Level.INFO, "Can't create or read file" + pathToSecondXML);
            } else {
                ParseXml parseXml = new ParseXml(pathToSecondXML);
                //TODO: выводит двойное значение. починить
                LOG.log(Level.INFO, "Done. Result of summing field attributes = {0}",
                        new Object[]{parseXml.getResultParseXml()});
            }
            LOG.log(Level.INFO, "Finish App");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
