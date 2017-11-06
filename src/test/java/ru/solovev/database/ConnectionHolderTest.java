package ru.solovev.database;

import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.assertNotNull;

public class ConnectionHolderTest {

    @Test
    public void getConnectionSuccess() throws Exception {
        final String sql = "SELECT count(*) FROM test";
        try (Connection con = ConnectionHolder.getInstance().getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            String val = rs.getString(1);

            assertNotNull(val);
        } catch (Exception e) {

        }
    }
}
