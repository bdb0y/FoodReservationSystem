package com.dev.foodreservation.database.utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {
    Statement statement;

    public Executor(Statement statement) {
        this.statement = statement;
    }

    public int ExecuteUpdate(Procedure procedure) throws SQLException {
        return this.statement.executeUpdate(procedure.get());
    }

    public ResultSet Execute(Procedure procedure) throws SQLException {
        return this.statement.executeQuery(procedure.get());
    }
}
