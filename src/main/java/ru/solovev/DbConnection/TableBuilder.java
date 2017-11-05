package ru.solovev.DbConnection;

import java.sql.Connection;

/***
 * Проверяем есть ли в таблице записи, если есть удаляем их.
 */

public class TableBuilder{

    private int numberOfInputRow;
    private Connection connection;

    public TableBuilder(int numberOfInputRow) throws Exception {
        this.numberOfInputRow = numberOfInputRow;
    }

    public void fillTable() throws Exception {
        connection = ConnectionHolder.getInstance().getConnection();
        UserDaoJdbcImpl userDaoJdbc = new UserDaoJdbcImpl(this.connection);
        int countTableRow = userDaoJdbc.checkCountTableRow();

        if (countTableRow != 0) {
            userDaoJdbc.deleteRow();
            //TODO: сделать логирование в дебаг
        }

        userDaoJdbc.insertRow(numberOfInputRow);
        connection.close();
    }
}
