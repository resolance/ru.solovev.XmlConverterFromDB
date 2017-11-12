package ru.solovev.database;

import ru.solovev.database.exceptionApp.DbException;
import ru.solovev.xml.build.Entries;
import ru.solovev.xml.build.EntryObj;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJdbcImpl implements UserDaoJdbc {
    private static final Logger LOG = Logger.getLogger(UserDaoJdbcImpl.class.getName());
    public static final String CHECK_COUNT_TABLE_ROW = "SELECT count(*) FROM magnit.test;";
    public static final String INSERT_ROW = "INSERT INTO magnit.test (field) values (?);";
    public static final String DELETE_ROW = "DELETE FROM magnit.test;";
    public static final String READ_TABLE = "SELECT FIELD FROM magnit.test";

    private Connection connection;

    public UserDaoJdbcImpl(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public int getCountRows() throws DbException, SQLException {
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
            if (LOG.isLoggable(Level.CONFIG)) {
                LOG.log(Level.CONFIG, "Get {0} rows", new Object[]{numberOfRow});
            }
            return numberOfRow;
        } catch (SQLException e) {
            //TODO: как правильно кидать исключение? учитывать сам эксепшн или нет?
            this.connection.rollback();
            LOG.log(Level.SEVERE, "Can't execute sql = " + CHECK_COUNT_TABLE_ROW);
            throw new DbException("Can't execute sql = " + CHECK_COUNT_TABLE_ROW);
        } finally {
            rs.close();
            st.close();
        }
    }

    @Override
    public void addRow(int numberOfInsertedRow) throws DbException, SQLException {
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

        } catch (SQLException e) {
            //TODO: add logging
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

            int countBefore = getCountRows();
            int countAfter = 0;

            if (0 == countBefore) {
                LOG.log(Level.INFO,"Nothing to clean. Rows in table: ", new Object[]{ countBefore});
            } else {
                ps = connection.prepareStatement(DELETE_ROW);
                ps.execute();
                this.connection.commit();
                countAfter = getCountRows();
                if (0 == countAfter) {
                    LOG.log(Level.INFO,"Table are cleared. Delete {0} rows", new Object[]{countBefore});
                } else {
                    //TODO: add logging
                    throw new DbException("Can't delete rows.");
                }

            }
        } catch (SQLException e) {
            this.connection.rollback();
            //TODO: add logging
            throw new DbException("Can't execute sql = " + DELETE_ROW);

        } finally {
            if (null != ps) {
                ps.close();
            }
        }

    }

    @Override
    /*Скорее всего использовать Объекты энтри не правильно, т.к. здесь дожно быть только работа с БД
    * возможно, правильней будет отсюда доставать коллекцию элементов, и в отдельном классе ее обрабатывать
    * Так же везде сделать try с ресурсами
    * */
    //TODO: Переделать как было до, достаем коллецию, и обрабатываем ее в отдельном классе


    public Entries readTable(int numberOfInsertedRow) throws DbException, SQLException {
        Entries entries = new Entries();
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
            //TODO: add logging
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
