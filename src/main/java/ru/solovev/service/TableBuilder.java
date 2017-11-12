package ru.solovev.service;

import ru.solovev.database.UserDaoJdbc;

/***
 * Проверяем есть ли в таблице записи, если есть удаляем их.
 */

public class TableBuilder {

    private int numberOfInputRow;
    private UserDaoJdbc userDaoJdbc;

    public TableBuilder(int numberOfInputRow, UserDaoJdbc userDaoJdbc) throws Exception {
        this.numberOfInputRow = numberOfInputRow;
        this.userDaoJdbc = userDaoJdbc;
    }

    public void fillTable() throws Exception {
        int countTableRow = userDaoJdbc.getCountRows();
        if (countTableRow != 0) {
            userDaoJdbc.deleteRow();
        }
        userDaoJdbc.addRow(numberOfInputRow);
    }
}
