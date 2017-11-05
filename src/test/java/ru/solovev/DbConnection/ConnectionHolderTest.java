package ru.solovev.DbConnection;

import ru.solovev.DbConnection.ConnectionHolder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionHolderTest {
    public static void main(String[] args) {
        final String sql = "SELECT count(*) FROM test";
        final int rowCount;
        try {
            Connection con = ConnectionHolder.getInstance().getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                System.out.println(rs.getString(1));
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 }
