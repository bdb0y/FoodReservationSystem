package com.dev.foodreservation.database;

import com.dev.foodreservation.database.interfaces.IntLoginHandler;
import com.dev.foodreservation.database.utilities.Executor;
import com.dev.foodreservation.database.utilities.Procedure;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginHandler implements IntLoginHandler {

    private final Connection connection;
    private final Statement statement;

    public LoginHandler() throws SQLException {
        this.connection = new DatabaseConnection().connect();
        this.statement = connection.createStatement();
    }

    @Override
    public boolean adminLogin(String user, String password)
            throws SQLException {
        Procedure procedure = new Procedure("AdminLogin");
        procedure.addField("username", "'" + user + "'");
        procedure.addField("pass", "'" + password + "'");
        ResultSet resultSet = new Executor(statement).Execute(procedure);
        int index = 0;
        while (resultSet.next()) ++index;
        return index > 0;
    }

    @Override
    public boolean studentLogin(long user, String pass)
            throws SQLException {
        Procedure procedure = new Procedure("StudentLogin");
        procedure.addField("id", user);
        procedure.addField("pass", "'" + pass + "'");
        ResultSet resultSet = new Executor(statement).Execute(procedure);
        int index = 0;
        while (resultSet.next()) ++index;
        return index > 0;
    }
}
