package ru.solovev.DbConnection;

import ru.solovev.XmlWorker.XmlBuilder.Entries;
import ru.solovev.DbConnection.ExceptionApp.DbException;

import java.sql.SQLException;


public interface UserDaoJdbc {
    int checkCountTableRow() throws DbException, SQLException;
    void insertRow(int Var) throws DbException, SQLException;
    void deleteRow() throws DbException, SQLException;
    Entries readTable(int var) throws DbException, SQLException;
}
