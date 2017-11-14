package ru.solovev.xml.build;

import org.junit.Test;
import ru.solovev.database.ConnectionHolder;
import ru.solovev.database.UserDaoJdbc;
import ru.solovev.database.UserDaoJdbcImpl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.FileOutputStream;
import java.sql.Connection;

public class MarshallerXmlFromDbTest {
    @Test
    public void getXmlFromDb() throws Exception {

        final int numberOfInsertedRow = 10;

        Connection connection = ConnectionHolder.getInstance().getConnection();
        UserDaoJdbc userDaoJdbc = new UserDaoJdbcImpl(connection);

        JAXBContext context = JAXBContext.newInstance(Entries.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        Entries entries = userDaoJdbc.readData(numberOfInsertedRow);
        System.out.println(entries);

        marshaller.marshal(entries, new FileOutputStream("./1.xml"));
        marshaller.marshal(entries, System.out);

        connection.close();
    }
}






