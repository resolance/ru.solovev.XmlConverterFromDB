package ru.solovev.DbConnection;

import ru.solovev.XmlWorker.XmlBuilder.Entries;
import ru.solovev.XmlWorker.XmlBuilder.EntryObj;
import ru.solovev.DbConnection.ExceptionApp.DbException;

import java.sql.*;

public class UserDaoJdbcImpl implements UserDaoJdbc {

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

            //TODO: сделать логирование вставленных строк
            System.out.println("The number of rows inserted: " + result.length);

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
                System.out.println("Nothing to clean");
            } else {
                ps = connection.prepareStatement(DELETE_ROW);
                ps.execute();
                this.connection.commit();
                countAfter = checkCountTableRow();
                if (0 == countAfter) {
                    System.out.println("Table is clear. Delete " + countBefore + " rows.");
                } else {
                    throw new DbException("Can't delete rows.");
                }

            }

            //TODO: добавить вывод в лог
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
