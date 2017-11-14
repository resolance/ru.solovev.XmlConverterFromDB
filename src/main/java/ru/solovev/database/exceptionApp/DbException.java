package ru.solovev.database.exceptionApp;

/**
 *
 *  @author res
 */

public class DbException extends Exception {
    public DbException(String message) {
        super(message);
    }

    public DbException(String message, Throwable cause) {
        super(message, cause);
    }

}
