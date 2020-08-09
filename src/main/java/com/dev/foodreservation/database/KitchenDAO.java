package com.dev.foodreservation.database;

import com.dev.foodreservation.database.interfaces.IntKitchen;
import com.dev.foodreservation.database.utilities.Executor;
import com.dev.foodreservation.database.utilities.Procedure;
import com.dev.foodreservation.objects.Kitchen;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class KitchenDAO implements IntKitchen {

    private Connection connection;
    private Statement statement;

    public KitchenDAO() throws SQLException {
        connection = new DatabaseConnection().connect();
        statement = connection.createStatement();
    }


    @Override
    public int create(String name, byte kitchenType) throws SQLException {
        Procedure procedure = new Procedure("SaveKitchen");
        procedure.addField("n", "'" + name + "'");
        procedure.addField("kt", kitchenType);
        return new Executor(statement).ExecuteUpdate(procedure);
    }

    @Override
    public int remove(int id) throws SQLException {
        Procedure procedure = new Procedure("RemoveKitchen");
        procedure.addField("id", id);
        return new Executor(statement).ExecuteUpdate(procedure);
    }

    @Override
    public int update(Kitchen kitchen) throws SQLException {
        Procedure procedure = new Procedure("UpdateKitchen");
        procedure.addField("id", kitchen.getId());
        procedure.addField("n", "'" + kitchen.getName() + "'");
        procedure.addField("kt", kitchen.getKitchenType());
        return new Executor(statement).ExecuteUpdate(procedure);
    }

    @Override
    public boolean addStudentKitchen(long studentRollId, int kitchenId) throws SQLException {
        Procedure procedure =
                new Procedure("InsertStudentKitchen");
        procedure.addField("sri", studentRollId);
        procedure.addField("ki", kitchenId);
        return new Executor(statement).ExecuteUpdate(procedure) > 0;
    }

    @Override
    public boolean removeStudentKitchen(int... kitchenId)
            throws SQLException {
        Procedure procedure = new Procedure("RemoveStudentKitchen");
        for (int id : kitchenId) {
            procedure.addField("id", id);
            new Executor(statement).ExecuteUpdate(procedure);
        }
        return true;
    }

    @Override
    public List<Kitchen> idGet(int id) throws SQLException {
        Procedure procedure = new Procedure("GetKitchen");
        procedure.addField("id", id);
        List<Kitchen> kitchens = new ArrayList<>();
        ResultSet resultSet = new Executor(statement).Execute(procedure);

        while (resultSet.next()) kitchens.add(kitchenRSV(resultSet));
        return kitchens;
    }

    @Override
    public List<Kitchen> nameGet(String name) throws SQLException {
        Procedure procedure = new Procedure("GetNameKitchen");
        procedure.addField("n", name);
        List<Kitchen> kitchens = new ArrayList<>();
        ResultSet resultSet = new Executor(statement).Execute(procedure);

        while (resultSet.next()) kitchens.add(kitchenRSV(resultSet));
        return kitchens;
    }

    @Override
    public List<Kitchen> typeGet(int type) throws SQLException {
        Procedure procedure = new Procedure("GetKitchenMeal");
        procedure.addField("type", type);
        List<Kitchen> kitchens = new ArrayList<>();
        ResultSet resultSet = new Executor(statement).Execute(procedure);

        while (resultSet.next()) kitchens.add(kitchenRSV(resultSet));
        return kitchens;
    }

    @Override
    public List<Kitchen> getStudentKitchen(long rollId)
            throws SQLException {
        Procedure procedure = new Procedure("GetStudentKitchen");
        procedure.addField("id", rollId);
        List<Kitchen> kitchens = new ArrayList<>();
        ResultSet resultSet = new Executor(statement)
                .Execute(procedure);
        while (resultSet.next()) kitchens.add(kitchenRSV(resultSet));
        return kitchens;
    }

    private Kitchen kitchenRSV(ResultSet resultSet)
            throws SQLException {
        return new Kitchen(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getByte(3)
        );
    }
}