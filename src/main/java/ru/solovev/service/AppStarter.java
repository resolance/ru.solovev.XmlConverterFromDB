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

/**
 * Main class executor. Load properties, set all parameters
 *
 * @author res
 */


public class AppStarter {
    private static final Logger LOG = Logger.getLogger(AppStarter.class.getName());

    public final static String DEFAULT_PATH = System.getProperty("user.home");
    private int numberOfInputRow;
    private String pathToFirstXML;
    private String pathToSecondXML;
    private String pathToXslt;

    public void appStart() {
        try {
            setSystemPath();
            LOG.log(Level.INFO, "Staring App");

            prepareTable();
            transformDataToXML(numberOfInputRow, pathToFirstXML);

            checkAndMath(getPathToTransformedXml(
                    pathToFirstXML, pathToSecondXML, pathToXslt));

            LOG.log(Level.INFO, "Finish App");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error due to executing app", ex);
        }
    }

    private void setSystemPath() throws Exception {
        PropertiesSystemLoader propertiesSystemLoader = PropertiesSystemLoader.getInstance();
        PropertiesLoggerLoader.getInstance();

            /*this.pathToFirstXML = DEFAULT_PATH + "/" + propertiesSystemLoader.getFirstXmlName();
            this.pathToSecondXML = DEFAULT_PATH + "/" + propertiesSystemLoader.getSecondXmlName();*/

        this.pathToFirstXML = propertiesSystemLoader.getFirstXmlName();
        this.pathToSecondXML = propertiesSystemLoader.getSecondXmlName();

        this.pathToXslt = propertiesSystemLoader.getPathToXsltTransformer();
        this.numberOfInputRow = Integer.parseInt(propertiesSystemLoader.getNumberRows());

    }


    private void checkAndMath(final String pathToXML) {
        if (null == pathToXML) {
            LOG.log(Level.SEVERE, "Can't create or read file" + pathToXML);
        } else {
            ParseXml parseXml = new ParseXml(pathToXML);
            int result = parseXml.getResultParseXml();
            LOG.log(Level.INFO, "Done. Result of summing field attributes = {0}",
                    new Object[]{result});
        }

    }

    private void prepareTable() throws Exception {

        TableBuilder tableBuilder = new TableBuilder(
                numberOfInputRow, new UserDaoJdbcImpl(
                ConnectionHolder.getInstance().getConnection())
        );
        tableBuilder.fillTable();
    }

    private void transformDataToXML(final int numberRow,
                                    final String pathToXML) throws Exception {

        MarshallerXmlFromDb marshallerXmlFromDb =
                new MarshallerXmlFromDb(numberRow, pathToXML);
        marshallerXmlFromDb.getXmlFromDb();
    }

    private String getPathToTransformedXml(final String pathToFirstXML,
                                           final String pathToSecondXML,
                                           final String pathToXslt) {
        XmlConverterXslt xmlConverterXslt =
                new XmlConverterXslt(pathToFirstXML, pathToSecondXML, pathToXslt);

        return xmlConverterXslt.doNewXml();
    }
}
