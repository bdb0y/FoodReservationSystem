package com.dev.foodreservation.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection implements Database{

    private final static String URL = "jdbc:jtds:sqlserver://DESKTOP-142OO4N:1433/MealAutomation;instance=SQLEXPRESS";
    private final static String USERNAME = "DESKTOP-142OO4N\\amin";
    private final static String PASSWORD = "*#qkedyisnotmine#*";
    private Connection connection;
    @Override
    public Connection connect() throws SQLException {
        connection = DriverManager.getConnection(URL);
        return connection;
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
        connection = null;
    }
}
