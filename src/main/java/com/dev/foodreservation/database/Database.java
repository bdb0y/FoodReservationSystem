package com.dev.foodreservation.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {

    Connection connect() throws SQLException;
    boolean isConnected();
    void close() throws SQLException;
}
