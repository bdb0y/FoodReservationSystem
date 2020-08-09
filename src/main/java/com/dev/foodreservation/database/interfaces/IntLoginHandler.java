package com.dev.foodreservation.database.interfaces;

import java.sql.SQLException;

public interface IntLoginHandler {

    boolean adminLogin(String user, String password)
            throws SQLException;

    boolean studentLogin(long user, String pass)
            throws SQLException;
}
