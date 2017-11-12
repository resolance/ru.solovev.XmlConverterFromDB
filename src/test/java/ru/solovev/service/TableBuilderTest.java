package ru.solovev.service;

import org.junit.Test;
import ru.solovev.database.ConnectionHolder;
import ru.solovev.database.UserDaoJdbcImpl;
import ru.solovev.loaderProperties.PropertiesSystemLoader;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class TableBuilderTest {
    @Test
    public void fillTable() throws Exception {

        long startTime;
        long endTime;
        long duration;
        long condition = 30000;
        boolean isCondition = false;

        PropertiesSystemLoader propertiesSystemLoader = PropertiesSystemLoader.getInstance();
        TableBuilder tableBuilder = new TableBuilder(
                Integer.parseInt(propertiesSystemLoader.getNumberRows()), new UserDaoJdbcImpl(
                ConnectionHolder.getInstance().getConnection())
        );
        startTime = System.nanoTime();
        tableBuilder.fillTable();
        endTime = System.nanoTime();
        duration = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);

        if (condition > duration) {
            isCondition = true;
        } else {
            isCondition = false;
        }
        assertTrue(isCondition);
    }

}