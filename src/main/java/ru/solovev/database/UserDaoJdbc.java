package ru.solovev.database;

import ru.solovev.database.exceptionApp.DbException;
import ru.solovev.xml.build.Entries;

import java.sql.SQLException;


public interface UserDaoJdbc {
    int getCountRows() throws DbException, SQLException;
    void addRow(int Var) throws DbException, SQLException;
    void deleteRow() throws DbException, SQLException;
    Entries readTable(int var) throws DbException, SQLException;
}
