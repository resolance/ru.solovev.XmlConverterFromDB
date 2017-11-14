package ru.solovev.database;

import ru.solovev.database.exceptionApp.DbException;
import ru.solovev.xml.build.Entries;

import java.sql.SQLException;

/**
 * Main interface
 *
 * @author res
 */
public interface UserDaoJdbc {

    /**
     * Read the table to get count rows from field(FIELD)
     *
     * @throws DbException,SQLException
     */
    int getCountRows() throws DbException, SQLException;

    /**
     * Add rows in table
     *
     * @throws DbException,SQLException
     */
    void addRow(int Var) throws DbException, SQLException;

    /**
     * Clear testing table
     *
     * @throws DbException,SQLException
     */
    void deleteRow() throws DbException, SQLException;

    /**
     * Read testing table and return Entries object
     *
     * @throws DbException,SQLException
     */
    Entries readData(int var) throws DbException, SQLException;
}
