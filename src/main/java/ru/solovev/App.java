package ru.solovev;

import ru.solovev.XmlWorker.XmlBuilder.MarshallerXmlFromDb;
import ru.solovev.DbConnection.TableBuilder;

public class App {
    public static void main(String[] args) {

        //TODO: Запрашиваем данные из бд и формируем XML сохраняем в систему
        //TODO: с помощью XSLT преоразуем в другой XLM. сохраняем в систему
        //TODO: парсим 2.xml и суммируем все значения полей филд - выводим в консоли
        int numberOfInputRow = 0;
        String pathToFirstXML = "";
        try {
            numberOfInputRow = Integer.parseInt(args[0]);
            pathToFirstXML = args[1];
        } catch (ClassFormatError e) {
            e.printStackTrace();
        }

        try {
            TableBuilder tableBuilder = new TableBuilder(numberOfInputRow);
            tableBuilder.fillTable();
            MarshallerXmlFromDb marshallerXmlFromDb = new MarshallerXmlFromDb(numberOfInputRow, pathToFirstXML);
            marshallerXmlFromDb.getXmlFromDb();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("");
    }
}
