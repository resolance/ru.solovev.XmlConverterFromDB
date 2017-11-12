package ru.solovev.service;

import ru.solovev.database.ConnectionHolder;
import ru.solovev.database.UserDaoJdbcImpl;

import java.sql.Connection;

public class TableBuilderTest {

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionHolder.getInstance().getConnection();
        UserDaoJdbcImpl userDaoJdbc = new UserDaoJdbcImpl(connection);
        int numberOfRow = 10_000;

        /*Проверяем коунт таблицы*/
        int countTableRow = userDaoJdbc.checkCountTableRow();
        System.out.println("\nNumber of row in table before is: " + countTableRow);

        /*Грохаем строки в таблице*/
        long start = System.nanoTime();
        userDaoJdbc.deleteRow();
        long end = System.nanoTime();
        long startTime = System.nanoTime();
        System.out.println("Time for cleaning table is: " + deltaTime(start, end) + " ms.\n");


        /*Инсертим строки*/
        start = System.nanoTime();
        userDaoJdbc.insertRow(numberOfRow);
        end = System.nanoTime();
        System.out.println("Time for inserting " + numberOfRow + " rows: " + deltaTime(start, end) + " ms.");

        /*Либо через
        *   long endTime = System.nanoTime();
        *   long duration = endTime - startTime;
        *   duration = TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS);
        */

        connection.close();


    }

    public static long deltaTime(long start, long end) {
        long delta = (end - start) / 1000000;
        return delta;
    }
}