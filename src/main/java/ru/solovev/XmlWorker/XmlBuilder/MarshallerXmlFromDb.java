package ru.solovev.XmlWorker.XmlBuilder;


import ru.solovev.DbConnection.ConnectionHolder;
import ru.solovev.DbConnection.UserDaoJdbc;
import ru.solovev.DbConnection.UserDaoJdbcImpl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.FileOutputStream;
import java.sql.Connection;


public class MarshallerXmlFromDb {

    private Connection connection;
    private int numberOfInsertedRow;
    private String pathToFirstXML;

    public MarshallerXmlFromDb() {
    }


    public MarshallerXmlFromDb(int numberOfInputRow, String pathToFirstXML) throws Exception {
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

        Entries entries = userDaoJdbc.readTable(numberOfInsertedRow);

        //TODO: Сделать логирование, пока просто в консоль и добавить время
        System.out.println("\nCreate XML");
        marshaller.marshal(entries, new FileOutputStream(pathToFirstXML));
        //marshaller.marshal(entries, System.out);

        //TODO: Сделать проверку наличия файла
        System.out.println("\nDone. first XML saved in: " + pathToFirstXML +"\n");
        connection.close();
    }
}
