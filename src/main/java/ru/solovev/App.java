package ru.solovev;

import ru.solovev.XmlWorker.ParseXml.ParseXml;
import ru.solovev.XmlWorker.XmlBuilder.MarshallerXmlFromDb;
import ru.solovev.DbConnection.TableBuilder;
import ru.solovev.XmlWorker.XmlConvertor.XmlConvertorXslt;

public class App {
    public static void main(String[] args) {
        //TODO: настраиваем подключение к БД +
        //TODO: пишем даные в БД
        //TODO: Запрашиваем данные из бд и формируем XML сохраняем в систему+
        //TODO: с помощью XSLT преоразуем в другой XML. сохраняем в систему+
        //TODO: парсим 2.xml и суммируем все значения полей филд - выводим в консоли

        //TODO: вынести данные к пропертис из командной строки
        int numberOfInputRow = 0;
        final String pathToFirstXML;
        final String outputSource;
        final String pathToXslt;


        try {
            numberOfInputRow = Integer.parseInt(args[0]);
        } catch (ClassFormatError e) {
            e.printStackTrace();
        }

        pathToFirstXML = args[1];
        outputSource = args[2];
        pathToXslt = args[3];

        try {
            TableBuilder tableBuilder = new TableBuilder(numberOfInputRow);
            tableBuilder.fillTable();
            MarshallerXmlFromDb marshallerXmlFromDb = new MarshallerXmlFromDb(numberOfInputRow, pathToFirstXML);
            marshallerXmlFromDb.getXmlFromDb();

            XmlConvertorXslt xmlConvertorXslt = new XmlConvertorXslt(pathToFirstXML,outputSource,pathToXslt);
            xmlConvertorXslt.doNewXml();

            ParseXml parseXml = new ParseXml(outputSource);
            System.out.println("\nResult of summing field attributes = " + parseXml.getResultParseXml());


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("");
    }
}
