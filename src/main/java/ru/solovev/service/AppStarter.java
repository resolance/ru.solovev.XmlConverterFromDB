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

    public final static String defaultPath = System.getProperty("user.home");
    private int numberOfInputRow;
    private String pathToFirstXML;
    private String pathToSecondXML;
    private String pathToXslt;

    public void appStart() {

        setSystemPath();
        LOG.log(Level.INFO, "Staring App");

        prepareTable();
        transformDataToXML(getNumberOfInputRow(), getPathToFirstXML());
        checkAndMath(getPathToTransformedXml(
                getPathToFirstXML(), getPathToSecondXML(), getPathToXslt()));

        LOG.log(Level.INFO, "Finish App");
    }

    public void setSystemPath() {
        try {
            PropertiesSystemLoader propertiesSystemLoader = PropertiesSystemLoader.getInstance();
            PropertiesLoggerLoader.getInstance();

            this.pathToFirstXML = defaultPath + "/" + propertiesSystemLoader.getFirstXmlName();
            this.pathToSecondXML = defaultPath + "/" + propertiesSystemLoader.getSecondXmlName();
            this.pathToXslt = propertiesSystemLoader.getPathToXsltTransformer();
            this.numberOfInputRow = Integer.parseInt(propertiesSystemLoader.getNumberRows());
        } catch (Exception ex) {
            LOG.log(Level.INFO, "Can't read properties files ", ex);
        }
    }


    private void checkAndMath(final String pathToXML) {
        if (null == pathToXML) {
            LOG.log(Level.INFO, "Can't create or read file" + pathToXML);
        } else {
            ParseXml parseXml = new ParseXml(pathToXML);
            LOG.log(Level.INFO, "Done. Result of summing field attributes = {0}",
                    new Object[]{parseXml.getResultParseXml()});
        }

    }

    private void prepareTable() {
        try {
            TableBuilder tableBuilder = new TableBuilder(
                    getNumberOfInputRow(), new UserDaoJdbcImpl(
                    ConnectionHolder.getInstance().getConnection())
            );
            tableBuilder.fillTable();

        } catch (Exception ex) {
            LOG.log(Level.INFO, "Can't drop or add data into DB ", ex);
        }
    }

    private void transformDataToXML(final int numberRow, final String pathToXML) {
        try {
            MarshallerXmlFromDb marshallerXmlFromDb =
                    new MarshallerXmlFromDb(numberRow, pathToXML);
            marshallerXmlFromDb.getXmlFromDb();
        } catch (Exception e) {
            LOG.log(Level.INFO, "Can't transformed data to XML ", e);
        }
    }

    private String getPathToTransformedXml(final String pathToFirstXML,
                                           final String pathToSecondXML,
                                           final String pathToXslt) {
        XmlConverterXslt xmlConverterXslt =
                new XmlConverterXslt(pathToFirstXML, pathToSecondXML, pathToXslt);

        return xmlConverterXslt.doNewXml();
    }

    public String getPathToFirstXML() {
        return this.pathToFirstXML;
    }

    public int getNumberOfInputRow() {
        return this.numberOfInputRow;
    }

    public String getPathToSecondXML() {
        return this.pathToSecondXML;
    }

    public String getPathToXslt() {
        return this.pathToXslt;
    }
}
