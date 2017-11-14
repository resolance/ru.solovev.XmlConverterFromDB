package ru.solovev.xml.build;


import ru.solovev.database.ConnectionHolder;
import ru.solovev.database.UserDaoJdbc;
import ru.solovev.database.UserDaoJdbcImpl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Xml creator
 *
 * @author res
 * @throws Exception
 */
public class MarshallerXmlFromDb {

    public static final Logger LOG = Logger.getLogger(MarshallerXmlFromDb.class.getName());
    private Connection connection;
    private int numberOfInsertedRow;
    private String pathToFirstXML;

    public MarshallerXmlFromDb() {
    }


    public MarshallerXmlFromDb(final int numberOfInputRow, final String pathToFirstXML) throws Exception {
        this.numberOfInsertedRow = numberOfInputRow;
        this.pathToFirstXML = pathToFirstXML;
    }

    public void getXmlFromDb() throws Exception {
        connection = ConnectionHolder.getInstance().getConnection();
        UserDaoJdbc userDaoJdbc = new UserDaoJdbcImpl(this.connection);

        JAXBContext context = JAXBContext.newInstance(Entries.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        Entries entries = userDaoJdbc.readData(numberOfInsertedRow);

        LOG.log(Level.INFO, "Creating XML file.");
        marshaller.marshal(entries, new FileOutputStream(pathToFirstXML));

        if (new File(pathToFirstXML).isFile()) {
            LOG.log(Level.INFO, "Done. First XML saved in: " + pathToFirstXML);
        } else {
            LOG.log(Level.INFO, "Can't read file. " + pathToFirstXML);
        }
        connection.close();
    }
}