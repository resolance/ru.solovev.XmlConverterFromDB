package ru.solovev.database;

import ru.solovev.database.exceptionApp.DbException;
import ru.solovev.xml.build.Entries;
import ru.solovev.xml.build.EntryObj;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJdbcImpl implements UserDaoJdbc {
    public static final Logger LOG = Logger.getLogger(UserDaoJdbcImpl.class.getName());
    public static final String CHECK_COUNT_TABLE_ROW = "SELECT count(*) FROM magnit.test;";
    public static final String INSERT_ROW = "INSERT INTO magnit.test (field) values (?);";
    public static final String DELETE_ROW = "DELETE FROM magnit.test;";
    public static final String READ_TABLE = "SELECT * FROM magnit.test";

    private Connection connection;

    public UserDaoJdbcImpl(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public int checkCountTableRow() throws DbException, SQLException {
        this.connection.setAutoCommit(false);
        this.connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        int numberOfRow = 0;
        Statement st = null;
        ResultSet rs = null;

        try {
            st = connection.createStatement();
            rs = st.executeQuery(CHECK_COUNT_TABLE_ROW);
            if (rs.next()) {
                numberOfRow = Integer.parseInt(rs.getString(1));
            }
            //LOG.log(Level.INFO, "Get {0} rows", new Object[]{numberOfRow});
            return numberOfRow;
        } catch (SQLException e) {
            this.connection.rollback();
            throw new DbException("Can't execute sql = " + CHECK_COUNT_TABLE_ROW);
        } finally {
            rs.close();
            st.close();
        }
    }

    @Override
    public void insertRow(int numberOfInsertedRow) throws DbException, SQLException {
        this.connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        this.connection.setAutoCommit(false);

        //TODO: продумать как сделать добавление множества строк
        Statement st = null;

        try {
            st = connection.createStatement();

            for (int i = 1; i <= numberOfInsertedRow; i++) {
                st.addBatch("INSERT INTO magnit.test (FIELD) values (" + i + ");");
            }
            int[] result = st.executeBatch();
            this.connection.commit();

            LOG.log(Level.INFO,"Add: {0} rows ", new Object[]{ result.length});
            //System.out.println("The number of rows inserted: " + result.length);

        } catch (SQLException e) {
            this.connection.rollback();
            throw new DbException("Can't execute sql = " + INSERT_ROW);

        } finally {
            st.close();

        }

    }

    @Override
    public void deleteRow() throws DbException, SQLException {
        this.connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        this.connection.setAutoCommit(false);

        PreparedStatement ps = null;
        try {

            int countBefore = checkCountTableRow();
            int countAfter = 0;

            if (0 == countBefore) {
                LOG.log(Level.INFO,"Nothing to clean. Rows in table: ", new Object[]{ countBefore});
            } else {
                ps = connection.prepareStatement(DELETE_ROW);
                ps.execute();
                this.connection.commit();
                countAfter = checkCountTableRow();
                if (0 == countAfter) {
                    LOG.log(Level.INFO,"Table is clear. Delete {0} rows", new Object[]{countBefore});
                } else {
                    //TODO: нужна запись в лог?
                    throw new DbException("Can't delete rows.");
                }

            }
        } catch (SQLException e) {
            this.connection.rollback();
            System.err.println(e.getSQLState());
            throw new DbException("Can't execute sql = " + DELETE_ROW);

        } finally {
            if (null != ps) {
                ps.close();
            }
        }

    }

    @Override
    /*Скорее всего использовать Объекты энтри не правильно, т.к. здесь дожно быть только работа с БД
    * возможно, правильней будет отсюда доставать коллекцию элементов, и в отдельном классе ее обрабатывать*/
    //TODO: Переделать как было до, достаем коллецию, и обрабатываем ее в отдельном классе

    public Entries readTable(int numberOfInsertedRow) throws DbException, SQLException {
        Entries entries = new Entries();
        EntryObj entryObj = new EntryObj();
        this.connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        this.connection.setAutoCommit(false);

        Statement st = null;
        ResultSet rs = null;

        try {
            st = connection.createStatement();
            rs = st.executeQuery(READ_TABLE);
            while (rs.next()) {
                entries.add(new EntryObj(rs.getInt(1)));
            }

        }catch (SQLException e){
            this.connection.rollback();
            System.err.println(e.getSQLState());
            throw new DbException("Can't execute sql = " + READ_TABLE);

        } finally {
            if (st != null){
                st.close();
            }
            if (rs != null){
                rs.close();
            }
        }
        return entries;
    }

}
