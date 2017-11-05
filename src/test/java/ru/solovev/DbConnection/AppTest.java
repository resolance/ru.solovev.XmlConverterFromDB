package ru.solovev.DbConnection;

import ru.solovev.ConvertXml.CreateXmlFromDb.MarshallerXmlFromDb;

public class AppTest {
    public static void main(String[] args) {

        String ar = "10";
        String pathToFirstXML = "./1.xml";
        int numberOfInputRow = 10;
        try {
            numberOfInputRow = Integer.parseInt(ar);
        } catch (ClassFormatError e) {
            System.err.println("argument not a number");
        }

        try {
            TableBuilder tableBuilder = new TableBuilder(numberOfInputRow);
            tableBuilder.fillTable();
            MarshallerXmlFromDb marshallerXmlFromDb = new MarshallerXmlFromDb(numberOfInputRow ,pathToFirstXML);
            marshallerXmlFromDb.getXmlFromDb();
        } catch (Exception e) {
            e.printStackTrace();
        }
    /*Проверим, что не осталось висяков*/
        System.out.println("");
    }
}
