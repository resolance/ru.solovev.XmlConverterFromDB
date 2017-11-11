package ru.solovev.service;

import ru.solovev.database.UserDaoJdbc;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/***
 * Проверяем есть ли в таблице записи, если есть удаляем их.
 */

public class TableBuilder{

    public static final Logger LOG = Logger.getLogger(TableBuilder.class.getName());
    private long startDuration;
    private long endDuration;
    private long duration;
    private int numberOfInputRow;
    private UserDaoJdbc userDaoJdbc;

    public TableBuilder(int numberOfInputRow, UserDaoJdbc userDaoJdbc) throws Exception {
        this.numberOfInputRow = numberOfInputRow;
        this.userDaoJdbc = userDaoJdbc;
    }

    public void fillTable() throws Exception {
        int countTableRow = userDaoJdbc.checkCountTableRow();
        startDuration = System.nanoTime();
        if (countTableRow != 0) { userDaoJdbc.deleteRow(); }
        endDuration = System.nanoTime();
        duration = TimeUnit.MILLISECONDS.convert((endDuration-startDuration),TimeUnit.NANOSECONDS);
        LOG.log(Level.INFO,"Duration time to delete the table: {0} ms.",new Object[]{duration});

        startDuration = System.nanoTime();
        userDaoJdbc.insertRow(numberOfInputRow);
        endDuration = System.nanoTime();
        LOG.log(Level.INFO,"Duration time to insert rows: {0} ms.",new Object[]{duration});
    }
}
