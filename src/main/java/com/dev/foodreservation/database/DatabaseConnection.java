package com.dev.foodreservation.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection implements Database {

    private final static String URL = DatabaseConstants.URL;
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
