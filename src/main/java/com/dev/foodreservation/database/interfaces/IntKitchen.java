package com.dev.foodreservation.database.interfaces;

import com.dev.foodreservation.objects.Kitchen;

import java.sql.SQLException;
import java.util.List;

public interface IntKitchen {

    int create(String name, byte kitchenType) throws SQLException;

    int remove(int id) throws SQLException;

    int update(Kitchen kitchen) throws SQLException;

    boolean addStudentKitchen(long studentRollId,
                              int kitchenId)
            throws SQLException;

    boolean removeStudentKitchen(int... kitchenId)
            throws SQLException;

    List<Kitchen> idGet(int id) throws SQLException;

    List<Kitchen> nameGet(String name) throws SQLException;

    List<Kitchen> typeGet(int type) throws SQLException;

    List<Kitchen> getStudentKitchen(long rollId)
            throws SQLException;
}