package ConvertXml;

import ru.solovev.database.ConnectionHolder;
import ru.solovev.database.UserDaoJdbc;
import ru.solovev.database.UserDaoJdbcImpl;
import ru.solovev.xml.build.Entries;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.FileOutputStream;
import java.sql.Connection;

public class MarshallerXmlFromDbTest {
    public static void main(String[] args) throws Exception {

        final int numberOfInsertedRow = 100;

        Connection connection = ConnectionHolder.getInstance().getConnection();
        UserDaoJdbc userDaoJdbc = new UserDaoJdbcImpl(connection);

        /*Проверим что достали из БД*/
        //System.out.println(userDaoJdbc.readTable(numberOfInsertedRow));

        /*Создадим лист элементов */
        /*ArrayList list = new ArrayList<String>(numberOfInsertedRow);
        list.addAll(userDaoJdbc.readTable(numberOfInsertedRow));*/

        JAXBContext context = JAXBContext.newInstance(Entries.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        Entries entries = userDaoJdbc.readTable(numberOfInsertedRow);
        System.out.println(entries);

        marshaller.marshal(entries, new FileOutputStream("./1.xml"));
        marshaller.marshal(entries, System.out);


        connection.close();
    }
}






